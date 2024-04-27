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
        IFacade ifacade = new Facade(); // Interface de façade

        while (!start) {
            String msg = "    #################### TodoList Editor ##################\r\n"
                    + "               La commande             ===> Son Explication \r\n"
                    + "               New <name list>\\r\\n   ===>   Créer une nouvelle liste de tâches \r\n"
                    + "               Load <path list>\\r\\n  ===>   Charger une liste de tâches \r\n"
                    + "         Faites votre choix SVP 🤔! ";

            // Obtenir la commande de l'utilisateur
            String command = ifacade.choiceCommand(msg).trim().toLowerCase();
            String[] commandTab = command.split(" ");

            // Vérification de la syntaxe de la commande
            if (commandTab.length == 2) {
                String mainCommand = commandTab[0];

                switch (mainCommand) {
                    case "new":
                        start = true;
                        String nameTodoList = commandTab[1];
                        ifacade.createTodoList(nameTodoList);
                        System.out.println("Création de la TodoList: " + nameTodoList);
                        break;

                    case "load":
                        start = true;
                        String pathFile = commandTab[1];
                        ifacade.loadTodoList(pathFile);
                        System.out.println("Chargement de la TodoList depuis le fichier: " + pathFile);
                        break;

                    default:
                        System.out.println("Commande invalide, réessayez.");
                }
            } else {
                System.out.println("Commande invalide, réessayez.");
            }
        }
    }

	
	 
}
