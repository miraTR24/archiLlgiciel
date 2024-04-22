package projetXml;

import java.time.LocalDate;

public class BooleanTacheBuilder implements TacheBuilder {
	
	private TacheFactory factory;
    private String description;
    private LocalDate dateEcheance;
    private Priorite priorite;
    private boolean isCompleted;
    
    public BooleanTacheBuilder(TacheFactory factory) { // Constructeur avec TacheFactory
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
        throw new UnsupportedOperationException("Cannot add subtask to BooleanTache");
    }

    public BooleanTacheBuilder setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
        return this;
    }

    @Override
    public Tache build() {
        return factory.createTacheBoolean(description, dateEcheance, priorite, isCompleted);
    }
}