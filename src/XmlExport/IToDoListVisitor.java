package XmlExport;

import ToDoListOperation.BoolTache;
import ToDoListOperation.ComplexTache;
import ToDoListOperation.SimpleTache;

/**
 * @author KHICHA
 * @author TIRECHE
 * L'interface <code>IToDoListVisitor</code> définit les méthodes pour visiter différents types de tâches dans une liste de tâches.
 */
public interface IToDoListVisitor {
    
    /**
     * Visite une tâche simple.
     * 
     * @param simpleTache La tâche simple à visiter.
     */
    void visitorSimpleTache(SimpleTache simpleTache);
    
    /**
     * Visite une tâche booléenne.
     * 
     * @param boolTache La tâche booléenne à visiter.
     */
    void visitorBoolTache(BoolTache boolTache);
    
    /**
     * Visite une tâche complexe.
     * 
     * @param complexTache La tâche complexe à visiter.
     */
    void visitorComplexTache(ComplexTache complexTache);
}
