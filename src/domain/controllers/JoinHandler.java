package domain.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

import domain.Client;
import domain.GameState;
import domain.KUAlchemistsGame;
import ui.JoinWaitPage;

public class JoinHandler {
	
	private Client client;
	private ExecutorService pool;
	private JoinWaitPage joinPage;
	private boolean joinPageOpen;
	
	public void connectToServer(String address, String port) {
		int portNo = Integer.valueOf(port);
		client = new Client(address, portNo);
		pool = Executors.newCachedThreadPool();
		pool.execute(client);
	}
	
	public void broadcastGameState(boolean isNewPlayer, boolean start, boolean quit){
		GameState state = new GameState(isNewPlayer, start, quit);
		client.broadcastChange(state);
	}
	
	public void login(String username, int avatar) {
		KUAlchemistsGame.getInstance().createPlayer(username, avatar);
		KUAlchemistsGame.getInstance().setDevicePlayer(username);
		broadcastGameState(true, false, false);
	}
	
	public void startGame() {
		broadcastGameState(false, true, false);
	}

	public void openJoinWaitPage() {
		joinPage = new JoinWaitPage();
		joinPage.setVisible(true);
		joinPage.add(joinPage.getPanel());
		joinPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		joinPage.setSize(600, 600);
		
		joinPageOpen = true;
	}
	
	public void disposeJoinPage() {
		if (joinPageOpen) {
			joinPage.disposePage();
		}
	}
}