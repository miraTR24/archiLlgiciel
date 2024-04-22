package projetXml;

import java.time.LocalDate;

public class SimpleTache implements Tache {
    private String description;
    private LocalDate deadline;
    private Priorite priorite;
    private int estimatedDuration;
    private int progress;

    public SimpleTache(String description, LocalDate deadline, Priorite priorite, int estimatedDuration, int progress) {
        this.description = description;
        this.deadline = deadline;
        this.priorite = priorite;
        this.estimatedDuration = estimatedDuration;
        this.progress = progress;
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

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public int getProgress() {
        return progress;
    }
}
