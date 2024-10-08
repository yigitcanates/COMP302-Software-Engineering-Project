package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.KUAlchemistsGame;
import domain.Ingredient;
import domain.IngredientDeck;
import domain.controllers.HandlerFactory;

public class ElixirOfInsightDialog extends JDialog{
	static private JButton firstTop, secondTop, thirdTop;
	static private JLabel labelDescription;
	private static JPanel panelArtifact;
	
	//public static int;
	
	private List<Ingredient> clickedIngredientOrder; // List to store the order of clicked buttons
	
	
	public ElixirOfInsightDialog(Frame parent) {
		
		super(parent, "Elixir of Insight", true);
		setPanelArtifact(new JPanel());
		getPanelArtifact().setLayout(null);
		
		getPanelArtifact().setBackground(new Color(255,225,168));
		
		List<Ingredient> topThreeIngredientsList = IngredientDeck.getInstance().getTopThreeCards();
		clickedIngredientOrder = new ArrayList<>();
		
		labelDescription = new JLabel("Click on the ingredients in the order from bottom to top");
		labelDescription.setFont(new Font("Bahnschrift", Font.BOLD, 19));
		labelDescription.setForeground(new Color(71, 45, 48));
		
		
		firstTop = new JButton(new ImageIcon(topThreeIngredientsList.get(0).getImage()));
		secondTop = new JButton(new ImageIcon(topThreeIngredientsList.get(1).getImage()));
		thirdTop = new JButton(new ImageIcon(topThreeIngredientsList.get(2).getImage()));
		
		firstTop.setBounds(110, 170, 100, 50);
		firstTop.setBackground(new Color(201,203,163));
		//firstTop.setForeground(Color.BLACK);
		//firstTop.setBackground(Color.WHITE);
		
		secondTop.setBounds(230, 170, 100, 50);
		secondTop.setBackground(new Color(201,203,163));
		//secondTop.setForeground(Color.BLACK);
		//secondTop.setBackground(Color.WHITE);
		
		thirdTop.setBounds(350, 170, 100, 50);
		thirdTop.setBackground(new Color(201,203,163));
		//thirdTop.setForeground(Color.BLACK);
		//thirdTop.setBackground(Color.WHITE);
		
		labelDescription.setBounds(50, 100, 600, 20);
		getPanelArtifact().add(labelDescription);
		
		// ActionListener to handle button clicks and update the order list
        ActionListener buttonClickListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton clickedButton = (JButton) e.getSource();
                
                if (clickedButton == firstTop) {
                	getClickedIngredientOrder().add(topThreeIngredientsList.get(0));
                	firstTop.setVisible(false);
                }
                else if (clickedButton == secondTop) {
                	getClickedIngredientOrder().add(topThreeIngredientsList.get(1));
                	secondTop.setVisible(false);
                }
                else if (clickedButton == thirdTop) {
                	getClickedIngredientOrder().add(topThreeIngredientsList.get(2));
                	thirdTop.setVisible(false);
                }

                // Pass the clickedButtonOrder list to the controller
                // Call the controller method to handle the order
                if (getClickedIngredientOrder().size() == 3) {
                	HandlerFactory.getInstance().getUseArtifactHandler().performArtifact(clickedIngredientOrder);
                	dispose();
                }
                
            }
        };
        
        // Attach the ActionListener to the buttons
        firstTop.addActionListener(buttonClickListener);
        secondTop.addActionListener(buttonClickListener);
        thirdTop.addActionListener(buttonClickListener);
			
	    // Add buttons to the panel
        getPanelArtifact().add(firstTop);
        getPanelArtifact().add(secondTop);
        getPanelArtifact().add(thirdTop);
	}

	


	// Getter and Setter for panelArtifact JPanel
    public JPanel getPanelArtifact() {
        return panelArtifact;
    }

    public void setPanelArtifact(JPanel panelArtifact) {
    	ElixirOfInsightDialog.panelArtifact = panelArtifact;
    }

    // Getter method for clickedButtonOrder
    public List<Ingredient> getClickedIngredientOrder() {
        return clickedIngredientOrder;
    }

    // Setter method for clickedButtonOrder
    public void setClickedIngredientOrder(List<Ingredient> clickedIngredientOrder) {
        this.clickedIngredientOrder = clickedIngredientOrder;
    }
    
	
}
