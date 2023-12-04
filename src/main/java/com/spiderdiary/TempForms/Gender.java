package com.spiderdiary.TempForms;

public enum Gender {
    MALE("Samiec"),
    FEMALE("Samica"),
    NOT_SPECIFIED("Nieznana");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


}