package ToDoListOperation;

/**
 * @author KHICHA
 * @author TIRECHE
 * Cette interface  <code>ITacheFactory</code>  définit une fabrique de constructeurs de tâches pour la création de différents types de tâches.
 */
public interface ITacheFactory {

    /**
     * Crée et retourne un constructeur de tâches simples.
     * @return Un constructeur de tâches simples.
     */
    TacheBuilder createSimpleTache();

    /**
     * Crée et retourne un constructeur de tâches booléennes.
     * @return Un constructeur de tâches booléennes.
     */
    TacheBuilder createTacheBoolean();

    /**
     * Crée et retourne un constructeur de tâches complexes.
     * @return Un constructeur de tâches complexes.
     */
    TacheBuilder createTacheComplexe();
}
