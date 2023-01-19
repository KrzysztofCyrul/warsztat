package com.kris.warsztat.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTime {

    public static String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

}
