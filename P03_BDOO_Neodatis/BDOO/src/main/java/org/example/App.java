package org.example;

import java.io.File;
import java.util.*;

public class App {
    static Scanner keyboard = new Scanner(System.in);
    static Jugador player;
    static ArrayList<Pregunta> questions;
    static boolean tryCatch = false;
    static int jackpot = 0;
    static int round = 1;
    static ArrayList<Integer> indexQuestion = new ArrayList<Integer>();
    static PreguntaDAO dao = new PreguntaDAO();

    public static void main(String[] args) throws Exception {
        questions = dao.listar();
        startMenu();
    }

    public static void startMenu() throws Exception {
        System.out.println("Seleccione una opción:");
        System.out.println("\t1) Ver reglas");
        System.out.println("\t2) Comenzar a jugar");
        System.out.println("\t3) Ver ránking");
        System.out.println("\t4) Borrar ránking");
        System.out.println("\t5) Cerrar juego");
        int option = validarOpcion(5);

        switch (option) {
            case 1:
                showRules();
                break;
            case 2:
                System.out.println("Introduzca un nombre de usuario");
                String username = keyboard.next();
                player = new Jugador();
                player.setUsername(username);
                System.out.println();
                beforeQuestionMenu();
                break;
            case 3:
                ArrayList<Jugador> ranking = new ArrayList<Jugador>();
                ranking = dao.listarRanking();
                System.out.println("RÁNKING:");
                for (Jugador objPlayer : ranking) {
                    System.out.println("● " + objPlayer.username + ": " + objPlayer.score + " puntos");
                }
                System.out.println();
                System.out.println("\nPulse 1 para regresar\n");
                validarOpcion(1);
                startMenu();
                break;
            case 4:
                File dbFile = new File("NeodatisRanking.db");
                if (dbFile.exists()) {
                    dbFile.delete();
                } else {
                    dbFile.createNewFile();
                }
                System.out.println("\nPulse 1 para regresar\n");
                validarOpcion(1);
                startMenu();
                break;
            case 5:
                System.exit(0);
                break;
        }
    }

    public static void showRules() throws Exception {
        System.out.println("Recomendable ver la función: tirarRuleta()");
        System.out.println("\nPulse 1 para regresar\n");
        validarOpcion(1);
        startMenu();
    }

    public static void beforeQuestionMenu() throws Exception {
        System.out.println("Seleccione una opción:");
        System.out.println("\t1) Responder pregunta");
        System.out.println("\t2) Consultar estado");
        System.out.println("\t3) Plantarse");
        int option = validarOpcion(3);

        switch (option) {
            case 1:
                tirarRuleta();
                System.out.println("Ronda: " + round + " || (Acierto: +" + player.getAmountAtStake() + " | Fallo: -" + player.getAmountAtStake() / 4 + ") || Puntos totales: " + player.score);
                generateQuestion();
                break;
            case 2:
                showStatus();
                break;
            case 3:
                player.setLeaveGame(true);
                comprobarFinJuego();
                System.exit(0);
                break;
        }
    }

    public static void showStatus() throws Exception {
        System.out.println("RONDA: " + round);
        System.out.println("BOTE: " + jackpot);
        System.out.println(player.toString());

        System.out.println("\nPulse 1 para regresar\n");
        validarOpcion(1);
        beforeQuestionMenu();
    }

    public static void inQuestionMenu(Pregunta question) throws Exception {
        System.out.println("\nSeleccione una opción");
        System.out.println("\t1) Responder pregunta");
        System.out.println("\t2) Usar comodín");
        int option = validarOpcion(2);

        switch (option) {
            case 1:
                System.out.println("Marque opción:");
                option = validarOpcion(4);
                comprobarPregunta(option, question.getOptions(), question);
                break;
            case 2:
                wildcardsMenu(question);
                break;
        }
    }

