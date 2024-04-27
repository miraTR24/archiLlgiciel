package projetXml;

import java.util.List;


public interface ToDoList {

    void addTask(Tache task);
    void removeTask(Tache task);
    void removeTaskById (int id);
    List<Tache> getAllTasks();
    void displayTasks() ;
    
    public abstract void acceptVistor(ToDoListVisitor toDoList, String pathname);
}
