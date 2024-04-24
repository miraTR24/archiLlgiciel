package projetXml;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ToDoListXmlGenerator {
    public void generateXml(TodoListImpl todoList, String xmlFilePath) {
        try {
            // Créer un document DOM
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Créer l'élément racine <todoList>
            Element rootElement = doc.createElement("todoList");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
          //  rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "xml/ToDoList.xsd"); // Spécifie l'emplacement du schéma XSD
            doc.appendChild(rootElement);

            // Parcourir les tâches de la ToDoList et les ajouter au document XML
            for (Tache task : todoList.getAllTasks()) {
                appendTaskToXml(doc, rootElement, task);
            }

            // Transformer le document DOM en XML et l'enregistrer dans un fichier
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

            System.out.println("XML file created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void appendTaskToXml(Document doc, Element parentElement, Tache task) {
        // Vérifier le type de tâche et créer l'élément XML approprié
        if (task instanceof SimpleTache) {
            SimpleTache simpleTask = (SimpleTache) task;
            Element simpleTaskElement = doc.createElement("simpleTask");
            
            appendTextElement(doc, simpleTaskElement, "description", simpleTask.getDescription());
            appendTextElement(doc, simpleTaskElement, "deadline", simpleTask.getDeadline().toString());
            appendTextElement(doc, simpleTaskElement, "priorite", simpleTask.getPriorite().toString());
            appendTextElement(doc, simpleTaskElement, "estimatedDuration", String.valueOf(simpleTask.getEstimatedDuration()));
            appendTextElement(doc, simpleTaskElement, "progress", String.valueOf(simpleTask.getProgress()));

            parentElement.appendChild(simpleTaskElement);
        } else if (task instanceof BoolTache) {
        	BoolTache booleanTask = (BoolTache) task;
            Element booleanTaskElement = doc.createElement("booleanTask");
            
            appendTextElement(doc, booleanTaskElement, "description", booleanTask.getDescription());
            appendTextElement(doc, booleanTaskElement, "deadline", booleanTask.getDeadline().toString());
            appendTextElement(doc, booleanTaskElement, "priorite", booleanTask.getPriorite().toString());
            appendTextElement(doc, booleanTaskElement, "isCompleted", String.valueOf(booleanTask.isCompleted()));

            parentElement.appendChild(booleanTaskElement);
        } else if (task instanceof ComplexTache) {
            ComplexTache complexTask = (ComplexTache) task;
            Element complexTaskElement = doc.createElement("complexTask");
            
            appendTextElement(doc, complexTaskElement, "description", complexTask.getDescription());
            appendTextElement(doc, complexTaskElement, "deadline", complexTask.getDeadline().toString());
            appendTextElement(doc, complexTaskElement, "priorite", complexTask.getPriorite().toString());
          //  appendTextElement(doc, complexTaskElement, "progress", String.valueOf(complexTask.getProgress()));

            // Ajouter les sous-tâches
            Element subTasksElement = doc.createElement("subTasks");
            for (Tache subTask : complexTask.getSubTaches()) {
                appendTaskToXml(doc, subTasksElement, subTask); // Récursif
            }
            complexTaskElement.appendChild(subTasksElement);

            parentElement.appendChild(complexTaskElement);
        }
    }

    private void appendTextElement(Document doc, Element parentElement, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.setTextContent(textContent);
        parentElement.appendChild(element);
    }
}
