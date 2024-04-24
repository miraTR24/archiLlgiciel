package projetXml;

import java.time.LocalDate;
import java.util.List;

public interface TacheFactory {
    Tache createSimpleTache(String description, LocalDate deadline, Priorite priorite, int estimatedDuration, int progress);
    Tache createTacheBoolean(String description, LocalDate deadline, Priorite priorite,int estimatedDuration, int progress ,boolean isCompleted);
    Tache createTacheComplexe(String description, LocalDate deadline, Priorite priorite,  int estimatedDuration,int progress,List<Tache> subTaches);
}
