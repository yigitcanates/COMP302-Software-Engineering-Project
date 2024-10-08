package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.ArtifactCard;
import domain.controllers.HandlerFactory;

public class WisdomIdolDialog extends JDialog {
	
private static JPanel panelArtifact;
	
	public static JPanel getPanelArtifact() {
		return panelArtifact;
	}

	public static void setPanelArtifact(JPanel panelArtifact) {
		WisdomIdolDialog.panelArtifact = panelArtifact;
	}

	public WisdomIdolDialog(Frame parent) {
		
		super(parent, "Wisdom Idol", true);
		setPanelArtifact(new JPanel());
		getPanelArtifact().setLayout(null);
		
		getPanelArtifact().setBackground(new Color(255,225,168));
		
		JLabel labelDescription1 = new JLabel("An Alchemist debunked your theory!!!");
		labelDescription1.setBounds(120, 65, 400, 20);
		getPanelArtifact().add(labelDescription1);

		labelDescription1.setFont(new Font("Bahnschrift", Font.BOLD, 19));
		labelDescription1.setForeground(new Color(71, 45, 48));
		
		JLabel labelDescription = new JLabel("Do you want to keep your reputation as a Wisdom Idol?");
		labelDescription.setBounds(75, 100, 600, 20);
		getPanelArtifact().add(labelDescription);
		
		labelDescription.setFont(new Font("Bahnschrift", Font.BOLD, 19));
		labelDescription.setForeground(new Color(71, 45, 48));
		
		
		JButton Button1 = new JButton("Yes");
		JButton Button2 = new JButton("No");
		
		Button1.setBounds(170, 150, 60, 40);
		Button1.setForeground(new Color(71, 45, 48));
		Button1.setBackground(new Color(201,203,163));
		Button1.setFont(new Font("Bahnschrift", Font.BOLD, 15));
		
		Button2.setBounds(290, 150, 60, 40);
		Button2.setForeground(new Color(71, 45, 48));
		Button2.setBackground(new Color(201,203,163));
		Button2.setFont(new Font("Bahnschrift", Font.BOLD, 15));
		
		Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	HandlerFactory.getInstance().getUseArtifactHandler().useArtifact(new ArtifactCard("Wisdom Idol", 3, false));
                HandlerFactory.getInstance().getUseArtifactHandler().performArtifact(1);
            	dispose();
            }
        });
		
		Button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

		getPanelArtifact().add(Button1);
		getPanelArtifact().add(Button2);
	}
}