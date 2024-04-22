package projetXml;

import java.time.LocalDate;

public class BoolTache implements Tache {
    private String description;
    private LocalDate deadline;
    private Priorite priorite;
    private boolean isCompleted;

    public BoolTache(String description, LocalDate deadline, Priorite priorite, boolean isCompleted) {
        this.description = description;
        this.deadline = deadline;
        this.priorite = priorite;
        this.isCompleted = isCompleted;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public Priorite getPriorite() {
        return priorite;
    }


}