package uno;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;

import javax.swing.JLabel;

public class MenuPanel extends JPanel
{

	// Constructor for the login panel.
	public MenuPanel(MenuControl Mc)
	{
		// Create a panel for the labels at the top of the GUI.
		JPanel labelPanel = new JPanel(new GridLayout(3, 1, 5, 5));
		JLabel instructionLabel = new JLabel("The UNO Card Game", JLabel.CENTER);
		labelPanel.add(instructionLabel);

		// Create a panel for the buttons.
		JPanel buttonPanel = new JPanel();
		JButton twoPlayerButton = new JButton("Join 2 player game");
		twoPlayerButton.addActionListener(Mc);
		JButton threePlayerButton = new JButton("Join 3 player game");
		threePlayerButton.addActionListener(Mc);
		JButton fourPlayerButton = new JButton("Join 4 player game");
		fourPlayerButton.addActionListener(Mc); 
		JButton ExitButton = new JButton("Exit");
		ExitButton.addActionListener(Mc); 
		buttonPanel.add(twoPlayerButton);
		buttonPanel.add(threePlayerButton);
		buttonPanel.add(fourPlayerButton);
		buttonPanel.add(ExitButton);

		// Arrange the three panels in a grid.
		JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
		grid.add(labelPanel);
		grid.add(buttonPanel);
		this.add(grid);
	}
}