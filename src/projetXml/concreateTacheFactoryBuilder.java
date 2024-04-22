package projetXml;

import java.time.LocalDate;
import java.util.List;

public class concreateTacheFactoryBuilder implements TacheFactory {

	
	 @Override
	    public Tache createSimpleTache(String description, LocalDate deadline, Priorite priorite, int estimatedDuration, int progress) {
	        return new SimpleTache(description, deadline, priorite, estimatedDuration, progress);
	    }
	 
	 @Override
	    public Tache createTacheBoolean(String description, LocalDate deadline, Priorite priorite, boolean isCompleted) {
	        return new BoolTache(description, deadline, priorite, isCompleted);
	    }
	 
	 @Override
	    public Tache createTacheComplexe(String description, LocalDate deadline, Priorite priorite, List<Tache> subTaches) {
	        return new ComplexTache(description, deadline, priorite, subTaches);
	    }


}
