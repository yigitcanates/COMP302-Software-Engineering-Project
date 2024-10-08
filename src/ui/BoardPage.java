package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import domain.AlchemyMarker;
import domain.ArtListener;
import domain.ArtifactCard;
import domain.Avatar;
import domain.EndListener;
import domain.GameStateListener;
import domain.IngListener;
import domain.Ingredient;
import domain.KUAlchemistsGame;
import domain.PotListener;
import domain.PubListener;
import domain.PublicationTrack;
import domain.Theory;
import domain.TurnListener;
import domain.controllers.HandlerFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class BoardPage extends JFrame implements TurnListener, ActionListener, EndListener, GameStateListener {
	
	private static JPanel waitPanel, panelBoard, player1_ingr, player2_ingr, player3_ingr, player4_ingr,
				   player1_arts, player2_arts, player3_arts, player4_arts,
				   player1_pot, player2_pot, player3_pot, player4_pot;
	private static PotionBrew potionBrewing;
	private static PublicationArea publicationArea;
	private static SellPotionPanel sellPotionPanel;
	private static RoundPanel roundpnl;
	private static JButton help, pause, deductionBoard, turnButton, ingrDeckButton, player_ing,
	artifactDeckButton, player_art;
	private JLabel gold, gold2, gold3, gold4,
				   sickness, sickness2, sickness3, sickness4,
				   reputation,reputation2, reputation3, reputation4,
				   name, avatar, name2, avatar2, name3, avatar3, name4, avatar4;
	
	public BoardPage() {
		super("KUAlchemists");
		
		setPanelBoard(new BackgroundPanel("images/pixil-frame-0.png"));
		getPanelBoard().setLayout(null);
		
		waitPanel = new BackgroundPanel("images/waitpnl.png");
		waitPanel.setLayout(null);
		waitPanel.setBounds(0, 0, 600, 600);
		waitPanel.setVisible(false);
		this.add(waitPanel);
		
		
		
		
		
		help = new JButton();
		pause = new JButton();
		turnButton = new JButton();
		ingrDeckButton = new JButton();
		artifactDeckButton = new JButton();
		
		roundpnl = new RoundPanel();
		roundpnl.setOpaque(false);
		roundpnl.setBounds(1285, 29, 99, 90);
		KUAlchemistsGame.getInstance().addTurnListener(roundpnl);
		getPanelBoard().add(roundpnl);
		

		ingrDeckButton.setIcon(new ImageIcon("images/ingrdeck.png"));
		ingrDeckButton.setBounds(330 - ingrDeckButton.getIcon().getIconWidth(), 340, ingrDeckButton.getIcon().getIconWidth(), ingrDeckButton.getIcon().getIconHeight());
		ingrDeckButton.setMargin(new Insets(0,0,0,0));
		ingrDeckButton.setForeground(Color.BLUE);
		ingrDeckButton.addActionListener(e -> {
        	System.out.println("ingrDeckButton clicked");
        	HandlerFactory.getInstance().getForageIngHandler().forageIngredient();
        	updateGoldUI();
        	showTurnMessage(KUAlchemistsGame.getInstance().getCurrentPlayer().getUsername() + " foraged an ingredient.");
        	ingrDeckButton.setEnabled(false);
        	revalidate();
        	repaint();
        	
        });
        getPanelBoard().add(ingrDeckButton);
        
		artifactDeckButton.setIcon(new ImageIcon("images/artfdeck.png"));
		artifactDeckButton.setMargin(new Insets(0,0,0,0));
		artifactDeckButton.setBounds(355, 340, artifactDeckButton.getIcon().getIconWidth(), artifactDeckButton.getIcon().getIconHeight());
		artifactDeckButton.setForeground(Color.BLUE);
		artifactDeckButton.addActionListener(e -> {
        	System.out.println("artifactDeckButton clicked");
        	if (KUAlchemistsGame.getInstance().getCurrentPlayer().getGold()<3) {
        		showMessage("NOT ENOUGH GOLD!");
        	}
        	else {
	        	HandlerFactory.getInstance().getBuyArtifactHandler().buyArtifact();
	        	updateGoldUI();
	        	System.out.println(KUAlchemistsGame.getInstance().getCurrentPlayer().getArtifacts());
	        	showTurnMessage(KUAlchemistsGame.getInstance().getCurrentPlayer().getUsername() + " bought an artifact.");
	        	artifactDeckButton.setEnabled(false);
        	}
        });
        getPanelBoard().add(artifactDeckButton);
        
        help.setMargin(new Insets(0, 0, 0, 0));
        help.setIcon(new ImageIcon("images/helpbtn.png"));
        help.setFocusPainted(false);
		help.setBounds(1310, 97, help.getIcon().getIconWidth()-14, help.getIcon().getIconHeight()-8);
		help.setForeground(Color.BLACK);
		help.setBackground(Color.WHITE);
		help.addActionListener(e -> showHelpDialog());
		
		getPanelBoard().add(help);
		
		pause.setMargin(new Insets(0, 0, 0, 0));
        pause.setFocusPainted(false);
        pause.setIcon(new ImageIcon("images/pause btn.png"));
		pause.setBounds(1310, 128, pause.getIcon().getIconWidth()-14, pause.getIcon().getIconHeight()-8);
		pause.setForeground(Color.BLACK);
		pause.setBackground(Color.WHITE);
		pause.addActionListener(e -> showPauseDialog());
		
		getPanelBoard().add(pause);
		
        potionBrewing = new PotionBrew(new ImageIcon("images/panelbg.png").getImage());
        sellPotionPanel = new SellPotionPanel(new ImageIcon("images/panelbg.png").getImage());
        sellPotionPanel.setVisible(false);
        KUAlchemistsGame.getInstance().addTurnListener(potionBrewing);
        KUAlchemistsGame.getInstance().addTurnListener(sellPotionPanel);
        publicationArea = new PublicationArea(new ImageIcon("images/panelbg.png").getImage());
        deductionBoard = new JButton("Deduction Board");

        int buttonWidth = 200;
        int buttonHeight = 40;
        
        potionBrewing.setBounds(55, 450, 275, 250);
        potionBrewing.setLayout(null);
        potionBrewing.setBackground(Color.BLUE);
        potionBrewing.updatePotionBrew();
        getPanelBoard().add(potionBrewing);
        
        KUAlchemistsGame.getInstance().addIngListener((PotionBrew) potionBrewing);
        KUAlchemistsGame.getInstance().addIngListener((PotionBrew) potionBrewing);
        
        sellPotionPanel.setBounds(355, 450, 330, 250);
        sellPotionPanel.setLayout(null);
        sellPotionPanel.setBackground(Color.GREEN);
        sellPotionPanel.updatePanel();
        getPanelBoard().add(sellPotionPanel);
        
        KUAlchemistsGame.getInstance().addIngListener((SellPotionPanel) sellPotionPanel);
        KUAlchemistsGame.getInstance().addIngListener((SellPotionPanel) sellPotionPanel);

        publicationArea.setBounds(750, 300, 550, 550);
        publicationArea.setLayout(null);
        publicationArea.setVisible(false);
        publicationArea.setBackground(Color.RED);
        publicationArea.updatePublicationArea();
        getPanelBoard().add(publicationArea);
        
        PublicationTrack.getInstance().addPubListener((PublicationArea) publicationArea);
        

        deductionBoard.setBounds(400, 520, buttonWidth, buttonHeight);
        deductionBoard.setForeground(Color.GREEN);
        deductionBoard.addActionListener(e -> {
        	System.out.println("Deduction Board button clicked");
        });
        
        
        name = new JLabel("Player I: " + KUAlchemistsGame.getInstance().getPlayer(1).getUsername());
        name.setFont(new Font("Bahnschrift", Font.BOLD, 19));
        name.setForeground(new Color(255,225,168));
        name.setBounds(58, 60, 200, 25);
        getPanelBoard().add(name);
        
        avatar = new JLabel();
        avatar.setIcon(Avatar.getAvatarImage(KUAlchemistsGame.getInstance().getPlayer(1).getAvatar()));
        avatar.setBounds(240, 40, 60, 60);
        getPanelBoard().add(avatar);
        
        
        gold = new JLabel("Gold: " + KUAlchemistsGame.getInstance().getPlayer(1).getGold());
        gold.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        gold.setForeground(new Color(255,225,168));
        gold.setBounds(58, 95, 60, 30);
        getPanelBoard().add(gold);

        
        sickness = new JLabel("Sickness: " + KUAlchemistsGame.getInstance().getPlayer(1).getSickness());
        sickness.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        sickness.setForeground(new Color(255,225,168));
        sickness.setBounds(121, 95, 90, 30);
        getPanelBoard().add(sickness);
        
        reputation = new JLabel("Reputation: " + KUAlchemistsGame.getInstance().getPlayer(1).getReputation());
        reputation.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        reputation.setForeground(new Color(255,225,168));
        reputation.setBounds(213, 95, 110, 30);
        getPanelBoard().add(reputation);
        
        player1_arts = new PlayerArts(1, new ImageIcon("images/panelbg.png").getImage());
		player1_arts.setLayout(null);
		player1_arts.setBounds(55, 234, 275, 47);
		player1_arts.setBackground(Color.ORANGE);
		panelBoard.add(player1_arts);
		((PlayerArts) player1_arts).updateArts();
		
		KUAlchemistsGame.getInstance().addArtListener((PlayerArts) player1_arts);
		
		player1_ingr = new PlayerIngs(1, new ImageIcon("images/panelbg.png").getImage());
		player1_ingr.setLayout(null);
		player1_ingr.setBounds(55, 175, 275, 51);
		player1_ingr.setBackground(Color.MAGENTA);
		panelBoard.add(player1_ingr);
		((PlayerIngs) player1_ingr).updateIngs();
		
		KUAlchemistsGame.getInstance().addIngListener((PlayerIngs) player1_ingr);
		
		player1_pot = new PlayerPot(1, new ImageIcon("images/panelbg.png").getImage());
		player1_pot.setLayout(null);
		player1_pot.setBounds(55, 120, 275, 47);
		player1_pot.setBackground(Color.MAGENTA);
		panelBoard.add(player1_pot);
		((PlayerPot) player1_pot).updatePot();
		
		KUAlchemistsGame.getInstance().addPotListener((PlayerPot) player1_pot);
		
        name2 = new JLabel("Player II: " + KUAlchemistsGame.getInstance().getPlayer(2).getUsername());
        name2.setFont(new Font("Bahnschrift", Font.BOLD, 19));
        name2.setForeground(new Color(255,225,168));
        name2.setBounds(358, 60, 200, 25);
        getPanelBoard().add(name2);
        
        avatar2 = new JLabel();
        avatar2.setIcon(Avatar.getAvatarImage(KUAlchemistsGame.getInstance().getPlayer(2).getAvatar()));
        avatar2.setBounds(540, 40, 60, 60);
        getPanelBoard().add(avatar2);
        
        gold2 = new JLabel("Gold: " + KUAlchemistsGame.getInstance().getPlayer(2).getGold());
        gold2.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        gold2.setVisible(false);
        gold2.setForeground(new Color(255,225,168));
        gold2.setBounds(358, 95, 60, 30);
        getPanelBoard().add(gold2);
        
        sickness2 = new JLabel("Sickness: " + KUAlchemistsGame.getInstance().getPlayer(2).getSickness());
        sickness2.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        sickness2.setVisible(false);
        sickness2.setForeground(new Color(255,225,168));
        sickness2.setBounds(421, 95, 90, 30);
        getPanelBoard().add(sickness2);
        
        reputation2 = new JLabel("Reputation: " + KUAlchemistsGame.getInstance().getPlayer(2).getReputation());
        reputation2.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        reputation2.setForeground(new Color(255,225,168));
        reputation2.setBounds(513, 95, 110, 30);
        getPanelBoard().add(reputation2);
        
		player2_arts = new PlayerArts(2, new ImageIcon("images/panelbg.png").getImage());
		player2_arts.setLayout(null);
		player2_arts.setVisible(false);
		player2_arts.setBounds(355, 234, 275, 47);
		player2_arts.setBackground(Color.ORANGE);
		panelBoard.add(player2_arts);
		((PlayerArts) player2_arts).updateArts();

		KUAlchemistsGame.getInstance().addArtListener((PlayerArts) player2_arts);
		
		player2_ingr = new PlayerIngs(2, new ImageIcon("images/panelbg.png").getImage());
		player2_ingr.setLayout(null);
		player2_ingr.setVisible(false);
		player2_ingr.setBounds(355, 175, 275, 51);
		player2_ingr.setBackground(Color.MAGENTA);
		panelBoard.add(player2_ingr);
		((PlayerIngs) player2_ingr).updateIngs();

		KUAlchemistsGame.getInstance().addIngListener((PlayerIngs) player2_ingr);
		
		player2_pot = new PlayerPot(2, new ImageIcon("images/panelbg.png").getImage());
		player2_pot.setLayout(null);
		player2_pot.setBounds(355, 120, 275, 47);
		player2_pot.setBackground(Color.MAGENTA);
		panelBoard.add(player2_pot);
		((PlayerPot) player2_pot).updatePot();
		
		KUAlchemistsGame.getInstance().addPotListener((PlayerPot) player2_pot);
        
        if(KUAlchemistsGame.getNumPlayers()==3) {
        	showPlayer3();
        	KUAlchemistsGame.getInstance().addIngListener((PotionBrew) potionBrewing);
        	KUAlchemistsGame.getInstance().addIngListener((SellPotionPanel) sellPotionPanel);
        }
        
        if(KUAlchemistsGame.getNumPlayers()==4) {
        	showPlayer3();
        	showPlayer4();
        	KUAlchemistsGame.getInstance().addIngListener((PotionBrew) potionBrewing);
        	KUAlchemistsGame.getInstance().addIngListener((SellPotionPanel) sellPotionPanel);
        }
        
        //added for turn change. 
        turnButton.setIcon(new ImageIcon("images/endturn.png"));
        turnButton.setMargin(new Insets(0, 0, 0, 0));
        turnButton.setFocusPainted(false);
		turnButton.setBounds(560, 800, turnButton.getIcon().getIconWidth()-5, turnButton.getIcon().getIconHeight()-10);
		turnButton.addActionListener(e -> {
			nextTurnMessage();
			KUAlchemistsGame.getInstance().switchTurns();
			
		});
		getPanelBoard().add(turnButton);
		
		JButton dBoardButton = new JButton();
		dBoardButton.setIcon(new ImageIcon("images/deductionboard.png"));
		dBoardButton.setBackground(new Color(226, 109, 92));
		dBoardButton.setBounds(240, 750, dBoardButton.getIcon().getIconWidth()+10, dBoardButton.getIcon().getIconHeight());
		dBoardButton.addActionListener(e -> {
			DeductionBoardDialog dialog = new DeductionBoardDialog(this);
			dialog.setLayout(null);
			HandlerFactory.getInstance().getDeductionBoardHandler().addListener(dialog);
			dialog.setSize(620,900);
			dialog.setVisible(true);
        });
        getPanelBoard().add(dBoardButton);
		
		
	}	
	
	private void updateGoldUI() {
		gold.setText("Gold: " + KUAlchemistsGame.getInstance().getPlayer(1).getGold());
		gold2.setText("Gold: " + KUAlchemistsGame.getInstance().getPlayer(2).getGold());
		if (KUAlchemistsGame.getNumPlayers() > 2) {
			gold3.setText("Gold: " + KUAlchemistsGame.getInstance().getPlayer(3).getGold());
		}
		if (KUAlchemistsGame.getNumPlayers() > 3) {
			gold4.setText("Gold: " + KUAlchemistsGame.getInstance().getPlayer(4).getGold());
		}
	}
	
	private void updateReputationUI() {
		reputation.setText("Reputation: " + KUAlchemistsGame.getInstance().getPlayer(1).getReputation());
		reputation2.setText("Reputation: " + KUAlchemistsGame.getInstance().getPlayer(2).getReputation());
		if (KUAlchemistsGame.getNumPlayers() > 2) {
			reputation3.setText("Reputation: " + KUAlchemistsGame.getInstance().getPlayer(3).getReputation());
		}
		if (KUAlchemistsGame.getNumPlayers() > 3) {
			reputation4.setText("Reputation: " + KUAlchemistsGame.getInstance().getPlayer(4).getReputation());
		}
	}
	
	private void updateSicknessUI() {
		sickness.setText("Sickness: " + KUAlchemistsGame.getInstance().getPlayer(1).getSickness());
		sickness2.setText("Sickness: " + KUAlchemistsGame.getInstance().getPlayer(2).getSickness());
		if (KUAlchemistsGame.getNumPlayers() > 2) {
			sickness3.setText("Sickness: " + KUAlchemistsGame.getInstance().getPlayer(3).getSickness());
		}
		if (KUAlchemistsGame.getNumPlayers() > 3) {
			sickness4.setText("Sickness: " + KUAlchemistsGame.getInstance().getPlayer(4).getSickness());
		}
	}

	public void showPlayer3() {
        name3 = new JLabel("Player III: " + KUAlchemistsGame.getInstance().getPlayer(3).getUsername());
        name3.setFont(new Font("Bahnschrift", Font.BOLD, 19));
        name3.setForeground(new Color(255,225,168));
        name3.setBounds(658, 60, 200, 25);
        getPanelBoard().add(name3);
        
        avatar3 = new JLabel();
        avatar3.setIcon(Avatar.getAvatarImage(KUAlchemistsGame.getInstance().getPlayer(3).getAvatar()));
        avatar3.setBounds(840, 40, 60, 60);
        getPanelBoard().add(avatar3);
        
        gold3 = new JLabel("Gold: " + KUAlchemistsGame.getInstance().getPlayer(3).getGold());
        gold3.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        gold3.setVisible(false);
        gold3.setForeground(new Color(255,225,168));
        gold3.setBounds(658, 95, 60, 30);
        getPanelBoard().add(gold3);
        
        sickness3 = new JLabel("Sickness: " + KUAlchemistsGame.getInstance().getPlayer(3).getSickness());
        sickness3.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        sickness3.setForeground(new Color(255,225,168));
        sickness3.setVisible(false);
        sickness3.setBounds(721, 95, 90, 30);
        getPanelBoard().add(sickness3);
        
        reputation3 = new JLabel("Reputation: " + KUAlchemistsGame.getInstance().getPlayer(3).getReputation());
        reputation3.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        reputation3.setForeground(new Color(255,225,168));
        reputation3.setBounds(813, 95, 110, 30);
        getPanelBoard().add(reputation3);
        
		player3_arts = new PlayerArts(3, new ImageIcon("images/panelbg.png").getImage());
		player3_arts.setLayout(null);
		player3_arts.setVisible(false);
		player3_arts.setBounds(655, 234, 275, 47);
		player3_arts.setBackground(Color.ORANGE);
		panelBoard.add(player3_arts);
		((PlayerArts) player3_arts).updateArts();
		
		KUAlchemistsGame.getInstance().addArtListener((PlayerArts) player3_arts);
		
		player3_ingr = new PlayerIngs(3, new ImageIcon("images/panelbg.png").getImage());
		player3_ingr.setLayout(null);
		player3_ingr.setVisible(false);
		player3_ingr.setBounds(655, 175, 275, 51);
		player3_ingr.setBackground(Color.MAGENTA);
		panelBoard.add(player3_ingr);
		((PlayerIngs) player3_ingr).updateIngs();
		
		KUAlchemistsGame.getInstance().addIngListener((PlayerIngs) player3_ingr);
		
		player3_pot = new PlayerPot(3, new ImageIcon("images/panelbg.png").getImage());
		player3_pot.setLayout(null);
		player3_pot.setBounds(655, 120, 275, 47);
		player3_pot.setBackground(Color.MAGENTA);
		panelBoard.add(player3_pot);
		((PlayerPot) player3_pot).updatePot();
		
		KUAlchemistsGame.getInstance().addPotListener((PlayerPot) player3_pot);
		
	}
	
	public void showPlayer4() {
        name4 = new JLabel("Player IV: " + KUAlchemistsGame.getInstance().getPlayer(4).getUsername());
        name4.setFont(new Font("Bahnschrift", Font.BOLD, 19));
        name4.setForeground(new Color(255,225,168));
        name4.setBounds(958, 60, 200, 25);
        getPanelBoard().add(name4);
        
        avatar4 = new JLabel();
        avatar4.setIcon(Avatar.getAvatarImage(KUAlchemistsGame.getInstance().getPlayer(4).getAvatar()));
        avatar4.setBounds(1140, 40, 60, 60);
        getPanelBoard().add(avatar4);
        
        gold4 = new JLabel("Gold: " + KUAlchemistsGame.getInstance().getPlayer(4).getGold());
        gold4.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        gold4.setVisible(false);
        gold4.setForeground(new Color(255,225,168));
        gold4.setBounds(958, 95, 60, 30);
        getPanelBoard().add(gold4);
        
        sickness4 = new JLabel("Sickness: " + KUAlchemistsGame.getInstance().getPlayer(4).getSickness());
        sickness4.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        sickness4.setVisible(false);
        sickness4.setForeground(new Color(255,225,168));
        sickness4.setBounds(1021, 95, 90, 30);
        getPanelBoard().add(sickness4);
        
        reputation4 = new JLabel("Reputation: " + KUAlchemistsGame.getInstance().getPlayer(4).getReputation());
        reputation4.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        reputation4.setForeground(new Color(255,225,168));
        reputation4.setBounds(1113, 95, 110, 30);
        getPanelBoard().add(reputation4);
        
        player4_arts = new PlayerArts(4, new ImageIcon("images/panelbg.png").getImage());
        player4_arts.setLayout(null);
        player4_arts.setVisible(false);
		player4_arts.setBounds(955, 234, 275, 47);
		player4_arts.setBackground(Color.ORANGE);
		panelBoard.add(player4_arts);
		((PlayerArts) player4_arts).updateArts();
		
		KUAlchemistsGame.getInstance().addArtListener((PlayerArts) player4_arts);
		
		player4_ingr = new PlayerIngs(4, new ImageIcon("images/panelbg.png").getImage());
		player4_ingr.setLayout(null);
		player4_ingr.setVisible(false);
		player4_ingr.setBounds(955, 175, 275, 51);
		player4_ingr.setBackground(Color.MAGENTA);
		panelBoard.add(player4_ingr);
		((PlayerIngs) player4_ingr).updateIngs();
		
		KUAlchemistsGame.getInstance().addIngListener((PlayerIngs) player4_ingr);
		
		player4_pot = new PlayerPot(4, new ImageIcon("images/panelbg.png").getImage());
		player4_pot.setLayout(null);
		player4_pot.setBounds(955, 120, 275, 47);
		player4_pot.setBackground(Color.MAGENTA);
		panelBoard.add(player4_pot);
		((PlayerPot) player4_pot).updatePot();
		
		KUAlchemistsGame.getInstance().addPotListener((PlayerPot) player4_pot);
	}
	
	public class RoundPanel extends JPanel implements TurnListener{

		JLabel round;
		JLabel turn;
		String[] rounds = new String[]{"images/round1.png","images/round2 .png", "images/round3.png"};
		String[] turns = new String[]{"images/turn3.png", "images/turn1.png","images/turn2.png"};
		
		
		public RoundPanel() {
			super();			
			this.round = new JLabel();
			this.turn = new JLabel();
			round.setIcon(new ImageIcon("images/round1.png"));
			round.setBounds(0, 0, WIDTH, HEIGHT);
			turn.setIcon(new ImageIcon("images/turn1.png"));
			turn.setBounds(0, 30, WIDTH, HEIGHT);
			this.add(round);
			this.add(turn);
		}


		@Override
		public void onTurnChange() {
			
			if (KUAlchemistsGame.getInstance().getRound() < 4) {
				round.setIcon(new ImageIcon(rounds[KUAlchemistsGame.getInstance().getRound()-1]));
				turn.setIcon(new ImageIcon(turns[KUAlchemistsGame.getInstance().getTurnCounter()%3]));
			}
		}
	}
	
	
	public class EndgameDialog extends JDialog {

	    private final String[] playerNames;
	    private final List<Integer> playerScores;

	    public EndgameDialog(JFrame parent, String title, String[] playerNames, List<Integer> playerScores) {
	        super(parent, title, ModalityType.APPLICATION_MODAL);

	        this.playerNames = playerNames;
	        this.playerScores = playerScores;

	        initializeDialog();
	    }

	    private void initializeDialog() {
	        setLayout(null);
	        setSize(400, 400);
	        getContentPane().setBackground(new Color(71, 45, 48));

	        JPanel contentPanel = new JPanel();
	        contentPanel.setLayout(null);
	        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        contentPanel.setBounds(20, 20, 300, 300);
	        contentPanel.setBackground(new Color(71, 45, 48));

	        // Label and list for each player
	        for (int i = 0; i < playerNames.length; i++) {
	            JLabel playerLabel = new JLabel(playerNames[i] + ": ");
	            playerLabel.setFont(new Font("Bahnschrift", Font.BOLD, 19));
	            playerLabel.setForeground(new Color(255,225,168));
	            playerLabel.setOpaque(false);
	            
	            playerLabel.setBounds(120, 45 + i * 25, 150, 20);
	            contentPanel.add(playerLabel);

	            JLabel scoreLabel = new JLabel(Integer.toString(playerScores.get(i)));
	            scoreLabel.setFont(new Font("Bahnschrift", Font.BOLD, 19));
	            scoreLabel.setForeground(new Color(255,225,168));
	            scoreLabel.setOpaque(false);
	            
	            scoreLabel.setBounds(205, 45 + i*25, 50, 20);
	            contentPanel.add(scoreLabel);
	        }


	        int max = Collections.max(playerScores);
	        int maxIndex = playerScores.indexOf(max);
	        
	        String winner = playerNames[maxIndex];
	        
	        JLabel winnerLabel = new JLabel("The winner is:  " + winner);
            winnerLabel.setFont(new Font("Bahnschrift", Font.BOLD, 19));
            winnerLabel.setForeground(new Color(255,225,168));
            
            winnerLabel.setBounds(80, 140, 250, 30);
            contentPanel.add(winnerLabel);
	        
	        
	        //Play again button
	        JButton playAgainButton = new JButton();
	        playAgainButton.setIcon(new ImageIcon("images/newgame.png"));
	        playAgainButton.setBackground(new Color(255, 225, 168));
	        
	        playAgainButton.addActionListener(e -> {
	            // Implement action for playing again
	            this.dispose();
	            
	            for (Frame frame: BoardPage.getFrames()) {
	            	frame.dispose();
	            }
	            
	            HandlerFactory.getInstance().getRestartHandler().restart();
	        });
	        
	        playAgainButton.setBounds(35, 210, playAgainButton.getIcon().getIconWidth(), playAgainButton.getIcon().getIconHeight());
	        
	        contentPanel.add(playAgainButton);

	        // Exit button
	        JButton exitButton = new JButton(new ImageIcon("images/exit.png"));
	        exitButton.setBackground(new Color(255,225,168));
	        exitButton.addActionListener(e -> {
	            System.exit(0);
	        });
	        
	        exitButton.setBounds(180, 210, exitButton.getIcon().getIconWidth(), exitButton.getIcon().getIconHeight());
	        contentPanel.add(exitButton);

	        add(contentPanel);

	        setLocationRelativeTo(null);
	        setVisible(true);
	    }
	}
	
	/**
     * Observer pattern.
     *
     * Inner class implementing ArtListener for PlayerArts
     */
    private class PlayerArts extends JPanel implements ArtListener {
    	
    	private Image image;
    	private int playerNum;
    	private Window parentWindow; //  = SwingUtilities.getWindowAncestor(this);
        
    	public PlayerArts(int playerNum, Image image) {
			super();
			this.playerNum = playerNum;
			this.parentWindow = SwingUtilities.getWindowAncestor(this);
			this.image = image;
		}
    	
    	@Override
    	protected void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    	}

		public void updateArts() {
    		this.removeAll();
    		
    		JLabel pa_text = new JLabel();
    		pa_text.setIcon(new ImageIcon("images/artflabel.png"));
    		pa_text.setBounds(5, 12, pa_text.getIcon().getIconWidth(), pa_text.getIcon().getIconHeight());
            this.add(pa_text);
            
            for (int i=0; i<KUAlchemistsGame.getInstance().getPlayer(playerNum).getArtifacts().size(); i++) {
    			JButton player_art = new JButton();
    			player_art.setIcon(new ImageIcon(KUAlchemistsGame.getInstance().getPlayer(playerNum).getArtifacts().get(i).getImage()));
    			player_art.setBounds(110 + (player_art.getIcon().getIconWidth() + 5)*i, 5, player_art.getIcon().getIconWidth(), player_art.getIcon().getIconHeight()); // should change
    			this.add(player_art);
    			int temp = i;
    			
    			// if the artifact requires immediate user interaction through panel, such as elixir of insight
    			if (KUAlchemistsGame.getInstance().getPlayer(playerNum).getArtifacts().get(temp).getHasPanel()) {
    				
    				// elixir of insight
    				if (KUAlchemistsGame.getInstance().getPlayer(playerNum).getArtifacts().get(temp).getID() == 0) {
    					player_art.addActionListener(e -> {
    						System.out.println("Elixir of Insight Artifact is used");
    						// to set the behavior (strategy) of UseArtifactHandler and to remove artifact card from player's list
    						HandlerFactory.getInstance().getUseArtifactHandler().useArtifact(KUAlchemistsGame.getInstance().getPlayer(playerNum).getArtifacts().get(temp));
    						
	    					ElixirOfInsightDialog dialog = new ElixirOfInsightDialog((Frame) this.parentWindow);
	    					dialog.add(dialog.getPanelArtifact());
	    					dialog.setSize(600,350);
	    					
	    					dialog.setVisible(true);
	    					
	    					showTurnMessage(KUAlchemistsGame.getInstance().getCurrentPlayer().getUsername() + " used an artifact.");
	      					});
    				}
    			}
    			else {
    				player_art.addActionListener(e -> {
                    	showMessage("This artifact cannot be used now!");
                    });
    			}    			
                
    		}
    		revalidate();  //need to revise
    		repaint();  //need to revise
    	}
        
        @Override
    	public void onArtChange() {
    		updateArts();
    	}
    }
    
    private void showMessage(String msg) {
	    JDialog message = new JDialog(
	    		this,
	    		"Turn",
	    		true);
	    message.setSize(350, 150);
	    message.setModal(true);
	    
	    message.getContentPane().setBackground(new Color(255,225,168));
	    
	    message.setLayout(null);
	    
	    JLabel text = new JLabel(msg);
	    text.setFont(new Font("Bahnschrift", Font.BOLD, 19));
	    text.setBounds(20, 30, 300, 30);
	    text.setForeground(new Color(189, 12, 9));
	    
	    message.add(text);
	    
	    JButton ok = new JButton("OK");
	    ok.setBounds(125, 73, 100, 30);
	    ok.setFont(new Font("Bahnschrift", Font.BOLD, 15));
	    ok.setForeground(new Color(255,225,168));
	    ok.setBackground(new Color(189, 12, 9));
	    ok.addActionListener(e -> message.dispose());
	    message.add(ok);
	    
	    message.setLocationRelativeTo(text);
	    message.setVisible(true);
	}
    
    
    /**
     * Observer pattern.
     *
     * Inner class implementing IngListener for PlayerIngs.
     */
    private class PlayerIngs extends JPanel implements IngListener {
    	
    	private Image image;
    	private int playerNum;

    	public PlayerIngs(int playerNum, Image image) {
			super();
			this.playerNum = playerNum;
			this.image = image;
		}
    	
    	@Override
    	protected void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    	}

		public void updateIngs() {
			
    		this.removeAll();
    		
            JLabel pi_text = new JLabel();
            pi_text.setIcon(new ImageIcon("images/ingredientslabel.png"));
            pi_text.setBounds(5, 14, pi_text.getIcon().getIconWidth(), pi_text.getIcon().getIconHeight());
            this.add(pi_text);
            
            for (int i = 0; i < KUAlchemistsGame.getInstance().getPlayer(playerNum).getIngredients().size(); i++) {
                JButton player_ing = new JButton();
                player_ing.setIcon(new ImageIcon(KUAlchemistsGame.getInstance().getPlayer(playerNum).getIngredients().get(i).getImage()));
                player_ing.setOpaque(false);
                player_ing.setContentAreaFilled(false);
                player_ing.setBorderPainted(false);
                player_ing.setBounds(118 + 37*i, 5, 40, 40);
                this.add(player_ing);
                player_ing.addActionListener(e -> {
                	System.out.println("Ingredient is transmuted.");
                	HandlerFactory.getInstance().getTransmuteIngHandler().transmuteIngredient(KUAlchemistsGame.getInstance().getPlayer(playerNum));
                    updateGoldUI();
                    showTurnMessage(KUAlchemistsGame.getInstance().getCurrentPlayer().getUsername() + " transmuted an ingredient.");
                    });
            }
            this.revalidate();  //need to revise
            this.repaint();  //need to revise
    	}
    	
		@Override
		public void onIngChange() {
			System.out.println("updating playering on ing change");
			updateIngs();
		}
    }
    
    /**
     * Observer pattern.
     *
     * Inner class implementing PotListener for PlayerPot.
     */
    private class PlayerPot extends JPanel implements PotListener {
    	
    	private Image image;
    	private int playerNum;

    	public PlayerPot(int playerNum, Image image) {
			super();
			this.playerNum = playerNum;
			this.image = image;
		}
    	
    	@Override
    	protected void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    	}
    	
		public void updatePot() {
			
    		this.removeAll();
    		
            JLabel ppot_text = new JLabel();
            ppot_text.setIcon(new ImageIcon("images/potlabel.png"));
            ppot_text.setBounds(5, 14, ppot_text.getIcon().getIconWidth(), ppot_text.getIcon().getIconHeight());
            this.add(ppot_text);
            
            for (int i = 0; i < KUAlchemistsGame.getInstance().getPlayer(playerNum).getPotions().size(); i++) {            	
                JLabel player_pot = new JLabel();
                player_pot.setIcon(new ImageIcon(KUAlchemistsGame.getInstance().getPlayer(playerNum).getPotions().get(i).getImage()));
                player_pot.setBounds(98 + (player_pot.getIcon().getIconWidth() + 7)*i, 9, player_pot.getIcon().getIconWidth(), player_pot.getIcon().getIconHeight());
                this.add(player_pot);
            }
            this.revalidate();  //need to revise
            this.repaint();  //need to revise
    	}
    	
		@Override
		public void onPotChange() {
			updatePot();
		}
    }
    
