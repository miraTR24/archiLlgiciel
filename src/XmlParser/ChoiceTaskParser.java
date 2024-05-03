package XmlParser;

public class ChoiceTaskParser {

    public static ITaskParserFactory getParser(String taskType) {
       	
    	switch (taskType) {
            case "simpleTask":
                return new SimpleTaskParser();
            case "booleanTask":
                return new BoolTaskParser();
            case "complexTask":
                return new ComplexTaskParser();
            default:
                throw new IllegalArgumentException("ce type de tache existe pas: " + taskType);
        }
    }
    
    
}