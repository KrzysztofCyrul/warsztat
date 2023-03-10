package com.kris.warsztat.controller.mainwindowcontrollers;

import com.kris.warsztat.helpers.ScenePath;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SceneController {

    private static double x;
    private static double y;

    private static Parent main;

    public static void getInitialScene(Stage stage) throws IOException {
        main = FXMLLoader.load((SceneController.class.getResource(ScenePath.LOGIN.getPath())));
        Scene scene = new Scene(main);
        controlDrag(stage);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Warsztat IN-Cars");
        stage.setScene(scene);
        stage.show();
    }

    public static void getMainScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.HOME.getPath());
    }

    public static void getVisitScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.VISITS.getPath());
    }

    public static void getmechsScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.MECHS.getPath());
    }

    public static void getpojazdyScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.POJAZDY.getPath());
    }



    private static void changeScreen(ActionEvent event, String path) throws IOException {
        main = FXMLLoader.load(SceneController.class.getResource(path));
        Scene visitScene = new Scene(main);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controlDrag(window);
        window.setScene(visitScene);
        window.show();

    }

    public static void controlDrag(Stage stage) {
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

    public static void close(ActionEvent actionEvent) {
        Node  source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
