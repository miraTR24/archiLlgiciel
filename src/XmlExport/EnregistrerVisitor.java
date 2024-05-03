package XmlExport;

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

import ToDoListOperation.BoolTache;
import ToDoListOperation.ComplexTache;
import ToDoListOperation.SimpleTache;
import ToDoListOperation.Tache;

public class EnregistrerVisitor implements ToDoListVisitor,Serializable{
	
	private  static  final  long serialVersionUID =  1350092881346723535L;
	
	private Document document;
	
	private Element ele;

	public EnregistrerVisitor(Document document , Element ele ){
		this.document= document; 
	    this.ele= ele;
	}
	
	@Override
	public void visitorSimpleTache(SimpleTache simpleTache) {
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
	public void visitorBoolTache(BoolTache boolTache) {
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
    public void visitorComplexTache(ComplexTache complexTache) {
        try {
            Element complexTaskElement = document.createElement("complexTask");

            appendTextElement(document, complexTaskElement, "description", complexTache.getDescription());
            appendTextElement(document, complexTaskElement, "deadline", complexTache.getDeadline().toString());
            appendTextElement(document, complexTaskElement, "priorite", complexTache.getPriorite().toString());
            appendTextElement(document, complexTaskElement, "estimatedDuration", String.valueOf(complexTache.getEstimatedDuration()));
            appendTextElement(document, complexTaskElement, "progress", String.valueOf(complexTache.getProgress()));

            Element subTasksElement = document.createElement("subTasks");
            EnregistrerVisitor subVisitor = new EnregistrerVisitor(document,subTasksElement);

            for (Tache subTask : complexTache.getSubTaches()) {
               
                subTask.acceptVistor(subVisitor);
            }

            complexTaskElement.appendChild(subTasksElement);

            ele.appendChild(complexTaskElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	
	
	private void appendTextElement(Document doc, Element parentElement, String tagName, String textContent) {
	    Element element = doc.createElement(tagName);
	    element.setTextContent(textContent);
	    parentElement.appendChild(element);
	}


}
