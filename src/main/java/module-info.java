module pojazdWarsztat {
    requires javafx.graphics;
    requires org.hibernate.orm.core;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    requires java.persistence;
    requires static lombok;
    requires java.sql;
    requires java.naming;


    exports com.kris.warsztat to  javafx.graphics;
    opens com.kris.warsztat.model to  org.hibernate.orm.core, javafx.base;
    exports com.kris.warsztat.controller.mainwindowcontrollers to  javafx.fxml;
    opens com.kris.warsztat.controller.mainwindowcontrollers to  javafx.fxml;
    exports com.kris.warsztat.controller.popupwindowcontrollers to  javafx.fxml;
    opens com.kris.warsztat.controller.popupwindowcontrollers to  javafx.fxml;
}