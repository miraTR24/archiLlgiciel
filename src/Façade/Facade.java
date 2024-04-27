package Façade;

import java.util.Scanner;

import FactoryMethodParser.XMLParser;
import projetXml.EnregistrerVisitor;
import projetXml.ToDoList;
import projetXml.TodoListImpl;



public class Facade implements IFacade {


	private TodoListImpl todoList;
	@Override
	public void createTodoList(String nameTodoList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadTodoList(String pathTodoList) {
		System.out.println("here.");
		 TodoListImpl todoList1 = new TodoListImpl();
         XMLParser parseXml  = new XMLParser();
         parseXml.parseXml(todoList1, pathTodoList);

         // Affichez la TodoList importée
         todoList1.displayTasks();
         if(todoList1 != null) welcomeTodoList(pathTodoList, todoList1, 1);	

	}


	private void welcomeTodoList(String nameTodoList, ToDoList todoList, int level) {
        boolean start = false;
        System.out.println(" ############################################################################\r\n"
                + "     ######## Welcome to the TodoList : " + nameTodoList + " #########\r\n"
                + " ############################################################################\r\n");

        while (!start) {
            String msg = " ###############  ** Commands ** ###############\r\n"
                    + "        Add             ===>  \r\n"
                    + "        Delete          ===> you have to write *delete idTask* \r\n"
                    + "        Modify            ===>  \r\n"
                    + "        Save           ===>  \r\n"
                    + "        exit  list       ===>  Exit\r\n"
                    + "      Please choose a valid choice! ";

            String command = choiceCommand(msg).trim().toLowerCase();
            String[] commandTab = command.split(" ");

            if (commandTab.length >= 1 && commandTab.length < 3) {
                String mainCommand = commandTab[0];

                switch (mainCommand) {
                    case "add":
                        
                        break;

                    case "delete":
                    	String Id = commandTab[1];
                    	todoList.removeTaskById(Integer.parseInt(Id));
                        System.out.println("suppression de la tache numéro : " + Id);
                        todoList.displayTasks();
                        
                        break;

                    case "modify":
                       
                        break;

                    case "save":
                        
                        
                        break;

                    

                    case "exit":
                        if (commandTab.length > 1) {
                            System.out.println("Bad Command, Retry.");
                        } else {
                            System.out.println("You left the " + nameTodoList);
                            start = true;
                        }
                        break;

                    default:
                        System.out.println("Bad Command, Retry.");
                }
            } else {
                System.out.println("Bad Command, Retry.");
            }
        }
    }

	@Override
    public String choiceCommand(String msg) {
        Scanner sc = new Scanner(System.in);
        System.out.println(msg);
        return sc.nextLine().trim();
    }

	
}



