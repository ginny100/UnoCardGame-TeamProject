package Uno;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

public class MenuControl implements ActionListener
{
	// Private data fields for the container and chat client.
	private JPanel container;
	private GameClient client;

	// Constructor for the login controller.
	public MenuControl(JPanel container,GameClient client)
	{
		this.container = container;
		this.client = client;
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae)
	{
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		if (command == "Join 2 player game")
		{
			MenuData data = new MenuData("2", client.getUserName());
			try {
				//You are sending the data of 2 to the server so that the
				//Server knows whether or not it is a 2 player game
				client.sendToServer(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (command == "Join 3 player game")
		{
			MenuData data = new MenuData("3", client.getUserName());
			try {
				//You are sending the data of 2 to the server so that the
				//Server knows whether or not it is a 3 player game
				client.sendToServer(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (command == "Join 3 player game")
		{
			MenuData data = new MenuData("4", client.getUserName());
			try {
				//You are sending the data of 2 to the server so that the
				//Server knows whether or not it is a 4 player game
				client.sendToServer(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (command == "Exit")
		{
			//This is supposed to exit the program. Maybe we should change it to log out
			System.exit(0);
		}
	}
	
	public void JoinPlayers() {
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "5");
	}

}
