package Façade;

import projetXml.ComplexTache;
import projetXml.Tache;

public interface IFacade {

	
	public void createComplexTask(); 
	public void createSimpleTask();
	public void createBooleanTask();
	public void importXML();
	public void saveToDoList();
	public void modifyTask(Tache task);
	public void deleteTask(Tache task);
	
	
}
	
	
	

