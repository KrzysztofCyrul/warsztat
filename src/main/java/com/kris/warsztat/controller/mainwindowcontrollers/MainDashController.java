package com.kris.warsztat.controller.mainwindowcontrollers;

import com.kris.warsztat.dao.ConsultDao;
import com.kris.warsztat.dao.mechDao;
import com.kris.warsztat.dao.pojazdDao;
import com.kris.warsztat.helpers.CurrentTime;
import com.kris.warsztat.helpers.CurrentUser;
import com.kris.warsztat.helpers.SceneName;
import com.kris.warsztat.model.consult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;

public class MainDashController {

    @FXML
    private Label title;

    @FXML
    private Label date;

    @FXML
    private Label mechInfoBlockName;

    @FXML
    private Label mechNumber;

    @FXML
    private Label pojazdInfoBlockName;

    @FXML
    private Label pojazdNumber;

    @FXML
    private Label visitInfoBlockName;

    @FXML
    private Label visitNumber;

    @FXML
    private Label userInfo;

    @FXML
    private TableView<consult> visitTable;

    @FXML
    private TableColumn<consult, Long> visitId;

    @FXML
    private TableColumn<consult, Long> visitDate;

    @FXML
    private TableColumn<consult, String> pojazdId;

    @FXML
    private TableColumn<consult, String> mechId;

    @FXML
    private TableColumn<consult, String> descriptionId;

    @FXML
    private Label visitInfoText;

    ConsultDao consultDao = new ConsultDao();

    @FXML
    private void initialize() {
        setTexts();
        fillTableWithData();
    }

    private void setTexts() {
        title.setText(SceneName.DASHBOARD.getName());
        date.setText(LocalDate.now().toString());
        mechNumber.setText(getNumberOfmechs());
        pojazdNumber.setText(getNumberOfpojazdy());
        visitNumber.setText(getVisitNumber());
        visitInfoBlockName.setText("Nadchodzące wizyty");
        mechInfoBlockName.setText("Dostępni Mechanicy");
        pojazdInfoBlockName.setText("Pojazdy w bazie");
        visitInfoText.setText(getCalendarInformation());
        setUserInfo();
    }

    private void fillTableWithData() {
        visitId.setCellValueFactory(new PropertyValueFactory<>("id"));
        visitDate.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
        pojazdId.setCellValueFactory(new PropertyValueFactory<>("pojazd"));
        mechId.setCellValueFactory(new PropertyValueFactory<>("mechanik"));
        descriptionId.setCellValueFactory(new PropertyValueFactory<>("description"));
        visitTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        visitTable.setItems(getVisitObservableList());
    }

    private ObservableList<consult> getVisitObservableList() {
        ObservableList<consult> consults = FXCollections.observableArrayList();
        consults.addAll(consultDao.getConsultInterval());
        return consults;
    }

    private String getNumberOfmechs() {
        return String.valueOf(new mechDao().getmechsNumber());
    }

    private String getNumberOfpojazdy() {
        return String.valueOf(new pojazdDao().getNumberOfpojazdy());
    }

    private String getVisitNumber() {
        return String.valueOf(consultDao.getConsultInterval().size());
    }

    private void setUserInfo() {
        userInfo.setText(String.format("Użytkownik: %s", CurrentUser.getCurrentUser().getUserName()));
    }

    private String getCalendarInformation() {
        return String.format("Nadchodzące wizyty na 14 dni. Ostatnia aktualizacja: %s", CurrentTime.getTime());
    }

    @FXML
    void showVisitScreen(ActionEvent event) throws IOException {
        SceneController.getVisitScene(event);
    }

    @FXML
    void showMechScreen(ActionEvent event) throws IOException {
        SceneController.getmechsScene(event);
    }

    @FXML
    void showPojazdScreen(ActionEvent event) throws IOException {
        SceneController.getpojazdyScene(event);
    }

    @FXML
    void refreshWindow(ActionEvent event) throws IOException {
        SceneController.getMainScene(event);
    }

    @FXML
    void exitProgram(ActionEvent event) {
        SceneController.close(event);
    }
}
