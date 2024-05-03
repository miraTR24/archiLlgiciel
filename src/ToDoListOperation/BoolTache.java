package ToDoListOperation;

import java.time.LocalDate;

import XmlExport.ToDoListVisitor;

public class BoolTache extends Tache implements TacheBuilder {
	private int id=0;
	private String description;
    private LocalDate dateEcheance;
    private Priorite priorite;
    private int estimatedDuration;
    private int progress;
    private boolean isCompleted;

    public BoolTache() {
    	this.id=Tache.idCounter++;
    }

    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public LocalDate getDeadline() {
        return dateEcheance;
    }

    @Override
    public Priorite getPriorite() {
        return priorite;
    }
    
    // Getter pour isCompleted
    public boolean isCompleted() {
        return isCompleted;
    }


	@Override
	public int getProgress() {
		
		return this.isCompleted()? 100 : 0;
	}

	@Override
	public int getEstimatedDuration() {
		 return estimatedDuration;
	}

	@Override
	public void acceptVistor(ToDoListVisitor toDoListVisitor) {
	
			
			toDoListVisitor.visitorBoolTache(this);
		
	}
	
    @Override
    public void display() {
    	System.out.println("Id : " + getId());
        System.out.println("Description : " + getDescription());
        System.out.println("Deadline : " + getDeadline());
        System.out.println("Priorite : " + getPriorite());
        System.out.println("Estimated Duration : " + getEstimatedDuration());
        System.out.println("Completed : " + isCompleted());
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
        throw new UnsupportedOperationException("Cannot add subtask to BooleanTache");
    }

    public TacheBuilder setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
        progress= this.isCompleted()? 100 : 0;
        
        return this;
    }
    
    @Override
    public TacheBuilder setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
        return this;
    }

    @Override
    public TacheBuilder setProgress(int progress) {
        this.progress = progress;
        if (progress==100)
        {
			isCompleted=true;
			}
        else isCompleted=false;
        return this;
    }


	@Override
	public Tache build() {
		
		return this;
	}



}