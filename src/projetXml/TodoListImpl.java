package projetXml;

import java.util.ArrayList;
import java.util.Iterator;
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

	    @Override
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
		
		@Override
	    public void removeTaskById(int taskId) {
	        if (tasks == null || tasks.isEmpty()) {
	            System.out.println("Il n'y a pas de tâches.");
	            return;
	        }

	        boolean found = false;

	        Iterator<Tache> it = tasks.iterator();
	        while (it.hasNext()) {
	            Tache task = it.next();
	            if (task.getId() == taskId) {
	                it.remove(); // Supprime la tâche correspondante
	                System.out.println("Tâche avec ID " + taskId + " supprimée.");
	                found = true;
	                break; // Sortie de la boucle après suppression
	            }

	            // Si la tâche est complexe, chercher dans les sous-tâches
	            if (task instanceof ComplexTache) {
	                ComplexTache complexTask = (ComplexTache) task;
	                List<Tache> subTaches = complexTask.getSubTaches();
	                Iterator<Tache> subIt = subTaches.iterator();

	                while (subIt.hasNext()) {
	                    Tache subTask = subIt.next();
	                    if (subTask.getId() == taskId) {
	                        subIt.remove(); // Supprime la sous-tâche
	                        System.out.println("Sous-tâche avec ID " + taskId + " supprimée.");
	                        found = true;
	                        break;
	                    }
	                }

	                // Si la sous-tâche est trouvée, pas besoin de chercher dans les autres tâches
	                if (found) {
	                    break;
	                }
	            }
	        }

	        if (!found) {
	            System.out.println("Aucune tâche ou sous-tâche avec l'ID " + taskId + " trouvée.");
	        }
	    }
		
		  public void replaceTask(Tache oldTask, Tache newTask) {
		        int index = tasks.indexOf(oldTask);
		        if (index != -1) {
		            tasks.set(index, newTask);
		        }
		    }


}
