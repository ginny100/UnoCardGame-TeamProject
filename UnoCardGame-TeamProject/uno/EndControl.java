package uno;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class EndControl implements ActionListener{

	private JPanel container;
	private GameClient client;

	public EndControl(JPanel container,GameClient client)
	{
		this.container = container;
		this.client = client;
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The Exit button closes the application.
		if (command == "Exit")
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}
		
		// The Play Again button brings the user back to the main menu and allows them to enter a new game
		else if (command == "Play Again") {
			//TODO
		}


	}

	// If any errors occur, update the errorlabel
	public void displayError(String error) {

	}
}
