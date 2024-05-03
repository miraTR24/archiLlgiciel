package projetXml;

import java.time.LocalDate;

public class SimpleTache extends Tache implements TacheBuilder {
	//private static int idCounter = 0;
	private int id=0;
	private String name;
    private String description;
    private LocalDate dateEcheance;
    private Priorite priorite;
    private int estimatedDuration;
    private int progress;

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

	@Override
	public void acceptVistor(ToDoListVisitor toDoListVisitor) {
		
	
		toDoListVisitor.visitorSimpleTache(this);
		
	}
	
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

	@Override
	public Tache build() {
		
		return this;
	}




	
}
