package com.kris.warsztat.helpers;

public enum SceneName {
    DASHBOARD("STRONA GŁÓWNA"),
    VISITS("WIZYTY KLIENTÓW"),
    MECHS("MECHANICY"),
    POJAZDY("BAZA POJAZDÓW"),

    ;

    private final String name;

    SceneName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
