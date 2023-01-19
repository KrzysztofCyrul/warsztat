package com.kris.warsztat.helpers;

public enum ScenePath {

    HOME("/view/dashboard.fxml"),
    VISITS("/view/visitDash.fxml"),
    MECHS("/view/mechsDash.fxml"),
    POJAZDY("/view/pojazdDash.fxml"),
    ADD_pojazd("/view/addpojazd.fxml"),
    ADD_mech("/view/addmech.fxml"),
    ADD_VISIT("/view/addVisit.fxml"),
    LOGIN("/view/login.fxml")
    ;

    private final String path;

    ScenePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
