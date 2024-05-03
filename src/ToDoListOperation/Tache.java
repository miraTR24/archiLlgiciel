package ToDoListOperation;

import java.time.LocalDate;

import XmlExport.ToDoListVisitor;



public abstract class Tache {
	public static int idCounter = 1;
	
		public abstract int getId();
	 	public abstract String getDescription();
	    public abstract LocalDate getDeadline();
	    public abstract Priorite getPriorite();
	    public abstract int getProgress();
	    public abstract int getEstimatedDuration();
	    public abstract void acceptVistor(ToDoListVisitor toDoListVisitor);
	    public abstract void display(); // Afficher la tâche
}