package ToDoListOperation;

import java.time.LocalDate;

/**
 * Cette classe  <code>concreateTacheFactory</code>implémente l'interface <code>TacheFactory</code> pour la création de différents types de tâches.
 * @author KHICHA
 * @author TIRECHE
 */
public class concreateTacheFactory implements ITacheFactory {

    /**
     * Crée et retourne un constructeur de tâches simples.
     * @return Un constructeur de tâches simples.
     */
    @Override
    public TacheBuilder createSimpleTache() {
        return new SimpleTache();
    }

    /**
     * Crée et retourne un constructeur de tâches booléennes.
     * @return Un constructeur de tâches booléennes.
     */
    @Override
    public TacheBuilder createTacheBoolean() {
        return new BoolTache();
    }

    /**
     * Crée et retourne un constructeur de tâches complexes.
     * @return Un constructeur de tâches complexes.
     */
    @Override
    public TacheBuilder createTacheComplexe() {
        return new ComplexTache();
    }
}
