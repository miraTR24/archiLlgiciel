package FactoryMethodParser;

public class TaskParserFactory {

    public static TaskParser getParser(String taskType) {
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