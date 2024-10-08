package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JoinWaitPage extends JFrame {

	private JPanel panel;

	public JoinWaitPage() {
		super("Join Page");
		panel = new BackgroundPanel("images/joinwaitpage.png");
		panel.setLayout(null);
	}
	
	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
	public void disposePage() {
		dispose();
	}
}
