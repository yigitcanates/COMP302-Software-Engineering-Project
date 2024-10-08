package domain;

public interface IGameAdapter {
	
	//abstract method that will be implemented by offline and online adapters
	public void startLoginView();

	public void switchTurn(); 
	
	public void sendEndGame();

}
