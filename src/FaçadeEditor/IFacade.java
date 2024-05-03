package FaçadeEditor;

import ToDoListOperation.ComplexTache;
import ToDoListOperation.Tache;

public interface IFacade {

	
	public void createComplexTask(); 
	public void createSimpleTask();
	public void createBooleanTask();
	public void importXML();
	public void saveToDoList();
	public void modifyTask(Tache task);
	public void deleteTask(Tache task);
	
	
	// console methode 
	
}
	
	
	

