package org.example;

import javax.swing.*;

@SuppressWarnings("serial")
public class Window extends JFrame {
    static int widthWindow = 700;
    static int heightWindow = 700;

    public void buildWindow() {

        setTitle("Carrera de Hilos");
        setSize(widthWindow, heightWindow);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new Panel());

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
