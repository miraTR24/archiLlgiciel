package XmlParser;

import java.time.LocalDate;

import org.w3c.dom.Element;

import ToDoListOperation.BoolTache;
import ToDoListOperation.Priorite;
import ToDoListOperation.SimpleTache;
import ToDoListOperation.Tache;
import ToDoListOperation.TacheBuilder;

public class BoolTaskParser implements ITaskParserFactory {

    @Override
    public Tache parseTask(Element taskElement) {
        String description = taskElement.getElementsByTagName("description").item(0).getTextContent();
        LocalDate deadline = LocalDate.parse(taskElement.getElementsByTagName("deadline").item(0).getTextContent());
        Priorite priorite = Priorite.valueOf(taskElement.getElementsByTagName("priorite").item(0).getTextContent());
        int estimatedDuration = Integer.parseInt(taskElement.getElementsByTagName("estimatedDuration").item(0).getTextContent());
        boolean isCompleted = Boolean.parseBoolean(taskElement.getElementsByTagName("isCompleted").item(0).getTextContent());
        
   
    
    TacheBuilder builder = new BoolTache(); // Utiliser le bon TacheBuilder
    Tache task = ((BoolTache) builder
            .setDescription(description)
            .setDateEcheance(deadline)
            .setPriorite(priorite)
            .setEstimatedDuration(estimatedDuration))
            .setCompleted(isCompleted)
            .build();

    return task;
}
}

