package Uno;

import java.awt.CardLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient
{
	// Private data fields for storing the GUI controllers.
	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;
	private MenuControl menuControl;
	private GameControl gameControl;
	private String user;
	private ArrayList<String> userCards;

	public void setUserName(String user) {
		this.user = user;
	}

	public String getUserName() {
		return user;
	}

	// Setters for the GUI controllers.
	public void setLoginControl(LoginControl loginControl)
	{
		this.loginControl = loginControl;
	}
	public void setCreateAccountControl(CreateAccountControl createAccountControl)
	{
		this.createAccountControl = createAccountControl;
	}
	public void setMenuControl(MenuControl menuControl)
	{
		this.menuControl = menuControl;
	}
	public void setGameControl(GameControl gameControl)
	{
		this.gameControl = gameControl;
	}

	// Constructor for initializing the client with default settings.
	public GameClient()
	{
		super("localhost", 8300);
		userCards = new ArrayList<String>();
	}

	// Method that handles messages from the server.
	public void handleMessageFromServer(Object arg0)
	{
		System.out.println(arg0);
		// If we received a String, figure out what this event is.
		if (arg0 instanceof String)
		{
			// Get the text of the message.
			String message = (String)arg0;
			System.out.println(message);
			String messages[] = message.split(",");

			if(messages[0].equals("PutOnTop")) {
				gameControl.CardPlaced(messages[1], Integer.parseInt(messages[2]));
			}
			else if(messages[0].equals("cardOnTop")) {
				gameControl.CardPlaced(messages[1], Integer.parseInt(messages[2]));
			}
			else if(message.equals("CanPlace")) {
				gameControl.canPlaceCard();
			}
			else if(messages[0].equals("uno")) {
				gameControl.unoCall(Integer.parseInt(messages[1]));
			}
			else if(message.equals("YourTurn")) {
				gameControl.usersTurn();
			}
			else if(message.equals("Blue") || message.equals("Blue") || message.equals("Yellow")
					|| message.equals("Green")) {
				gameControl.colorChange(message);
			}
			// If we successfully logged in, tell the login controller.
			else if (message.equals("LoginSuccessful"))
			{
				loginControl.loginSuccess();
			}

			// If we successfully created an account, tell the create account controller.
			else if (message.equals("CreateAccountSuccessful"))
			{
				createAccountControl.createAccountSuccess();
			}

			else if(message.equals("2waiting")) {
				menuControl.JoinPlayers();
				gameControl.setNumPlayers(2);
			}

			else if(message.equals("3waiting")) {
				menuControl.JoinPlayers();
				gameControl.setNumPlayers(3);
			}

			else if(message.equals("4waiting")) {
				menuControl.JoinPlayers();
				gameControl.setNumPlayers(4);
			}

			else if(message.equals("GameTrue")) {
				gameControl.StartGame();
			}
		}

		else if (arg0 instanceof ArrayList<?>) {
			//The deck will be in the server for players to access
			//The array for the cards played will be in the server
			//One array for the cards in hand
			ArrayList<String> cards = (ArrayList<String>)arg0;
			userCards.addAll(cards);
			gameControl.setUserCards(userCards);
			//One array for the cards that you are forced to draw
		}
		
		else if (arg0 instanceof Integer) {
			gameControl.setUserPlayerNum(Integer.parseInt(arg0.toString()));
		}

		// If we received an Error, figure out where to display it.
		else if (arg0 instanceof Error)
		{
			// Get the Error object.
			Error error = (Error)arg0;

			// Display login errors using the login controller.
			if (error.getType().equals("Login"))
			{
				loginControl.displayError(error.getMessage());
			}

			// Display account creation errors using the create account controller.
			else if (error.getType().equals("CreateAccount"))
			{
				createAccountControl.displayError(error.getMessage());
			}
		}
	}  

}
