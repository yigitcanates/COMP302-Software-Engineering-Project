package domain.controllers;

import java.util.List;

import domain.KUAlchemistsGame;

public class LoginHandler {
	
	public void login(List<String> usernames, List<Integer> avatars, Integer numPlayers) {
		for (int i = 0; i < numPlayers; i++) {
			KUAlchemistsGame.getInstance().createPlayer(usernames.get(i), avatars.get(i));
		}
	}
	
}