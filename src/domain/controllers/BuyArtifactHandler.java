package domain.controllers;

import domain.KUAlchemistsGame;
import domain.Player;

public class BuyArtifactHandler {
	
	public void buyArtifact() {
		
		Player player = KUAlchemistsGame.getInstance().getCurrentPlayer();
		
		System.out.println("buy artifacts for player #" + KUAlchemistsGame.getInstance().getCurrentPlayerNo());
		
		player.getArtFromDeck();
		
	}
	
}
