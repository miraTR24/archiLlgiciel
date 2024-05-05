package FacadeMain;

import java.util.Scanner;

import javax.swing.SwingUtilities;

import FacadeConsole.LauncherConsole;
import FacadeMain.IFacade;
import FaçadeEditor.LauncherApp;

/**
 * @author KHICHA
 * @author TIRECHE
 * La classe <code>Facade</code> implémente l'interface <code>IFacade</code> et permet à l'utilisateur de choisir quelle application utiliser.
 */
public class Facade implements IFacade {

    /**
     * Permet à l'utilisateur de choisir quelle application utiliser.
     */
    @Override
    public void choix() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choisissez l'application à utiliser :");
        System.out.println("1. Lancer l'application console");
        System.out.println("2. Lancer l'application éditeur");
        System.out.println("Entrez le numéro de votre choix : ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                // Lancer l'application console
                LauncherConsole.start();
                break;

            case 2:
                // Lancer l'application éditeur
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