//    

	private class PotionBrew extends JPanel implements  IngListener, TurnListener, ItemListener {
		
		private Image image;
		
		public PotionBrew(Image image) {
			super();
			this.image = image;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	    	
	    	ArrayList<JCheckBox> checkboxes;
	    	ArrayList<JCheckBox> clickedcheckboxes;
	    	ArrayList<Integer> ingrindex;
	    	JButton makeExpBtn;
	    	JRadioButton testBtn1;
	    	JRadioButton testBtn2;
	    	JLabel test;
	    	private Window parentWindow; //  = SwingUtilities.getWindowAncestor(this);
	        
	    	public void updatePotionBrew() {
	    		this.parentWindow = SwingUtilities.getWindowAncestor(this);
	    		this.removeAll();
	    		
	    		JLabel pa_text = new JLabel();
	    		pa_text.setIcon(new ImageIcon("images/makeexplbl.png"));
	    		pa_text.setBounds(8, 8, pa_text.getIcon().getIconWidth(), pa_text.getIcon().getIconHeight());
	            this.add(pa_text);
	            
	            test = new JLabel();
	            test.setIcon(new ImageIcon("images/testonlbl.png"));
	    		test.setBounds(85, 60, test.getIcon().getIconWidth(), test.getIcon().getIconHeight());
	            this.add(test);
	            test.setVisible(false);
	            
	        	testBtn1= new JRadioButton();
	        	testBtn1.setIcon(new ImageIcon("images/yourself.png"));
	        	testBtn1.setOpaque(false);
	        	testBtn1.setContentAreaFilled(false);
	        	testBtn1.setBounds(90, 90, testBtn1.getIcon().getIconWidth()+10, testBtn1.getIcon().getIconHeight());
	        	testBtn1.addItemListener(this);
	        	this.add(testBtn1);
	        	testBtn1.setVisible(false);
	        	
	        	testBtn2= new JRadioButton();
	        	testBtn2.setIcon(new ImageIcon("images/student.png"));
	        	testBtn2.setOpaque(false);
	        	testBtn2.setContentAreaFilled(false);
	        	testBtn2.setBounds(90, 115, testBtn2.getIcon().getIconWidth()+10, testBtn2.getIcon().getIconHeight());
	        	testBtn2.addItemListener(this);
	        	this.add(testBtn2);
	        	testBtn2.setVisible(false);
	        	
	        	ButtonGroup testgroup = new ButtonGroup();
	        	
	        	testgroup.add(testBtn1);
	        	testgroup.add(testBtn2);
	        	
	            makeExpBtn = new JButton();
	            makeExpBtn.setIcon(new ImageIcon("images/makeexpbtn.png"));
	            makeExpBtn.setMargin(new Insets(0,0,0,0));
	            makeExpBtn.setBounds(20, 205, makeExpBtn.getIcon().getIconWidth(), makeExpBtn.getIcon().getIconHeight());
	            this.add(makeExpBtn);
	            makeExpBtn.setVisible(false);
	            makeExpBtn.addActionListener(e -> {
	            	System.out.println("Experiment button in UI");
	            	String str = null;
	            	if (testBtn1.isSelected()) {
	            		str = "yourself";
	            	}
	            	if(testBtn2.isSelected()) {
	            		str = "student";
	            	}
	            	HandlerFactory.getInstance().getMakeExperimentHandler().makeExperiment(
	            			KUAlchemistsGame.getInstance().getCurrentPlayer().getIngredients().get(ingrindex.get(0)),
	            			KUAlchemistsGame.getInstance().getCurrentPlayer().getIngredients().get(ingrindex.get(1)),
	            			str, KUAlchemistsGame.getInstance().getCurrentPlayer());
	            	
	            	int magicMortarFlag=0;
	            	
	            	for (ArtifactCard artf : KUAlchemistsGame.getInstance().getCurrentPlayer().getArtifacts()) {
	            		if (artf.getID()==2) magicMortarFlag=1;
	            	}
	            	
	            	// if the player has magic mortar card
	            	if (magicMortarFlag==1){
	            		// MagicMortarDialog takes which ingredient the user wants to be not discarded
	            		System.out.println("magic mortar used");
	            		HandlerFactory.getInstance().getUseArtifactHandler().useArtifact(new ArtifactCard("Magic Mortar", 2, false));
	            		MagicMortarDialog dialog = new MagicMortarDialog((Frame) this.parentWindow);
						dialog.add(dialog.getPanelArtifact());
						dialog.setSize(600,350);
						dialog.setVisible(true);
	            	}
	            	
	            	updateGoldUI();
	            	updateSicknessUI();
	            	showTurnMessage(KUAlchemistsGame.getInstance().getCurrentPlayer().getUsername() + " made an experiment.");
	                
	            });
	            
	            checkboxes = new ArrayList<JCheckBox>();
	            clickedcheckboxes = new ArrayList<JCheckBox>();
	            ingrindex = new ArrayList<Integer>();
	            
	            for (int i=0; i<KUAlchemistsGame.getInstance().getCurrentPlayer().getIngredients().size(); i++) {
	    			JCheckBox player_ing = new JCheckBox();
	    			player_ing.setIcon(new ImageIcon(KUAlchemistsGame.getInstance().getCurrentPlayer().getIngredients().get(i).getImage()));
	    			player_ing.setBounds(20, 55 + 37 * i, 40, 30); 
	    			player_ing.setOpaque(false);
	    			checkboxes.add(player_ing);
	    			this.add(player_ing);
	                player_ing.addItemListener(this);
	                
	    		}
	            revalidate();
	            repaint();
	    	
	    	}
	    	
			@Override
			public void onTurnChange() {
				updatePotionBrew();
			}
			
			@Override
			public void onIngChange() {
				updatePotionBrew();
			}
	
	
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(checkboxes.contains(e.getSource())) {
					if (e.getStateChange() == 1) {
						System.out.println(ingrindex.toString());
						System.out.println(checkboxes.size());
						
						if (ingrindex.size()==2){
							JCheckBox temp = clickedcheckboxes.get(0);
							clickedcheckboxes.remove(0);
							ingrindex.remove(0);
							temp.setSelected(false);
							temp.setBorderPainted(false);						
						}
						
						ingrindex.add(checkboxes.indexOf(e.getSource()));
						clickedcheckboxes.add((JCheckBox) e.getSource());
						((JCheckBox) e.getSource()).setBorderPainted(true);
						
						if(ingrindex.size()==2) {
							testBtn1.setVisible(true);
							testBtn2.setVisible(true);
							test.setVisible(true);
						}
					}
					else {
						System.out.println(ingrindex.toString());
						if(clickedcheckboxes.contains(e.getSource())) {
							ingrindex.remove(clickedcheckboxes.indexOf(e.getSource()));
							clickedcheckboxes.remove(e.getSource());
							if(ingrindex.size()!=2) {
								testBtn1.setVisible(false);
								testBtn2.setVisible(false);
								test.setVisible(false);
								makeExpBtn.setVisible(false);
							}
						}
					}
				}
				if (e.getSource()== testBtn1 || e.getSource()==testBtn2) {
					if (ingrindex.size()==2) {
						makeExpBtn.setVisible(true);
					}
					if (e.getStateChange()==1) {
						((JRadioButton) e.getSource()).setBorderPainted(true);
					}
					else {
						((JRadioButton) e.getSource()).setBorderPainted(false);
					}
				}
				
			}
	    
	    }
    
    private class SellPotionPanel extends JPanel implements ItemListener, TurnListener, IngListener {
    	
    	private Image image;
    	
    	public SellPotionPanel(Image image) {
    		super();
    		this.image = image;
    	}
    	
    	@Override
    	protected void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    	}
    	
    	ArrayList<JCheckBox> checkboxes;
    	ArrayList<JCheckBox> clickedcheckboxes;
    	ArrayList<Integer> ingrindex;
    	JButton sellPotBtn;
    	JRadioButton predBtn1;
    	JRadioButton predBtn2;
    	JRadioButton predBtn3;
    	JLabel pred;

		
    	public void updatePanel() {
			
    		this.removeAll();
    		
    		JLabel pa_text = new JLabel();
    		pa_text.setIcon(new ImageIcon("images/sellpotionlabel.png"));
    		pa_text.setBounds(10, 5, pa_text.getIcon().getIconWidth(), pa_text.getIcon().getIconHeight());
            this.add(pa_text);
            
            pred = new JLabel();
            pred.setIcon(new ImageIcon("images/makepredictionlabel.png"));
    		pred.setBounds(85, 60,  pred.getIcon().getIconWidth(), pred.getIcon().getIconHeight());
            this.add(pred);
            pred.setVisible(false);
            
            //button for positive prediction
            predBtn1= new JRadioButton();
            predBtn1.setIcon(new ImageIcon("images/positivelabel.png"));
            predBtn1.setMargin(new Insets(0,0,0,0));
            predBtn1.setBounds(93, 90, predBtn1.getIcon().getIconWidth() + 10, predBtn1.getIcon().getIconHeight() + 10);
            predBtn1.addItemListener(this);
            predBtn1.setOpaque(false);
            predBtn1.setContentAreaFilled(false);
        	this.add(predBtn1);
        	predBtn1.setVisible(false);
        	
        	//button for positive/neutral prediction
        	predBtn2= new JRadioButton();
        	predBtn2.setIcon(new ImageIcon("images/positiveneutrallabel.png"));
            predBtn2.setMargin(new Insets(0,0,0,0));
        	predBtn2.setBounds(93, 115, predBtn2.getIcon().getIconWidth() + 10, predBtn2.getIcon().getIconHeight() + 10);
        	predBtn2.addItemListener(this);
        	predBtn2.setOpaque(false);
            predBtn2.setContentAreaFilled(false);
        	this.add(predBtn2);
        	predBtn2.setVisible(false);
        	
        	//button for 'may be negative' prediction
        	predBtn3= new JRadioButton();
        	predBtn3.setIcon(new ImageIcon("images/maybenegativelabel .png"));
            predBtn3.setMargin(new Insets(0,0,0,0));
            predBtn3.setOpaque(false);
            predBtn3.setContentAreaFilled(false);
        	predBtn3.setBounds(93, 140, predBtn2.getIcon().getIconWidth() + 10, predBtn2.getIcon().getIconHeight() + 10);
        	predBtn3.addItemListener(this);
        	this.add(predBtn3);
        	predBtn3.setVisible(false);
        	
        	ButtonGroup testgroup = new ButtonGroup();
        	
        	testgroup.add(predBtn1);
        	testgroup.add(predBtn2);
        	testgroup.add(predBtn3);
        	
        	//final button to perform the sale of the potion
        	sellPotBtn = new JButton();
        	sellPotBtn.setIcon(new ImageIcon("images/sellpotion.png"));
            sellPotBtn.setMargin(new Insets(0,0,0,0));
        	sellPotBtn.setBounds(20, 205, sellPotBtn.getIcon().getIconWidth(), sellPotBtn.getIcon().getIconHeight());
            this.add(sellPotBtn);
            sellPotBtn.setVisible(false);
            
            //when 'sell potion' button is pressed
            sellPotBtn.addActionListener(e -> {
            	System.out.println("SellPotion button in UI");
            	
            	int prediction = 9999;
            	
            	if (predBtn1.isSelected()) { //positive prediction
            		prediction = 1;
            	}
            	if(predBtn2.isSelected()) { //positive/neutral
            		prediction = 0;
            	}
            	if(predBtn3.isSelected()) { //'may be negative' prediction
            		prediction = -1;
            	}
            	
            	//transmit the massage to the specific controller
            	int result = HandlerFactory.getInstance().getSellPotionHandler().sellPotion(
            			KUAlchemistsGame.getInstance().getCurrentPlayer().getIngredients().get(ingrindex.get(0)),
            			KUAlchemistsGame.getInstance().getCurrentPlayer().getIngredients().get(ingrindex.get(1)),
            			prediction, KUAlchemistsGame.getInstance().getCurrentPlayer());
            	
            	updateGoldUI();
            	updateReputationUI();
            	
            	String msg = null;
            	
            	if (result==1) { //positive prediction
            		msg = "positive";
            	}
            	if(result==0) { //positive/neutral
            		msg = "neutral";
            	}
            	if(result==-1) { //'may be negative' prediction
            		msg="negative";
            	}
            	
            	showTurnMessage(KUAlchemistsGame.getInstance().getCurrentPlayer().getUsername() + " made a " + msg + " potion sale.");
            	
            });
            
            checkboxes = new ArrayList<JCheckBox>(); //keeps all the ingredients
            clickedcheckboxes = new ArrayList<JCheckBox>(); //keeps the chosen ingredients
            ingrindex = new ArrayList<Integer>(); //keeps the indices of the chosen ingredients in the player's ingredient list
            
            for (int i=0; i<KUAlchemistsGame.getInstance().getCurrentPlayer().getIngredients().size(); i++) {
    			JCheckBox player_ing = new JCheckBox();
    			player_ing.setIcon(new ImageIcon(KUAlchemistsGame.getInstance().getCurrentPlayer().getIngredients().get(i).getImage()));
    			player_ing.setBounds(20, 55 + 37 * i, 40, 30); 
    			player_ing.setOpaque(false);
    			checkboxes.add(player_ing);
    			this.add(player_ing);
                player_ing.addItemListener(this);
                
    		}
            revalidate();
            repaint();
    	
    	}
    	
    	@Override
    	public void onTurnChange() {
    		updatePanel();
    	};
    	
    	@Override
    	public void onIngChange() {
    		updatePanel();
    	};
    	

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(checkboxes.contains(e.getSource())) { //source of change is one of the checkboxes
				if (e.getStateChange() == 1) { //the checkbox is selected
					
					if (ingrindex.size()==2){ //two ingredients were already chosen
						JCheckBox temp = clickedcheckboxes.get(0);
						clickedcheckboxes.remove(0); //discard the first chosen ingredient
						ingrindex.remove(0); //discard the index of the first chosen ingredient
						temp.setSelected(false); //discard the first chosen ingredient
						temp.setBorderPainted(false);
						
					}
					
					ingrindex.add(checkboxes.indexOf(e.getSource())); //add the index of the new chosen ingredient
					clickedcheckboxes.add((JCheckBox) e.getSource()); //add the new chosen ingredient
					((JCheckBox) e.getSource()).setBorderPainted(true);
					
					if(ingrindex.size()==2) { //two ingredients are chosen --> show the prediction buttons
						predBtn1.setVisible(true);
						predBtn2.setVisible(true);
						predBtn3.setVisible(true);
						pred.setVisible(true);
					}
				}
				
				else { //the checkbox is deselected
					if(clickedcheckboxes.contains(e.getSource())) {
						ingrindex.remove(clickedcheckboxes.indexOf(e.getSource())); //discard the deselected ingredient's index
						clickedcheckboxes.remove(e.getSource()); //discard the deselected ingredient
						
						//number of selected ingredients is lower than 2 --> hide the prediction + 'sell potion' buttons
						if(ingrindex.size()!=2) {
							predBtn1.setVisible(false);
							predBtn2.setVisible(false);
							predBtn3.setVisible(false);
							pred.setVisible(false);
							sellPotBtn.setVisible(false);
						}
					}
				}
			}
			
			//source of change is one of the prediction buttons
			if (e.getSource()== predBtn1 || e.getSource()==predBtn2 || e.getSource()==predBtn3) {
				
				if (ingrindex.size()==2) { //two ingredients are chosen --> show the final sell potion button
					sellPotBtn.setVisible(true);
				}
				
				if (e.getStateChange()==1) {
					((JRadioButton) e.getSource()).setBorderPainted(true);
				}
				else {
					((JRadioButton) e.getSource()).setBorderPainted(false);
				}
				
			}
			
		}
    
    }
    
    
    
    
    /**
     * Already published theories needs be made visible for debunking.
     *
     * 
     */
    private class PublicationArea extends JPanel implements PubListener, ItemListener {
    	
    	private Image image;
    	
    	public PublicationArea(Image image) {
    		super();
    		this.image = image;
    	}
    	
    	public List<JRadioButton> getTheoryButtons() {
			return theoryButtons;
		}

		public void setTheoryButtons(List<JRadioButton> theoryButtons) {
			this.theoryButtons = theoryButtons;
		}

		@Override
    	protected void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    	}
    	
    	List<JRadioButton> theoryButtons;
    	JButton debunkBtn;
    	List<JRadioButton> aspectButtons;
    	List<JRadioButton> ingrButtons;
    	List<JRadioButton> markerButtons;
    	JLabel aspectLbl;
		private Window parentWindow;
    	
    	public void updatePublicationArea() {
    		this.parentWindow = SwingUtilities.getWindowAncestor(this);
    		this.removeAll();
    		
    		JLabel marker_text = new JLabel();
    		marker_text.setIcon(new ImageIcon("images/ingrlabel21.png"));
    		marker_text.setBounds(10, 5, 110, 20);
            this.add(marker_text);
            
            JLabel ingr_text = new JLabel();
            ingr_text.setIcon(new ImageIcon("images/alchlabel3.png"));
    		ingr_text.setBounds(140, 5, 210, 20);
            this.add(ingr_text);
            
            //label of the new area for published theories
            JLabel theory_text = new JLabel();
            theory_text.setIcon(new ImageIcon("images/publabel1.png"));
    		theory_text.setBounds(310, 4, 200, 20);
            this.add(theory_text);
            
            ButtonGroup ingrGroup = new ButtonGroup();
            ButtonGroup markerGroup = new ButtonGroup();
            ButtonGroup theoryGroup = new ButtonGroup();
            ButtonGroup aspectGroup = new ButtonGroup();
            
            ingrButtons = new ArrayList<JRadioButton>();
            List<Ingredient> ingrs = HandlerFactory.getInstance().getPublicationHandler().getAvailableIngredients();
            
            for (int i=0; i<ingrs.size(); i++) {
            	JRadioButton ingrBtn= new JRadioButton();
            	ingrButtons.add(ingrBtn);
            	ingrBtn.setIcon(new ImageIcon(ingrs.get(i).getImage()));
            	ingrBtn.setBounds(30, 30 + 60*i, 45, 55);
            	ingrBtn.setOpaque(false);
            	ingrBtn.addItemListener(this);
            	ingrGroup.add(ingrBtn);
            	this.add(ingrBtn);
            	ingrBtn.setVisible(true);
            }
            markerButtons = new ArrayList<JRadioButton>();
            List<AlchemyMarker> markers = HandlerFactory.getInstance().getPublicationHandler().getAvailableAlchemies();
            
            for (int i=0; i<markers.size(); i++) {
            	JRadioButton markerBtn= new JRadioButton();
            	markerButtons.add(markerBtn);
            	markerBtn.setIcon(new ImageIcon(markers.get(i).getImage()));
            	markerBtn.setBounds(160, 30 + 60*i, 65, 59);
            	markerBtn.setOpaque(false);
            	markerBtn.setMargin(new Insets(0,0,0,0));
            	markerBtn.addItemListener(this);
            	markerGroup.add(markerBtn);
            	this.add(markerBtn);
            	markerBtn.setVisible(true);
            }
            
            List<Theory> theories = HandlerFactory.getInstance().getPublicationHandler().getPublishedTheories();
            
            theoryButtons = new ArrayList<JRadioButton>();
            		
            for (int i=0; i<theories.size(); i++) {
            	JLabel ingrlbl = new JLabel();
            	ingrlbl.setIcon(new ImageIcon(theories.get(i).getIngredient().getImage()));
            	ingrlbl.setBounds(310, 30 + 60*i, 45, 55);
            	this.add(ingrlbl);
            	
            	JLabel markerlbl = new JLabel();
            	markerlbl.setIcon(new ImageIcon(theories.get(i).getMarker().getImage()));
            	markerlbl.setBounds(420, 30 + 60*i, 65, 59);
            	this.add(markerlbl);
            	
            	JRadioButton theoryBtn = new JRadioButton();
            	theoryBtn.setIcon(new ImageIcon("images/publish.png"));
            	theoryButtons.add(theoryBtn);
            	
            	if(KUAlchemistsGame.getInstance().getRound()!=3 || 
            			theories.get(i).getPlayerNo()==KUAlchemistsGame.getCurrentPlayerNo()) {
            		theoryBtn.setEnabled(false);
            	}
            	
            	theoryBtn.setBounds(356, 42 + 60*i, theoryBtn.getIcon().getIconWidth()+10, theoryBtn.getIcon().getIconHeight());
            	theoryBtn.setMargin(new Insets(0,0,0,0));
            	theoryBtn.setOpaque(false);
            	theoryBtn.setContentAreaFilled(false);
            	theoryBtn.addItemListener(this);
            	theoryGroup.add(theoryBtn);
            	this.add(theoryBtn);
            	theoryBtn.setVisible(true);
            	
            }

    		aspectButtons = new ArrayList<JRadioButton>();
    		
    		aspectLbl = new JLabel();
    		aspectLbl.setIcon(new ImageIcon("images/chooseaspectlabel.png"));
    		aspectLbl.setBounds(315, 460, aspectLbl.getIcon().getIconWidth(), aspectLbl.getIcon().getIconHeight());
    		aspectLbl.setVisible(false);
    		this.add(aspectLbl);
    		
            JRadioButton as1 = new JRadioButton();
            as1.setIcon(new ImageIcon("images/red.png"));
            as1.setOpaque(false);
            as1.setContentAreaFilled(false);
            aspectButtons.add(as1);
            as1.setMargin(new Insets(0,0,0,0));
            as1.setBounds(310, 485, as1.getIcon().getIconWidth(), as1.getIcon().getIconHeight());
            as1.addItemListener(this);
            aspectGroup.add(as1);
            this.add(as1);
            as1.setVisible(false);
            
    		JRadioButton as2 = new JRadioButton();
    		as2.setIcon(new ImageIcon("images/green.png"));
    		as2.setOpaque(false);
            as2.setContentAreaFilled(false);
    		aspectButtons.add(as2);
    		as2.setBounds(365, 485, as2.getIcon().getIconWidth(), as2.getIcon().getIconHeight());
    		as2.setMargin(new Insets(0,0,0,0));
    		as2.addItemListener(this);
    		aspectGroup.add(as2);
    		this.add(as2);
    		as2.setVisible(false);
    		
    		JRadioButton as3 = new JRadioButton();
    		as3.setOpaque(false);
            as3.setContentAreaFilled(false);
    		as3.setIcon(new ImageIcon("images/blue.png"));
    		as3.setMargin(new Insets(0,0,0,0));
    		aspectButtons.add(as3);
    		as3.setBounds(420, 485, as3.getIcon().getIconWidth(), as3.getIcon().getIconHeight());
    		as3.addItemListener(this);
    		aspectGroup.add(as3);
    		this.add(as3);
    		as3.setVisible(false);
    		
            JButton publishBtn = new JButton();
            publishBtn.setIcon(new ImageIcon("images/publabel2.png"));
            publishBtn.setOpaque(false);
            publishBtn.setBounds(60, 515, publishBtn.getIcon().getIconWidth(), 20);
            this.add(publishBtn);
            publishBtn.setVisible(true);
            publishBtn.addActionListener(e -> {
            	System.out.println("Publish button in UI");
            	
            	int printingPressFlag = 0;
            	for (ArtifactCard artf : KUAlchemistsGame.getCurrentPlayer().getArtifacts()) {
            		if (artf.getID()==1) printingPressFlag=1;
            	}
            	
            	if (printingPressFlag==1){
            		System.out.println("Player has printing press");
            		PrintingPressDialog dialog = new PrintingPressDialog((Frame) this.parentWindow);
					dialog.add(dialog.getPanelArtifact());
					dialog.setSize(600,350);
					dialog.setVisible(true);
            	}
            	// if the player has Printing Press card
           	 	else {
           	 		System.out.println("Player doesnt have printing press");
           	 		System.out.println(KUAlchemistsGame.getCurrentPlayer().getArtifacts());
           	 	}
            	if (KUAlchemistsGame.getInstance().getCurrentPlayer().getGold()<1) {
            		showMessage("NOT ENOUGH GOLD!");
            	}
            	
            	else {
	            	String ingrName = null;
	            	String markerName = null;
	            	
	            	
	            	for (Enumeration<AbstractButton> buttons = ingrGroup.getElements(); buttons.hasMoreElements();) {
	                    AbstractButton button = buttons.nextElement();
	
	                    if (button.isSelected()) {
	                         ingrName = ingrs.get(ingrButtons.indexOf(button)).getName();
	                    }
	                }
	            	
	            	for (Enumeration<AbstractButton> buttons = markerGroup.getElements(); buttons.hasMoreElements();) {
	                    AbstractButton button = buttons.nextElement();
	
	                    if (button.isSelected()) {
	                         markerName = Integer.toString(markers.get(markerButtons.indexOf(button)).getID());
	                    }
	                }
	            	
	            	HandlerFactory.getInstance().getPublicationHandler().makePublication(ingrName, markerName, KUAlchemistsGame.getInstance().getCurrentPlayerNo());
	            	
	            	
	            	
	            	updateGoldUI();
	            	updateReputationUI();
	            	showTurnMessage(KUAlchemistsGame.getInstance().getCurrentPlayer().getUsername() + " made a publication.");
            	}
            });
            
            debunkBtn = new JButton();
            debunkBtn.setIcon(new ImageIcon("images/debunkbtn.png"));
            debunkBtn.setBounds(320, 515, debunkBtn.getIcon().getIconWidth(), 20);
            debunkBtn.setMargin(new Insets(0,0,0,0));
            this.add(debunkBtn);
            debunkBtn.setVisible(false);
            debunkBtn.addActionListener(e -> {
            	System.out.println("Debunk button in UI");
            	
            	int theoryIndex = -1;
            	int aspectIndex = -1;
            	
            	for (JRadioButton theoryBtn : theoryButtons) {
            		if (theoryBtn.isSelected()) {
            			theoryIndex = theoryButtons.indexOf(theoryBtn); 
            		}
            	}
            	
            	for (JRadioButton as : aspectButtons) {
            		if (as.isSelected()) {
            			aspectIndex = aspectButtons.indexOf(as); 
            		}
            	}
            	
            	String aspect = null;
            	String aspectSign = null;
            	
            	if (aspectIndex==0) {
            		aspect = "Red Aspect:  ";
            		if (theories.get(theoryIndex).getIngredient().getRedAspect().isSign()) {
            			aspectSign = "+";
            		}
            		else {
            			aspectSign = "-";
            		}
            	}
            	if (aspectIndex==1) {
            		aspect = "Green Aspect:  ";
            		if (theories.get(theoryIndex).getIngredient().getGreenAspect().isSign()) {
            			aspectSign = "+";
            		}
            		else {
            			aspectSign = "-";
            		}
            	}
            	if (aspectIndex==2) {
            		aspect = "Blue Aspect:  ";
            		if (theories.get(theoryIndex).getIngredient().getBlueAspect().isSign()) {
            			aspectSign = "+";
            		}
            		else {
            			aspectSign = "-";
            		}
            	}
            	
            	
            	String image = theories.get(theoryIndex).getIngredient().getImage();
            	int debunkedPlayerNo = theories.get(theoryIndex).getPlayerNo();
            	
            	int result = HandlerFactory.getInstance().getPublicationHandler().debunkTheory(theories.get(theoryIndex),
            			aspectIndex + 1,
            			KUAlchemistsGame.getInstance().getCurrentPlayerNo());
            	
            	updateReputationUI();
            	
            	showTurnMessage(KUAlchemistsGame.getInstance().getCurrentPlayer().getUsername() + (result==1 ? " debunked " +
            			KUAlchemistsGame.getInstance().getPlayer(debunkedPlayerNo).getUsername()+ "'s theory." : 
            				" made an unsuccessful debunk") , 
            			image, 
            			"'s" + " " + aspect + aspectSign);
            });
    		
    		revalidate();
            repaint();
    	}

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (theoryButtons.contains(e.getSource())) {
				for (JRadioButton asBtn : aspectButtons) {
					asBtn.setVisible(true);
				}
				aspectLbl.setVisible(true);
				if (e.getStateChange()==1) {
					((JRadioButton) e.getSource()).setBorderPainted(true);
				}
				else {
					((JRadioButton) e.getSource()).setBorderPainted(false);
				}
			}
			if(aspectButtons.contains(e.getSource())) {
				debunkBtn.setVisible(true);
				if (e.getStateChange()==1) {
					((JRadioButton) e.getSource()).setBorderPainted(true);
				}
				else {
					((JRadioButton) e.getSource()).setBorderPainted(false);
				}
			}
			if (ingrButtons.contains(e.getSource())) {
				if (e.getStateChange()==1) {
					((JRadioButton) e.getSource()).setBorderPainted(true);
				}
				else {
					((JRadioButton) e.getSource()).setBorderPainted(false);
				}
			}
			if (markerButtons.contains(e.getSource())) {
				if (e.getStateChange()==1) {
					((JRadioButton) e.getSource()).setBorderPainted(true);
				}
				else {
					((JRadioButton) e.getSource()).setBorderPainted(false);
				}
			}
			
		}

		@Override
		public void onPubChange() {
			updatePublicationArea();
			
		}
    
    }
    
    private void showTurnMessage(String message) {
	    JDialog turn = new JDialog(
	    		this,
	    		"Turn",
	    		true);
	    turn.setSize(350, 200);
	    String msgString = "Click the turn button after you are done";
	    String msgString2 = "with other operations.";
	    turn.setModal(true);
	    turn.getContentPane().setBackground(new Color(255,225,168));
	    turn.setLayout(null);
	    
	    JLabel turnText = new JLabel(message);
	    turnText.setFont(new Font("Bahnschrift", Font.BOLD, 15));
	    turnText.setBounds(20, 30, 300, 30);
	    turnText.setForeground(new Color(226, 109, 92));
	    
	    JLabel turnText1 = new JLabel(msgString);
	    turnText1.setFont(new Font("Bahnschrift", Font.BOLD, 15));
	    turnText1.setBounds(20, 60, 300, 30);
	    turnText1.setForeground(new Color(226, 109, 92));
	    
	    JLabel turnText2 = new JLabel(msgString2);
	    turnText2.setFont(new Font("Bahnschrift", Font.BOLD, 15));
	    turnText2.setBounds(20, 80, 300, 30);
	    turnText2.setForeground(new Color(226, 109, 92));
	    
	    turn.add(turnText);
	    turn.add(turnText1);
	    turn.add(turnText2);
	    
	    JButton ok = new JButton();
	    ok.setIcon(new ImageIcon("images/ok.png"));
	    ok.setBounds(205, 115, ok.getIcon().getIconWidth(), ok.getIcon().getIconHeight());
	    ok.addActionListener(e -> turn.dispose());
	    turn.add(ok);
	    
	    turn.setLocationRelativeTo(turnText);
	    turn.setVisible(true);
	}
    
    private void showTurnMessage(String message, String image, String aspect) {
	    JDialog turn = new JDialog(
	    		this,
	    		"Turn",
	    		true);
	    turn.setSize(350, 200);
	    String msgString = "Click the turn button after you are done";
	    String msgString2 = "with other operations.";
	    turn.setModal(true);
	    turn.getContentPane().setBackground(new Color(255,225,168));
	    turn.setLayout(null);
	    
	    JLabel turnText = new JLabel(message);
	    turnText.setFont(new Font("Bahnschrift", Font.BOLD, 15));
	    turnText.setBounds(20, 10, 300, 30);
	    turnText.setForeground(new Color(226, 109, 92));
	    
	    JLabel ingr = new JLabel(new ImageIcon(image));
	    ingr.setBounds(20, 42, ingr.getIcon().getIconWidth(), ingr.getIcon().getIconHeight());
	    ingr.setOpaque(false);
	    turn.add(ingr);
	    
	    JLabel ingrlbl = new JLabel(aspect);
	    ingrlbl.setFont(new Font("Bahnschrift", Font.BOLD, 15));
	    ingrlbl.setForeground(new Color(71, 45, 48));
	    ingrlbl.setBounds(55, 40, 200, 30);
	    turn.add(ingrlbl);	    
	    
	    JLabel turnText1 = new JLabel(msgString);
	    turnText1.setFont(new Font("Bahnschrift", Font.BOLD, 15));
	    turnText1.setBounds(20, 75, 300, 30);
	    turnText1.setForeground(new Color(226, 109, 92));
	    
	    JLabel turnText2 = new JLabel(msgString2);
	    turnText2.setFont(new Font("Bahnschrift", Font.BOLD, 15));
	    turnText2.setBounds(20, 95, 300, 30);
	    turnText2.setForeground(new Color(226, 109, 92));
	    
	    turn.add(turnText);
	    turn.add(turnText1);
	    turn.add(turnText2);
	    
	    JButton ok = new JButton();
	    ok.setIcon(new ImageIcon("images/ok.png"));
	    ok.setBounds(205, 120, ok.getIcon().getIconWidth(), ok.getIcon().getIconHeight());
	    ok.addActionListener(e -> turn.dispose());
	    turn.add(ok);
	    
	    turn.setLocationRelativeTo(turnText);
	    turn.setVisible(true);
	}
	
    private void nextTurnMessage() {
    	JDialog turn = new JDialog(
	    		this,
	    		"Turn",
	    		true);
	    turn.setSize(350, 200);
	    turn.setLayout(null);
	    String msgString = KUAlchemistsGame.getInstance().getCurrentPlayer().getUsername() + "'s turn ended!"; 
	    String msgString1 = "Now it is " +
	    		KUAlchemistsGame.getInstance().getPlayer(KUAlchemistsGame.getInstance().getNextPlayerNo()).getUsername() 
	    		+ "'s turn!"; 
	    
	    turn.setModal(true);
	    
	    turn.getContentPane().setBackground(new Color(226, 109, 92));
	    
	    JLabel turnText = new JLabel(msgString);
	    turnText.setFont(new Font("Bahnschrift", Font.BOLD, 15));
	    turnText.setBounds(20, 50, 200, 30);
	    turnText.setForeground(new Color(255,225,168));
	    
	    JLabel turnText1 = new JLabel(msgString1);
	    turnText1.setFont(new Font("Bahnschrift", Font.BOLD, 15));
	    turnText1.setBounds(20, 70, 200, 30);
	    turnText1.setForeground(new Color(255,225,168));
	    
	    turn.add(turnText);
	    turn.add(turnText1);
	    
	    JButton ok = new JButton();
	    ok.setIcon(new ImageIcon("images/ok.png"));
	    ok.setBounds(205, 115, ok.getIcon().getIconWidth(), ok.getIcon().getIconHeight());
	    ok.addActionListener(e -> turn.dispose());
	    turn.add(ok);
	    
	    turn.setLocationRelativeTo(turnText);
	    turn.setVisible(true);
    }
	
	
	private void showHelpDialog() {
	    JDialog help = new JDialog(
	    		this,
	    		"Help",
	    		true);
	    
	    help.setSize(400, 400);
	    help.setModal(false);
	    help.setLayout(null);
	    
	    JLabel helpText = new JLabel();
	    helpText.setIcon(new ImageIcon("images/help.png"));
	    helpText.setBounds(0, 0, 400, 400);
	    help.add(helpText);
	    
	    help.setLocationRelativeTo(helpText);
	    help.setVisible(true);
	}

	private void showPauseDialog() {
	    JDialog pause = new JDialog(
	    		this, 
	    		"Pause", 
	    		true);
	    pause.setSize(400, 400);
	    pause.setLayout(null);
	    
	    JLabel pauseText = new JLabel();
	    pauseText.setIcon(new ImageIcon("images/pause.png"));
	    pauseText.setBounds(0, 0, 400, 400);
	    pause.add(pauseText);
	    
	    JButton ok = new JButton();
	    ok.setIcon(new ImageIcon("images/ok.png"));
	    ok.setBounds(155, 300, ok.getIcon().getIconWidth(), ok.getIcon().getIconHeight());
	    ok.addActionListener(e -> pause.dispose());
	    pause.add(ok);
	    
	    pause.setLocationRelativeTo(pauseText);
	    pause.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public static JPanel getPanelBoard() {
		return panelBoard;
	}

	public static void setPanelBoard(JPanel panelBoard) {
		BoardPage.panelBoard = panelBoard;
	}

	@Override
	public void onStateChange() {
		String currentName = KUAlchemistsGame.getInstance().getCurrentPlayer().getUsername();
		String playerName = KUAlchemistsGame.getInstance().getDevicePlayer();
		if (! playerName.equals(currentName)) {
			getPanelBoard().setVisible(false);
			waitPanel.setVisible(true);
			
		} else {
			waitPanel.setVisible(false);
			getPanelBoard().setVisible(true); 
			potionBrewing.onTurnChange();
			sellPotionPanel.onTurnChange();
			roundpnl.onTurnChange();
			publicationArea.onPubChange();
			updateReputationUI();
			onTurnChange();
			updateGoldUI();
	    	updateSicknessUI();
	    	updateReputationUI();
		}
	}

	@Override
	public void onTurnChange() {
		
		ingrDeckButton.setEnabled(true);
		artifactDeckButton.setEnabled(true);
		
		if(KUAlchemistsGame.getInstance().getRound()==2) {
			sellPotionPanel.setVisible(true);
	        publicationArea.setVisible(true);
		}
		
		if(KUAlchemistsGame.getInstance().getRound()==3) {
			for (JRadioButton btn: publicationArea.getTheoryButtons()){
				if (HandlerFactory.getInstance().getPublicationHandler().getPublishedTheories().get
						(publicationArea.getTheoryButtons().indexOf(btn)).getPlayerNo()== KUAlchemistsGame.getCurrentPlayerNo()) {
				btn.setEnabled(false);
				}
				else {
					btn.setEnabled(true);
				}
			}
			
		}
		
		if (KUAlchemistsGame.getInstance().getCurrentPlayerNo()==1){
			gold.setVisible(true);
			sickness.setVisible(true);
			player1_arts.setVisible(true);
			player1_ingr.setVisible(true);
			if (LoginPage.playerNum==2) {
				gold2.setVisible(false);
				sickness2.setVisible(false);
				player2_arts.setVisible(false);
				player2_ingr.setVisible(false);
			}
			if (LoginPage.playerNum==3) {
				gold3.setVisible(false);
				sickness3.setVisible(false);
				player3_arts.setVisible(false);
				player3_ingr.setVisible(false);
			}
			if (LoginPage.playerNum==4) {
				gold4.setVisible(false);
				sickness4.setVisible(false);
				player4_arts.setVisible(false);
				player4_ingr.setVisible(false);
			}
		}
		
		if (KUAlchemistsGame.getInstance().getCurrentPlayerNo()==2){
			gold2.setVisible(true);
			sickness2.setVisible(true);
			player2_arts.setVisible(true);
			player2_ingr.setVisible(true);
			
			gold.setVisible(false);
			sickness.setVisible(false);
			player1_arts.setVisible(false);
			player1_ingr.setVisible(false);
		
		}
		
		if (KUAlchemistsGame.getInstance().getCurrentPlayerNo()==3){
			gold3.setVisible(true);
			sickness3.setVisible(true);
			player3_arts.setVisible(true);
			player3_ingr.setVisible(true);
			
			gold2.setVisible(false);
			sickness2.setVisible(false);
			player2_arts.setVisible(false);
			player2_ingr.setVisible(false);
		
		}
		
		if (KUAlchemistsGame.getInstance().getCurrentPlayerNo()==4){
			gold4.setVisible(true);
			sickness4.setVisible(true);
			player4_arts.setVisible(true);
			player4_ingr.setVisible(true);
			
			gold3.setVisible(false);
			sickness3.setVisible(false);
			player3_arts.setVisible(false);
			player3_ingr.setVisible(false);
		
		}
		
		
		
		int wisdomIdolFlag = 0;
    	for (ArtifactCard artf : KUAlchemistsGame.getCurrentPlayer().getArtifacts()) {
    		if (artf.getID()==3) wisdomIdolFlag=1;
    	}
		
		//Wisdom Idol Implementation
		for (String p: KUAlchemistsGame.getRecentlyDebunkedPlayers()) {
			if (p.equals(KUAlchemistsGame.getCurrentPlayer().getUsername())) {
				
				// if the player has Wisdom Idol card
            	if (wisdomIdolFlag==1){
            		WisdomIdolDialog dialog = new WisdomIdolDialog(this);
					dialog.add(dialog.getPanelArtifact());
					dialog.setSize(600,350);
					dialog.setVisible(true);
            	}
			}
		}
		
		KUAlchemistsGame.getInstance().removeRecentlyDebunkedPlayer(KUAlchemistsGame.getCurrentPlayer().getUsername());
		updateReputationUI();
		
	}
	
	@Override
	public void onEndChange() {
		String[] playerNames = KUAlchemistsGame.getInstance().getPlayerNames();
		List<Integer> playerScores = KUAlchemistsGame.getInstance().getScores();

		EndgameDialog dialog = new EndgameDialog(null, "Game Over!", playerNames, playerScores);
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		dialog.setLocationRelativeTo(null);
	}

}