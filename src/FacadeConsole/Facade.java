package FacadeConsole;

import ToDoListOperation.TodoListImpl;
import XmlParser.XMLParser;

public class Facade implements IFacade{

	@Override
	public void readToDoList(String filePath, TodoListImpl toDoList) {
		 System.out.println("Chargement de toutes les tâches depuis : " + filePath);
	        if (toDoList != null) {
	            XMLParser parseXml  = new XMLParser();
	            parseXml.parseXml(toDoList, filePath);
	            toDoList.displayTasks(); // Affiche toutes les tâches
	        } else {
	            System.out.println("La liste de tâches est vide ou non initialisée.");
	        }
		
	}

	@Override
	public void readTopFiveToDoList(String filePath, TodoListImpl toDoList) {
		  System.out.println("Chargement du top 5 des tâches non complétées...");
	        if (toDoList != null) {
	            XMLParser parseXml  = new XMLParser();
	            parseXml.parseXml(toDoList, filePath);
	            toDoList.displayTopFiveTasks(); // Affiche le top 5
	        } else {
	            System.out.println("La liste de tâches est vide ou non initialisée.");
	        }
		
	}

}
