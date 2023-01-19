package com.kris.warsztat.controller.popupwindowcontrollers;

import com.kris.warsztat.dao.ConsultDao;
import com.kris.warsztat.dao.mechDao;
import com.kris.warsztat.dao.pojazdDao;
import com.kris.warsztat.helpers.UpdateStatus;
import com.kris.warsztat.model.consult;
import com.kris.warsztat.model.mechanik;
import com.kris.warsztat.model.pojazd;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AddVisitController {

    @FXML
    private DatePicker visitDate;

    @FXML
    private TextArea description;

    @FXML
    private Label alertText;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<pojazd> selectpojazd;

    @FXML
    private ComboBox<mechanik> selectmech;

    @FXML
    private void initialize() {
        selectpojazd.setItems(getpojazdObservableList());
        selectmech.setItems(getmechObservableList());
    }

    private ObservableList<pojazd> getpojazdObservableList() {
        ObservableList<pojazd> list = FXCollections.observableArrayList();
        list.addAll(new pojazdDao().getpojazdy());
        return list;
    }

    private ObservableList<mechanik> getmechObservableList() {
        ObservableList<mechanik> list = FXCollections.observableArrayList();
        list.addAll(new mechDao().getmechs());
        return list;
    }

    @FXML
    private void saveNewVisitToDb(ActionEvent event) {
        if(validateInputs()) {
            consult consult = createConsultFromInput();
            boolean isSaved = new ConsultDao().createConsult(consult);
            if (isSaved) {
                UpdateStatus.setIsVisitAdded(true);
                alertText.setText("Wizyta dodana");
                delayWindowClose(event);
            }
        }
    }

    private consult createConsultFromInput() {
        consult visit = new consult();
        visit.setVisitDate(visitDate.getValue());
        visit.setPojazd(selectpojazd.getValue());
        visit.setMechanik(selectmech.getValue());
        visit.setDescription(description.getText());
        return visit;
    }

    private boolean validateInputs() {

        if (visitDate.getValue() == null) {
            alertText.setText("Data musi być wybrana z kalendarza!");
            return false;
        }

        if(selectpojazd.getValue() == null) {
            alertText.setText("*Wybierz pojazd");
            return false;
        }

        if(selectmech.getValue() == null) {
            alertText.setText("*Wybierz mechanika");
            return false;
        }

        if (description.getText().equals("")) {
            alertText.setText("*Dodaj opis!");
            return false;
        }
        return true;
    }

    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}