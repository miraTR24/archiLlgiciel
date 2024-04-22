package projetXml;

import java.time.LocalDate;

public class SimpleTacheBuilder implements TacheBuilder {
	
	private TacheFactory factory;
    private String description;
    private LocalDate dateEcheance;
    private Priorite priorite;
    private int dureeEstimee;
    private int progress;
    
    public SimpleTacheBuilder(TacheFactory factory) {
        this.factory = factory;
    }

    @Override
    public TacheBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public TacheBuilder setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
        return this;
    }

    @Override
    public TacheBuilder setPriorite(Priorite priorite) {
        this.priorite = priorite;
        return this;
    }

    @Override
    public TacheBuilder addSubtask(Tache subtask) {
        throw new UnsupportedOperationException("Cannot add subtask to SimpleTache");
    }

    @Override
    public Tache build() {
        this.dureeEstimee = 0;
        this.progress = 0;
        return factory.createSimpleTache(description, dateEcheance, priorite, dureeEstimee, progress);
    }
}