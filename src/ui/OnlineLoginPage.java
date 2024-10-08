package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;

import domain.KUAlchemistsGame;
import domain.controllers.HandlerFactory;

public class OnlineLoginPage extends JFrame implements ItemListener{
	
	private JPanel panel;
	private JRadioButton av1, av2, av3, av4, av5, av6;
	private int av = 1;
	
	public OnlineLoginPage() {
		super("Login Page");
		panel = new BackgroundPanel("images/pixil-frame-0.png");
		panel.setLayout(null);	
		
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon("images/usernamelabel1.png"));
		label.setBounds(285, 216, label.getIcon().getIconWidth(), label.getIcon().getIconHeight());
		
		JTextField username = new JTextField();
		username.setBackground(new Color(255,225,168));
		username.setSelectedTextColor(new Color(71, 45, 48));
		username.setFont(new Font("Bahnschrift", Font.BOLD, 19));
		username.setBounds(300, 245, 200, 30);
		
		JLabel label2 = new JLabel();
		label2.setIcon(new ImageIcon("images/choose avatar1.png"));
		label2.setBounds(300, 300, label2.getIcon().getIconWidth(), label2.getIcon().getIconHeight());
		
		av1 = new JRadioButton(new ImageIcon("images/avatar1.png"));
		av1.addItemListener(this);
		av1.setContentAreaFilled(false);
		av1.setBounds(297, 335, av1.getIcon().getIconWidth()+5, av1.getIcon().getIconHeight()+5);
		
		av2 = new JRadioButton(new ImageIcon("images/avatar2.png"));
		av2.addItemListener(this);
		av2.setContentAreaFilled(false);
		av2.setBounds(372, 335, av1.getIcon().getIconWidth()+5, av1.getIcon().getIconHeight()+5);
		
		av3 = new JRadioButton(new ImageIcon("images/avatar3.png"));
		av3.addItemListener(this);
		av3.setContentAreaFilled(false);
		av3.setBounds(447, 335, av1.getIcon().getIconWidth()+5, av1.getIcon().getIconHeight()+5);
		
		av4 = new JRadioButton(new ImageIcon("images/avatar4.png"));
		av4.addItemListener(this);
		av4.setContentAreaFilled(false);
		av4.setBounds(297, 400, av1.getIcon().getIconWidth()+5, av1.getIcon().getIconHeight()+5);
		
		av5 = new JRadioButton(new ImageIcon("images/avatar5.png"));
		av5.addItemListener(this);
		av5.setContentAreaFilled(false);
		av5.setBounds(372, 400, av1.getIcon().getIconWidth()+5, av1.getIcon().getIconHeight()+5);
	
		av6 = new JRadioButton(new ImageIcon("images/avatar6.png"));
		av6.addItemListener(this);
		av6.setContentAreaFilled(false);
		av6.setBounds(447, 400, av1.getIcon().getIconWidth()+5, av1.getIcon().getIconHeight()+5);
		
		ButtonGroup btngroup = new ButtonGroup();
		
		btngroup.add(av1);
		btngroup.add(av2);
		btngroup.add(av3);
		btngroup.add(av4);
		btngroup.add(av5);
		btngroup.add(av6);
		
		JLabel label3 = new JLabel();
		label3.setIcon(new ImageIcon("images/paddress.png"));
		label3.setBounds(720, 245, label3.getIcon().getIconWidth(), label3.getIcon().getIconHeight());
		
		JTextField IPaddress = new JTextField();
		IPaddress.setBackground(new Color(255,225,168));
		IPaddress.setSelectedTextColor(new Color(71, 45, 48));
		IPaddress.setFont(new Font("Bahnschrift", Font.BOLD, 19));
		IPaddress.setBounds(720, 270, 200, 30);
		
		JLabel label4 = new JLabel();
		label4.setIcon(new ImageIcon("images/portnumber.png"));
		label4.setBounds(720, 330, label4.getIcon().getIconWidth(), label4.getIcon().getIconHeight());
		
		JTextField portNo = new JTextField();
		portNo.setBackground(new Color(255,225,168));
		portNo.setSelectedTextColor(new Color(71, 45, 48));
		portNo.setFont(new Font("Bahnschrift", Font.BOLD, 19));
		portNo.setBounds(720, 355, 200, 30);
		
		JButton hostBtn = new JButton();
		hostBtn.setIcon(new ImageIcon("images/host.png"));
		hostBtn.setBackground(new Color(255,225,168));
		hostBtn.setBounds(560, 309, 100, 35);
		hostBtn.addActionListener( 
				new ActionListener() { 
					@Override
					public void actionPerformed(ActionEvent arg0) {
						HandlerFactory.getInstance().getHostHandler().startServer();
						HandlerFactory.getInstance().getHostHandler().login(username.getText(), av);
						
						HostWaitPage hostPage = new HostWaitPage();
						hostPage.setVisible(true);
						hostPage.add(hostPage.getPanel());
						hostPage.setExtendedState(JFrame.MAXIMIZED_BOTH);
						hostPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						KUAlchemistsGame.getInstance().addPlayerNumListener(hostPage);	
						
						dispose();
					}
				}
				);
		
		JButton joinBtn = new JButton();
		joinBtn.setIcon(new ImageIcon("images/join.png"));
		joinBtn.setBackground(new Color(255,225,168));
		joinBtn.setBounds(775, 410, 100, 35);
		joinBtn.addActionListener(
				new ActionListener() { 
					@Override
					public void actionPerformed(ActionEvent arg0) {
						HandlerFactory.getInstance().getJoinHandler().connectToServer(IPaddress.getText(), portNo.getText());
						HandlerFactory.getInstance().getJoinHandler().login(username.getText(), av);
						HandlerFactory.getInstance().getJoinHandler().openJoinWaitPage();
				
						dispose();
					}
				}
				);
		
		panel.add(label);
		panel.add(username);
		panel.add(label2);
		panel.add(av1);
		panel.add(av2);
		panel.add(av3);
		panel.add(av4);
		panel.add(av5);
		panel.add(av6);
		panel.add(label3);
		panel.add(IPaddress);
		panel.add(label4);
		panel.add(portNo);
		panel.add(hostBtn);
		panel.add(joinBtn);
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
			if (e.getSource()==av1) {
				av=1;
			}
			if (e.getSource()==av2) {
				av=2;
			}
			if (e.getSource()==av3) {
				av=3;
			}
			if (e.getSource()==av4) {
				av=4;
			}
			if (e.getSource()==av5) {
				av=5;
			}
			if (e.getSource()==av6) {
				av=6;
			}			
				
		}
		else {
			((JRadioButton)e.getSource()).setBorderPainted(false);
		}
		
	}
}
