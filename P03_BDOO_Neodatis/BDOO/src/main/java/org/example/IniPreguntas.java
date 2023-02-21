package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class IniPreguntas {
    public static void main(String[] args) throws Exception {
        File dbFile = new File("NeodatisQuiz.db");
        if (dbFile.exists()) {
            dbFile.delete();
        } else {
            dbFile.createNewFile();
        }

        PreguntaDAO dao = new PreguntaDAO();

        String filePath = "questions.xml";
        File xmlFile = new File(filePath);
        ArrayList<Pregunta> questions = new ArrayList<Pregunta>();

        String valTextQuestion = "";
        String valDifficultyQuestion = "";
        String valTextOption = "";
        boolean valRight = false;

        try {
            // Parseamos el documento XML con DOM
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            // Obtenemos el elemento raíz del XML
            Element eRoot = doc.getDocumentElement();
            NodeList nlPets = eRoot.getElementsByTagName("pregunta");

            // Recorremos la lista de nodos
            for (int i = 0; i < nlPets.getLength(); i++) {

                Node nPet = nlPets.item(i);
                Element ePet = (Element) nPet;
                NodeList nlPetData = nPet.getChildNodes();

                // IMPORTANTE crearlo aquó
                ArrayList<Opcion> valOptionsQuestion = new ArrayList<Opcion>();

                if (ePet.hasAttribute("text")) {
                    valTextQuestion = ePet.getAttribute("text");
                }

                if (ePet.hasAttribute("difficulty")) {
                    valDifficultyQuestion = ePet.getAttribute("difficulty");
                }

                for (int x = 0; x < nlPetData.getLength(); x++) {

                    Node n = nlPetData.item(x);

                    if (n.getNodeType() == Node.ELEMENT_NODE) {

                        if (((Element) n).hasAttribute("right")) {
                            valRight = Boolean.parseBoolean(((Element) n).getAttribute("right"));
                        }

                        if (n.getNodeName().equals("opcion")) {
                            valTextOption = (n != null) ? n.getTextContent() : null;
                        }
                    }

                    if (x == 1 || x == 3 || x == 5 || x == 7) {
                        Opcion option = new Opcion(valTextOption, valRight);
                        valOptionsQuestion.add(option);
                    }
                }

                Pregunta question = new Pregunta(valTextQuestion, valOptionsQuestion, valDifficultyQuestion);
                questions.add(question);
                dao.almacenar(question);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
