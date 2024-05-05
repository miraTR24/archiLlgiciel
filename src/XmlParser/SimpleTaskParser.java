package XmlParser;

import java.time.LocalDate;

import org.w3c.dom.Element;

import ToDoListOperation.Priorite;
import ToDoListOperation.SimpleTache;
import ToDoListOperation.Tache;
import ToDoListOperation.TacheBuilder;

/**
 * @author KHICHA 
 * @author TIRECHE
 * La classe <code>SimpleTaskParser</code> implémente l'interface <code>ITaskParserFactory</code> pour analyser un élément XML représentant une tâche simple.
 */
public class SimpleTaskParser implements ITaskParserFactory {

    /**
     * Analyse un élément XML représentant une tâche simple et renvoie une instance de la classe <code>SimpleTache</code>.
     * 
     * @param taskElement L'élément XML représentant une tâche simple.
     * @return Une instance de la classe <code>SimpleTache</code> représentant la tâche simple analysée.
     */
    @Override
    public Tache parseTask(Element taskElement) {
        // Récupération des informations de base de la tâche
        String description = taskElement.getElementsByTagName("description").item(0).getTextContent();
        LocalDate deadline = LocalDate.parse(taskElement.getElementsByTagName("deadline").item(0).getTextContent());
        Priorite priorite = Priorite.valueOf(taskElement.getElementsByTagName("priorite").item(0).getTextContent());
        int estimatedDuration = Integer.parseInt(taskElement.getElementsByTagName("estimatedDuration").item(0).getTextContent());
        int progress = Integer.parseInt(taskElement.getElementsByTagName("progress").item(0).getTextContent());

        // Utilisation du bon TacheBuilder
        TacheBuilder builder = new SimpleTache();
        // Construction de la tâche simple à partir des informations récupérées
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
