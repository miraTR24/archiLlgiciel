package Client;

import Façade.Facade;
import Façade.IFacade;

public class LaunchToDoList {
	
	
	public static void main(String[] args) {
		// creer un instance de facade
		LaunchToDoList.launchToDoList();
	}

	private static void launchToDoList() {
		// TODO Auto-generated method stub
		
		boolean start = false;
        do{
        	String msg = "    #################### TodoList Editor ##################\r\n"
        			+ "               La commande             ===> Son Explication \r\n"
            		+ "               New <name list>\\r\\n   ===>   Créer une nouvelle Liste de tache \r\n"
            		+ "               Load <path list>\\r\\n  ===>   Charger une liste de tache  \r\n"
            		+ "         Faite votre choix SVP 🤔! ";
        	
       
        	// le facade 
        	IFacade ifacade = new Facade();
        	// la commande 
        	String command = ifacade.choiceCommand(msg);
        	// analyser la commande
        	String [] commandTab = command.split(" ");
        	
	        // check le syntax de la commande 
        	if(commandTab.length == 2) {
	    	  
		        switch(commandTab[0]){
		        	// new playList
		            case "NEW":
		            case "New":
		            case "new":
		            	// Si le joueur ecrit un de ses 3 mots pour commencer
		                start = true;
		                // la methode create playList avec le nom du playList
		                String nameToDolist =  commandTab[1];
		        		ifacade.createTodoList(commandTab[1]);
		        		
		        		break;
							                
		            case "LOAD":    
		            case "Load":
		            case "load": 
		         
		            	// on charge le fichier xml de la list
		            	// start = true;
		            	// la methode load palyList avec le nom du fichier 
		            	String pathFile =  commandTab[1];
		 //*********************//icici
		        		ifacade.loadTodoList(pathFile);
		        		
		        		
		                break;
		            default :
		                System.out.println("Bad Command, Retry.");     
		        }
	       }else System.out.println("Bad Command, Retry.");
        
        }while(start!=true); 
		
	}

}
