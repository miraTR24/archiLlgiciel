package projetXml;

import java.time.LocalDate;

public class BoolTache extends Tache {
	private int id=0;
	private String name;
    private String description;
    private LocalDate deadline;
    private Priorite priorite;
    private int estimatedDuration;
    private int progress;
    private boolean isCompleted;

    public BoolTache(String description, LocalDate deadline, Priorite priorite,int estimatedDuration,int progress, boolean isCompleted) {
    	this.id=Tache.idCounter++;
        this.description = description;
        this.deadline = deadline;
        this.priorite = priorite;
        this.estimatedDuration=estimatedDuration;
        this.progress=progress;
        this.isCompleted = isCompleted;
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
        return deadline;
    }

    @Override
    public Priorite getPriorite() {
        return priorite;
    }
    
    // Getter pour isCompleted
    public boolean isCompleted() {
        return isCompleted;
    }

    // Setter pour isCompleted
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
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
	public void acceptVistor(ToDoListVisitor toDoListVisitor, String pathname) {
	
			
			name = pathname;
			toDoListVisitor.visitorBoolTache(this, pathname);
		
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



}