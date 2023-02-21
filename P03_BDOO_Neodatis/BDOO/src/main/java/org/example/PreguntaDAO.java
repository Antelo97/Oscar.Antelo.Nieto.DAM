package org.example;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

import java.io.File;
import java.util.ArrayList;

public class PreguntaDAO {

    public void almacenar(Pregunta pregunta) throws Exception {
        ODB odb = null;

        try {
            // Abrir BBDD (la crea si no existe)
            odb = ODBFactory.open("NeodatisQuiz.db");
            // Almacenar pregunta
            odb.store(pregunta);

        } finally {
            if (odb != null) {
                // Cerramos la BBDD
                odb.close();
            }
        }
    }

    public ArrayList<Pregunta> listar() throws Exception {
        ODB odb = null;
        ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();

        try {
            // Abrimos la BBDD (la crea si no existe)
            odb = ODBFactory.open("NeodatisQuiz.db");
            Objects Opreguntas = odb.getObjects(Pregunta.class);

            // Cargar preguntas en el ArrayList
            while (Opreguntas.hasNext()) {
                preguntas.add((Pregunta) Opreguntas.next());
            }
        } finally {
            if (odb != null) {
                // Cerramos la BBDD
                odb.close();
            }
        }

        return preguntas;
    }

    public void almacenarJugador(Jugador player) throws Exception {
        ODB odb = null;

        try {
            // Abrir BBDD (la crea si no existe)
            odb = ODBFactory.open("NeodatisRanking.db");
            // Almacenar jugador
            odb.store(player);

        } finally {
            if (odb != null) {
                // Cerramos la BBDD
                odb.close();
            }
        }
    }

    public ArrayList<Jugador> listarRanking() throws Exception {
        ODB odb = null;
        ArrayList<Jugador> ranking = new ArrayList<Jugador>();

        try {
            // Abrimos la BBDD (la crea si no existe)
            odb = ODBFactory.open("NeodatisRanking.db");
            Objects Oranking = odb.getObjects(Jugador.class);

            // Cargar preguntas en el ArrayList
            while (Oranking.hasNext()) {
                ranking.add((Jugador) Oranking.next());
            }
        } finally {
            if (odb != null) {
                // Cerramos la BBDD
                odb.close();
            }
        }

        return ranking;
    }
}
