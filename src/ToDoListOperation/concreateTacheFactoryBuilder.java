package ToDoListOperation;

import java.time.LocalDate;
import java.util.List;

public class concreateTacheFactoryBuilder implements TacheFactory  {

	@Override
	public TacheBuilder createSimpleTache() {
		// TODO Auto-generated method stub
		return new SimpleTache();
	}

	@Override
	public TacheBuilder createTacheBoolean() {
		// TODO Auto-generated method stub
		return new BoolTache();
	}

	@Override
	public TacheBuilder createTacheComplexe() {
		// TODO Auto-generated method stub
		return new ComplexTache();
	}

	



}
