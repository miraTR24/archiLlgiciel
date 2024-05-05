package XmlParser;

import org.w3c.dom.Element;

import ToDoListOperation.Tache;

/**
 * @author KHICHA
 * @author TIRECHE
 * L'interface <code>ITaskParserFactory</code> définit une méthode pour analyser un élément XML représentant une tâche
 * et renvoyer une instance appropriée de la classe <code>Tache</code>.
 */
public interface ITaskParserFactory {
    
    /**
     * Analyse un élément XML représentant une tâche et renvoie une instance appropriée de la classe <code>Tache</code>.
     * 
     * @param taskElement L'élément XML représentant une tâche.
     * @return Une instance de la classe <code>Tache</code> représentant la tâche analysée.
     */
    Tache parseTask(Element taskElement);
}
