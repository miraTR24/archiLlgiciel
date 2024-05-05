package ToDoListOperation;

import java.time.LocalDate;

import XmlExport.IToDoListVisitor;

/**
 * Cette classe abstraite représente une tâche générique dans une liste de tâches.
 * Les classes concrètes doivent étendre cette classe et implémenter les méthodes abstraites.
 * @author Khicha
 * @author Tireche
 */

public abstract class Tache {
	 /**
     * Compteur d'identifiant pour les tâches.
     */
	public static int idCounter = 1;
	  /**
     * Récupère l'identifiant de la tâche.
     * @return L'identifiant de la tâche.
     */	
	public abstract int getId();
	
	  /**
     * Récupère la description de la tâche.
     * @return La description de la tâche.
     */
 	public abstract String getDescription();
 	 /**
     * Récupère la date d'échéance de la tâche.
     * @return La date d'échéance de la tâche.
     */ 	
    public abstract LocalDate getDeadline();
    /**
     * Récupère la priorité de la tâche.
     * @return La priorité de la tâche.
     */
    public abstract Priorite getPriorite();

    /**
     * Récupère le progrès de la tâche.
     * @return Le progrès de la tâche.
     */
    public abstract int getProgress();

    /**
     * Récupère la durée estimée de la tâche.
     * @return La durée estimée de la tâche.
     */
    public abstract int getEstimatedDuration();

    /**
     * Accepte un visiteur pour cette tâche.
     * @param toDoListVisitor Le visiteur à accepter.
     */
    public abstract void acceptVistor(IToDoListVisitor toDoListVisitor);

    /**
     * Affiche les détails de la tâche.
     */
    public abstract void display(); // Afficher la tâche
}