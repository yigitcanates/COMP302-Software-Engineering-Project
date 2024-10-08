package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import domain.DBListener;
import domain.DeductionBoard;
import domain.KUAlchemistsGame;
import domain.Potion;
import domain.controllers.HandlerFactory;

public class DeductionBoardDialog extends JDialog implements DBListener{
	JPanel resultsTriangleJPanel, deductionGridJPanel, ingredientsJPanel;
	String[] ingredients; // = {"toad", "claw", "scorpion", "fern", "feather", "mushroom", "flower", "root"}; 
	


	int selectedQuality;
	DeductionBoard dBoard;
	
	
	public DeductionBoardDialog(Frame parent) {
		super(parent, "Deduction Board", true);
		
		this.dBoard = KUAlchemistsGame.getInstance().getCurrentPlayer().getdBoard();
		this.selectedQuality = 2;
		this.ingredients = new String[]{"rat", "bird", "garlic", "clover", "aloevera", "mushroom", "flower", "bluelotus"}; 
		
		setResultsTriangle(new JPanel());
		getResultsTriangle().setLayout(null);
		getResultsTriangle().setBackground(new Color(71,45,48));
		getResultsTriangle().setBounds(0,0,600,320);
		configureResultsTriangle();
		
		this.add(getResultsTriangle());
		
		setIngredientsJPanel(new JPanel());
		getIngredientsJPanel().setBounds(0, 320, 600, 50);
		getIngredientsJPanel().setBackground(new Color(255,225,168));
		getIngredientsJPanel().setLayout(null);
		IngredientPanel();
		this.add(getIngredientsJPanel());
		
		setDeductionGridJPanel(new JPanel());
		getDeductionGridJPanel().setLayout(null);
		getDeductionGridJPanel().setBounds(0,370,600,600);
		getDeductionGridJPanel().setBackground(new Color(71,45,48));
		configureDeductionGrid();
		
		this.add(getDeductionGridJPanel());
	}
	
	
	

    public void IngredientPanel() {
    	 
        int imageWidth = 25;  // Adjust based on your image dimensions
        
                
        
        for (int i = 0; i < 8; i++) {
            //ImageIcon image = createImageIcon("images/ingredient-" + this.ingredients[i] + ".png", 25, 50);
            JLabel label = new JLabel(new ImageIcon("images/ingredient-" + this.ingredients[i] + ".png"));
            //label.setPreferredSize(new Dimension(imageWidth, image.getIconHeight()));  // Maintain aspect ratio
            label.setBounds(76 + 65*i, 0, 25, 50);
            getIngredientsJPanel().add(label);
        }
        
    }
	
	private ImageIcon createImageIcon(String path, int width, int height){
		ImageIcon icon = null;
		try {
			//System.out.println(path);
            BufferedImage image = ImageIO.read(new File(path));
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImage);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return icon;
	}
	
	// GETTERS AND SETTERS
	public JPanel getIngredientsJPanel() {
		return ingredientsJPanel;
	}

	public void setIngredientsJPanel(JPanel ingredientsJPanel) {
		this.ingredientsJPanel = ingredientsJPanel;
	}
	
	public JPanel getResultsTriangle() {
		return resultsTriangleJPanel;
	}

	public void setResultsTriangle(JPanel resultsTriangle) {
		this.resultsTriangleJPanel = resultsTriangle;
	}

	public JPanel getDeductionGridJPanel() {
		return deductionGridJPanel;
	}

	public void setDeductionGridJPanel(JPanel deductionGridJPanel) {
		this.deductionGridJPanel = deductionGridJPanel;
	}

	public void configureDeductionGrid() {
		
		for (int i = 0; i < 8; i++) {	// iterate over alchemy markers
			int tempi = i;
			
			ImageIcon marker = createImageIcon("images/alchemy-" + this.ingredients[i] + ".png", 48, 50);
			JLabel label = new JLabel(marker);
			//label.setIcon(new ImageIcon("images/alchemy-" + this.ingredients[i] + ".png"));
			label.setOpaque(false); // Set opaque to true to see the background color
            //label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for clarity
            label.setBounds(6, 7 + i * 58, 50, 52);
            getDeductionGridJPanel().add(label); // Add label to the frame		
		}
		
		Boolean[][] dGrid = dBoard.getDeductionGrid();
		for (int i = 0; i < 8; i++) {	// iterate over alchemy markers
			int tempi = i;
			for (int j = 0; j < 8; j++) {	//iterate over ingredients
				int tempj = j;
				JButton button = new JButton();
				button.setOpaque(true); // Set opaque to true to see the background color
                if (dGrid[j][i]) {
                	button.setBackground(new Color(189, 12, 9)); // set red if marked by user 
                }
                else{
                	button.setBackground(new Color(222,220,222)); // Set initial background color
                }
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for clarity
                button.setBounds(64 + 64*j, 5 + i * 58, 57, 54);
                
                button.addActionListener(e -> {
        			HandlerFactory.getInstance().getDeductionBoardHandler().markDeductionGrid(tempj, tempi);
                });
                
                //button.setText("#" + (i+1));
                getDeductionGridJPanel().add(button); // Add label to the frame
                
			}
		}
	}
	
