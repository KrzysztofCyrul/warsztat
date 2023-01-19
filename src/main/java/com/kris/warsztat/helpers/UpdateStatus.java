package com.kris.warsztat.helpers;

public final class UpdateStatus {

    private UpdateStatus() {

    }

    private static boolean isPojazdAdded;
    private static boolean isMechAdded;
    private static boolean isVisitAdded;

    public static boolean ismechAdded() {
        return isMechAdded;
    }

    public static void setIsMechAdded(boolean isMechAdded) {
        UpdateStatus.isMechAdded = isMechAdded;
    }

    public static boolean isVisitAdded() {
        return isVisitAdded;
    }

    public static void setIsVisitAdded(boolean isVisitAdded) {
        UpdateStatus.isVisitAdded = isVisitAdded;
    }

    public static boolean ispojazdAdded() {
        return isPojazdAdded;
    }

    public static void setIsPojazdAdded(boolean ispojazd) {
        UpdateStatus.isPojazdAdded = ispojazd;
    }
}
