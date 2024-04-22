package projetXml;

import java.time.LocalDate;

public interface Tache {
    String getDescription();
    LocalDate getDeadline();
    Priorite getPriorite();

}