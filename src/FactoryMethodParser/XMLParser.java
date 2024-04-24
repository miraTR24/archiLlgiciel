// XMLParser.java
package FactoryMethodParser;

import org.w3c.dom.*;

import projetXml.Tache;
import projetXml.TodoListImpl;

import javax.xml.parsers.*;
import java.io.*;
import java.time.LocalDate;

public class XMLParser {

    public static void parseXml(TodoListImpl todoList, String pathFile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(pathFile));

            Element root = document.getDocumentElement();
            NodeList taskNodes = root.getChildNodes();

            for (int i = 0; i < taskNodes.getLength(); i++) {
                Node taskNode = taskNodes.item(i);
                if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element taskElement = (Element) taskNode;
                    Tache task = createTaskFromElement(taskElement);
                    todoList.addTask(task);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Tache createTaskFromElement(Element taskElement) {
        String taskType = taskElement.getTagName();
        TaskParser parser = TaskParserFactory.getParser(taskType);
        return parser.parseTask(taskElement);
    }
}