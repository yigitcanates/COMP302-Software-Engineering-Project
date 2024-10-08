package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import domain.controllers.HandlerFactory;
import ui.BoardPage;

public class GameState implements Serializable  {
	
	// From KUAlchemistsGame
	private List<Player> players = new ArrayList<Player>();
	private List<String> recentlyDebunkedPlayers = new ArrayList<String>();
	private int numPlayers;
	private int currentPlayerNo;
	private int turnCounter;
	private int round;
	
	// From IngredientDeck
	private List<Ingredient> ingrDeck;
	private int ingrDeckCardNum;
	
	// From ArtifactDeck
    private List<ArtifactCard> artDeck;
    private int artDeckCardNum;
	
	// From PublicationTrack
    private List<AlchemyMarker> availableAlchemies;
    private List<Ingredient> availableIngredients;
    private List<Theory> publishedTheories;
    
    // Other
	private boolean end;
	private boolean isNewPlayer; // if newPlayer, then players include only one player which is the new player.
	private boolean startGame;
	
	public GameState(boolean isNewPlayer, boolean startGame, boolean end)  {
		
		this.end = end;
		this.isNewPlayer = isNewPlayer;
		this.startGame = startGame;
		
		// From KUAlchemistsGame
		this.players =  KUAlchemistsGame.getPlayers();
		this.recentlyDebunkedPlayers = KUAlchemistsGame.getRecentlyDebunkedPlayers();
		this.numPlayers = KUAlchemistsGame.getNumPlayers();
		this.currentPlayerNo = KUAlchemistsGame.getCurrentPlayerNo();
		this.turnCounter = KUAlchemistsGame.getTurnCounter();
		this.round = KUAlchemistsGame.getRound();
		
		// From IngredientDeck
		this.ingrDeck = IngredientDeck.getInstance().getIngredients();
		this.ingrDeckCardNum = IngredientDeck.getInstance().getCardNum();
		
		// From ArtifactDeck
	    this.artDeck = ArtifactDeck.getInstance().getArtifacts();
	    this.artDeckCardNum = ArtifactDeck.getInstance().getCardNum();
		
	    // From PublicationTrack
	    this.availableAlchemies = PublicationTrack.getInstance().getAvailableAlchemies();
	    this.availableIngredients = PublicationTrack.getInstance().getAvailableIngredients();
	    this.publishedTheories = PublicationTrack.getInstance().getPublishedTheories();
	}
	
	public void updateGameState() {
		
		if (isNewPlayer) {
			KUAlchemistsGame.getInstance().addPlayer(players.get(0));
		}
		
		else {
			// KUAlchemistsGame related
			KUAlchemistsGame.setPlayers(players);
			KUAlchemistsGame.setRecentlyDebunkedPlayers(recentlyDebunkedPlayers);
			KUAlchemistsGame.setNumPlayers(numPlayers);
			KUAlchemistsGame.setCurrentPlayerNo(currentPlayerNo);
			KUAlchemistsGame.setTurnCounter(turnCounter);
			KUAlchemistsGame.setRound(round);
			
			// IngredientDeck related
			IngredientDeck.getInstance().setIngredients(ingrDeck);
			IngredientDeck.getInstance().setCardNum(ingrDeckCardNum);
			
			// ArtifactDeck related
			ArtifactDeck.getInstance().setArtifacts(artDeck);
			ArtifactDeck.getInstance().setCardNum(artDeckCardNum);
			
			// PublicationTrack related
			PublicationTrack.getInstance().setAvailableAlchemies(availableAlchemies);
			PublicationTrack.getInstance().setAvailableIngredients(availableIngredients);
			PublicationTrack.getInstance().setPublishedTheories(publishedTheories);
			
			if (startGame) {
				HandlerFactory.getInstance().getJoinHandler().disposeJoinPage();
				
				BoardPage boardPage = new BoardPage();
				boardPage.setVisible(true);
				boardPage.add(BoardPage.getPanelBoard());
				boardPage.setExtendedState(JFrame.MAXIMIZED_BOTH);
				boardPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				KUAlchemistsGame.getInstance().addEndListener(boardPage);
				KUAlchemistsGame.getInstance().addStateListener(boardPage);
			}
			
			if (end) {
				KUAlchemistsGame.endGame();
			}
			
			else {
				KUAlchemistsGame.getInstance().publishStateEvent();
				System.out.println("Game state updated successfully");
			}
		}
	}
}
