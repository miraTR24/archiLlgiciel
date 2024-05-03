package FacadeConsole;
import projetXml.ComplexTache;
import projetXml.TodoListImpl;
public interface IFacade {
	
	
	
	 void readToDoList( String filePath,TodoListImpl toDoList ) ;
	
	 void readTopFiveToDoList( String filePath,TodoListImpl toDoList);


}
