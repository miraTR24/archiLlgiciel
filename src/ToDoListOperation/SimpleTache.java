package ToDoListOperation;

import java.time.LocalDate;
/**
 * Cette classe représente une tâche simple dans une liste de tâches.
 * Elle implémente l'interface TacheBuilder pour la construction de tâches.
 * @author Khicha 
 * @author Tireche 
 */

import XmlExport.IToDoListVisitor;

public class SimpleTache extends Tache implements TacheBuilder {

	private int id=0;
	private String description;
    private LocalDate dateEcheance;
    private Priorite priorite;
    private int estimatedDuration;
    private int progress;
    

    /**
     * Constructeur par défaut qui initialise l'ID de la tâche.
     */

    public SimpleTache() {
    
        this.id=Tache.idCounter++;    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public LocalDate getDeadline() {
        return dateEcheance;
    }

    @Override
    public Priorite getPriorite() {
        return priorite;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public int getProgress() {
        return progress;
    }

    /**
     * Accepte un visiteur pour cette tâche simple.
     * @param toDoListVisitor Le visiteur à accepter.
     */
	@Override
	public void acceptVistor(IToDoListVisitor toDoListVisitor) {
		
	
		toDoListVisitor.visitorSimpleTache(this);
		
	}
	
	  /**
     * Affiche les détails de la tâche simple.
     */	
	   @Override
	    public void display() {
		   System.out.println("Id : " + getId());
	        System.out.println("Description : " + getDescription());
	        System.out.println("Deadline : " + getDeadline());
	        System.out.println("Priorite : " + getPriorite());
	        System.out.println("Estimated Duration : " + getEstimatedDuration());
	        System.out.println("Progress : " + getProgress() + "%");
	    }

	    @Override
	    public TacheBuilder setDescription(String description) {
	        this.description = description;
	        return this;
	    }

	    @Override
	    public TacheBuilder setDateEcheance(LocalDate dateEcheance) {
	        this.dateEcheance = dateEcheance;
	        return this;
	    }

	    @Override
	    public TacheBuilder setPriorite(Priorite priorite) {
	        this.priorite = priorite;
	        return this;
	    }

	    @Override
	    public TacheBuilder addSubtask(Tache subtask) {
	        throw new UnsupportedOperationException("Cannot add subtask to SimpleTache");
	    }
	    
	    @Override
	    public TacheBuilder setEstimatedDuration(int estimatedDuration) {
	        this.estimatedDuration = estimatedDuration;
	        return this;
	    }

	    @Override
	    public TacheBuilder setProgress(int progress) {
	        this.progress = progress;
	        return this;
	    }
	    /**
	     * Construit et retourne la tâche simple.
	     * @return La tâche simple construite.
	     */
	@Override
	public Tache build() {
		
		return this;
	}




	
}
