package XmlParser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ToDoListOperation.ComplexTache;
import ToDoListOperation.Priorite;
import ToDoListOperation.Tache;
import ToDoListOperation.TacheBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComplexTaskParser implements ITaskParserFactory {

	@Override
	public Tache parseTask(Element taskElement) {
	    String description = taskElement.getElementsByTagName("description").item(0).getTextContent();
        Priorite priorite = Priorite.valueOf(taskElement.getElementsByTagName("priorite").item(0).getTextContent());

        List<Tache> subTasks = new ArrayList<>();
        NodeList subTaskNodes = taskElement.getElementsByTagName("subTasks").item(0).getChildNodes();
        for (int i = 0; i < subTaskNodes.getLength(); i++) {
            Node subTaskNode = subTaskNodes.item(i);
            if (subTaskNode.getNodeType() == Node.ELEMENT_NODE) {
                Element subTaskElement = (Element) subTaskNode;
                Tache subTask = XMLParser.createTaskFromElement(subTaskElement);
                subTasks.add(subTask);
            }
        } 

	  

	    TacheBuilder builder = new ComplexTache();
	    Tache complexTask = ((ComplexTache) builder
	            .setDescription(description)
	            .setPriorite(priorite)
	            .setProgress(Integer.parseInt(taskElement.getElementsByTagName("progress").item(0).getTextContent())))
	            .setSubTaches(subTasks)
	            .build();

	    return complexTask;
	}

}




/*package FactoryMethodParser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import projetXml.ComplexTache;
import projetXml.Priorite;
import projetXml.Tache;

import java.util.ArrayList;
import java.util.List;

public class ComplexTaskParser implements TaskParser {

    @Override
    public Tache parseTask(Element taskElement) {
        String description = taskElement.getElementsByTagName("description").item(0).getTextContent();
        Priorite priorite = Priorite.valueOf(taskElement.getElementsByTagName("priorite").item(0).getTextContent());

        List<Tache> subTasks = new ArrayList<>();
        NodeList subTaskNodes = taskElement.getElementsByTagName("subTasks").item(0).getChildNodes();
        for (int i = 0; i < subTaskNodes.getLength(); i++) {
            Node subTaskNode = subTaskNodes.item(i);
            if (subTaskNode.getNodeType() == Node.ELEMENT_NODE) {
                Element subTaskElement = (Element) subTaskNode;
                Tache subTask = XMLParser.createTaskFromElement(subTaskElement);
                subTasks.add(subTask);
            }
        }

        return new ComplexTache(description, priorite, subTasks);
    }
}*/