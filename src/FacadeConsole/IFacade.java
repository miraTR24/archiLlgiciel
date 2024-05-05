package FacadeConsole;

import ToDoListOperation.TodoListImpl;

/**
 * @author KHICHA
 * @author TIRECHE
 * Cette interface définit les méthodes pour lire une liste de tâches depuis un fichier et afficher les cinq premières tâches.
 */
public interface IFacade {
	
	/**
	 * Lit une liste de tâches à partir d'un fichier spécifié et la charge dans l'implémentation de la liste de tâches fournie.
	 * @param filePath Le chemin du fichier à partir duquel charger la liste de tâches.
	 * @param toDoList L'implémentation de la liste de tâches dans laquelle charger les tâches.
	 */
	 void readToDoList(String filePath, TodoListImpl toDoList);
	
	/**
	 * Lit une liste de tâches à partir d'un fichier spécifié et affiche les cinq premières tâches.
	 * @param filePath Le chemin du fichier à partir duquel lire la liste de tâches.
	 * @param toDoList L'implémentation de la liste de tâches à utiliser pour stocker les tâches.
	 */
	 void readTopFiveToDoList(String filePath, TodoListImpl toDoList);
}
