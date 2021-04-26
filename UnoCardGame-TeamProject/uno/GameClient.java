package uno;

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
	private EndControl endControl;
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
	public void setEndControl(EndControl endControl) {
		this.endControl = endControl;
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
		System.out.println("Message: " + arg0);
		// If we received a String, figure out what this event is.
		if (arg0 instanceof String)
		{
			// Get the text of the message.
			String message = (String)arg0;
			String messages[] = message.split(",");

			// The server sends the client the card to be played
			if(messages[0].equals("PutOnTop")) {
				gameControl.cardPlaced(messages[1], Integer.parseInt(messages[2]));
			}
			
			// The server sends the initial card to begin the game
			else if(messages[0].equals("CardOnTop")) {
				gameControl.cardPlaced(messages[1], Integer.parseInt(messages[2]));
			}
			
			// The server sends this String to indicate the user is/isn't available to Uno attacks
			else if (messages[0].equals("Safe")) {
				gameControl.updateSafety(Boolean.parseBoolean(messages[1]));
			}
			
			// The server sends individual users this to indicate it is their turn
			else if(message.equals("YourTurn")) {
				gameControl.usersTurn();
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
			
			// Join the appropriate waiting room
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

			// Begin the game
			else if(message.equals("GameTrue")) {
				gameControl.StartGame();
			}
		}

		// The server sends an ArrayList containing the user's hand in String format
		else if (arg0 instanceof ArrayList<?>) {
			//The deck will be in the server for players to access
			//The array for the cards played will be in the server
			//One array for the cards in hand
			//One array for the cards that you are forced to draw
			ArrayList<String> cards = (ArrayList<String>)arg0;
			userCards.addAll(cards);
			gameControl.setUserCards(userCards);
			gameControl.updateCardCount();   

		}

		// Server sends an array containing each player's card count
		else if (arg0 instanceof Integer[]) {
			gameControl.updateUnoLabels((Integer[])arg0);
		}

		// Server sends each client the number they are in the game
		else if (arg0 instanceof Integer) {
			gameControl.setUserPlayerNum(Integer.parseInt(arg0.toString()));
		}
		
		// Server sends each client the winner of the game
		else if (arg0 instanceof User) {
			gameControl.sendToEndScreen((User)arg0);
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
