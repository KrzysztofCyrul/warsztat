package com.kris.warsztat.controller.popupwindowcontrollers;

import com.kris.warsztat.controller.mainwindowcontrollers.SceneController;
import com.kris.warsztat.dao.mechDao;
import com.kris.warsztat.helpers.UpdateStatus;
import com.kris.warsztat.model.mechanik;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;

public class AddmechController {

    @FXML
    private TextField mechFirstName;

    @FXML
    private TextField mechLastName;

    @FXML
    private TextField mechSpeciality;

    @FXML
    private TextField mechAddress;

    @FXML
    private Label alertText;

    @FXML
    private void saveNewmechToDb(ActionEvent event) throws IOException {
        if (validateInputs()) {
            mechanik mech = createmechFromInput();
            boolean isSaved = new mechDao().createmech(mech);
            if (isSaved) {
                UpdateStatus.setIsMechAdded(true);
                alertText.setText("Mechanik dodany!");
                delayWindowClose(event);
            }
        }
    }

    private boolean validateInputs() {
        if (mechFirstName.getText().equals("")) {
            alertText.setText("*Podaj imię!");
            return false;
        }

        if (mechLastName.getText().equals("")) {
            alertText.setText("*Podaj nazwisko!");
            return false;
        }

        if (mechSpeciality.getText().equals("")) {
            alertText.setText("*Podaj specjalność");
            return false;
        }

        if (mechAddress.getText().equals("")) {
            alertText.setText("*Podaj adres");
            return false;
        }
        return true;
    }

    private mechanik createmechFromInput() {
        mechanik mech = new mechanik();
        mech.setFirstName(mechFirstName.getText());
        mech.setLastName(mechLastName.getText());
        mech.setSpeciality(mechSpeciality.getText());
        mech.setAddress(mechAddress.getText());
        return mech;
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
