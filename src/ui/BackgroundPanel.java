package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel{
	
	private Image image;
	
	public BackgroundPanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }

	public BackgroundPanel(Image image) {
		super();
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	

}