package FactoryMethodParser;

import org.w3c.dom.Element;

import projetXml.Tache;

public interface ITaskParserFactory {
    Tache parseTask(Element taskElement);
}