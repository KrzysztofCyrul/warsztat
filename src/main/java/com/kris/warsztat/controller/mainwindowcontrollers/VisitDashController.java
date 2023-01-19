package com.kris.warsztat.controller.mainwindowcontrollers;

import com.kris.warsztat.controller.popupwindowcontrollers.NewWindowController;
import com.kris.warsztat.dao.ConsultDao;
import com.kris.warsztat.helpers.CurrentTime;
import com.kris.warsztat.helpers.CurrentUser;
import com.kris.warsztat.helpers.SceneName;
import com.kris.warsztat.helpers.UpdateStatus;
import com.kris.warsztat.model.consult;
import javafx.beans.binding.Bindings;
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
public class VisitDashController {

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
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private Button clearBtn;

    @FXML
    private TableView<consult> visitTable;

    @FXML
    private TableColumn<consult, Long> visitId;

    @FXML
    private TableColumn<consult, LocalDate> visitDate;

    @FXML
    private TableColumn<consult, String> pojazdId;

    @FXML
    private TableColumn<consult, String> mechId;

    @FXML
    private TableColumn<consult, String> descriptionId;

    ConsultDao consultDao = new ConsultDao();
    ObservableList<consult> visitObList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        setTexts();
        setObList();
        fillTable();
        addTableSettings();
        clearSearchResults();
        exitBtn.setOnAction(SceneController::close);
    }

    private void setTexts() {
        date.setText(LocalDate.now().toString());
        title.setText(SceneName.VISITS.getName());
        updateTime.setText("Ostatnia aktualizacja: " + CurrentTime.getTime());
        setDbInfo();
        setUserInfo();
    }

    private void setObList() {
        visitObList.clear();
        visitObList.addAll(consultDao.getConsults());
    }

    private void fillTable() {
        visitId.setCellValueFactory(new PropertyValueFactory<>("id"));
        visitDate.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
        pojazdId.setCellValueFactory(new PropertyValueFactory<>("pojazd"));
        mechId.setCellValueFactory(new PropertyValueFactory<>("mechanik"));
        descriptionId.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionId.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void addTableSettings() {
        visitTable.setEditable(true);
        visitTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        visitTable.setItems(getSortedList());
    }

    private SortedList<consult> getSortedList() {
        SortedList<consult> sortedList = new SortedList<>(getFilteredListByDates());
        sortedList.comparatorProperty().bind(visitTable.comparatorProperty());
        return sortedList;
    }

    @FXML
    private FilteredList<consult> getFilteredListByDates() {
        FilteredList<consult> filteredItems = new FilteredList<>(getFilteredListByString());

        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> {
                    LocalDate minDate = dateFrom.getValue();
                    LocalDate maxDate = dateTo.getValue();

                    final LocalDate finalMin = minDate == null ? LocalDate.MIN : minDate;
                    final LocalDate finalMax = maxDate == null ? LocalDate.MAX : maxDate;

                    return ti -> !finalMin.isAfter(ti.getVisitDate()) && !finalMax.isBefore(ti.getVisitDate());
                },
                dateFrom.valueProperty(),
                dateTo.valueProperty()));
        return filteredItems;
    }

    private FilteredList<consult> getFilteredListByString() {
        FilteredList<consult> filteredList = new FilteredList<>(visitObList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(consult -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (consult.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (consult.getPojazd().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (consult.getMechanik().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else return consult.getId().toString().contains(lowerCaseFilter);
                }));
        return filteredList;
    }

    private void clearSearchResults() {
        clearBtn.setOnAction(event -> {
            dateFrom.setValue(null);
            dateTo.setValue(null);
            searchBar.setText("");
            visitTable.setItems(getSortedList());
        });
    }

    @FXML
    private void changeDescriptionCell(TableColumn.CellEditEvent editEvent) {
        consult consult = visitTable.getSelectionModel().getSelectedItem();
        consult.setDescription(editEvent.getNewValue().toString());
        consultDao.updateConsult(consult);
    }

    @FXML
    void deleteVisit(ActionEvent event) throws IOException {
        ObservableList<consult> selectedRows = visitTable.getSelectionModel().getSelectedItems();
        for (com.kris.warsztat.model.consult consult : selectedRows) {
            consultDao.deleteConsult(consult);
        }
        refreshWindow(event);
    }

    @FXML
    private void newWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewVisitWindow();
        if (UpdateStatus.isVisitAdded()) {
            refreshWindow(event);
            UpdateStatus.setIsVisitAdded(false);
        }
    }

    private void setUserInfo() {
        userInfo.setText(String.format("Użytkownik: %s", CurrentUser.getCurrentUser().getUserName()));
    }

    private void setDbInfo() {
        stats.setText(String.format("Liczba wizyt w bazie: %s", consultDao.getNumberOfVisits()));
    }

    @FXML
    void refreshWindow(ActionEvent event) throws IOException {
        SceneController.getVisitScene(event);
    }

    @FXML
    void showpojazdyScreen(ActionEvent event) throws IOException {
        SceneController.getpojazdyScene(event);
    }

    @FXML
    void showmechsScreen(ActionEvent event) throws IOException {
        SceneController.getmechsScene(event);
    }

    @FXML
    void showDashboard(ActionEvent event) throws IOException {
        SceneController.getMainScene(event);
    }
}