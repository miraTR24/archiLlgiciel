package projetXml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class TodoListImpl implements ToDoList {

    private List<Tache> tasks;
    private Document document;
    private String name;

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
                it.remove();
                System.out.println("Tâche avec ID " + taskId + " supprimée.");
                found = true;
                break;
            }

            // Si la tâche est complexe, cherchez dans les sous-tâches
            if (task instanceof ComplexTache) {
                ComplexTache complexTask = (ComplexTache) task;
                List<Tache> subTasks = complexTask.getSubTaches();
                Iterator<Tache> subIt = subTasks.iterator();

                while (subIt.hasNext()) {
                    Tache subTask = subIt.next();
                    if (subTask.getId() == taskId) {
                        subIt.remove();
                        System.out.println("Sous-tâche avec ID ");
                        found = true;
                        break;
                    }
                }

                if (found) {
                    break; // Sortie si trouvé
                }
            }
        }

        if (!found) {
            System.out.println("Aucune tâche ou sous-tâche avec l'ID " );
        }
    }
@Override
    public void replaceTask(Tache oldTask, Tache newTask) {
        int index = tasks.indexOf(oldTask);
        if (index != -1) {
            tasks.set(index, newTask);
        }
    }
@Override
    public void displayTopFiveTasks() {
        List<Tache> nonCompletedTasks = this.tasks.stream()
            .filter(t -> {
                if (t instanceof BoolTache) {
                    return !((BoolTache) t).isCompleted();
                }
                return t.getProgress() < 100;
            })
            .collect(Collectors.toList());

        List<Tache> sortedTasks = nonCompletedTasks.stream()
            .sorted(Comparator.comparing(Tache::getDeadline))
            .collect(Collectors.toList());

        List<Tache> top5EarliestTasks = sortedTasks.stream()
            .limit(5)
            .collect(Collectors.toList());

        System.out.println("Les 5 tâches non complétées avec les échéances les plus proches sont :");
        top5EarliestTasks.forEach(Tache::display);
    }
}
