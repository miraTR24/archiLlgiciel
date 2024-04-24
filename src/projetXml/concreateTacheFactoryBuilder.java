package projetXml;

import java.time.LocalDate;
import java.util.List;

public class concreateTacheFactoryBuilder implements TacheFactory {

	
	 @Override
	    public Tache createSimpleTache(String description, LocalDate deadline, Priorite priorite, int estimatedDuration, int progress) {
	        return new SimpleTache(description, deadline, priorite, estimatedDuration, progress);
	    }
	 
	 @Override
	    public Tache createTacheBoolean(String description, LocalDate deadline, Priorite priorite,int estimatedDuration, int progress ,boolean isCompleted) {
	        return new BoolTache(description, deadline, priorite, estimatedDuration, progress,isCompleted);
	    }
	 
	 @Override
	    public Tache createTacheComplexe(String description, LocalDate deadline, Priorite priorite, int estimatedDuration,int progress,List<Tache> subTaches) {
	        return new ComplexTache(description, priorite, subTaches);
	    }


}
