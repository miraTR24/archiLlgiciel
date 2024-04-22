package projetXml;

import java.time.LocalDate;
import java.util.List;

public interface TacheFactory {
    Tache createSimpleTache(String description, LocalDate deadline, Priorite priorite, int estimatedDuration, int progress);
    Tache createTacheBoolean(String description, LocalDate deadline, Priorite priorite, boolean isCompleted);
    Tache createTacheComplexe(String description, LocalDate deadline, Priorite priorite, List<Tache> subTaches);
}
