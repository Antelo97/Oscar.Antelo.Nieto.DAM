package org.example;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

@SuppressWarnings("serial")
public class Panel extends JPanel implements ActionListener {

    static JButton btnStartRace;
    static JButton btnRandomizePriorities;
    static JButton btnResetPriorities;
    static JSlider js1;
    static JSlider js2;
    static JSlider js3;
    static JProgressBar jpb1;
    static JProgressBar jpb2;
    static JProgressBar jpb3;
    static JTextField jtf1;
    static JTextField jtf2;
    static JTextField jtf3;
    JLabel lblThread1;
    JLabel lblThread2;
    JLabel lblThread3;
    JLabel lblPriorities;
    JLabel lblPriority1;
    JLabel lblPriority2;
    JLabel lblPriority3;
    JLabel lblProgress1;
    JLabel lblProgress2;
    JLabel lblProgress3;
    static JLabel lblProgressPercent1;
    static JLabel lblProgressPercent2;
    static JLabel lblProgressPercent3;
    JLabel lblCount1;
    JLabel lblCount2;
    JLabel lblCount3;
    JSeparator jsep1;
    JSeparator jsep2;
    JSeparator jsep3;

    public Panel() {

        int separation = 160;

        setLayout(null);

        // btnStartRace
        btnStartRace = new JButton("Comenzar carrera");
        Font font = btnStartRace.getFont();
        btnStartRace.setFont(font.deriveFont(16.0f));
        btnStartRace.setSize(180, 40);
        btnStartRace.setLocation(385, 30);
        btnStartRace.addActionListener(this);
        add(btnStartRace);

        // btnRandomizePriorities
        btnRandomizePriorities = new JButton("Randomizar");
        btnRandomizePriorities.setFont(font.deriveFont(14.0f));
        btnRandomizePriorities.setSize(120, 30);
        btnRandomizePriorities.setLocation(170, 45);
        btnRandomizePriorities.addActionListener(this);
        add(btnRandomizePriorities);

        // btnResetPriorities
        btnResetPriorities = new JButton("Resetear (5)");
        btnResetPriorities.setFont(font.deriveFont(14.0f));
        btnResetPriorities.setSize(120, 30);
        btnResetPriorities.setLocation(40, 45);
        btnResetPriorities.addActionListener(this);
        add(btnResetPriorities);

        // js1
        js1 = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
        js1.setSize(300, 30);
        js1.setLocation((int) ((Window.widthWindow - js1.getWidth()) / 2), 150);
        js1.setMajorTickSpacing(1);
        js1.setMinorTickSpacing(1);
        js1.setPaintLabels(true);
        js1.setSnapToTicks(true);

        js1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int value = source.getValue();
                    lblThread1.setText("Hilo 1 (prioridad = " + value + ")");
                }
            }
        });
        add(js1);

        // js2
        js2 = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
        js2.setSize(300, 30);
        js2.setLocation((int) ((Window.widthWindow - js1.getWidth()) / 2), js1.getY() + separation);
        js2.setMajorTickSpacing(1);
        js2.setMinorTickSpacing(1);
        js2.setPaintLabels(true);
        js2.setSnapToTicks(true);

        js2.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int value = source.getValue();
                    lblThread2.setText("Hilo 2 (prioridad = " + value + ")");
                }
            }
        });
        add(js2);

        // js3
        js3 = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
        js3.setSize(300, 30);
        js3.setLocation((int) ((Window.widthWindow - js1.getWidth()) / 2), js1.getY() + separation * 2);
        js3.setMajorTickSpacing(1);
        js3.setMinorTickSpacing(1);
        js3.setPaintLabels(true);
        js3.setSnapToTicks(true);

        js3.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int value = source.getValue();
                    lblThread3.setText("Hilo 3 (prioridad = " + value + ")");
                }
            }
        });
        add(js3);

        // jbp1
        jpb1 = new JProgressBar();
        jpb1.setSize(300, 20);
        jpb1.setLocation((int) ((Window.widthWindow - jpb1.getWidth()) / 2), js1.getY() + 50);
        jpb1.setMinimum(0);
        jpb1.setMaximum(100);
        add(jpb1);

        // jpb2
        jpb2 = new JProgressBar();
        jpb2.setSize(300, 20);
        jpb2.setLocation((int) ((Window.widthWindow - jpb1.getWidth()) / 2), jpb1.getY() + separation);
        jpb2.setMinimum(0);
        jpb2.setMaximum(100);
        add(jpb2);

        // jbp3
        jpb3 = new JProgressBar();
        jpb3.setSize(300, 20);
        jpb3.setLocation((int) ((Window.widthWindow - jpb1.getWidth()) / 2), jpb1.getY() + separation * 2);
        jpb3.setMinimum(0);
        jpb3.setMaximum(100);
        add(jpb3);

        // jtf1
        jtf1 = new JTextField();
        jtf1.setSize(100, 30);
        jtf1.setLocation(540, 180);
        jtf1.setText("0");
        jtf1.setMargin(new Insets(0, 5, 0, 0));
        jtf1.setEditable(false);
        add(jtf1);

        // jtf2
        jtf2 = new JTextField();
        jtf2.setSize(100, 30);
        jtf2.setLocation(540, jtf1.getY() + separation);
        jtf2.setText("0");
        jtf2.setMargin(new Insets(0, 5, 0, 0));
        jtf2.setEditable(false);
        add(jtf2);

        // jtf3
        jtf3 = new JTextField("jtf3");
        jtf3.setSize(100, 30);
        jtf3.setLocation(540, jtf1.getY() + separation * 2);
        jtf3.setText("0");
        jtf3.setMargin(new Insets(0, 5, 0, 0));
        jtf3.setEditable(false);
        add(jtf3);

        // lblThread1
        lblThread1 = new JLabel("Hilo 1 (prioridad = " + js1.getValue() + ")");
        lblThread1.setFont(font.deriveFont(16.0f));
        lblThread1.setSize(200, 30);
        lblThread1.setLocation(275, 110);
        add(lblThread1);

        // lblThread2
        lblThread2 = new JLabel("Hilo 2 (prioridad = " + js2.getValue() + ")");
        lblThread2.setFont(font.deriveFont(16.0f));
        lblThread2.setSize(200, 30);
        lblThread2.setLocation(275, lblThread1.getY() + separation);
        add(lblThread2);

        // lblThread3
        lblThread3 = new JLabel("Hilo 3 (prioridad = " + js3.getValue() + ")");
        lblThread3.setFont(font.deriveFont(16.0f));
        lblThread3.setSize(200, 30);
        lblThread3.setLocation(275, lblThread1.getY() + separation * 2);
        add(lblThread3);

        // lblPriorities
        lblPriorities = new JLabel("Prioridades");
        lblPriorities.setFont(font.deriveFont(14.0f));
        lblPriorities.setSize(100, 30);
        lblPriorities.setLocation(120, 10);
        add(lblPriorities);

        // lblPriority1
        lblPriority1 = new JLabel("Prioridad");
        lblPriority1.setFont(font.deriveFont(14.0f));
        lblPriority1.setSize(100, 30);
        lblPriority1.setLocation(120, 140);
        add(lblPriority1);

        // lblPriority2
        lblPriority2 = new JLabel("Prioridad");
        lblPriority2.setFont(font.deriveFont(14.0f));
        lblPriority2.setSize(100, 30);
        lblPriority2.setLocation(120, lblPriority1.getY() + separation);
        add(lblPriority2);

        // lblPriority3
        lblPriority3 = new JLabel("Prioridad");
        lblPriority3.setFont(font.deriveFont(14.0f));
        lblPriority3.setSize(100, 30);
        lblPriority3.setLocation(120, lblPriority1.getY() + separation * 2);
        add(lblPriority3);

        // lblProgress1
        lblProgress1 = new JLabel("Progreso");
        lblProgress1.setFont(font.deriveFont(14.0f));
        lblProgress1.setSize(100, 30);
        lblProgress1.setLocation(120, 195);
        add(lblProgress1);

        // lblProgress2
        lblProgress2 = new JLabel("Progreso");
        lblProgress2.setFont(font.deriveFont(14.0f));
        lblProgress2.setSize(100, 30);
        lblProgress2.setLocation(120, lblProgress1.getY() + separation);
        add(lblProgress2);

        // lblProgress3
        lblProgress3 = new JLabel("Progreso");
        lblProgress3.setFont(font.deriveFont(14.0f));
        lblProgress3.setSize(100, 30);
        lblProgress3.setLocation(120, lblProgress1.getY() + separation * 2);
        add(lblProgress3);

        // lblProgressPercent1
        lblProgressPercent1 = new JLabel("0%");
        lblProgressPercent1.setFont(font.deriveFont(14.0f));
        lblProgressPercent1.setSize(35, 30);
        lblProgressPercent1.setLocation((int) ((Window.widthWindow - lblProgressPercent1.getWidth()) / 2), 215);
        add(lblProgressPercent1);

        // lblProgressPercent2
        lblProgressPercent2 = new JLabel("0%");
        lblProgressPercent2.setFont(font.deriveFont(14.0f));
        lblProgressPercent2.setSize(35, 30);
        lblProgressPercent2.setLocation((int) ((Window.widthWindow - lblProgressPercent1.getWidth()) / 2), lblProgressPercent1.getY() + separation);
        add(lblProgressPercent2);

        // lblProgressPercent3
        lblProgressPercent3 = new JLabel("0%");
        lblProgressPercent3.setFont(font.deriveFont(14.0f));
        lblProgressPercent3.setSize(35, 30);
        lblProgressPercent3.setLocation((int) ((Window.widthWindow - lblProgressPercent1.getWidth()) / 2), lblProgressPercent1.getY() + separation * 2);
        add(lblProgressPercent3);

        // lblCount1
        lblCount1 = new JLabel("Contador");
        lblCount1.setFont(font.deriveFont(14.0f));
        lblCount1.setSize(65, 30);
        lblCount1.setLocation(555, 145);
        add(lblCount1);

        // lblCount2
        lblCount2 = new JLabel("Contador");
        lblCount2.setFont(font.deriveFont(14.0f));
        lblCount2.setSize(65, 30);
        lblCount2.setLocation(555, lblCount1.getY() + separation);
        add(lblCount2);

        // lblCount3
        lblCount3 = new JLabel("Contador");
        lblCount3.setFont(font.deriveFont(14.0f));
        lblCount3.setSize(65, 30);
        lblCount3.setLocation(555, lblCount1.getY() + separation * 2);
        add(lblCount3);

        // jsep1
        jsep1 = new JSeparator(SwingConstants.HORIZONTAL);
        jsep1.setSize((int) (Window.widthWindow - 60), 2);
        jsep1.setLocation(20, 100);
        jsep1.setForeground(Color.black);
        add(jsep1);

        // jsep2
        jsep2 = new JSeparator(SwingConstants.HORIZONTAL);
        jsep2.setSize((int) (Window.widthWindow - 60), 2);
        jsep2.setLocation(20, 260);
        jsep2.setForeground(Color.black);
        add(jsep2);

        // jsep3
        jsep3 = new JSeparator(SwingConstants.HORIZONTAL);
        jsep3.setSize((int) (Window.widthWindow - 60), 2);
        jsep3.setLocation(20, 260 + separation);
        jsep3.setForeground(Color.black);
        add(jsep3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource().equals(btnStartRace)) {

            Thread thr1 = new Thread(30, "HILO 1");
            Thread thr2 = new Thread(30, "HILO 2");
            Thread thr3 = new Thread(30, "HILO 3");

            thr1.setPriority(js1.getValue());
            thr2.setPriority(js2.getValue());
            thr3.setPriority(js3.getValue());

            thr1.start();
            thr2.start();
            thr3.start();

            disableComponents();

        } else if (e.getSource().equals(btnRandomizePriorities)) {

            randomizePriorities();

        } else if (e.getSource().equals(btnResetPriorities)) {

            resetPriorities();
        }
    }

    public void resetPriorities() {
        js1.setValue(5);
        js2.setValue(5);
        js3.setValue(5);
    }

    public void randomizePriorities() {
        Random random = new Random();
        js1.setValue(random.nextInt(10) + 1);
        js2.setValue(random.nextInt(10) + 1);
        js3.setValue(random.nextInt(10) + 1);
    }

    public void disableComponents() {
        btnStartRace.setEnabled(false);
        btnRandomizePriorities.setEnabled(false);
        btnResetPriorities.setEnabled(false);
        js1.setEnabled(false);
        js2.setEnabled(false);
        js3.setEnabled(false);
    }
}
