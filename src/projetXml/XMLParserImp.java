package projetXml;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.time.LocalDate;

public class XMLParserImp {
    
    public static void parseXml(TodoListImpl todoList) {
        try {
            // Chargement du document XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("xml/todoList2.xml"));

            // Récupération de la racine
            Element root = document.getDocumentElement();

            // Parcours des éléments de la racine
            NodeList taskNodes = root.getChildNodes();
            for (int i = 0; i < taskNodes.getLength(); i++) {
                Node taskNode = taskNodes.item(i);
                if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element taskElement = (Element) taskNode;
                    Tache task = createTaskFromElement(taskElement);
                    todoList.addTask(task);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Tache createTaskFromElement(Element taskElement) {
        String taskType = taskElement.getTagName();
        switch (taskType) {
            case "simpleTask":
                return createSimpleTask(taskElement);
            case "booleanTask":
                return createBooleanTask(taskElement);
            case "complexTask":
                return createComplexTask(taskElement);
            default:
                throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
    }

    public static Tache createSimpleTask(Element taskElement) {
        String description = taskElement.getElementsByTagName("description").item(0).getTextContent();
        LocalDate deadline = LocalDate.parse(taskElement.getElementsByTagName("deadline").item(0).getTextContent());
        Priorite priorite = Priorite.valueOf(taskElement.getElementsByTagName("priorite").item(0).getTextContent());
        int estimatedDuration = Integer.parseInt(taskElement.getElementsByTagName("estimatedDuration").item(0).getTextContent());
        int progress = Integer.parseInt(taskElement.getElementsByTagName("progress").item(0).getTextContent());
        return new SimpleTache(description, deadline, priorite, estimatedDuration, progress);
    }

    public static Tache createBooleanTask(Element taskElement) {
        String description = taskElement.getElementsByTagName("description").item(0).getTextContent();
        LocalDate deadline = LocalDate.parse(taskElement.getElementsByTagName("deadline").item(0).getTextContent());
        Priorite priorite = Priorite.valueOf(taskElement.getElementsByTagName("priorite").item(0).getTextContent());
        int estimatedDuration = Integer.parseInt(taskElement.getElementsByTagName("estimatedDuration").item(0).getTextContent());
        boolean isCompleted = Boolean.parseBoolean(taskElement.getElementsByTagName("isCompleted").item(0).getTextContent());
        return new BoolTache(description, deadline, priorite, estimatedDuration,0,isCompleted);
    }
    
    public static Tache createComplexTask(Element taskElement) {
        // Récupération des éléments de base de la tâche
        String description = taskElement.getElementsByTagName("description").item(0).getTextContent();
        LocalDate deadline = LocalDate.parse(taskElement.getElementsByTagName("deadline").item(0).getTextContent());
        Priorite priorite = Priorite.valueOf(taskElement.getElementsByTagName("priorite").item(0).getTextContent());
        int progress = Integer.parseInt(taskElement.getElementsByTagName("progress").item(0).getTextContent());
        int estimatedDuration = Integer.parseInt(taskElement.getElementsByTagName("estimatedDuration").item(0).getTextContent());

        // Récupération des sous-tâches
        NodeList subTaskNodes = taskElement.getElementsByTagName("subTasks").item(0).getChildNodes();
        TodoListImpl subTaskList = new TodoListImpl();
        for (int i = 0; i < subTaskNodes.getLength(); i++) {
            Node subTaskNode = subTaskNodes.item(i);
            if (subTaskNode.getNodeType() == Node.ELEMENT_NODE) {
                Element subTaskElement = (Element) subTaskNode;
                Tache subTask = createTaskFromElement(subTaskElement);
                subTaskList.addTask(subTask);
            }
        }

        return new ComplexTache(description, priorite, subTaskList.getAllTasks());

    }


   
}
