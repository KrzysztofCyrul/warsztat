package com.kris.warsztat.controller.popupwindowcontrollers;

import com.kris.warsztat.controller.mainwindowcontrollers.SceneController;
import com.kris.warsztat.dao.pojazdDao;
import com.kris.warsztat.helpers.UpdateStatus;
import com.kris.warsztat.model.pojazd;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;

public class AddpojazdController {

    @FXML
    private TextField pojazdRejestracja;

    @FXML
    private DatePicker pojazdPrzegladDate;

    @FXML
    private TextArea ownerInfo;

    @FXML
    private CheckBox isFaktura;

    @FXML
    private Label alertText;

    @FXML
    private void createNewpojazd(ActionEvent event) throws IOException {
        if (validateInputs()) {
            pojazd pojazd = createpojazdFromInput();
            boolean isSaved = new pojazdDao().createpojazd(pojazd);
            if (isSaved) {
                UpdateStatus.setIsPojazdAdded(true);
                alertText.setText("Pojazd dodany");
                delayWindowClose(event);
            }
        }
    }

    private pojazd createpojazdFromInput() {
        pojazd pojazd = new pojazd();
        pojazd.setRejestracja(pojazdRejestracja.getText());
        pojazd.setPrzegladDate(pojazdPrzegladDate.getValue());
        pojazd.setOwnerName(ownerInfo.getText());
        pojazd.setIsFaktura(isFaktura.isSelected());
        return pojazd;
    }

    private boolean validateInputs() {
        if (pojazdRejestracja.getText().equals("")) {
            alertText.setText("*Musisz dodać rejestrację pojazdu!");
            return false;
        }

        if (pojazdPrzegladDate.getValue() == null) {
            alertText.setText("*Musisz wybrać datę przeglądu!");
            return false;
        }

        if (ownerInfo.getText().equals("")) {
            alertText.setText("*Musisz dodac informację o pojeździe");
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
        SceneController.close(event);
    }
}