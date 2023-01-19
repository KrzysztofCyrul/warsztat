package com.kris.warsztat.controller.mainwindowcontrollers;

import com.kris.warsztat.dao.UserDao;
import com.kris.warsztat.helpers.CurrentUser;
import com.kris.warsztat.model.user;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label infoLine;

    @FXML
    private Label welcome;

    @FXML
    private Button exitBtn;

    UserDao userDao = new UserDao();

    @FXML
    private void initialize() {
    close();
}

    @FXML
    private void loginUser(ActionEvent event) throws IOException, InterruptedException {
        String user = username.getText();
        String pass = password.getText();

        if(!validFields()) {
            infoLine.setText("Login i Hasło nie mogą być puste");
            return;
        }

        if (!validateLogin()) {
            infoLine.setText("Użytkownik nie znaleziony");
            return;
        }

        welcome.setText("Witaj, " + CurrentUser.getCurrentUser().getUserName() + "!");
        infoLine.setText("Przekierowanie do bazy");

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished( event2 -> {
            try {
                SceneController.getMainScene(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }

    boolean validFields() {
        return !username.getText().isEmpty() && !password.getText().isEmpty();
    }

    private boolean validateLogin() {
        user user = userDao.getConnectedUser(username.getText(), password.getText());
        if (user == null) {
            return false;
        }
        CurrentUser.setCurrentUser(user);
        return true;
    }

    private void close() {
        exitBtn.setOnAction(SceneController::close);
    }


}
