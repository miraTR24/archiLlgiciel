package FaçadeEditor;

import ToDoListOperation.ComplexTache;
import ToDoListOperation.Tache;

/**
 * @author KHICHA
 * @author TIRECHE
 * Cette interface définit les méthodes pour une façade offrant des fonctionnalités pour interagir avec une liste de tâches.
 */
public interface IFacade {

    /**
     * Crée une tâche complexe.
     */
    public void createComplexTask();

    /**
     * Crée une tâche simple.
     */
    public void createSimpleTask();

    /**
     * Crée une tâche booléenne.
     */
    public void createBooleanTask();

    /**
     * Importe une liste de tâches depuis un fichier XML.
     */
    public void importXML();

    /**
     * Sauvegarde la liste de tâches sous forme XML.
     */
    public void saveToDoList();

    /**
     * Modifie une tâche existante.
     * @param task La tâche à modifier.
     */
    public void modifyTask(Tache task);

    /**
     * Supprime une tâche de la liste.
     * @param task La tâche à supprimer.
     */
    public void deleteTask(Tache task);
}
	

