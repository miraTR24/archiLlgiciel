package projetXml;

import org.w3c.dom.*;

import FactoryMethodParser.XMLParser;

import javax.xml.parsers.*;
import java.io.*;
import java.time.LocalDate;

public class ImporterVisitor implements ToDoListVisitor {

    @Override
    public void visitorSimpleTache(SimpleTache simpleTache, String pathFile) {
        try {
            // Charger le document XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(pathFile));

            // Récupérer la racine
            Element root = document.getDocumentElement();

            // Ajouter la tâche simple au TodoList
            Tache task = XMLParser.createTaskFromElement(root);
            simpleTache = (SimpleTache) task;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visitorBoolTache(BoolTache boolTache, String pathFile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(pathFile));

            Element root = document.getDocumentElement();

            Tache task = XMLParser.createTaskFromElement(root);
            boolTache = (BoolTache) task;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visitorComplexTache(ComplexTache complexTache, String pathFile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(pathFile));

            Element root = document.getDocumentElement();

            Tache task = XMLParser.createTaskFromElement(root);
            complexTache = (ComplexTache) task;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visitorTodoListImpl(TodoListImpl toDoList, String pathFile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(pathFile));

            Element root = document.getDocumentElement();

            NodeList taskNodes = root.getChildNodes();
            for (int i = 0; i < taskNodes.getLength(); i++) {
                Node taskNode = taskNodes.item(i);
                if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element taskElement = (Element) taskNode;
                    Tache task = XMLParser.createTaskFromElement(taskElement);
                    toDoList.addTask(task);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
