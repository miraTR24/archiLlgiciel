package projetXml;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class TestBuilder {
    public static void main(String[] args) {
    	
    	
       /* 
        * 
        * TacheFactory factory = new concreateTacheFactoryBuilder(); // Instance de l'usine
        BooleanTacheBuilder builder = new BooleanTacheBuilder(factory); // Builder pour créer la tâche
        
        Tache tache = builder
            .setDescription("Test Task")
            .setDateEcheance(LocalDate.now().plusDays(5)) // Deadline dans 5 jours
            .setPriorite(Priorite.HAUTE)
            .build(); // Construire la tâche

        System.out.println("Description: " + tache.getDescription());
        System.out.println("Deadline: " + tache.getDeadline());
        System.out.println("Priorite: " + tache.getPriorite());
        System.out.println("__________________________________________________________ " );  */

    	/*  èèèèèèèèèèèèèèèèèèèèèèèèèèè TACHE BOOL 
    	  TacheFactory factory = new concreateTacheFactoryBuilder();

          // Instancie la TodoList
          TodoListImpl todoList = new TodoListImpl();

          // Utilise le SimpleTacheBuilder pour créer une tâche simple
          SimpleTacheBuilder simpleTacheBuilder = new SimpleTacheBuilder(factory);
          Tache simpleTache1 = simpleTacheBuilder
              .setDescription("Example Task")
              .setDateEcheance(LocalDate.now().plusDays(1))
              .setPriorite(Priorite.MOYENNE)
              .build();
          
          Tache simpleTache2 = simpleTacheBuilder
                  .setDescription("Example Task 2")
                  .setDateEcheance(LocalDate.now().plusDays(1))
                  .setPriorite(Priorite.MOYENNE)
                  .build();


          // Ajoute la tâche à la TodoList
          todoList.addTask(simpleTache1);
          todoList.addTask(simpleTache2);
          // Affiche la TodoList
          System.out.println("Before Removing:");
          todoList.displayTasks();

          // Supprime la tâche de la TodoList
          todoList.removeTask(simpleTache1);

          // Affiche la TodoList après suppression
          System.out.println("\nAfter Removing:");
          todoList.displayTasks();
    	
    	*/
    	
    	
    	
        // Créez une nouvelle instance de TodoList
        TodoListImpl todoList = new TodoListImpl();

        // Créez des tâches simples
        Tache simpleTask1 = new SimpleTache("Faire les courses", LocalDate.now().plusDays(1), Priorite.MOYENNE, 1, 50);
        Tache simpleTask2 = new SimpleTache("Nettoyer la maison", LocalDate.now().plusDays(2), Priorite.BASSE, 2, 20);

        // Ajoutez des sous-tâches à une tâche complexe
        List<Tache> subTasks = new ArrayList<>();
        subTasks.add(new SimpleTache("Préparer le dîner", LocalDate.now().plusDays(3), Priorite.HAUTE, 1, 30));
        subTasks.add(new SimpleTache("Servir le dîner", LocalDate.now().plusDays(3), Priorite.HAUTE, 1, 10));
        
        // Créez une tâche complexe
        Tache complexTask = new ComplexTache("Organiser un dîner", LocalDate.now().plusDays(3), Priorite.HAUTE, subTasks);

        // Ajoutez les tâches à la TodoList
        todoList.addTask(simpleTask1);
        todoList.addTask(simpleTask2);
        todoList.addTask(complexTask);

        // Affichez la liste des tâches
        todoList.displayTasks();

        // Utilisez ToDoListXmlGenerator pour générer le fichier XML
        ToDoListXmlGenerator xmlGenerator = new ToDoListXmlGenerator();
        String xmlFilePath = "ToDoList.xml"; // Chemin du fichier XML à créer

        xmlGenerator.generateXml(todoList, xmlFilePath); // Génération du fichier XML

        // Affichez un message de confirmation
        System.out.println("XML file created: " + new File(xmlFilePath).getAbsolutePath());
    }
}