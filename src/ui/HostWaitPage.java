package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import domain.KUAlchemistsGame;
import domain.playerNumListener;
import domain.controllers.HandlerFactory;

public class HostWaitPage extends JFrame implements playerNumListener  {
	
	private JPanel panel;
	private JLabel label3;
	private JButton goBtn;

	public HostWaitPage(){
		super("Hosting Page");
		panel = new BackgroundPanel("images/pixil-frame-0.png");
		panel.setLayout(null);
		
		/*
		JLabel iplbl = new JLabel(new ImageIcon("images/paddress.png"));
		iplbl.setBounds(200, 200, iplbl.getIcon().getIconWidth(), iplbl.getIcon().getIconHeight());
		iplbl.setOpaque(false);*/
		
		
		JLabel label1 = new JLabel("IP Address: " + HandlerFactory.getInstance().getHostHandler().getIPAddress());
		label1.setFont(new Font("Bahnschrift", Font.BOLD, 19));
		label1.setForeground(new Color(255,225,168));
		label1.setBounds(500, 260, 600, 30);
		
		JLabel label2 = new JLabel("Port: " +  Integer.toString(HandlerFactory.getInstance().getHostHandler().getPort()));
		label2.setFont(new Font("Bahnschrift", Font.BOLD, 19));
		label2.setForeground(new Color(255,225,168));
		label2.setBounds(500, 330, 200, 30);
		
		int numPlayers = KUAlchemistsGame.getNumPlayers();
		label3 = new JLabel("Current Number of Players: " + Integer.toString(numPlayers)); // to do observer pattern for current number of players.
		label3.setFont(new Font("Bahnschrift", Font.BOLD, 19));
		label3.setForeground(new Color(255,225,168));
		label3.setBounds(500, 400, 500, 30);
		
		goBtn = new JButton();
		goBtn.setEnabled(false);
		goBtn.setIcon(new ImageIcon("images/startgame1.png"));
		goBtn.setBounds(532, 480, goBtn.getIcon().getIconWidth(), goBtn.getIcon().getIconHeight());
		goBtn.addActionListener( 
				new ActionListener() { 
					@Override
					public void actionPerformed(ActionEvent arg0) {
						HandlerFactory.getInstance().getJoinHandler().startGame();
						dispose();
					}
				}
				);
		
		//panel.add(iplbl);
		panel.add(label1);
		panel.add(label2);
		panel.add(label3);
		panel.add(goBtn);
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	@Override
	public void onPlayerNumChange() {
		int numPlayers = KUAlchemistsGame.getNumPlayers();
		label3.setText("Current Number of Players: " + Integer.toString(numPlayers));
		if(numPlayers>1) {
			goBtn.setEnabled(true);
		}
		else {
			goBtn.setEnabled(false);
		}
		//panel.add(label3);
	}
}
