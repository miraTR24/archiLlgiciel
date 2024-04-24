// SimpleTaskParser.java
package FactoryMethodParser;

import java.time.LocalDate;

import org.w3c.dom.Element;

import projetXml.Priorite;
import projetXml.SimpleTache;
import projetXml.Tache;

public class SimpleTaskParser implements TaskParser {

    @Override
    public Tache parseTask(Element taskElement) {
        String description = taskElement.getElementsByTagName("description").item(0).getTextContent();
        LocalDate deadline = LocalDate.parse(taskElement.getElementsByTagName("deadline").item(0).getTextContent());
        Priorite priorite = Priorite.valueOf(taskElement.getElementsByTagName("priorite").item(0).getTextContent());
        int estimatedDuration = Integer.parseInt(taskElement.getElementsByTagName("estimatedDuration").item(0).getTextContent());
        int progress = Integer.parseInt(taskElement.getElementsByTagName("progress").item(0).getTextContent());
        return new SimpleTache(description, deadline, priorite, estimatedDuration, progress);
    }
}