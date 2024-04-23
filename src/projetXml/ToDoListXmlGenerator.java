package projetXml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class ToDoListXmlGenerator {
    public void generateXml(ToDoList todoList, String filePath) {
        try {
            // Créez un document XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Racine
            Element root = doc.createElement("ToDoList");
            doc.appendChild(root);

            // Ajoutez les tâches
            List<Tache> tasks = todoList.getAllTasks();
            for (Tache task : tasks) {
                appendTaskElement(doc, root, task);
            }

            // Écrivez le document XML dans le fichier
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("XML file generated successfully.");

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void appendTaskElement(Document doc, Element parent, Tache task) {
        Element taskElement = doc.createElement("Task");
        taskElement.setAttribute("description", task.getDescription());
        taskElement.setAttribute("deadline", task.getDeadline().toString());
        taskElement.setAttribute("priority", task.getPriorite().name());

        parent.appendChild(taskElement);

        if (task instanceof ComplexTache) {
            List<Tache> subTasks = ((ComplexTache) task).getSubTaches();
            for (Tache subTask : subTasks) {
                appendTaskElement(doc, taskElement, subTask);
            }
        }
    }
}
