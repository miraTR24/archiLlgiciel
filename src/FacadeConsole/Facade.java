package FacadeConsole;

import ToDoListOperation.TodoListImpl;
import XmlParser.XMLParser;

/**
 * @author KHICHA
 * @author TIRECHE
 * Cette classe implémente l'interface IFacade pour fournir des méthodes de lecture de tâches à partir d'un fichier.
 */
public class Facade implements IFacade {

    /**
     * Charge toutes les tâches à partir du fichier spécifié et les affiche.
     * @param filePath Le chemin du fichier à partir duquel charger les tâches.
     * @param toDoList L'implémentation de la liste de tâches dans laquelle charger les tâches.
     */
    @Override
    public void readToDoList(String filePath, TodoListImpl toDoList) {
        System.out.println("Chargement de toutes les tâches depuis : " + filePath);
        if (toDoList != null) {
            XMLParser parseXml = new XMLParser();
            parseXml.parseXml(toDoList, filePath);
            toDoList.displayTasks(); // Affiche toutes les tâches
        } else {
            System.out.println("La liste de tâches est vide ou non initialisée.");
        }
    }

    /**
     * Charge les cinq premières tâches non complétées à partir du fichier spécifié et les affiche.
     * @param filePath Le chemin du fichier à partir duquel charger les tâches.
     * @param toDoList L'implémentation de la liste de tâches dans laquelle charger les tâches.
     */
    @Override
    public void readTopFiveToDoList(String filePath, TodoListImpl toDoList) {
        System.out.println("Chargement du top 5 des tâches non complétées...");
        if (toDoList != null) {
            XMLParser parseXml = new XMLParser();
            parseXml.parseXml(toDoList, filePath);
            toDoList.displayTopFiveTasks(); // Affiche le top 5
        } else {
            System.out.println("La liste de tâches est vide ou non initialisée.");
        }
    }
}
