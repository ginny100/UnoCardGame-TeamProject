package Uno;

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
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class GameServer extends AbstractServer
{
	// Data fields for this chat server.
	private JTextArea log;
	private JLabel status;
	private boolean running = false;
	private DatabaseFile File = new DatabaseFile();

	private String[] twoPlayerCardNum;
	private String[] threePlayerCardNum;
	private String[] fourPlayerCardNum;

	private int numTwoPlayersReady;
	private int numThreePlayersReady;
	private int numFourPlayersReady;

	private ArrayList<String> deck;
	private ArrayList<String> cardsPlaced;
	private Deck d;

	private ArrayList<String> handCardsAdded;

	private HashMap<Integer, ConnectionToClient> clients;
	private HashMap<Integer, ConnectionToClient> reverse;

	private int usersTurn;

	// Constructor for initializing the server with default settings.
	public GameServer()
	{
		super(12345);
		this.setTimeout(500);

		twoPlayerCardNum = new String[2];
		threePlayerCardNum= new String[3];
		fourPlayerCardNum= new String[4];

		numTwoPlayersReady = 0;
		numThreePlayersReady = 0;
		numFourPlayersReady = 0;

		cardsPlaced = new ArrayList<String>();
		d = new Deck();
		deck = d.getDeck();

		handCardsAdded = new ArrayList<String>();

		clients = new HashMap<Integer, ConnectionToClient>();
		reverse = new HashMap<Integer, ConnectionToClient>();


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
	public void clientConnected(ConnectionToClient client)
	{
		log.append("Client " + client.getId() + " connected\n");
	}

	// Call all the appropriate methods
	public void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		System.out.println("Recieved: " + arg0.toString());

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
				clients.put(numTwoPlayersReady, arg1);
				twoPlayerCardNum[numTwoPlayersReady]="7";
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
				clients.put(numTwoPlayersReady, arg1);
				twoPlayerCardNum[numTwoPlayersReady]="7";
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
				clients.put(numThreePlayersReady, arg1);
				threePlayerCardNum[numThreePlayersReady]="7";
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
				clients.put(numThreePlayersReady, arg1);
				threePlayerCardNum[numThreePlayersReady]="7";
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
				clients.put(numFourPlayersReady, arg1);
				fourPlayerCardNum[numFourPlayersReady]="7";
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
				clients.put(numFourPlayersReady, arg1);
				fourPlayerCardNum[numFourPlayersReady]="7";
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
				//sendToAllClients("cardOnTop,"+deck.get(0));
				sendToAllClients("cardOnTop,R,10");	// Remove this <---
				clients.get(0).sendToClient("YourTurn");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			usersTurn = 0;
			//cardsPlaced.add(drawFromDeck());
			cardsPlaced.add("R,10"); // Remove this <---
		}
	}


	// Handle GameData sent to the server
	public void handleGameData(GameData data, ConnectionToClient arg1) {
		// Prevent errors if gameData did not come from button
		if (data.getButtonName() != null) {
			if(data.getButtonName().equals("DrawCard")) {
				handCardsAdded.add(drawFromDeck());
				try {
					arg1.sendToClient(handCardsAdded);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handCardsAdded.removeAll(handCardsAdded);
				usersTurn++;
				sendNewTurn(data.getNumPlayers());
			}
			else if(data.getButtonName().equals("uno")) {

				for(int i = 0; i< data.getNumPlayers(); i++) {
					if(data.getPlayerNum() != i && twoPlayerCardNum[i].equals("1")) {
						handCardsAdded.add(drawFromDeck());
						try {
							clients.get(i).sendToClient(handCardsAdded);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						handCardsAdded.removeAll(handCardsAdded);
						//result = "uno";
					}
				}
			}
		}
		//!!!!!!!!!!
		// FIX BELOW
		//!!!!!!!!!!
		else {
			//this is where you put the card on the stack
			//This whole thing basically sets the rules for the class

			String tokens[] = cardsPlaced.get(0).split(",");
			if(data.getCardColor().equals("W") && data.getCardValue().equals("1")) {
				//The color is figured out in the gameControl
				//Send draw 4 to the next player
				for(int i = 0; i < 4; i++) {
					handCardsAdded.add(drawFromDeck());
				}
				try {
					clients.get(usersTurn+1).sendToClient(handCardsAdded);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cardsPlaced.add(data.getCardColor()+","+data.getCardValue());
				handCardsAdded.removeAll(handCardsAdded);

				//This is because when the user places a draw 4
				//the user also has to choose which color they want so it is still their turn
				usersTurn--;
				sendNewTurn(data.getNumPlayers());
			}

			//Checking if the card being placed is allowed to be placed on the pile
			else if(data.getCardColor().equals(tokens[0]) || data.getCardValue().equals(tokens[1])){

				//If it is 0-9 then place it on top of the pile and send it back to the clients
				//So they can show that card on top of the pile
				if(Integer.parseInt(data.getCardValue()) <= 9 && Integer.parseInt(data.getCardValue()) >= 0) {
					sendToAllClients("PutOnTop,"+data.getCardColor()+","+data.getCardValue());
					cardsPlaced.add(data.getCardColor()+","+data.getCardValue());
					sendNewTurn(data.getNumPlayers());
				}

				// If 10, then it is a draw two
				else if(Integer.parseInt(data.getCardValue()) == 10) {
					//Draw 2: Send Draw 2 to the next player
					for(int i = 0; i < 2; i++) {
						handCardsAdded.add(deck.get(0));
					}
					try {
						clients.get(usersTurn+1).sendToClient(handCardsAdded);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					handCardsAdded.removeAll(handCardsAdded);
					cardsPlaced.add(data.getCardColor()+","+data.getCardValue());
					sendNewTurn(data.getNumPlayers());
				}

				else if(Integer.parseInt(data.getCardValue()) == 11) {
					//Reverse card: Reverse the player line up
					int j = data.getNumPlayers();
					for(int i = 0; i < data.getNumPlayers(); i++) {
						reverse.put(i, clients.get(j));
						j--;
					}
					for(int i = 0; i < data.getNumPlayers(); i++) {
						clients.put(i, reverse.get(i));
					}
					cardsPlaced.add(data.getCardColor()+","+data.getCardValue());
					sendNewTurn(data.getNumPlayers());
				}

				else if(Integer.parseInt(data.getCardValue()) == 12) {
					//Skip card: Skips next person in the lineup
					usersTurn++;
					cardsPlaced.add(data.getCardColor()+","+data.getCardValue());
					sendNewTurn(data.getNumPlayers());
				}
				else if(data.getButtonName().equals("Red") || data.getButtonName().equals("Blue")
						|| data.getButtonName().equals("Yellow") || data.getButtonName().equals("Green")) {
					//Send color to next client or all clients
					//Choosing colors
					try {
						clients.get(usersTurn+1).sendToClient(data.getButtonName());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cardsPlaced.add(data.getCardColor()+","+data.getCardValue());
					sendNewTurn(data.getNumPlayers());
				}

				// An INVALID card was attempted to be played. Don't advance the turn
				else {
					// TODO Make error if they don't send a card that can be placed
				}
			}
		}
		@SuppressWarnings("unused")
		String idk = "test";
	}

	// Helper method to inform the correct user it is their turn
	private void sendNewTurn(int numPlayers) {
		if((usersTurn+1) == numPlayers) {
			usersTurn--;
		}
		else {
			usersTurn++;
		}

		try {
			//ChatClient.message.equals("YourTurn")->GameControl.usersTurn()
			clients.get(usersTurn).sendToClient("YourTurn");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	// Helper method to get a card from the deck and remove its entry
	private String drawFromDeck() {
		String card = deck.get(0);
		if(deck.isEmpty()) {
			deckReplacement();
		}
		deck.remove(0);
		return card;
	}

	private void deckReplacement() {
		deck.addAll(cardsPlaced);
		d.Shuffle(deck);
		cardsPlaced.removeAll(cardsPlaced);
	}

}
