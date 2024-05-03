package FaçadeEditor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ToDoListOperation.*;
import XmlParser.XMLParser;

public class TaskListFacade {
    private TodoListImpl todoList;

    public TaskListFacade() {
        this.todoList = new TodoListImpl();
    }

    public void loadTaskListFromFile(String filePath) {
        XMLParser.parseXml(todoList, filePath);
    }

    public void addTask(Tache task2) {
        Tache task = task2;
        todoList.addTask(task);
    }

    public void removeTask(String taskDescription) {
        List<Tache> tasks = todoList.getAllTasks();
        for (Tache task : tasks) {
            if (task.getDescription().equals(taskDescription)) {
                todoList.removeTask(task);
                break;
            }
        }
    }

    public void modifyTask(String oldTaskDescription, String newTaskDescription) {
        List<Tache> tasks = todoList.getAllTasks();
        for (Tache task : tasks) {
            if (task.getDescription().equals(oldTaskDescription)) {
            	
                break;
            }
        }
    }

    public List<String> getAllTasks() {
        List<Tache> tasks = todoList.getAllTasks();
        List<String> taskDescriptions = new ArrayList<>();
        for (Tache task : tasks) {
            taskDescriptions.add(task.getDescription());
        }
        return taskDescriptions;
    }

}