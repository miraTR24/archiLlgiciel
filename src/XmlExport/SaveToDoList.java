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
import ToDoListOperation.ToDoList;

public class SaveToDoList {
	
	public void saveListTache(ToDoList todoList, String pathFile ) {
		  try {
		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        
		        Document document = builder.newDocument(); // Création du document
		        Element ele = document.createElement("todoList");
		        ele.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		        ele.setAttribute("xsi:noNamespaceSchemaLocation", "ToDoList.xsd");
		        document.appendChild(ele);
		        
		        ToDoListVisitor  doListVisitor = new EnregistrerVisitor(document ,ele);
		        for (Tache task : todoList.getAllTasks()) {
		          task.acceptVistor(doListVisitor);
		        }
		        
		        TransformerFactory transformerFactory = TransformerFactory.newInstance();
		        Transformer transformer = transformerFactory.newTransformer();
		        DOMSource source = new DOMSource(document);
		        StreamResult result = new StreamResult(new File(pathFile));
		        transformer.transform(source, result); // Transformation et sauvegarde
		        
		    } catch (ParserConfigurationException e) {
		        System.err.println("Erreur lors de la création du document XML: " + e.getMessage());
		        e.printStackTrace();
		    } catch (Exception e) {
		        System.err.println("Erreur inattendue lors de l'enregistrement du document XML: " + e.getMessage());
		        e.printStackTrace();
		    }
	}
}


