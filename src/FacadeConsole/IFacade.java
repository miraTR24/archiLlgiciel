package FacadeConsole;
import ToDoListOperation.ComplexTache;
import ToDoListOperation.TodoListImpl;
public interface IFacade {
	
	
	
	 void readToDoList( String filePath,TodoListImpl toDoList ) ;
	
	 void readTopFiveToDoList( String filePath,TodoListImpl toDoList);


}
