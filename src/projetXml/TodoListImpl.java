package projetXml;

import java.util.ArrayList;
import java.util.List;

public class TodoListImpl implements ToDoList {

	  private List<Tache> tasks;

	    public TodoListImpl() {
	        this.tasks = new ArrayList<>();
	    }

	    @Override
	    public void addTask(Tache task) {
	        tasks.add(task);
	    }

	    @Override
	    public void removeTask(Tache task) {
	        tasks.remove(task);
	    }

	    @Override
	    public List<Tache> getAllTasks() {
	        return tasks;
	    }

	    public void displayTasks() {
	        System.out.println("=== Todo List ===");
	        for (int i = 0; i < tasks.size(); i++) {
	            System.out.println((i + 1) + ". " + tasks.get(i).getDescription());
	        }
	        System.out.println("=================");
	    }

}
