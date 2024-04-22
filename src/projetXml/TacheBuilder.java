package projetXml;

import java.time.LocalDate;

public interface TacheBuilder {

	TacheBuilder setDescription(String description);
    TacheBuilder setDateEcheance(LocalDate dateEcheance);
    TacheBuilder setPriorite(Priorite priorite);
    TacheBuilder addSubtask(Tache subtask);
    Tache build();
}
