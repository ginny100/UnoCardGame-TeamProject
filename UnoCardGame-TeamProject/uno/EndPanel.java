package uno;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndPanel extends JPanel {
	
	private JLabel errorLabel;
	private JLabel resultLabel;
	private JLabel subResultLabel;
	
	public EndPanel(EndControl ec) {
		// Create a panel for the error label at the top of the GUI.
	    JPanel labelPanel = new JPanel(new GridLayout(1, 1, 5, 5));
	    errorLabel = new JLabel("", JLabel.CENTER);
	    errorLabel.setForeground(Color.RED);
	    labelPanel.add(errorLabel);
	    
	    // Create a panel for the result labels at the center of the GUI
	    JPanel resultPanel = new JPanel(new GridLayout(2, 1, 5, 5));
	    resultLabel = new JLabel("You Lose", JLabel.CENTER);
	    resultLabel.setFont(new Font(resultLabel.getFont().getName(), Font.BOLD, 30));
	    subResultLabel = new JLabel("Player # (Username) Won", JLabel.CENTER);
	    subResultLabel.setFont(new Font("Garamond", Font.PLAIN, 15));
	    resultPanel.add(resultLabel);
	    resultPanel.add(subResultLabel);
	    
	    // Create a panel for the buttons.
	    JPanel buttonPanel = new JPanel();
	    JButton submitButton = new JButton("Play Again");
	    submitButton.addActionListener(ec);
	    JButton cancelButton = new JButton("Exit");
	    cancelButton.addActionListener(ec);    
	    buttonPanel.add(submitButton);
	    buttonPanel.add(cancelButton);

	    // Arrange the three panels in a grid.
	    JPanel grid = new JPanel(new GridLayout(3, 1, 0, 20));
	    grid.add(labelPanel);
	    grid.add(resultPanel);
	    grid.add(buttonPanel);
	    this.add(grid);
	}

	// Allows access to the errorLabel
	public void setError(String error) {
		errorLabel.setText(error);
	}
	
	// GameController can call these two methods to provide the right information before displaying it
	public void setStatus(String status) {
		resultLabel.setText("You " + status);
	}
	
	public void winnerInfo(String playerNum, String username) {
		subResultLabel.setText("Player " + playerNum + " ("+username+") Won");
	}
}
