package ToDoListOperation;

import java.time.LocalDate;
import java.util.List;

import XmlExport.ToDoListVisitor;

import java.util.ArrayList;

public class ComplexTache extends Tache  implements TacheBuilder{
	private int id=0;
	private String description; // Descriptif court (20 caractères)
    private LocalDate deadline; // Date d'échéance
    private Priorite priorite; // Priorité
    private int estimatedDuration; // Durée estimée en jours
    private int progress; // Progression en pourcentage
    private List<Tache> subTaches; // Sous-tâches

    public ComplexTache() {
       
        this.id=Tache.idCounter++;
        this.subTaches = new ArrayList<>();

    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public Priorite getPriorite() {
        return priorite;
    }
    
    @Override
    public int getEstimatedDuration() {
    	 int totalDuration = 0;

         for (Tache subtask : subTaches) {
             totalDuration += subtask.getEstimatedDuration();
         }

         return totalDuration;
     }
    
    // Calcul de la progression pondérée par la durée estimée de chaque sous-tâche
    
    @Override
    public int getProgress() {
    	  int totalProgress = 0;
          int count = 0;
           count = 0;
          for (Tache subtask : subTaches) {
              if (subtask instanceof BoolTache) {
                  BoolTache boolTask = (BoolTache) subtask;
                  totalProgress += boolTask.isCompleted()? 100 : 0;
                  count++;
              } else {
                  totalProgress += subtask.getProgress();
                  count++;
              }
          }

          return count == 0? 0 : totalProgress / count;
      }
    
    
    public List<Tache> getSubTaches() {
        return subTaches;
    }

    // Calcul de l'échéance en prenant la date la plus grande parmi les sous-tâches
    public LocalDate calculateDeadline() {
        LocalDate maxDeadline = null;
        for (Tache subTache : subTaches) {
            if (maxDeadline == null || subTache.getDeadline().isAfter(maxDeadline)) {
                maxDeadline = subTache.getDeadline();
            }
        }
        return maxDeadline;
    }

	@Override
	public void acceptVistor(ToDoListVisitor toDoListVisitor) {
		
		
		toDoListVisitor.visitorComplexTache(this);
		
	}
	@Override
	public void display() {
		System.out.println("Id : " + getId());
	    System.out.println("Description : " + getDescription());
	    System.out.println("Deadline : " + getDeadline());
	    System.out.println("Priorite : " + getPriorite());
	    System.out.println("Estimated Duration : " + getEstimatedDuration());
	    System.out.println(" total Progress is  : " + getProgress() + "%");
	    System.out.println("________________________________________________ : ");
	    System.out.println("Subtasks : ");
	    for (Tache subTask : subTaches) {
	        subTask.display(); }
	    
	    System.out.println("________________________________________________ : ");
	}

	public void replaceSubtask(int selectedRow, Tache modifiedSubtask) {
	    if (selectedRow >= 0 && selectedRow < subTaches.size()) {
	        subTaches.set(selectedRow, modifiedSubtask);
	    } else {
	        throw new IllegalArgumentException("Invalid selected row: " + selectedRow);
	    }
	}

	  @Override
	    public TacheBuilder setDescription(String description) {
	        this.description = description;
	        return this;
	    }

	    @Override
	    public TacheBuilder setDateEcheance(LocalDate deadline) {
	        this.deadline = deadline;
	        return this;
	    }

	    @Override
	    public TacheBuilder setEstimatedDuration(int estimatedDuration) {
	        this.estimatedDuration = estimatedDuration;
	        return this;
	    }
	    @Override
	    public TacheBuilder setPriorite(Priorite priorite) {
	        this.priorite = priorite;
	        return this;
	    }
	    
	    @Override
	    public TacheBuilder setProgress(int progress) {
	        this.progress = progress;
	        return this;
	    }
	    
	    @Override
	    public TacheBuilder addSubtask(Tache subtask) {
	    	System.out.println(subtask.getDescription());
	    	
	        this.subTaches.add(subtask);
	        return this;
	    }
	    
	    public TacheBuilder setSubTaches(List<Tache> subTaches) {
	        this.subTaches = subTaches;
	        return this;
	    }

	@Override
	public Tache build() {
		 if (description.length() > 20) {
	            throw new IllegalArgumentException("La description ne doit pas dépasser 20 caractères.");
	           }
		 
	        this.deadline = calculateDeadline(); // Calcul de l'échéance
	        this.estimatedDuration = getEstimatedDuration(); // Calcul de la durée estimée
	        this.progress = getProgress(); // Calcul de la progression
		return this;
	}





}