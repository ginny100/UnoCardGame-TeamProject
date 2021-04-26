package uno;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class GameServer extends AbstractServer
{
	// Data fields for this chat server.
	private JTextArea log;
	private JLabel status;
	private boolean running = false;
	private DatabaseFile File = new DatabaseFile();

	private int numTwoPlayersReady;
	private int numThreePlayersReady;
	private int numFourPlayersReady;

	private ArrayList<String> deck;
	private ArrayList<String> cardsPlaced;
	private String topCard;
	private Deck d;
	private int direction;

	private ArrayList<String> handCardsAdded;

	private Hashtable<Integer, User> users;
	//private ArrayList<ConnectionToClient> FIXMEPLEASEAHHH;

	private int usersTurn;

	// Constructor for initializing the server with default settings.
	public GameServer()
	{
		super(12345);
		this.setTimeout(500);

		numTwoPlayersReady = 0;
		numThreePlayersReady = 0;
		numFourPlayersReady = 0;
		direction = 1;	// Direction of play. 1 = forward, -1 = backwards

		cardsPlaced = new ArrayList<String>();
		topCard = new String();
		d = new Deck();
		deck = d.getDeck();

		handCardsAdded = new ArrayList<String>();

		users = new Hashtable<Integer, User>();
	}

	// Getter that returns whether the server is currently running.
	public boolean isRunning()
	{
		return running;
	}

	// Setters for the data fields corresponding to the GUI elements.
	public void setLog(JTextArea log)
	{
		this.log = log;
	}
	public void setStatus(JLabel status)
	{
		this.status = status;
	}
	public void setDatabase(DatabaseFile File){
		this.File = File;
	}

	// When the server starts, update the GUI.
	public void serverStarted()
	{
		running = true;
		status.setText("Listening");
		status.setForeground(Color.GREEN);
		log.append("Server started\n");
	}

	// When the server stops listening, update the GUI.
	public void serverStopped()
	{
		status.setText("Stopped");
		status.setForeground(Color.RED);
		log.append("Server stopped accepting new clients - press Listen to start accepting new clients\n");
	}

	// When the server closes completely, update the GUI.
	public void serverClosed()
	{
		running = false;
		status.setText("Close");
		status.setForeground(Color.RED);
		log.append("Server and all current clients are closed - press Listen to restart\n");
	}

	// When a client connects or disconnects, display a message in the log.
	@Override
	public void clientConnected(ConnectionToClient client)
	{
		log.append("Client " + client.getId() + " connected\n");
	}

	// Try to gracefully recover from users leaving
	@Override
	public void clientDisconnected(ConnectionToClient client) {
		System.out.println("Lost connection to " + client);
	}

	// Call all the appropriate methods
	public void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		if (arg0 instanceof MenuData) {
			handleMenuData((MenuData)arg0, arg1);
		} else if (arg0 instanceof LoginData) {
			handleLoginData((LoginData)arg0, arg1);
		} else if (arg0 instanceof GameData) {
			handleGameData((GameData)arg0, arg1);
		} else {
			System.out.println("Unexpected type of data recieved");
		}
	}

	// Handle LoginData sent to the server
	public void handleLoginData(LoginData data, ConnectionToClient arg1) {
		// Check the username and password with the database.
		Object result;

		File = new DatabaseFile();

		if(File.UserPassCheck(data.getUsername() + " " + data.getPassword())) {
			result = "LoginSuccessful";
			log.append("Client " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");
		}
		else
		{
			result = new Error("The username and password are incorrect.", "Login");
			log.append("Client " + arg1.getId() + " failed to log in\n");
		}

		// Send the result to the client.
		try
		{
			arg1.sendToClient(result);
		}
		catch (IOException e)
		{
			return;
		}
	}

	// Handle MenuData sent to the server
	private void handleMenuData(MenuData data, ConnectionToClient arg1)
	{
		Object result = null;
		Object result2 = null;
		ArrayList<String> firstCards = new ArrayList<String>();

		if(data.getButtonName().equals("2")) {
			result = "2waiting";
			// If there aren't enough players to play, add them to waiting
			if((numTwoPlayersReady+1) != 2) {
				try {
					arg1.sendToClient(numTwoPlayersReady);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				users.put(numTwoPlayersReady, new User(arg1, numTwoPlayersReady));
				numTwoPlayersReady++;
			}

			// If there are enough players to play, start the game
			else {
				try {
					arg1.sendToClient(numTwoPlayersReady);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				users.put(numTwoPlayersReady, new User(arg1, numTwoPlayersReady));
				numTwoPlayersReady--;
				result2 = "GameTrue";
			}
		}
		else if(data.getButtonName().equals("3")) {

			if(numThreePlayersReady+1 != 3) {
				try {
					arg1.sendToClient(numThreePlayersReady);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				users.put(numThreePlayersReady, new User(arg1, numThreePlayersReady));
				numThreePlayersReady++;
				result = "3waiting";
			}
			else {
				try {
					arg1.sendToClient(numThreePlayersReady);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				users.put(numThreePlayersReady, new User(arg1, numThreePlayersReady));
				numThreePlayersReady-=2;
				result = "3waiting";
				result2 = "GameTrue";
			}

		}
		else if(data.getButtonName().equals("4")) {

			if(numFourPlayersReady+1 != 4) {
				try {
					arg1.sendToClient(numFourPlayersReady);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				users.put(numFourPlayersReady, new User(arg1, numFourPlayersReady));
				numFourPlayersReady++;
				result = "4waiting";
			}
			else {
				try {
					arg1.sendToClient(numFourPlayersReady);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				users.put(numFourPlayersReady, new User(arg1, numFourPlayersReady));
				numFourPlayersReady-=3;
				result = "4waiting";
				result2 = "GameTrue";
			}

		}

		firstCardsFunction(firstCards);

		System.out.println("Result1: " + result);
		System.out.println("Result2: " + result2);

		// Send the result to the client.
		try
		{
			arg1.sendToClient(result);
			arg1.sendToClient(firstCards);
		}
		catch (IOException e)
		{
			return;
		}
		System.out.println(arg1);

		if(result2 != null) {
			try {
				sendToAllClients("GameTrue");
				topCard = drawFromDeck();
				sendToAllClients("cardOnTop,"+topCard);
				users.get(0).getConn().sendToClient("YourTurn");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			usersTurn = 0;
			cardsPlaced.add(topCard);
		}
	}

	// Handle GameData sent to the server
	public void handleGameData(GameData data, ConnectionToClient arg1) {
		System.out.println("GameData: " + data.getCardColor() + data.getCardValue() + " from " +data.getUserName());
		// Prevent errors if gameData did not come from button
		if (data.getButtonName() != null) {
			// User is drawing a card
			if(data.getButtonName().equals("DrawCard")) {
				handCardsAdded.add(drawFromDeck());
				try {
					arg1.sendToClient(handCardsAdded);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handCardsAdded.removeAll(handCardsAdded);
				users.get(data.getPlayerNum()).addNumCards(1);
				sendToAllClients(getUserCardCount());
				//sendNewTurn(data.getNumPlayers()); <- enable to make draws count as a turn
			}

			// User is calling out another for not saying Uno on time
			else if(data.getButtonName().equals("uno")) {
				User victim = users.get(data.getTarget());
				if (victim.hasSaidUno()) {
					// TODO punish attacker here?

				// Victim not safe
				} else {
					if (victim.getNumCards() > 1) {
						// TODO punish attacker here?
					} else {
						targetForceDrawCards(2, data.getPlayerNum(), data.getTarget());
					}
				}
			}

			// User is changing the color (with a wild)
			else if(data.getButtonName().equals("Red") || data.getButtonName().equals("Blue")
					|| data.getButtonName().equals("Yellow") || data.getButtonName().equals("Green")) {
				// Ensure the next user knows what color it is by displaying the previous card in the new color
				topCard = data.getButtonName().substring(0,1) + "," + topCard.substring(2); 
				sendToAllClients("PutOnTop,"+topCard);
				sendNewTurn(data.getNumPlayers());
			}
		}
		else {
			//this is where you put the card on the stack
			//This whole thing basically sets the rules for the class

			// User is playing a wild / wild draw four
			String tokens[] = topCard.split(",");
			if(data.getCardColor().equals("W")) {
				if (data.getCardValue().equals("1")) {
					// The color is figured out in the gameControl and resent with color button name
					// Send draw 4 to the next player
					forceDrawCards(4, data.getPlayerNum(), data.getNumPlayers());
				}
				cardsPlaced.add(data.getCardColor()+","+data.getCardValue());
				sendToAllClients("PutOnTop,"+data.getCardColor()+","+data.getCardValue());

				//This is because when the user places a draw 4
				//the user also has to choose which color they want so it is still their turn
				usersTurn -= direction;
				users.get(data.getPlayerNum()).addNumCards(-1);
				sendToAllClients(getUserCardCount());
				sendNewTurn(data.getNumPlayers());
			}

			//Checking if the card being placed is allowed to be placed on the pile
			else if(data.getCardColor().equals(tokens[0]) || data.getCardValue().equals(tokens[1])){

				//If it is 0-9 then place it on top of the pile and send it back to the clients
				//So they can show that card on top of the pile
				if(Integer.parseInt(data.getCardValue()) <= 9 && Integer.parseInt(data.getCardValue()) >= 0) {
					playCard(data);
				}

				// If 10, then it is a draw two
				else if(Integer.parseInt(data.getCardValue()) == 10) {
					// Force the next player to draw 2
					forceDrawCards(2, data.getPlayerNum(), data.getNumPlayers());
					playCard(data);
				}

				// If 11, then it is a reverse
				// TODO ensure the correct order is retained/clean up reverse
				else if(Integer.parseInt(data.getCardValue()) == 11) {
					// Reverse the player line up
					direction *= -1;
					playCard(data);
				}

				// If 12, then it is a skip
				else if(Integer.parseInt(data.getCardValue()) == 12) {
					// Skips next person in the lineup
					if (usersTurn + direction == data.getNumPlayers()) {
						usersTurn = 0;
					} else if (usersTurn + direction < 0){
						usersTurn = data.getNumPlayers()-1;
					} else {
						usersTurn += direction;
					}
					playCard(data);
				}

				// An INVALID card was attempted to be played. Don't advance the turn
				else {
					// TODO Make error if they don't send a card that can be placed
				}
			}
		}
		System.out.println("Completed GameData run. Turn is : " + usersTurn + " and top card is " + topCard);
	}

	// Helper method to place a card onto the pile for all users
	// Plays card provided in GameData
	private void playCard(GameData data) {
		System.out.println("Placing " + data.getCardColor() + data.getCardValue());
		cardsPlaced.add(data.getCardColor()+","+data.getCardValue());
		topCard = data.getCardColor()+","+data.getCardValue();
		sendToAllClients("PutOnTop,"+topCard);
		users.get(data.getPlayerNum()).addNumCards(-1);
		sendToAllClients(getUserCardCount());
		sendNewTurn(data.getNumPlayers());
	}

	// Helper method to inform the correct user it is their turn
	private void sendNewTurn(int numPlayers) {
		if(usersTurn+direction == numPlayers) {
			usersTurn = 0;
		}
		else if (usersTurn+direction < 0) {
			usersTurn = numPlayers - 1;
		}
		else {
			usersTurn += direction;
		}

		try {
			//ChatClient.message.equals("YourTurn")->GameControl.usersTurn()
			users.get(usersTurn).getConn().sendToClient("YourTurn");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Helper method for sending drawn cards to other players
	private void forceDrawCards(int numCards, int sender, int numPlayers) {
		int receiver = sender + direction;
		if (receiver == numPlayers) {
			receiver = 0;
		} else if (receiver < 0) {
			receiver = numPlayers-1;
		}

		for(int i = 0; i < numCards; i++) {
			handCardsAdded.add(drawFromDeck());
		}
		try {
			User recipient = users.get(receiver);
			recipient.getConn().sendToClient(handCardsAdded);
			recipient.addNumCards(numCards);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handCardsAdded.removeAll(handCardsAdded);
	}

	// Helper method (mostly unnecessary) who forces specific users to draw, regardless of order
	private void targetForceDrawCards(int numCards, int sender, int receiver) {
		for(int i = 0; i < numCards; i++) {
			handCardsAdded.add(drawFromDeck());
		}
		try {
			User recipient = users.get(receiver);
			recipient.getConn().sendToClient(handCardsAdded);
			recipient.addNumCards(numCards);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handCardsAdded.removeAll(handCardsAdded);
	}
	
	// Method that handles listening exceptions by displaying exception information.
	public void listeningException(Throwable exception) 
	{
		running = false;
		status.setText("Exception occurred while listening");
		status.setForeground(Color.RED);
		log.append("Listening exception: " + exception.getMessage() + "\n");
		log.append("Press Listen to restart server\n");
	}

	private void firstCardsFunction(ArrayList<String> firstCards) {
		for(int i = 0; i < 7; i++) {
			firstCards.add(deck.get(0));
			deck.remove(0);
		}
	}

	// Iterate through users and return an array of Integers stating how many cards each user has
	private Integer[] getUserCardCount() {
		Set<Integer> keys = users.keySet();
		Iterator<Integer> itr = keys.iterator();

		Integer userNum;
		Integer[] cardCount = new Integer[users.size()]; 

		while (itr.hasNext()) {
			userNum = itr.next();
			cardCount[userNum] = users.get(userNum).getNumCards();
		}

		return cardCount;
	}

	// Helper method to get a card from the deck and remove its entry
	private String drawFromDeck() {
		if(deck.isEmpty()) {
			deckReplacement();
		}
		String card = deck.get(0);
		deck.remove(0);
		return card;
	}

	private void deckReplacement() {
		deck.addAll(cardsPlaced);
		d.Shuffle(deck);
		cardsPlaced.removeAll(cardsPlaced);
	}

}