    public static void wildcardsMenu(Pregunta question) throws Exception {
        System.out.println("\nSeleccione un comodín:");
        System.out.println("\t1) Comodín del 75% (" + player.getWildcard75() + ")");
        System.out.println("\t2) Comodín de la IA (" + player.getWildcardIA() + ")");
        System.out.println("\t3) Comodín del descarte (" + player.getWildcardDecline() + ")");
        System.out.println("\t4) Comodín de multiplicar (" + player.getWildcardMultiply() + ")");
        System.out.println("\t5) Volver atrás");

        int correctAnswer = 10;
        for (Opcion ObjOption : question.getOptions()) {
            if (ObjOption.isRight()) {
                correctAnswer = question.getOptions().indexOf(ObjOption) + 1;
            }
        }

        int option = validarOpcionComodin(5);

        switch (option) {
            case 1:
                player.setWildcard75(player.getWildcard75() - 1);
                Random random = new Random();
                int discard = 10;
                do {
                    discard = random.nextInt(4) + 1;
                } while (discard == correctAnswer);

                System.out.println("Marque opción:");
                ArrayList<Opcion> temporaryOptions = question.getOptions();
                for (int i = 1; i <= temporaryOptions.size(); i++) {
                    if (i == discard) {
                        temporaryOptions.remove(i - 1);
                    }
                }

                System.out.println("P: " + question.getText());
                for (Opcion objOption : temporaryOptions) {
                    System.out.println("\t" + (temporaryOptions.indexOf(objOption) + 1) + ") " + objOption.getText());
                }
                option = validarOpcion(3);
                comprobarPregunta(option, temporaryOptions, question);
                beforeQuestionMenu();
                break;
            case 2:
                player.setWildcardIA(player.getWildcardIA() - 1);
                Random randomIA = new Random();
                int IA = randomIA.nextInt(10) + 1;
                if (IA > 2) {
                    System.out.println("La IA está al 80% segura de que la respuesta correcta es: " + correctAnswer + ")");
                } else {
                    do {
                        IA = randomIA.nextInt(4) + 1;
                    }
                    while (IA == correctAnswer);
                    System.out.println("La IA está al 80% segura de que la respuesta correcta es: " + IA + ")");
                }
                System.out.println("Marque opción: ");
                option = validarOpcion(4);
                comprobarPregunta(option, question.getOptions(), question);
                break;
            case 3:
                player.setWildcardDecline(player.getWildcardDecline() - 1);
                round++;
                beforeQuestionMenu();
                break;
            case 4:
                player.setWildcardMultiply(player.getWildcardMultiply() - 1);
                player.setAmountAtStake(player.getAmountAtStake() * 2);
                option = validarOpcion(4);
                comprobarPregunta(option, question.getOptions(), question);
                break;
            case 5:
                inQuestionMenu(question);
                break;
        }
    }


    public static void generateQuestion() throws Exception {
        Random random = new Random();
        int index = 0;

        if (indexQuestion.size() < questions.size()) {
            do {
                index = random.nextInt(questions.size()) + 0;
            } while (indexQuestion.contains(random));
        } else {
            indexQuestion.clear();
            do {
                index = random.nextInt(questions.size()) + 0;
            } while (indexQuestion.contains(random));
        }

        Pregunta question = questions.get(index);
        indexQuestion.add(index);

        System.out.println("P: " + question.getText());
        for (Opcion option : question.getOptions()) {
            System.out.println("\t" + (question.getOptions().indexOf(option) + 1) + ") " + option.getText());
        }

        inQuestionMenu(question);
    }

    public static void comprobarPregunta(int option, ArrayList<Opcion> options, Pregunta pregunta) throws Exception {
        if (options.get(option - 1).isRight()) {
            System.out.println("\033[32m¡Respuesta correcta!\033[0m\n");
            player.setScore(player.getScore() + player.getAmountAtStake());
        } else {
            System.out.println("\033[31m¡Respuesta incorrecta!\033[0m\n");
            player.setScore(player.getScore() - player.getAmountAtStake() / 4);
            player.setLives(player.getLives() - 1);
        }
        comprobarFinJuego();
        round++;
        jackpot += player.amountAtStake;
        beforeQuestionMenu();
    }

    public static void comprobarFinJuego() throws Exception {
        if (player.getLives() <= 0 || player.isLeaveGame()) {
            System.out.println("Partida finalizada. Has objetenido: " + player.score + " puntos");
            dao.almacenarJugador(player);
            System.exit(0);
        }
    }

    public static int validarOpcionComodin(int numOptions) {
        int option = 0;
        do {
            try {
                option = keyboard.nextInt();
                if (option >= 1 && option <= numOptions && player.getWildcard75() > 0 && player.getWildcardIA() > 0
                        && player.getWildcardDecline() > 0 && player.getWildcard75() > 0) {
                    tryCatch = true;
                } else {
                    System.err.println("Introduzca una opción válida");
                }
            } catch (InputMismatchException e) {
                System.err.println("Introduzca una opción válida");
                keyboard.next();
            }
        } while (!tryCatch);
        tryCatch = false;
        return option;
    }

    public static int validarOpcion(int numOptions) {
        int option = 0;
        do {
            try {
                option = keyboard.nextInt();
                if (option >= 1 && option <= numOptions) {
                    tryCatch = true;
                } else {
                    System.err.println("Introduzca una opción válida");
                }
            } catch (InputMismatchException e) {
                System.err.println("Introduzca una opción válida");
                keyboard.next();
            }
        } while (!tryCatch);
        tryCatch = false;
        return option;
    }

