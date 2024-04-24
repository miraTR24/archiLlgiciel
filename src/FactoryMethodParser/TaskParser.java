package FactoryMethodParser;

import org.w3c.dom.Element;

import projetXml.Tache;

public interface TaskParser {
    Tache parseTask(Element taskElement);
}