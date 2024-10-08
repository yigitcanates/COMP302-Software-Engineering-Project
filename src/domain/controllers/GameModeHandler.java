package domain.controllers;

import domain.KUAlchemistsGame;

public class GameModeHandler {
	
	//called in the GameModePage when "GO" button is pressed
	//sets the adapter of the main class and commands it to start the login pages according to the game mode
	public void setGameMode(String gamemode) {
		if (gamemode.equals("online")) {
			KUAlchemistsGame.getInstance().setGameMode("online");
			KUAlchemistsGame.getInstance().startLoginView();
		}
		if (gamemode.equals("offline")) {
			KUAlchemistsGame.getInstance().setGameMode("offline");
			KUAlchemistsGame.getInstance().startLoginView();
		}
	}
}
