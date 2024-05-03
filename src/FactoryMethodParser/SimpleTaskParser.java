package FactoryMethodParser;

import org.w3c.dom.Element;
import projetXml.Priorite;
import projetXml.SimpleTache;
import projetXml.Tache;
import projetXml.TacheBuilder;

import java.time.LocalDate;

public class SimpleTaskParser implements ITaskParserFactory {

    @Override
    public Tache parseTask(Element taskElement) {
        String description = taskElement.getElementsByTagName("description").item(0).getTextContent();
        LocalDate deadline = LocalDate.parse(taskElement.getElementsByTagName("deadline").item(0).getTextContent());
        Priorite priorite = Priorite.valueOf(taskElement.getElementsByTagName("priorite").item(0).getTextContent());
        int estimatedDuration = Integer.parseInt(taskElement.getElementsByTagName("estimatedDuration").item(0).getTextContent());
        int progress = Integer.parseInt(taskElement.getElementsByTagName("progress").item(0).getTextContent());

        TacheBuilder builder = new SimpleTache(); // Utiliser le bon TacheBuilder
        Tache task = builder
                .setDescription(description)
                .setDateEcheance(deadline)
                .setPriorite(priorite)
                .setEstimatedDuration(estimatedDuration)
                .setProgress(progress)
                .build();

        return task;
    }
}
