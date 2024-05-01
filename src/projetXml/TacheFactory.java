package projetXml;


public interface TacheFactory {
    TacheBuilder createSimpleTache();
    TacheBuilder createTacheBoolean();
    TacheBuilder createTacheComplexe();
}
