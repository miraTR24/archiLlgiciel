package Client;

import FacadeMain.Facade; // Importer la classe Facade
import FacadeMain.IFacade;

public class MainClient {
    public static void main(String[] args) {
        IFacade facade = new Facade(); // Créer une instance de la Facade
        facade.choix(); // Appeler la méthode choix() pour afficher le menu
    }
}
