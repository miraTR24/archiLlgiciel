package XmlParser;

import org.w3c.dom.Element;

import ToDoListOperation.Tache;

public interface ITaskParserFactory {
    Tache parseTask(Element taskElement);
}