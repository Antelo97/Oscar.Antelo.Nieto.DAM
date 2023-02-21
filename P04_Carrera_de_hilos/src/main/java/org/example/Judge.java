package org.example;

import javax.swing.*;

public class Judge {
    static String stPlace = "";
    static String ndPlace = "";
    static String rdPlace = "";

    static void assignPlace(String name) {
        if (stPlace == "" && ndPlace == "" && rdPlace == "") {
            stPlace = name;
        } else if (stPlace != "" && ndPlace == "" && rdPlace == "") {
            ndPlace = name;
        } else {
            rdPlace = name;
            endRace();
        }
    }

    static void endRace() {
        String msg = "● GANADOR ➔ " + stPlace +
                "\n● SEGUNDO ➔ " + ndPlace +
                "\n● TERCERO ➔ " + rdPlace;
        JOptionPane.showMessageDialog(null, msg);

        enableComponents();
    }

    static void enableComponents() {
        Panel.btnStartRace.setEnabled(true);
        Panel.btnRandomizePriorities.setEnabled(true);
        Panel.btnResetPriorities.setEnabled(true);
        Panel.js1.setEnabled(true);
        Panel.js2.setEnabled(true);
        Panel.js3.setEnabled(true);
        Panel.jpb1.setValue(0);
        Panel.jpb2.setValue(0);
        Panel.jpb3.setValue(0);
        Panel.jtf1.setText("0");
        Panel.jtf2.setText("0");
        Panel.jtf3.setText("0");
        Panel.lblProgressPercent1.setText("0%");
        Panel.lblProgressPercent2.setText("0%");
        Panel.lblProgressPercent3.setText("0%");
        Judge.stPlace = "";
        Judge.ndPlace = "";
        Judge.rdPlace = "";
    }
}
