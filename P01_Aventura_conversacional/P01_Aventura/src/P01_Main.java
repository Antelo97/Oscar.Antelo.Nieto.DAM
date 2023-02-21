import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class P01_Main {

    public static void main(String[] args) {

        Scanner keyborad = new Scanner(System.in);
        mainMenu();
    }

    public static void mainMenu() {
        // variables
        int adventure = 0;
        boolean withZero = true;

        System.out.println("Seleccione una aventura:");
        System.out.println("1 --> Gran Premio F1 Mónaco");
        System.out.println("2 --> Huida de las Mazmorras");
        System.out.println("0 --> Cerrar aplicación");
        adventure = manageOptions(3, true);

        switch (adventure) {
            case 1:
                processAdventureOne();
                break;
            case 2:
                processAdventureTwo();
                break;
            case 0:
                System.err.println("--- Cerrando aplicación ---");
                System.exit(0);
        }
    }

    public static void processAdventureOne() {

        String filePath = "C:\\Users\\oscar\\OneDrive\\Desktop\\CFGS DAM\\CFGS DAM II\\Acceso a Datos\\P01_Aventura\\aventuraF1.xml";
        File xmlFile = new File(filePath);
        ArrayList<P01a_Escena> scenes = new ArrayList<P01a_Escena>();

        // variables aventura
        String valAdventure = null;

        // variables escena
        int valCode = 0;
        String valTextSc = null;

        // variables opción
        int valID;
        String valTextOp;
        int valResult;

        try {
            // parseamos el documento XML con DOM
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            // obtenemos el elemento raíz del XML
            Element eRoot = doc.getDocumentElement();

            valAdventure = eRoot.getAttribute("titulo");

            NodeList nlScenes = eRoot.getElementsByTagName("escena");

            // recorremos la lista de nodos
            for (int i = 0; i < nlScenes.getLength(); i++) {

                Node nScene = nlScenes.item(i);
                Element eScene = (Element) nScene;
                NodeList nlSceneData = nScene.getChildNodes();

                ArrayList<P01b_Opcion> options = new ArrayList<P01b_Opcion>();

                if (eScene.hasAttribute("codigo")) {
                    valCode = Integer.parseInt(eScene.getAttribute("codigo"));
                }

                for (int x = 0; x < nlSceneData.getLength(); x++) {

                    Node n = nlSceneData.item(x);

                    if (n.getNodeType() == Node.ELEMENT_NODE) {

                        Element e = (Element) n;

                        if (e.hasAttribute("id") && e.hasAttribute("texto") && e.hasAttribute("resultado")) {
                            valID = Integer.parseInt(e.getAttribute("id"));
                            valTextOp = e.getAttribute("texto");
                            valResult = Integer.parseInt(e.getAttribute("resultado"));

                            P01b_Opcion option = new P01b_Opcion(valID, valTextOp, valResult);
                            options.add(option);
                        }

                        if (n.getNodeName().equals("texto")) {
                            valTextSc = (n != null) ? n.getTextContent() : null;
                        }
                    }
                }

                // creamos el objeto escena y lo añadimos a la lista
                P01a_Escena scene = new P01a_Escena(valCode, valTextSc, options);

                scenes.add(scene);
            }

            int option = 0;
            boolean withZero = true;

            System.out.println("\nSeleccione una opción:");
            System.out.println("1 --> Imprimir aventura por consola");
            System.out.println("2 --> Iniciar aventura");
            System.out.println("3 --> Regresar al menú principal");
            System.out.println("0 --> Cerrar aplicación");
            option = manageOptions(4, withZero);

            switch (option) {
                case 1:
                    int y = 0;
                    System.out.println(valAdventure);
                    for (P01a_Escena sc : scenes) {
                        System.out.print(sc.toString());
                    }
                    processAdventureOne();
                    break;

                case 2:
                    startAdventureOne(valAdventure, scenes);
                    break;

                case 3:
                    mainMenu();
                    break;

                case 0:
                    System.err.println("--- Cerrando aplicación ---");
                    System.exit(0);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startAdventureOne(String valAdventure, ArrayList<P01a_Escena> scenes) {
        int code = 1;
        int option = 0;
        boolean withZero = false;

        System.out.println("¡" + valAdventure + "!");
        System.out.println("=======================");
        do {
            int i = 0;
            for (i = 0; i < scenes.size(); i++) {
                if (scenes.get(i).getCode() == code) {
                    System.out.println("Descripción de la escena:");
                    System.out.println("● " + scenes.get(i).getText());
                    if (scenes.get(i).getCode() != scenes.size()) {
                        System.out.println("\nSeleccione una opción:");
                        for (int x = 0; x < scenes.get(i).getOptions().size(); x++) {
                            System.out.println((x + 1) + " --> " + scenes.get(i).getOptions().get(x).getText());
                        }
                        withZero = false;
                        option = manageOptions(scenes.get(i).getOptions().size(), withZero);
                        code = scenes.get(i).getOptions().get(option - 1).getResult();
                    } else {
                        System.out.println("\n¡Aventura finalizada!");
                        System.out.println("\nSeleccione una opción");
                        System.out.println("1 --> Volver a jugar");
                        System.out.println("2 --> Regresar al menú principal");
                        System.out.println("0 --> Cerrar aplicación");
                        withZero = true;
                        option = manageOptions(3, withZero);

                        switch (option) {
                            case 1:
                                startAdventureOne(valAdventure, scenes);
                                break;
                            case 2:
                                mainMenu();
                                break;
                            case 3:
                                System.exit(0);
                        }
                    }
                }
            }
        } while (code < scenes.size());
    }

    public static void processAdventureTwo() {

        String filePath = "C:\\Users\\oscar\\OneDrive\\Desktop\\CFGS DAM\\CFGS DAM II\\Acceso a Datos\\P01_Aventura\\aventuraMazmorras.xml";
        File xmlFile = new File(filePath);
        ArrayList<P01a_Escena> scenes = new ArrayList<P01a_Escena>();

        // variables aventura
        String valAdventure = null;

        // variables escena
        int valCode = 0;
        String valTextSc = null;

        // variables opción
        int valID;
        String valTextOp;
        int valResult;

        try {
            // parseamos el documento XML con DOM
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            // obtenemos el elemento raíz del XML
            Element eRoot = doc.getDocumentElement();

            valAdventure = eRoot.getAttribute("titulo");

            NodeList nlScenes = eRoot.getElementsByTagName("escena");

            // recorremos la lista de nodos
            for (int i = 0; i < nlScenes.getLength(); i++) {

                Node nScene = nlScenes.item(i);
                Element eScene = (Element) nScene;
                NodeList nlSceneData = nScene.getChildNodes();

                ArrayList<P01b_Opcion> options = new ArrayList<P01b_Opcion>();

                if (eScene.hasAttribute("codigo")) {
                    valCode = Integer.parseInt(eScene.getAttribute("codigo"));
                }

                for (int x = 0; x < nlSceneData.getLength(); x++) {

                    Node n = nlSceneData.item(x);

                    if (n.getNodeType() == Node.ELEMENT_NODE) {

                        Element e = (Element) n;

                        if (e.hasAttribute("id") && e.hasAttribute("texto") && e.hasAttribute("resultado")) {
                            valID = Integer.parseInt(e.getAttribute("id"));
                            valTextOp = e.getAttribute("texto");
                            valResult = Integer.parseInt(e.getAttribute("resultado"));

                            P01b_Opcion option = new P01b_Opcion(valID, valTextOp, valResult);
                            options.add(option);
                        }

                        if (n.getNodeName().equals("texto")) {
                            valTextSc = (n != null) ? n.getTextContent() : null;
                        }
                    }
                }

                // creamos el objeto escena y lo añadimos a la lista
                P01a_Escena scene = new P01a_Escena(valCode, valTextSc, options);

                scenes.add(scene);
            }

            int option = 0;
            boolean withZero = true;

            System.out.println("\nSeleccione una opción:");
            System.out.println("1 --> Imprimir aventura por consola");
            System.out.println("2 --> Iniciar aventura");
            System.out.println("3 --> Regresar al menú principal");
            System.out.println("0 --> Cerrar aplicación");
            option = manageOptions(4, withZero);

            switch (option) {
                case 1:
                    int y = 0;
                    System.out.println(valAdventure);
                    for (P01a_Escena sc : scenes) {
                        System.out.print(sc.toString());
                    }
                    processAdventureTwo();
                    break;

                case 2:
                    startAdventureTwo(valAdventure, scenes);
                    break;

                case 3:
                    mainMenu();
                    break;

                case 0:
                    System.err.println("--- Cerrando aplicación ---");
                    System.exit(0);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startAdventureTwo(String valAdventure, ArrayList<P01a_Escena> scenes) {
        int code = 1;
        int option = 0;
        boolean withZero = false;

        boolean checkRoom = false;
        boolean checkOption = true;

        /* Las escenas 10,21,22,31,32,41,42,51,52 solo desencadenan un efecto la primera vez que se pasa por ellas
        Controlaremos si ya se ha pasado por ellas a través de un Array de boolean, dónde cada boolean representa una escena */
        boolean[] checkRooms = new boolean[9];

        for (int i = 0; i < checkRooms.length; i++) {
            // false significará que todavía no se ha pasado por esa escena
            checkRooms[i] = false;
        }

        P01c_Jugador player = new P01c_Jugador(100, 0, false, false, false, false);

        System.out.println("¡" + valAdventure + "!");
        System.out.println("========================");
        do {
            int i = 0;
            for (i = 0; i < scenes.size(); i++) {
                if (scenes.get(i).getCode() == code) {
                    checkRoom = checkRooms(scenes.get(i), checkRooms);
                    if (checkRoom) {
                        System.out.println("Ya has visitado previamente esta sala, no ocurre nada");
                    } else {
                        checkRooms = updateCheckRooms(scenes.get(i), checkRooms);
                        System.out.println("Descripción de la escena:");
                        System.out.println("● " + scenes.get(i).getText());
                        player = processScene(player, scenes.get(i));
                    }
                    if (scenes.get(i).getCode() != 7 && player.getHealth() > 0) {
                        System.out.println("\nSeleccione una opción:");
                        for (int x = 0; x < scenes.get(i).getOptions().size(); x++) {
                            System.out.println((x + 1) + " --> " + scenes.get(i).getOptions().get(x).getText());
                        }
                        withZero = false;
                        do {
                            option = manageOptions(scenes.get(i).getOptions().size(), withZero);
                            checkOption = checkOption(player, scenes.get(i).getOptions().get(option - 1));
                        } while (!checkOption);
                        player = processOption(player, scenes.get(i).getOptions().get(option - 1));
                        code = scenes.get(i).getOptions().get(option - 1).getResult();
                    } else {

                        if (player.getHealth() <= 0) {
                            System.out.println("\nTu intento de huida ha fracasado, has perdido toda tu energía...");
                        }

                        System.out.println("\nSeleccione una opción");
                        System.out.println("1 --> Volver a jugar");
                        System.out.println("2 --> Regresar al menú principal");
                        System.out.println("0 --> Cerrar aplicación");
                        withZero = true;
                        option = manageOptions(3, withZero);

                        switch (option) {
                            case 1:
                                startAdventureTwo(valAdventure, scenes);
                                break;
                            case 2:
                                mainMenu();
                                break;
                            case 3:
                                System.exit(0);
                        }
                    }
                }
            }
        } while (code != 7);
    }

    public static boolean checkRooms(P01a_Escena scene, boolean[] checkRooms) {

        // Códigos de las escenas a controlar: 10,21,22,31,32,41,42,51,52
        if (scene.getCode() == 10) {
            return checkRooms[0];
        } else if (scene.getCode() == 21) {
            return checkRooms[1];
        } else if (scene.getCode() == 22) {
            return checkRooms[2];
        } else if (scene.getCode() == 31) {
            return checkRooms[3];
        } else if (scene.getCode() == 32) {
            return checkRooms[4];
        } else if (scene.getCode() == 41) {
            return checkRooms[5];
        } else if (scene.getCode() == 42) {
            return checkRooms[6];
        } else if (scene.getCode() == 51) {
            return checkRooms[7];
        } else if (scene.getCode() == 52) {
            return checkRooms[8];
        } else {
            return false;
        }
    }

    public static boolean[] updateCheckRooms(P01a_Escena scene, boolean[] checkRooms) {

        if (scene.getCode() == 10) {
            checkRooms[0] = true;
        } else if (scene.getCode() == 21) {
            checkRooms[1] = true;
        } else if (scene.getCode() == 22) {
            checkRooms[2] = true;
        } else if (scene.getCode() == 31) {
            checkRooms[3] = true;
        } else if (scene.getCode() == 32) {
            checkRooms[4] = true;
        } else if (scene.getCode() == 41) {
            checkRooms[5] = true;
        } else if (scene.getCode() == 42) {
            checkRooms[6] = true;
        } else if (scene.getCode() == 51) {
            checkRooms[7] = true;
        } else if (scene.getCode() == 52) {
            checkRooms[8] = true;
        }

        return checkRooms;
    }

    public static P01c_Jugador processScene(P01c_Jugador player, P01a_Escena scene) {

        if (scene.getCode() == 10) {
            player.setInitialKey(true);
            System.out.println("¡Has obtenido la llave inicial!");
        } else if (scene.getCode() == 21 || scene.getCode() == 41) {
            player.setRupees(player.getRupees() + 100);
            System.out.println("¡Has obtenido 100 rupias!");
        } else if (scene.getCode() == 22 || scene.getCode() == 32 || scene.getCode() == 42 || scene.getCode() == 52) {
            player = processFight(player, scene);
        } else if (scene.getCode() == 31 || scene.getCode() == 51) {
            player.setRupees(player.getRupees() + 150);
            System.out.println("¡Has obtenido 150 rupias!");
        }

        return player;
    }

    public static P01c_Jugador processFight(P01c_Jugador player, P01a_Escena scene) {

        if (player.isShield() && player.isWeapon()) {
            player.setShield(false);
            System.out.println("Utilizas el arma para vencer a tu enemigo y el escudo para defenderte");
            System.out.println("¡Conservas el arma pero pierdes el escudo!");
        } else if (!player.isShield() && player.isWeapon()) {
            player.setHealth(player.getHealth() - 50);
            System.out.println("Utilizas el arma para vencer a tu enemigo, pero al no tener escudo sufres daño");
            System.out.println("¡Pierdes 50 puntos de energía!");
        } else {
            player.setHealth(0);
            System.out.println("El enemigo te aniquila ya que no dispones de ninguna arma");
            System.out.println("¡Pierdes todos los puntos de energía y finaliza tu aventura!");
        }

        return player;
    }

    public static boolean checkOption(P01c_Jugador player, P01b_Opcion option) {

        if (option.getText().equals("Intento abrir la única puerta de la sala")) {
            if (!player.isInitialKey()) {
                System.err.println("No dispones de la llave necesaria");
                return false;
            } else {
                return true;
            }
        } else if (option.getText().equals("Intento abrir la única puerta de la sala final")) {
            if (!player.isMysteryKey()) {
                System.err.println("No dispones de la llave necesaria");
                return false;
            } else {
                return true;
            }
        } else if (option.getText().equals("Le compro un escudo por 50 rupias (si dispongo de ellas)")) {
            if (player.getRupees() < 50) {
                System.err.println("No dispones de suficientes rupias");
                return false;
            } else if (player.isShield()) {
                System.err.println("Ya dispones de un escudo y no es posible tener más de uno");
                return false;
            } else {
                return true;
            }
        } else if (option.getText().equals("Le compro un arma por 100 rupias (si dispongo de ellas)")) {
            if (player.getRupees() < 100) {
                System.err.println("No dispones de suficientes rupias");
                return false;
            } else if (player.isWeapon()) {
                System.err.println("Ya dispones de una arma y no es posible tener más de una");
                return false;
            } else {
                return true;
            }
        } else if (option.getText().equals("Le compro una llave misteriosa por 300 rupias (si dispongo de ellas)")) {
            if (player.getRupees() < 300) {
                System.err.println("No dispones de suficientes rupias");
                return false;
            } else if (player.isMysteryKey()) {
                System.err.println("Ya dispones de una llave misteriosa y no es posible tener más de una");
                return false;
            } else {
                return true;
            }
        } else if (option.getText().equals("Le vendo mi arma y/o escudo por la mitad de su precio")) {
            if (!player.isWeapon() && !player.isShield()) {
                System.err.println("No dispones de ningún objeto para vender");
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static P01c_Jugador processOption(P01c_Jugador player, P01b_Opcion option) {

        if (option.getText().equals("Consultar energía, economía e inventario")) {
            System.out.println(player.toString());
        } else if (option.getText().equals("Le compro un escudo por 50 rupias (si dispongo de ellas)")) {
            player.setRupees(player.getRupees() - 50);
            player.setShield(true);
            System.out.println("¡Has obtenido un escudo por 50 rupias!\n");
        } else if (option.getText().equals("Le compro un arma por 100 rupias (si dispongo de ellas)")) {
            player.setRupees(player.getRupees() - 100);
            player.setWeapon(true);
            System.out.println("¡Has obtenido un arma por 100 rupias!\n");
        } else if (option.getText().equals("Le compro una llave misteriosa por 300 rupias (si dispongo de ellas)")) {
            player.setRupees(player.getRupees() - 300);
            player.setMysteryKey(true);
            System.out.println("¡Has obtenido una llave misteriosa por 300 rupias!\n");
        } else if (option.getText().equals("Le vendo mi arma y/o escudo por la mitad de su precio")) {
            if (player.isShield()) {
                player.setRupees(player.getRupees() + 25);
                System.out.println("¡Has vendido tu escudo por 25 rupias!\n");
            }
            if (player.isWeapon()) {
                player.setRupees(player.getRupees() + 50);
                System.out.println("¡Has obtenido tu arma por 50 rupias!\n");
            }
        }

        return player;
    }

    public static int manageOptions(int numOptions, boolean withZero) {

        @SuppressWarnings("resource")
        Scanner keyboard = new Scanner(System.in);

        boolean tryCatch = true;
        int option = 0;
        int[] options = new int[numOptions];

        for (int i = 0; i < options.length; i++) {
            if (withZero) {
                options[i] = i;
            } else {
                options[i] = i + 1;
            }
        }

        do {
            tryCatch = true;
            try {
                option = keyboard.nextInt();
                System.out.println("");
                Arrays.sort(options);
            } catch (Exception e) {
                tryCatch = false;
                keyboard.next();
            }
            // devuelve un número negativo cuando la opción marcada no pertenece al array
            if (Arrays.binarySearch(options, option) < 0 || !tryCatch) {
                System.err.println("Introduzca una opción válida");
            }
        }
        while (Arrays.binarySearch(options, option) < 0 || !tryCatch);

        return option;
    }
}