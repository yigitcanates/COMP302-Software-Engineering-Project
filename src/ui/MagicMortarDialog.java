package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.Ingredient;
import domain.PotionMaker;
import domain.controllers.HandlerFactory;

public class MagicMortarDialog extends JDialog{

	private static Ingredient selectedIngr;
	private static JPanel panelArtifact;
	

	public static JPanel getPanelArtifact() {
		return panelArtifact;
	}


	public static void setPanelArtifact(JPanel panelArtifact) {
		MagicMortarDialog.panelArtifact = panelArtifact;
	}


	public Ingredient getSelectedIngr() {
		return selectedIngr;
	}


	public void setSelectedIngr(Ingredient selectedIngr) {
		this.selectedIngr = selectedIngr;
	}


	public MagicMortarDialog(Frame parent) {
		// display the ingredients used in making the potion
		// when the user clicks one of them, call UseArtifactHandler().useCard(list)
		// where list is a list of ingredients (only one in this case)
		super(parent, "Magic Mortar", true);
		setPanelArtifact(new JPanel());
		getPanelArtifact().setLayout(null);
		
		getPanelArtifact().setBackground(new Color(255,225,168));
		
		// get the ingredient objects from potionmaker
		Ingredient ingr1 = PotionMaker.getInstance().getIngr1();
		Ingredient ingr2 = PotionMaker.getInstance().getIngr2();
		
		// initialize potion maker
		JLabel labelDescription = new JLabel("Click on the ingredient to keep");
		labelDescription.setBounds(145, 70, 400, 20);
		labelDescription.setFont(new Font("Bahnschrift", Font.BOLD, 19));
		labelDescription.setForeground(new Color(71, 45, 48));
		
		getPanelArtifact().add(labelDescription);
				
		// initialize ingredient buttons and submit button
		JButton ingrButton1 = new JButton(new ImageIcon(ingr1.getImage()));
		JButton ingrButton2 = new JButton(new ImageIcon(ingr2.getImage()));
		
		JButton submitButton = new JButton("Submit Choice");
		
		ingrButton1.setBounds(170, 140, 80, 50);
		ingrButton1.setBackground(new Color(201,203,163));
		
		ingrButton2.setBounds(290, 140, 80, 50);
		ingrButton2.setBackground(new Color(201,203,163));
		
		submitButton.setBounds(192, 220, 156, 40);
		submitButton.setBackground(new Color(71,45,48));
		submitButton.setForeground(new Color(201,203,163));
		submitButton.setFont(new Font("Bahnschrift", Font.BOLD, 15));
		
		submitButton.setVisible(false);
		
		submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HandlerFactory.getInstance().getUseArtifactHandler().performArtifact(getSelectedIngr());
            	System.out.println("Magic mortar decision submitted");
            	dispose();
            }
        });
		
		ingrButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedIngr(ingr1);
                submitButton.setVisible(true);
            }
        });
		
		ingrButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedIngr(ingr2);
                submitButton.setVisible(true);
            }
        });
		
		getPanelArtifact().add(submitButton);
		getPanelArtifact().add(ingrButton1);
		getPanelArtifact().add(ingrButton2);
						
	}
}
