package XmlParser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ToDoListOperation.ComplexTache;
import ToDoListOperation.Priorite;
import ToDoListOperation.Tache;
import ToDoListOperation.TacheBuilder;

/**
 * @author KHICHA 
 * @author TIRECHE
 * La classe <code>ComplexTaskParser</code> implémente l'interface <code>ITaskParserFactory</code> pour analyser un élément XML représentant une tâche complexe.
 */
public class ComplexTaskParser implements ITaskParserFactory {

    /**
     * Analyse un élément XML représentant une tâche complexe et renvoie une instance de la classe <code>ComplexTache</code>.
     * 
     * @param taskElement L'élément XML représentant une tâche complexe.
     * @return Une instance de la classe <code>ComplexTache</code> représentant la tâche complexe analysée.
     */
    @Override
    public Tache parseTask(Element taskElement) {
        // Récupération des informations de base de la tâche
        String description = taskElement.getElementsByTagName("description").item(0).getTextContent();
        Priorite priorite = Priorite.valueOf(taskElement.getElementsByTagName("priorite").item(0).getTextContent());

        // Récupération des sous-tâches
        List<Tache> subTasks = new ArrayList<>();
        NodeList subTaskNodes = taskElement.getElementsByTagName("subTasks").item(0).getChildNodes();
        for (int i = 0; i < subTaskNodes.getLength(); i++) {
            Node subTaskNode = subTaskNodes.item(i);
            if (subTaskNode.getNodeType() == Node.ELEMENT_NODE) {
                Element subTaskElement = (Element) subTaskNode;
                // Création des sous-tâches à partir des éléments XML correspondants
                Tache subTask = XMLParser.createTaskFromElement(subTaskElement);
                subTasks.add(subTask);
            }
        }

        // Construction de la tâche complexe à partir des informations récupérées
        TacheBuilder builder = new ComplexTache();
        Tache complexTask = ((ComplexTache) builder
                .setDescription(description)
                .setPriorite(priorite)
                .setProgress(Integer.parseInt(taskElement.getElementsByTagName("progress").item(0).getTextContent())))
                .setSubTaches(subTasks) // Définition des sous-tâches
                .build(); // Construction de la tâche complexe
        
        return complexTask;
    }
}
