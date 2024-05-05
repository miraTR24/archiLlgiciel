package Client;

import FacadeMain.Facade; // Importer la classe Facade
import FacadeMain.IFacade;

/**
 * @author KHICHA
 * @author TIRECHE
 * La classe <code>MainClient</code> est la classe principale du client de l'application.
 * Elle contient la méthode <code>main</code> qui initialise et lance l'interface utilisateur.
 */
public class MainClient {
    
    /**
     * Méthode principale qui initialise et lance l'interface utilisateur en appelant la méthode choix() de la Facade.
     * 
     * @param args Les arguments de la ligne de commande (non utilisés dans cette application).
     */
    public static void main(String[] args) {
        IFacade facade = new Facade(); // Créer une instance de la Facade
        facade.choix(); // Appeler la méthode choix() pour afficher le menu
    }
}
