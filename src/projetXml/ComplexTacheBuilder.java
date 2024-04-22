package projetXml;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComplexTacheBuilder implements TacheBuilder {
	
	private TacheFactory factory;
    private String description;
    private LocalDate dateEcheance;
    private Priorite priorite;
    private List<Tache> subtasks = new ArrayList<>();
    
    public ComplexTacheBuilder(TacheFactory factory) {
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
        this.subtasks.add(subtask);
        return this;
    }

    @Override
    public Tache build() {
        return factory.createTacheComplexe(description, dateEcheance, priorite, subtasks);
    }
}
