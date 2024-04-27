package Façade;

public interface IFacade {

	// creer une liste
	public void createTodoList(String namePlayList);
	// charger une liste stocke� dans un fichier xml
	public void loadTodoList(String pathPlayList);
	// choix du commande 
	public String choiceCommand(String msg);	
}
	
	
	

