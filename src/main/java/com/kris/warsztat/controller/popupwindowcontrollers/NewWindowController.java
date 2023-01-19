package com.kris.warsztat.controller.popupwindowcontrollers;

import com.kris.warsztat.helpers.ScenePath;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class NewWindowController {

    static double x;
    static double y;

    public static void getNewpojazdWindow() throws IOException {
        getPopUpWindow(ScenePath.ADD_pojazd.getPath());
    }

    public static Stage getNewmechWindow() throws IOException {
        Stage stage = new Stage();
        Pane main = FXMLLoader.load(NewWindowController.class.getResource(ScenePath.ADD_mech.getPath()));
        controlDrag(main, stage);
        stage.setScene(new Scene(main));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Warsztat IN-CARS");
        stage.getScene();
        stage.showAndWait();
        return stage;
    }

    public static void getNewVisitWindow() throws IOException {
        getPopUpWindow(ScenePath.ADD_VISIT.getPath());
    }

    public static void getPopUpWindow(String path) throws IOException {
        Stage stage = new Stage();
        Pane main = FXMLLoader.load(NewWindowController.class.getResource(path));
        controlDrag(main, stage);
        stage.setScene(new Scene(main));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Warsztat IN-CARS");
        stage.getScene();
        stage.showAndWait();

    }

    public static void controlDrag(Pane main, Stage stage) {
        main.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x = stage.getX() - event.getScreenX();
                y = stage.getY() - event.getScreenY();
            }
        });
        main.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + x);
                stage.setY(event.getScreenY() + y);
            }
        });
    }
}
