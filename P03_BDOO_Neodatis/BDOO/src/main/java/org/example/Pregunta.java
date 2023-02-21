package org.example;

import java.util.ArrayList;

public class Pregunta {
    String text;
    ArrayList<Opcion> options;
    String difficulty;

    public Pregunta() {

    }

    public Pregunta(String text, ArrayList<Opcion> options, String difficulty) {
        this.text = text;
        this.options = options;
        this.difficulty = difficulty;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Opcion> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Opcion> options) {
        this.options = options;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
