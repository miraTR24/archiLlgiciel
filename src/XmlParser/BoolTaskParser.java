package XmlParser;

import java.time.LocalDate;

import org.w3c.dom.Element;

import ToDoListOperation.BoolTache;
import ToDoListOperation.Priorite;
import ToDoListOperation.Tache;
import ToDoListOperation.TacheBuilder;

/**
 * @author KHICHA 
 * @author TIRECHE
 * La classe <code>BoolTaskParser</code> implémente l'interface <code>ITaskParserFactory</code> pour analyser un élément XML représentant une tâche booléenne.
 */
public class BoolTaskParser implements ITaskParserFactory {

    /**
     * Analyse un élément XML représentant une tâche booléenne et renvoie une instance de la classe <code>BoolTache</code>.
     * 
     * @param taskElement L'élément XML représentant une tâche booléenne.
     * @return Une instance de la classe <code>BoolTache</code> représentant la tâche booléenne analysée.
     */
    @Override
    public Tache parseTask(Element taskElement) {
        String description = taskElement.getElementsByTagName("description").item(0).getTextContent();
        LocalDate deadline = LocalDate.parse(taskElement.getElementsByTagName("deadline").item(0).getTextContent());
        Priorite priorite = Priorite.valueOf(taskElement.getElementsByTagName("priorite").item(0).getTextContent());
        int estimatedDuration = Integer.parseInt(taskElement.getElementsByTagName("estimatedDuration").item(0).getTextContent());
        boolean isCompleted = Boolean.parseBoolean(taskElement.getElementsByTagName("isCompleted").item(0).getTextContent());
        
        // Création d'un TacheBuilder pour construire la tâche booléenne
        TacheBuilder builder = new BoolTache();
        // Utilisation du TacheBuilder pour définir les attributs de la tâche
        Tache task = ((BoolTache) builder
                .setDescription(description)
                .setDateEcheance(deadline)
                .setPriorite(priorite)
                .setEstimatedDuration(estimatedDuration))
                .setCompleted(isCompleted) // Définition de l'état de complétion
                .build(); // Construction de la tâche
        
        return task;
    }
}