    public static void useCancelWildcard() throws Exception {
        System.out.println("¿Desea usar el comodín de cancelación?");
        System.out.println("\t1) Sí");
        System.out.println("\t2) No");

        int option = validarOpcion(2);
        if (option == 1) {
            player.setWildcardCancelEffect(false);
            beforeQuestionMenu();
        } else {

        }
    }

    public static void tirarRuleta() throws Exception {
        System.out.println("Tirando la ruleta... ");
        Random random = new Random();
        int randomNum = random.nextInt(100) + 1;
        System.out.print("Ha salido el " + randomNum + ": ");

        if (randomNum >= 1 && randomNum <= 10) {
            System.out.println("Juegas por 0 puntos");
            player.setAmountAtStake(0);
        } else if (randomNum >= 11 && randomNum <= 20) {
            System.out.println("Juegas por 40 puntos");
            player.setAmountAtStake(40);
        } else if (randomNum >= 21 && randomNum <= 30) {
            System.out.println("Juegas por 80 puntos");
            player.setAmountAtStake(80);
        } else if (randomNum >= 31 && randomNum <= 40) {
            System.out.println("Juegas por 120 puntos");
            player.setAmountAtStake(120);
        } else if (randomNum >= 41 && randomNum <= 50) {
            System.out.println("Juegas por 160 puntos");
            player.setAmountAtStake(160);
        } else if (randomNum >= 51 && randomNum <= 60) {
            System.out.println("Juegas por 200 puntos");
            player.setAmountAtStake(200);
        } else if (randomNum >= 61 && randomNum <= 70) {
            System.out.println("Juegas por 240 puntos");
            player.setAmountAtStake(240);
        } else if (randomNum >= 71 && randomNum <= 80) {
            System.out.println("Juegas por 300 puntos");
            player.setAmountAtStake(300);
        } else if (randomNum >= 81 && randomNum <= 82) {
            System.out.println("Has ganado el bote (+" + jackpot + ")");
            player.setScore(player.getScore() + jackpot);
            tirarRuleta();
        } else if (randomNum >= 83 && randomNum <= 84) {
            System.out.println("Recuperas todos los comodines");
            player.setWildcard75(3);
            player.setWildcardDecline(3);
            player.setWildcardIA(3);
            player.setWildcardMultiply(3);
            player.setWildcardCancelEffect(true);
            tirarRuleta();
        } else if (randomNum >= 85 && randomNum <= 86) {
            System.out.println("Recuperas el comodín de cancelación de efectos");
            player.setWildcardCancelEffect(true);
            tirarRuleta();
        } else if (randomNum >= 87 && randomNum <= 88) {
            System.out.println("El bote se duplica");
            jackpot *= 2;
            tirarRuleta();
        } else if (randomNum >= 89 && randomNum <= 90) {
            System.out.println("Ganas 2 vidas");
            player.setLives(player.getLives() + 2);
            if (player.getLives() > 10) {
                player.setLives(10);
            }
            tirarRuleta();
        } else if (randomNum >= 91 && randomNum <= 92) {
            System.out.println("Pierdes el comodín de cancelación");
            if (player.isWildcardCancelEffect()) {
                useCancelWildcard();
            }
            player.setWildcardCancelEffect(false);
            tirarRuleta();
        } else if (randomNum >= 93 && randomNum <= 94) {
            System.out.println("Pierdes todos los comodines");
            if (player.isWildcardCancelEffect()) {
                useCancelWildcard();
            }
            player.setWildcard75(0);
            player.setWildcardDecline(0);
            player.setWildcardIA(0);
            player.setWildcardMultiply(0);
            player.setWildcardCancelEffect(false);
            tirarRuleta();
        } else if (randomNum >= 95 && randomNum <= 96) {
            System.out.println("El bote se resetea a 0");
            if (player.isWildcardCancelEffect()) {
                useCancelWildcard();
            }
            jackpot = 0;
            tirarRuleta();
        } else if (randomNum >= 97 && randomNum <= 98) {
            System.out.println("Pierdes la mitad de tus vidas");
            if (player.isWildcardCancelEffect()) {
                useCancelWildcard();
            }
            player.setLives(player.getLives() / 2);
            tirarRuleta();
        } else if (randomNum >= 99 && randomNum <= 100) {
            System.out.println("Pierdes toda tu puntuación");
            if (player.isWildcardCancelEffect()) {
                useCancelWildcard();
            }
            player.setScore(0);
            tirarRuleta();
        }
        System.out.println();
    }


}


