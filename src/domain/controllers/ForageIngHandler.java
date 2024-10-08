package domain.controllers;

import javax.sound.midi.SysexMessage;

import domain.KUAlchemistsGame;
import domain.Player;

public class ForageIngHandler {
	
	public void forageIngredient() {
		Player player = KUAlchemistsGame.getInstance().getCurrentPlayer();
		System.out.println("foraging ing for player #" + KUAlchemistsGame.getInstance().getCurrentPlayerNo());
		player.forageIngredient();
	}
}
