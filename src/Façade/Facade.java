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
		// TODO Auto-generated method stub

	}

	@Override
	public String choiceCommand(String msg) {
	    Scanner sc = new Scanner(System.in);
        System.out.println(msg);
        String command = sc.nextLine();
        return command;
	}

	
private void welcomePlayList(String nameTodoList, int level) {
		
		boolean start = false;
		System.out.println(" ############################################################################\r\n"
						 + "     ######## Welcome to the todolist : " + nameTodoList + " #########\r\n"
						 + " ############################################################################\r\n");
		do{
			String msg =  " ###############  ** Commands ** ###############\r\n"
					+ "        save in xml            ===>  Save < name file xml ex : save toto.xpl>\r\n"
            		+ "        display list         ===>  List\r\n"
            		+ "        enter            ===>  Enter <num sublist>\r\n"
            		+ "        return           ===>  Goup\r\n"
            		+ "        import file      ===>  import < path ( file / folder / sublist ) >\r\n"
            		+ "        exit  list       ===>  exit\r\n"
            		+ "      Please choose a valid choice ! ";
			// choix du commande
        	String command = choiceCommand(msg);
        	// analyser la commande
        	String [] commandTab = command.split(" ");
        	
	        // check le syntax de la commande 
        	if(commandTab.length >= 1 && commandTab.length < 3) {
	    	  
		        switch(commandTab[0]){
		        	// sauvgarder un playList dans un fichier xxx.xpl
		            case "SAVE":
		            case "Save":
		            case "save":
		            	
		            	if(commandTab.length < 2) { 
		            		System.out.println("Bad Command, Retry.");
		            	}else {
		            		// le nom de fichier xml
			            	String pathname = commandTab[1];
		            	
		            		if (pathname.endsWith(".xml")) {
		            	
		            		// en utilse le visteur pour sauvgarder un playList
		            			EnregistrerVisitor visitor = new EnregistrerVisitor();
		                        String xmlFilePath = "xml/"+nameTodoList; // Chemin du fichier XML à créer
		            			
		            			
		            	   // Appel du visiteur pour créer le fichier XML
			                visitor.visitorTodoListImpl(todoList, xmlFilePath);
			            	// fin save
			            	System.out.println("Fin Save");
			            	
			                // sinon un problme de sauvgarde
		            	    }else System.out.println("Unknown file extension !! ");
		            	}
		                 	
		            break;
		            // lister les entrer de la playList
		            case "LIST":
		            case "List":
		            case "list":
		            	
		            	if(commandTab.length > 1) { 
		            		System.out.println("Bad Command, Retry.");
		            	}else {
		            		//start=true;
			            	System.out.println("Your List << " + nameTodoList + " >> contains : ");
			            	// lister les entrer de la playList
			            	todoList.displayTasks();
		            	}
		            			            	
		            break;
		            // enter dans un sous list
		            case "ENTER":
		            case "Enter":
		            case "enter":
		            	if(commandTab.length < 2) { 
		            		System.out.println("Bad Command, Retry.");
		            	}else {
							try {
							
									
								// enter dans le sous list
					
			            	} catch(NumberFormatException e) {
			            		System.out.println(" Bad command !! ");
			            	}
		            	}
						
		            break;
		            // remonter dans le pere d'un sous liste
		            case "GOUP":
		            case "Goup":
		            case "goup":
		            	
		            
		            	
		            break;
		            
		            case "IMPORT":
		            case "Import":
		            case "import":
		            	if(commandTab.length < 2) { 
		            		System.out.println("Bad Command, Retry.");
		            	}else {
			            	// importer une sous list
			            	String path = commandTab[1];
		                   	
			                XMLParser parseXml  = new XMLParser();
			                parseXml.parseXml(todoList, path);
			            	//XmlPlayList xmlplayList = new XmlPlayList();
							// en utilisant le builder
							//xmlplayList.loadFile(path);
							
				            System.out.println("Fin import");
		            	}
		            	break;
		            // quitter un playList
		            case "EXIT":
		            case "Exit":
		            case "exit":
		            	
		            	if(commandTab.length > 1) { 
		            		System.out.println("Bad Command, Retry.");
		            	}else {
			            	System.out.println("You left the "+ nameTodoList);
			            	start=true;
		            	}
		            break;
		            
		            default :
		                System.out.println("Bad Command, Retry."); 
		        }
        	}else System.out.println("Bad Command, Retry.");
		}while(start!=true);

	}
	
}



