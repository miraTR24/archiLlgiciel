package projetXml;


public interface ToDoListVisitor {
	
		public void visitorSimpleTache(SimpleTache simpleTache);
		
		public void visitorBoolTache(BoolTache boolTache);
		
		public void visitorComplexTache(ComplexTache commplexTache);
		
		

}
