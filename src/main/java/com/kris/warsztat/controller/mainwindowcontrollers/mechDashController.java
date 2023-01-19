package com.kris.warsztat.controller.mainwindowcontrollers;

import com.kris.warsztat.controller.popupwindowcontrollers.NewWindowController;
import com.kris.warsztat.dao.mechDao;
import com.kris.warsztat.helpers.CurrentTime;
import com.kris.warsztat.helpers.CurrentUser;
import com.kris.warsztat.helpers.SceneName;
import com.kris.warsztat.helpers.UpdateStatus;
import com.kris.warsztat.model.mechanik;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.time.LocalDate;

public class mechDashController {

    @FXML
    private Label title;

    @FXML
    private Label date;

    @FXML
    private Label stats;

    @FXML
    private Label updateTime;

    @FXML
    private Button exitBtn;

    @FXML
    private Label userInfo;

    @FXML
    private TextField searchBar;

    @FXML
    private TableView<mechanik> mechsTable;

    @FXML
    private TableColumn<mechanik, Long> idColumn;

    @FXML
    private TableColumn<mechanik, String> nameColumn;

    @FXML
    private TableColumn<mechanik, String> lastNameColumn;

    @FXML
    private TableColumn<mechanik, String> specialityColumn;

    @FXML
    private TableColumn<mechanik, String> addressColumn;

    mechDao mechDao = new mechDao();
    ObservableList<mechanik> mechsObList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        setTexts();
        setObList();
        fillTable();
        addTableSettings();
        exitBtn.setOnAction(SceneController::close);
    }

    private void setTexts() {
        title.setText(SceneName.MECHS.getName());
        date.setText(LocalDate.now().toString());
        updateTime.setText("Ostatnia aktualizacja: " + CurrentTime.getTime());
        setDbInfo();
        setUserInfo();
    }

    private void setObList() {
        mechsObList.clear();
        mechsObList.addAll(mechDao.getmechs());
    }

    private void fillTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        specialityColumn.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        specialityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void addTableSettings() {
        mechsTable.setEditable(true);
        mechsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        mechsTable.setItems(getSortedList());
    }

    private SortedList<mechanik> getSortedList() {
        SortedList<mechanik> sortedList = new SortedList<>(getFilteredList());
        sortedList.comparatorProperty().bind(mechsTable.comparatorProperty());
        return sortedList;
    }

    private FilteredList<mechanik> getFilteredList() {
        FilteredList<mechanik> filteredList = new FilteredList<>(mechsObList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(mechanik -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (mechanik.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (mechanik.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (mechanik.getSpeciality().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (mechanik.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else return mechanik.getId().toString().contains(lowerCaseFilter);
                }));
        return filteredList;
    }

    @FXML
    private void changeNameCell(TableColumn.CellEditEvent<mechanik, String> editEvent) {
        mechanik selectedmech = mechsTable.getSelectionModel().getSelectedItem();
        selectedmech.setFirstName(editEvent.getNewValue());
        mechDao.updatemech(selectedmech);
    }

    @FXML
    private void changeLastNameCell(TableColumn.CellEditEvent<mechanik, String> editEvent) {
        mechanik selectedmech = mechsTable.getSelectionModel().getSelectedItem();
        selectedmech.setLastName(editEvent.getNewValue());
        mechDao.updatemech(selectedmech);
    }

    @FXML
    private void changeSpecCell(TableColumn.CellEditEvent<mechanik, String> editEvent) {
        mechanik selectedmech = mechsTable.getSelectionModel().getSelectedItem();
        selectedmech.setSpeciality(editEvent.getNewValue());
        mechDao.updatemech(selectedmech);
    }

    @FXML
    private void changeAddressCell(TableColumn.CellEditEvent<mechanik, String> editEvent) {
        mechanik selectedmech = mechsTable.getSelectionModel().getSelectedItem();
        selectedmech.setAddress(editEvent.getNewValue());
        mechDao.updatemech(selectedmech);
    }

    @FXML
    private void newWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewmechWindow();
        if(UpdateStatus.ismechAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsMechAdded(false);
        }
    }

    @FXML
    void deletemechs(ActionEvent event) throws IOException {
        ObservableList<mechanik> selectedRows = mechsTable.getSelectionModel().getSelectedItems();
        for (mechanik mech : selectedRows) {
            mechDao.deletemech(mech);
        }
        refreshScreen(event);
    }

    private void setUserInfo() {
        userInfo.setText(String.format("Użytkownik: %s", CurrentUser.getCurrentUser().getUserName()));
    }

    private void setDbInfo() {
        stats.setText(String.format("Liczba mechaników w bazie: %s", mechDao.getmechsNumber()));
    }

    @FXML
    void showDashboard(ActionEvent event) throws IOException {
        SceneController.getMainScene(event);
    }

    @FXML
    void showVisitScreen(ActionEvent event) throws IOException {
        SceneController.getVisitScene(event);
    }

    @FXML
    void showPojazdScreen(ActionEvent event) throws IOException {
        SceneController.getpojazdyScene(event);
    }

    @FXML
    void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getmechsScene(event);
    }
}
