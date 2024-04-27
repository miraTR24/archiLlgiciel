package projetXml;

import java.time.LocalDate;

public class SimpleTache extends Tache {
	//private static int idCounter = 0;
	private int id=0;
	private String name;
    private String description;
    private LocalDate deadline;
    private Priorite priorite;
    private int estimatedDuration;
    private int progress;

    public SimpleTache(String description, LocalDate deadline, Priorite priorite, int estimatedDuration, int progress) {
        this.id=Tache.idCounter++;
    	this.description = description;
        this.deadline = deadline;
        this.priorite = priorite;
        this.estimatedDuration = estimatedDuration;
        this.progress = progress;
    }

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
        return deadline;
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
	public void acceptVistor(ToDoListVisitor toDoListVisitor, String pathname) {
		
		name = pathname;
		toDoListVisitor.visitorSimpleTache(this, pathname);
		
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




	
}
