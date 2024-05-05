package XmlExport;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ToDoListOperation.Tache;
import ToDoListOperation.IToDoList;

/**
 * @author KHICHA
 * @author TIRECHE
 * La classe <code>SaveToDoList</code> est utilisée pour sauvegarder une liste de tâches dans un fichier XML.
 */
public class SaveToDoList {
    
    /**
     * Sauvegarde la liste de tâches dans un fichier XML.
     * 
     * @param todoList La liste de tâches à sauvegarder.
     * @param pathFile Le chemin du fichier XML dans lequel sauvegarder la liste de tâches.
     */
    public void saveListTache(IToDoList todoList, String pathFile) {
        try {
            // Création du builder de document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Création du document XML
            Document document = builder.newDocument();
            Element ele = document.createElement("todoList");
            ele.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            ele.setAttribute("xsi:noNamespaceSchemaLocation", "ToDoList.xsd");
            document.appendChild(ele);
            
            // Création du visiteur pour enregistrer les tâches dans le document
            IToDoListVisitor doListVisitor = new IToDoListVisitorImp(document, ele);
            for (Tache task : todoList.getAllTasks()) {
                task.acceptVistor(doListVisitor);
            }
            
            // Transformation et sauvegarde du document XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(pathFile));
            transformer.transform(source, result);
        } catch (ParserConfigurationException e) {
            System.err.println("Erreur lors de la création du document XML: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erreur inattendue lors de l'enregistrement du document XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
