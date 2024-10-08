package ui;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import domain.controllers.HandlerFactory;

public class GameModePage extends JFrame implements ItemListener{
	private JPanel panel;
	private JRadioButton onlineBtn; 
	private JRadioButton offlineBtn; 
	private JButton goBtn;
	
	
	public GameModePage() {
		
		super("KUAlchemists");
		panel = new BackgroundPanel("images/pixil-frame-0.png");
		panel.setLayout(null);
		
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon("images/choosegamemode.png"));
		label.setBounds(570, 250, label.getIcon().getIconWidth(), label.getIcon().getIconHeight());
		panel.add(label);
		
		//two radiobuttons shown in the game mode page for online and offline options		
		onlineBtn = new JRadioButton();
		onlineBtn.setIcon(new ImageIcon("images/online.png"));
		onlineBtn.setContentAreaFilled(false);
		onlineBtn.addItemListener(this);
		onlineBtn.setBounds(575, 315, onlineBtn.getIcon().getIconWidth()+6, onlineBtn.getIcon().getIconHeight()+7);
		panel.add(onlineBtn);
		
		offlineBtn = new JRadioButton();
		offlineBtn.setIcon(new ImageIcon("images/offline.png"));
		offlineBtn.addItemListener(this);
		offlineBtn.setContentAreaFilled(false);
		offlineBtn.setBounds(710, 315, offlineBtn.getIcon().getIconWidth()+6, offlineBtn.getIcon().getIconHeight()+7);
		panel.add(offlineBtn);
    	
    	ButtonGroup btngroup = new ButtonGroup();
    	
    	btngroup.add(onlineBtn);
    	btngroup.add(offlineBtn);    	
    	
    	//the button that will lead to the login page according to the game mode chosen
    	goBtn = new JButton();
    	goBtn.setIcon(new ImageIcon("images/startgame1.png"));
    	goBtn.setBounds(530, 380, 350, goBtn.getIcon().getIconHeight()+10);
    	goBtn.setBackground(new Color(255, 225, 168));
    	goBtn.addActionListener(e -> {
    		if (onlineBtn.isSelected()) {
    			HandlerFactory.getInstance().getGameModeHandler().setGameMode("online");
    		}
    		if (offlineBtn.isSelected()) {
    			
    			HandlerFactory.getInstance().getGameModeHandler().setGameMode("offline");
    			
    		}
    		dispose();
    	});
		panel.add(goBtn);
		
	}


	public JPanel getPanel() {
		return panel;
	}


	public void setPanel(JPanel panel) {
		this.panel = panel;
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (e.getStateChange()==1) {
			((JRadioButton)e.getSource()).setBorderPainted(true);
			((JRadioButton)e.getSource()).setBorder(BorderFactory.createLineBorder(new Color(255,225,168), 2));
		}
		else {
			((JRadioButton)e.getSource()).setBorderPainted(false);
		}
		
	}

}
