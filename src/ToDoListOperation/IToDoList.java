package ToDoListOperation;

import java.util.List;

/**
 * @author KHICHA
 * @author TIRECHE
 * Cette interface définit les opérations de base sur une liste de tâches.
 */
public interface IToDoList {

    /**
     * Ajoute une tâche à la liste.
     * @param task La tâche à ajouter.
     */
    void addTask(Tache task);

    /**
     * Supprime une tâche de la liste.
     * @param task La tâche à supprimer.
     */
    void removeTask(Tache task);

    /**
     * Supprime une tâche de la liste en utilisant son identifiant.
     * @param id L'identifiant de la tâche à supprimer.
     */
    void removeTaskById(int id);

    /**
     * Récupère toutes les tâches de la liste.
     * @return Une liste contenant toutes les tâches.
     */
    List<Tache> getAllTasks();

    /**
     * Affiche toutes les tâches de la liste.
     */
    void displayTasks();

    /**
     * Affiche les cinq premières tâches de la liste.
     */
    void displayTopFiveTasks();

    /**
     * Remplace une tâche existante par une nouvelle tâche.
     * @param oldTask La tâche existante à remplacer.
     * @param newTask La nouvelle tâche.
     */
    void replaceTask(Tache oldTask, Tache newTask);
}