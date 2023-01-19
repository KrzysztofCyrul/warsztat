package com.kris.warsztat.controller.mainwindowcontrollers;

import com.kris.warsztat.controller.popupwindowcontrollers.NewWindowController;
import com.kris.warsztat.dao.pojazdDao;
import com.kris.warsztat.helpers.CurrentTime;
import com.kris.warsztat.helpers.CurrentUser;
import com.kris.warsztat.helpers.SceneName;
import com.kris.warsztat.helpers.UpdateStatus;
import com.kris.warsztat.model.pojazd;
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

public class pojazdDashController {

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
    private TableView<pojazd> pojazdTable;

    @FXML
    private TableColumn<pojazd, Long> idColumn;

    @FXML
    private TableColumn<pojazd, LocalDate> dateColumn;

    @FXML
    private TableColumn<pojazd, String> typeColumn;

    @FXML
    private TableColumn<pojazd, Boolean> fakturaColumn;

    @FXML
    private TableColumn<pojazd, String> ownerColumn;

    pojazdDao pojazdDao = new pojazdDao();
    ObservableList<pojazd> pojazdyObList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        setTexts();
        setObList();
        fillTable();
        addTableSettings();
        exitBtn.setOnAction(SceneController::close);
    }

    private void setTexts() {
        title.setText(SceneName.POJAZDY.getName());
        date.setText(LocalDate.now().toString());
        updateTime.setText("Ostatnia aktualizacja: " + CurrentTime.getTime());
        setDbInfo();
        setUserInfo();
    }

    private void setObList() {
        pojazdyObList.clear();
        pojazdyObList.addAll(pojazdDao.getpojazdy());
    }

    private void fillTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("przegladDate"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("rejestracja"));
        fakturaColumn.setCellValueFactory(new PropertyValueFactory<>("isFaktura"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        ownerColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fakturaColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "Tak" : "Nie");
            }
        });
    }

    private void addTableSettings() {
        pojazdTable.setEditable(true);
        pojazdTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        pojazdTable.setItems(getSortedList());
    }

    private SortedList<pojazd> getSortedList() {
        SortedList<pojazd> sortedList = new SortedList<>(getFilteredList());
        sortedList.comparatorProperty().bind(pojazdTable.comparatorProperty());
        return sortedList;
    }

    private FilteredList<pojazd> getFilteredList() {
        FilteredList<pojazd> filteredList = new FilteredList<>(pojazdyObList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(pojazd -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (pojazd.getRejestracja().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (pojazd.isFakturaStringValue().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (pojazd.getPrzegladDate().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (pojazd.getOwnerName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else return pojazd.getId().toString().contains(lowerCaseFilter);
                }));
        return filteredList;
    }

    @FXML
    private void changeOwnerCell(TableColumn.CellEditEvent<pojazd, String> editEvent) {
        pojazd selectedpojazd = pojazdTable.getSelectionModel().getSelectedItem();
        selectedpojazd.setOwnerName(editEvent.getNewValue());
        pojazdDao.updatepojazd(selectedpojazd);
    }

    @FXML
    private void changeTypeCell(TableColumn.CellEditEvent<pojazd, String> editEvent) {
        pojazd selectedpojazd = pojazdTable.getSelectionModel().getSelectedItem();
        selectedpojazd.setRejestracja(editEvent.getNewValue());
        pojazdDao.updatepojazd(selectedpojazd);
    }

    @FXML
    private void deletepojazdy(ActionEvent event) throws Exception {
        ObservableList<pojazd> selectedRows = pojazdTable.getSelectionModel().getSelectedItems();
        for (pojazd pojazd : selectedRows) {
            pojazdDao.deletepojazd(pojazd);
        }
        refreshScreen(event);
    }

    @FXML
    private void newWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewpojazdWindow();
        if(UpdateStatus.ispojazdAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsPojazdAdded(false);
        }
    }

    private void setUserInfo() {
        userInfo.setText(String.format("Użytkownik: %s", CurrentUser.getCurrentUser().getUserName()));
    }

    private void setDbInfo() {
        stats.setText(String.format("Liczba pojazdów w bazie: %s", pojazdDao.getNumberOfpojazdy()));
    }

    @FXML
    private void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getpojazdyScene(event);
    }

    @FXML
    private void showVisitScreen(ActionEvent event) throws IOException {
        SceneController.getVisitScene(event);
    }

    @FXML
    private void showMechScreen(ActionEvent event) throws IOException {
        SceneController.getmechsScene(event);
    }

    @FXML
    private void showDashboard(ActionEvent event) throws IOException {
        SceneController.getMainScene(event);
    }
}