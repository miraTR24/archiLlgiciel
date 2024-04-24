package projetXml;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestBuilder {
    public static void main(String[] args) {
        try {
            // Créez une nouvelle instance de TodoList
            TodoListImpl todoList = new TodoListImpl();

            // Créez des tâches simples
            Tache simpleTask1 = new SimpleTache("Faire les courses", LocalDate.now().plusDays(1), Priorite.MOYENNE, 1, 50);
            Tache simpleTask2 = new SimpleTache("Nettoyer la maison", LocalDate.now().plusDays(2), Priorite.BASSE, 2, 20);

            // Créez une tâche booléenne avec tous les paramètres
            Tache boolTask = new BoolTache("Tondre la pelouse", LocalDate.now().plusDays(3), Priorite.MOYENNE, 2, 10, false);

            // Ajoutez des sous-tâches à une tâche complexe
            List<Tache> subTasks = new ArrayList<>();
            subTasks.add(new SimpleTache("Préparer le dîner", LocalDate.now().plusDays(6), Priorite.BASSE, 1, 30));
            subTasks.add(new SimpleTache("Servir le dîner", LocalDate.now().plusDays(3), Priorite.HAUTE, 1, 10));

            // Créez une tâche complexe avec les sous-tâches
            Tache complexTask = new ComplexTache("Organiser un dîner", Priorite.HAUTE, subTasks);

            // Ajoutez les tâches à la TodoList
            todoList.addTask(simpleTask1);
            todoList.addTask(simpleTask2);
            todoList.addTask(boolTask);
            todoList.addTask(complexTask);

            // Affichez la liste des tâches
            todoList.displayTasks();

            // Utilisez ToDoListXmlGenerator pour générer le fichier XML
            ToDoListXmlGenerator xmlGenerator = new ToDoListXmlGenerator();
            String xmlFilePath = "xml/ToDoList.xml"; // Chemin du fichier XML à créer

            xmlGenerator.generateXml(todoList, xmlFilePath); // Génération du fichier XML

            // Affichez un message de confirmation
            System.out.println("Fichier XML créé : " + new File(xmlFilePath).getAbsolutePath());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
