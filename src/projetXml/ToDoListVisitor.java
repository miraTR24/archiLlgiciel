package projetXml;


public interface ToDoListVisitor {
	
		public void visitorSimpleTache(SimpleTache simpleTache, String pathFile);
		
		public void visitorBoolTache(BoolTache boolTache, String pathFile);
		
		public void visitorComplexTache(ComplexTache commplexTache, String pathFile);
		
		public void visitorTodoListImpl(TodoListImpl toDoList, String pathFile);

}
