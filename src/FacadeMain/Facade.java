package FacadeMain;

import java.util.Scanner;

import javax.swing.SwingUtilities;

import FacadeConsole.LauncherConsole;
import FacadeMain.IFacade;
import FaçadeEditor.LauncherApp;

public class Facade implements IFacade{

	@Override
	public void choix() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Choisissez l'application à utiliser :");
        System.out.println("1. Lancer l'application console");
        System.out.println("2. Lancer l'application editeur");
        System.out.println("Entrez le numéro de votre choix : ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                // Lancer l'application console
                LauncherConsole.start();
                break;

            case 2:
                // Lancer l'application editeur
                SwingUtilities.invokeLater(() -> {
                    LauncherApp app = new LauncherApp(); 
                    app.setVisible(true); 
                });
                break;

            default:
                System.out.println("Choix non valide.");
                break;
        }

        scanner.close(); // Fermer le scanner
		
	}

}
