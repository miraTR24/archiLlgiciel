package projetXml;

import java.time.LocalDate;
import java.util.List;

public class ComplexTache implements Tache {
    private String description;
    private LocalDate deadline;
    private Priorite priorite;
    private List<Tache> subTaches;

    public ComplexTache(String description, LocalDate deadline, Priorite priorite, List<Tache> subTaches) {
        this.description = description;
        this.deadline = deadline;
        this.priorite = priorite;
        this.subTaches = subTaches;
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

    /*
    public int getEstimatedDuration() {
        return subTaches.stream().mapToInt(Tache::getEstimatedDuration).sum();
    }

    public int getProgress() {
        double totalProgress = subTaches.stream()
            .mapToDouble(t -> t.getEstimatedDuration() * (t.getProgress() / 100.0))
            .sum(); // Calcule le total des durées pondérées par le pourcentage de progression

        double totalDuration = subTaches.stream()
            .mapToInt(Tache::getEstimatedDuration)
            .sum(); // Calcule la durée totale de toutes les tâches

        double weightedAverageProgress = (totalProgress / totalDuration) * 100; // Calcul de la progression moyenne pondérée en pourcentage

        return (int) Math.round(weightedAverageProgress); // Conversion du résultat en int avec arrondi
    }*/


}