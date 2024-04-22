package projetXml;

import java.util.List;

public interface ToDoList {

    void addTask(Tache task);
    void removeTask(Tache task);
    List<Tache> getAllTasks();
}
