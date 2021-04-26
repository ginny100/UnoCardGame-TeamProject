package uno;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame
{
	

	// Constructor that creates the client GUI.
	public ClientGUI()
	{
		GameClient client = new GameClient();
		client.setHost("localhost");
		client.setPort(8300);
		try {
			client.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Set the title and default close operation.
		this.setTitle("Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the card layout container.
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel(cardLayout);

		//Create the Controllers next
		//Next, create the Controllers
		InitialControl ic = new InitialControl(container); 
		LoginControl lc = new LoginControl(container, client);
		CreateAccountControl ac = new CreateAccountControl(container, client);
		MenuControl mc = new MenuControl(container, client);
		GameControl gc = new GameControl(container, client);
		EndControl ec = new EndControl(container, client);

		client.setLoginControl(lc);
		client.setCreateAccountControl(ac);
		client.setMenuControl(mc);
		client.setGameControl(gc);
		client.setEndControl(ec);

		// Create the four views. (need the controller to register with the Panels
		JPanel view1 = new InitialPanel(ic);
		JPanel view2 = new LoginPanel(lc);
		JPanel view3 = new CreateAccountPanel(ac);
		JPanel view4 = new MenuPanel(mc);
		JPanel view5 = new GamePanel(gc);
		JPanel view6 = new EndPanel(ec);


		// Add the views to the card layout container.
		container.add(view1, "1");
		container.add(view2, "2");
		container.add(view3, "3");
		container.add(view4, "4");
		container.add(view5, "5");


		// Show the initial view in the card layout.
		cardLayout.show(container, "1");

		// Add the card layout container to the JFrame.
		this.add(container, BorderLayout.CENTER);
		
		// Show the JFrame.
		this.setSize(750, 750);
		this.setVisible(true);
	}

	// Main function that creates the client GUI when the program is started.
	public static void main(String[] args)
	{
		new ClientGUI();
	}
}
