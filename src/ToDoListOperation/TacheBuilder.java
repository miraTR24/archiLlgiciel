package ToDoListOperation;

import java.time.LocalDate;
/**
 * @author KHICHA
 * @author TIRECHE
 * Cette interface définit un constructeur de tâches pour la création et la configuration de tâches.
 */
public interface TacheBuilder {

    /**
     * Définit la description de la tâche.
     * @param description La description de la tâche.
     * @return Le constructeur de tâches.
     */
    TacheBuilder setDescription(String description);

    /**
     * Définit la date d'échéance de la tâche.
     * @param dateEcheance La date d'échéance de la tâche.
     * @return Le constructeur de tâches.
     */
    TacheBuilder setDateEcheance(LocalDate dateEcheance);

    /**
     * Définit la priorité de la tâche.
     * @param priorite La priorité de la tâche.
     * @return Le constructeur de tâches.
     */
    TacheBuilder setPriorite(Priorite priorite);

    /**
     * Ajoute une sous-tâche à la tâche.
     * @param subtask La sous-tâche à ajouter.
     * @return Le constructeur de tâches.
     */
    TacheBuilder addSubtask(Tache subtask);

    /**
     * Définit la durée estimée de la tâche.
     * @param estimatedDuration La durée estimée de la tâche.
     * @return Le constructeur de tâches.
     */
    TacheBuilder setEstimatedDuration(int estimatedDuration);

    /**
     * Définit le progrès de la tâche.
     * @param progress Le progrès de la tâche.
     * @return Le constructeur de tâches.
     */
    TacheBuilder setProgress(int progress);

    /**
     * Construit et retourne la tâche.
     * @return La tâche construite.
     */
    Tache build();
}