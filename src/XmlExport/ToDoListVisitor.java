package XmlExport;

import ToDoListOperation.BoolTache;
import ToDoListOperation.ComplexTache;
import ToDoListOperation.SimpleTache;

public interface ToDoListVisitor {
	
		public void visitorSimpleTache(SimpleTache simpleTache);
		
		public void visitorBoolTache(BoolTache boolTache);
		
		public void visitorComplexTache(ComplexTache commplexTache);
		
		

}