	public void configureResultsTriangle() {
		
		Potion[] rTriangle = dBoard.getResultsTriangle();
		
		JButton positiveButton = new JButton("+");
        JButton negativeButton = new JButton("-");
        JButton neutralButton = new JButton("N");
        
        //positiveButton.setFont(positiveButton.getFont().deriveFont(7f));
        //negativeButton.setFont(negativeButton.getFont().deriveFont(7f));
        //neutralButton.setFont(neutralButton.getFont().deriveFont(7f));
        
        positiveButton.setBackground(new Color(201, 203, 163));
        positiveButton.setFont(new Font("Bahnschrift", Font.BOLD, 20));
        positiveButton.setMargin(new Insets(0,0,0,0));
        negativeButton.setBackground(new Color(201, 203, 163));
        negativeButton.setFont(new Font("Bahnschrift", Font.BOLD, 20));
        negativeButton.setMargin(new Insets(0,0,0,0));
        neutralButton.setBackground(new Color(201, 203, 163));
        neutralButton.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        neutralButton.setMargin(new Insets(0,0,0,0));
        
        // Add action listeners to the buttons
        positiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedQuality = 1; // Update selectedPotion based on user selection
                
            }
        });

        negativeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	selectedQuality = -1; // Update selectedPotion based on user selection
                
            }
        });

        neutralButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	selectedQuality = 0; // Update selectedPotion based on user selection
                
            }
        });
        
        positiveButton.setBounds(15, 15, 40, 40);
        negativeButton.setBounds(65, 15, 40, 40);
        neutralButton.setBounds(115, 15, 40, 40);
        
        // Add buttons to the results triangle panel
        getResultsTriangle().add(positiveButton);
        getResultsTriangle().add(negativeButton);
        getResultsTriangle().add(neutralButton);
		
		int index = 0;
		int gap = 65;
		
		for (int i = 1; i < 8; i++) {
			int startX = (325 - (i*32));
			int startY = (i - 1) * 43;
			
			for (int j = 0; j < i; j++) {
				int tempindex = index;
				JLabel label = new JLabel();
				label.setOpaque(true); // Set opaque to true to see the background color
				label.setBackground(new Color(255,225,168));
	            label.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Add border for clarity
	            label.setBounds(startX + (j * gap), 6 + startY, 40, 40);
	            
	            if (rTriangle[index] != null) {
	            	switch (rTriangle[index].getQuality()) {
	            	case 1: 
	            		label.setText("+");
	            		label.setFont(new Font("Bahnschrift", Font.BOLD, 20));
	            		break;
	            	case -1:
	            		label.setText("-");
	            		label.setFont(new Font("Bahnschrift", Font.BOLD, 20));
	            		break;
	            	case 0:
	            		label.setText("N");
	            		label.setFont(new Font("Bahnschrift", Font.BOLD, 15));
	            		break;
	            	}
	            }
	            else {
	            	label.setText("-------");
	            	label.setFont(new Font("Bahnschrift", Font.BOLD, 9));
	            }
	            
	            label.setHorizontalAlignment(SwingConstants.CENTER); // Center text horizontally
	            label.setFocusable(true);
	            label.addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseClicked(MouseEvent e) {
	                    if (selectedQuality != 2) {
	                    	HandlerFactory.getInstance().getDeductionBoardHandler().markResultsTriangle(tempindex, selectedQuality);
	                    	selectedQuality = 2;
	                    	System.out.println("results triangle mark sent to handler by ui");
	                    }
	                }
	            });
	            
				getResultsTriangle().add(label);
				index++;
				
			}
		}
		
	}


	@Override
	public void onDBChange() {
		SwingUtilities.invokeLater(() -> {
			getDeductionGridJPanel().removeAll();
			getResultsTriangle().removeAll();
			getIngredientsJPanel().removeAll();
	        configureDeductionGrid();
	        configureResultsTriangle();
	        IngredientPanel();
	        // Remove and re-add components to the dialog's content pane
	        this.getContentPane().removeAll();
	        this.getContentPane().add(getResultsTriangle());
	        this.getContentPane().add(getDeductionGridJPanel());
	        this.getContentPane().add(getIngredientsJPanel());
	        revalidate(); // Revalidate the container to update the layout
	        repaint(); // Repaint to reflect the changes
	    });
	}

}
