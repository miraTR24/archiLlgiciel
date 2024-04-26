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
	            System.out.println("Task " + index[0] + ":");
	            task.display();
	            index[0]++;
	        }
	        System.out.println("=================");
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
