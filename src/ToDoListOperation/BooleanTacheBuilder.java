package ToDoListOperation;
/*
import java.time.LocalDate;

public class BooleanTacheBuilder implements TacheBuilder {
	
	private TacheFactory factory;
    private String description;
    private LocalDate dateEcheance;
    private Priorite priorite;
    private int estimatedDuration; 
    private int progress;
    private boolean isCompleted;
    
    public BooleanTacheBuilder(TacheFactory factory) { 
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
    public TacheBuilder setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
        return this;
    }

    @Override
    public TacheBuilder setProgress(int progress) {
        this.progress = progress;
        if (progress==100)
        {
			isCompleted=true;
			}
        else isCompleted=false;
        return this;
    }

    @Override
    public Tache build() {
        return factory.createTacheBoolean(description, dateEcheance, priorite,estimatedDuration,progress, isCompleted);
    }

}*/