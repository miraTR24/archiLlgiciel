package projetXml;

import java.io.Serializable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EnregistrerVisitor implements ToDoListVisitor,Serializable{
	
	private  static  final  long serialVersionUID =  1350092881346723535L;
	
	private Document document;
	
	private Element ele;

	@Override
	public void visitorSimpleTache(SimpleTache simpleTache, String pathFile) {
	    try {
	        Element simpleTaskElement = document.createElement("simpleTask");
	        
	        appendTextElement(document, simpleTaskElement, "description", simpleTache.getDescription());
	        appendTextElement(document, simpleTaskElement, "deadline", simpleTache.getDeadline().toString());
	        appendTextElement(document, simpleTaskElement, "priorite", simpleTache.getPriorite().toString());
	        appendTextElement(document, simpleTaskElement, "estimatedDuration", String.valueOf(simpleTache.getEstimatedDuration()));
	        appendTextElement(document, simpleTaskElement, "progress", String.valueOf(simpleTache.getProgress()));
	        
	        ele.appendChild(simpleTaskElement);
	    } catch (Exception e) {
	        System.err.println("Erreur lors de la création de l'élément SimpleTache: " + e.getMessage());
	        e.printStackTrace();
	    }
	}



	@Override
	public void visitorBoolTache(BoolTache boolTache, String pathFile) {
	    try {
	        Element booleanTaskElement = document.createElement("booleanTask");
	        
	        appendTextElement(document, booleanTaskElement, "description", boolTache.getDescription());
	        appendTextElement(document, booleanTaskElement, "deadline", boolTache.getDeadline().toString());
	        appendTextElement(document, booleanTaskElement, "priorite", boolTache.getPriorite().toString());
	        appendTextElement(document, booleanTaskElement, "estimatedDuration", String.valueOf(boolTache.getEstimatedDuration()));
	        appendTextElement(document, booleanTaskElement, "isCompleted", String.valueOf(boolTache.isCompleted()));
	        
	        ele.appendChild(booleanTaskElement);
	    } catch (Exception e) {
	        System.err.println("Erreur lors de la création de l'élément BoolTache: " + e.getMessage());
	        e.printStackTrace();
	    }
	}



	@Override
    public void visitorComplexTache(ComplexTache complexTache, String pathFile) {
        try {
            Element complexTaskElement = document.createElement("complexTask");

            appendTextElement(document, complexTaskElement, "description", complexTache.getDescription());
            appendTextElement(document, complexTaskElement, "deadline", complexTache.getDeadline().toString());
            appendTextElement(document, complexTaskElement, "priorite", complexTache.getPriorite().toString());
            appendTextElement(document, complexTaskElement, "estimatedDuration", String.valueOf(complexTache.getEstimatedDuration()));
            appendTextElement(document, complexTaskElement, "progress", String.valueOf(complexTache.getProgress()));

            Element subTasksElement = document.createElement("subTasks");
            for (Tache subTask : complexTache.getSubTaches()) {
                EnregistrerVisitor subVisitor = new EnregistrerVisitor();
                subVisitor.document = document;
                subVisitor.ele = subTasksElement;
                subTask.acceptVistor(subVisitor, pathFile);
            }

            complexTaskElement.appendChild(subTasksElement);

            ele.appendChild(complexTaskElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	@Override
	public void visitorTodoListImpl(TodoListImpl todoList, String pathFile) {
	    try {
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        
	        document = builder.newDocument(); // Création du document
	        ele = document.createElement("todoList");
	        ele.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
	        ele.setAttribute("xsi:noNamespaceSchemaLocation", "ToDoList.xsd");
	        document.appendChild(ele);
	        
	        for (Tache task : todoList.getAllTasks()) {
	            if (task instanceof SimpleTache) {
	                visitorSimpleTache((SimpleTache) task, pathFile);
	            } else if (task instanceof BoolTache) {
	                visitorBoolTache((BoolTache) task, pathFile);
	            } else if (task instanceof ComplexTache) {
	                visitorComplexTache((ComplexTache) task, pathFile);
	            }
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


	
	private void appendTextElement(Document doc, Element parentElement, String tagName, String textContent) {
	    Element element = doc.createElement(tagName);
	    element.setTextContent(textContent);
	    parentElement.appendChild(element);
	}


}
