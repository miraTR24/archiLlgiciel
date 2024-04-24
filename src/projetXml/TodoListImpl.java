package projetXml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class TodoListImpl implements ToDoList {

	  private List<Tache> tasks;
	  private Document document;
	  String name;

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
	        int[] index = {1};
	        for (Tache task : tasks) {
	            displayTaskRecursively(task, index, 0);
	        }
	        System.out.println("=================");
	    }

	    private void displayTaskRecursively(Tache task, int[] index, int depth) {
	        // Préfixe pour afficher la hiérarchie
	        String prefix = "  ".repeat(depth);

	        // Afficher la tâche avec le bon niveau de profondeur
	        System.out.println(prefix + index[0] + ". " + task.getDescription());

	        index[0]++;

	        if (task instanceof ComplexTache) {
	            List<Tache> subTasks = ((ComplexTache) task).getSubTaches();
	            for (Tache subTask : subTasks) {
	                displayTaskRecursively(subTask, index, depth + 1);
	            }
	        }
	    }

		@Override
		public void acceptVistor(ToDoListVisitor toDoList, String pathname) {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document = db.newDocument();
				this.document = document;
				
				name = pathname;
				toDoList.visitorTodoListImpl(this, pathname);
				
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}	
			
		}

}
