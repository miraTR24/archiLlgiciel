package projetXml;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    	
    	
    	
    	
    	
    	TodoListImpl todoList = new TodoListImpl();

        // Créez une instance de votre factory
        TacheFactory factory = new concreateTacheFactoryBuilder();

        // Créez une liste de sous-tâches
        List<Tache> subTasks = new ArrayList<>();
        subTasks.add(factory.createSimpleTache("Acheter des ingrédients", LocalDate.now().plusDays(1), Priorite.MOYENNE, 0, 0));
        subTasks.add(factory.createSimpleTache("Préparer le dîner", LocalDate.now().plusDays(2), Priorite.HAUTE, 0, 0));
        subTasks.add(factory.createSimpleTache("Inviter des amis", LocalDate.now().plusDays(3), Priorite.BASSE, 0, 0));

        // Créez une instance de votre builder de tâches complexes
        TacheBuilder complexTaskBuilder = new ComplexTacheBuilder(factory);
        complexTaskBuilder.setDescription("Organiser un dîner")
                            .setDateEcheance(LocalDate.now().plusDays(3))
                            .setPriorite(Priorite.HAUTE)
                            .addSubtask(factory.createTacheComplexe("Préparer le dîner", LocalDate.now().plusDays(2), Priorite.HAUTE, subTasks));
        Tache complexTask = complexTaskBuilder.build();
        todoList.addTask(complexTask);

        // Affichez la liste des tâches
        todoList.displayTasks();
    	*/
    	
    	   TodoListImpl todoList = new TodoListImpl();
           
           // Appel du parseur XML pour charger les tâches depuis le fichier XML
           XMLParser.parseXml(todoList);

           // Affichage de la TodoList dans la console
           todoList.displayTasks();
    	
    	
    	
    }
}