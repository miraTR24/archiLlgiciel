// TestBuilder.java
package ToDoListOperation;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import XmlParser.XMLParser;

public class TestBuilder {
    public static void main(String[] args) {
        try {
        	
            // Créez une nouvelle instance de TodoList
            TodoListImpl todoList = new TodoListImpl();
        	
        	
        	 TacheFactory factory = new concreateTacheFactoryBuilder();

             // Create a simple task
             TacheBuilder tacheSimpleBuilder = factory.createSimpleTache();
             Tache tacheSimple = tacheSimpleBuilder
                     .setDescription("Tâche simple")
                     .setDateEcheance(LocalDate.now())
                     .setPriorite(Priorite.BASSE)
                     .setEstimatedDuration(2)
                     .setProgress(30)
                     .build();

             // Create a boolean task
             TacheBuilder tacheBooleanBuilder = factory.createTacheBoolean();
             Tache tacheBooleenne = tacheBooleanBuilder
                     .setDescription("Tâche booléenne")
                     .setDateEcheance(LocalDate.now())
                     .setPriorite(Priorite.HAUTE)
                     .setProgress(100)
                     .build();

             // Create a complex task
             TacheBuilder tacheComplexeBuilder = factory.createTacheComplexe();
             Tache tacheComplexe = tacheComplexeBuilder
                     .setDescription("Tâche complexe")
                     .setPriorite(Priorite.HAUTE)
                     .setEstimatedDuration(4)
                     .addSubtask(tacheSimple)
                     .build();
         
        	
        		
             todoList.addTask(tacheComplexe);
              todoList.addTask(tacheSimple);
              todoList.addTask(tacheBooleenne);
             //  todoList.addTask(boolTask);
             //   todoList.addTask(complexTask);
          //   todoList.removeTaskById(2);;
        
             TodoListImpl todoList1 = new TodoListImpl();
             XMLParser parseXml  = new XMLParser();
             String xmlFilePath = "xml/ToDoList222.xml"; // Chemin du fichier XML à créer
             parseXml.parseXml(todoList1, xmlFilePath);
             todoList1.displayTasks();
        	
        	
        /*
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	


            // Créez des tâches simples
            Tache simpleTask1 = new SimpleTache("hahahah", LocalDate.now().plusDays(1), Priorite.MOYENNE, 1, 50);
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
         //   todoList.removeTaskById(2);;

            // Affichez la liste des tâches
            todoList.displayTasks();

            // Utilisez EnregistrerVisitor pour générer le fichier XML
            EnregistrerVisitor visitor = new EnregistrerVisitor();
            String xmlFilePath = "xml/ToDoList222.xml"; // Chemin du fichier XML à créer
            System.out.println("-----------------------------------------------------------------");

            // Appel du visiteur pour créer le fichier XML
            visitor.visitorTodoListImpl(todoList, xmlFilePath);

            System.out.println("Fichier XML créé : " + new File(xmlFilePath).getAbsolutePath());
System.out.println("--------------sssss--------ddddf--------------");

            TodoListImpl todoList1 = new TodoListImpl();
            XMLParser parseXml  = new XMLParser();
            parseXml.parseXml(todoList1, xmlFilePath);

            // Affichez la TodoList importée
            todoList1.displayTasks();*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}