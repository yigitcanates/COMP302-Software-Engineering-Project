package domain;

import javax.swing.JFrame;

import ui.LoginPage;

public class OfflineAdapter implements IGameAdapter {

	//the old login page is created with 4 player option
	@Override
	public void startLoginView() {
		
		LoginPage loginPage = new LoginPage(); 	
		loginPage.add(loginPage.getPanelLogin());
		loginPage.setExtendedState(JFrame.MAXIMIZED_BOTH);
		loginPage.setVisible(true);
		loginPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}

	@Override
	public void switchTurn() {
		KUAlchemistsGame.publishTurnEvent();
		
	}

	@Override
	public void sendEndGame() {
	}
}