package XmlParser;

/**
 * @author KHICHA 
 * @author TIRECHE
 * La classe <code>ChoiceTaskParser</code> fournit une méthode statique pour obtenir un parser de tâche en fonction du type de tâche spécifié.
 */
public class ChoiceTaskParser {

    /**
     * Retourne un parser de tâche en fonction du type de tâche spécifié.
     * 
     * @param taskType Le type de tâche pour lequel obtenir un parser.
     * @return Un objet implémentant l'interface <code>ITaskParserFactory</code> correspondant au type de tâche spécifié.
     * @throws IllegalArgumentException Si le type de tâche spécifié n'est pas pris en charge.
     */
    public static ITaskParserFactory getParser(String taskType) {
        switch (taskType) {
            case "simpleTask":
                return new SimpleTaskParser();
            case "booleanTask":
                return new BoolTaskParser();
            case "complexTask":
                return new ComplexTaskParser();
            default:
                throw new IllegalArgumentException("Type de tâche non pris en charge : " + taskType);
        }
    }
}
