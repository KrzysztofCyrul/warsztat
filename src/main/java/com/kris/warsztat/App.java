package com.kris.warsztat;

import com.kris.warsztat.controller.mainwindowcontrollers.SceneController;
import com.kris.warsztat.util.HibernateUtil;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.hibernate.Session;

public class App extends Application {

    public static void main(String[] args) {
        App.launch();
    }

    @Override
    public void init() throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.close();
    }

    @Override
    public void start(Stage stage) throws Exception {
        SceneController.getInitialScene(stage);
        stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        HibernateUtil.shutdown();
    }
}