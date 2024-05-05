package XmlParser;

import org.w3c.dom.*;

import ToDoListOperation.Tache;
import ToDoListOperation.TodoListImpl;

import javax.xml.parsers.*;
import java.io.*;
import java.time.LocalDate;

/**
 * @author KHICHA
 * @author TIRECHE
 * La classe <code>XMLParser</code> fournit des méthodes statiques pour parser un fichier XML contenant des tâches et créer des objets Tache correspondants.
 */
public class XMLParser {

    /**
     * Parse un fichier XML contenant des tâches et les ajoute à une liste de tâches TodoListImpl.
     * 
     * @param todoList La liste de tâches à laquelle ajouter les tâches parsées.
     * @param pathFile Le chemin du fichier XML à parser.
     */
    public static void parseXml(TodoListImpl todoList, String pathFile) {
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
                    Tache task = createTaskFromElement(taskElement);
                    todoList.addTask(task);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crée un objet Tache à partir d'un élément XML représentant une tâche.
     * 
     * @param taskElement L'élément XML représentant la tâche.
     * @return Un objet Tache créé à partir de l'élément XML.
     * @throws IllegalArgumentException Si le type de tâche spécifié n'est pas pris en charge.
     */
    public static Tache createTaskFromElement(Element taskElement) {
        String taskType = taskElement.getTagName();
        ITaskParserFactory parser;
        switch (taskType) {
            case "simpleTask":
                parser = new SimpleTaskParser();
                return parser.parseTask(taskElement);
            case "booleanTask":
                parser = new BoolTaskParser();
                return parser.parseTask(taskElement);
            case "complexTask":
                parser = new ComplexTaskParser();
                return parser.parseTask(taskElement);
            default:
                throw new IllegalArgumentException("Type de tâche non pris en charge : " + taskType);
        }
    }
}
