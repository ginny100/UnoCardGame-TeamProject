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

public class ChatServer extends AbstractServer
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
	public ChatServer()
	{
		super(12345);
		this.setTimeout(500);

		twoPlayerCardNum = new String[2];
		threePlayerCardNum= new String[3];
		fourPlayerCardNum= new String[4];

		numTwoPlayersReady = 0;
		numThreePlayersReady = 0;
		numFourPlayersReady = 0;

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

	// When a message is received from a client, handle it.
	public void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		// If we received LoginData, verify the account information.
		if (arg0 instanceof LoginData)
		{
			// Check the username and password with the database.
			LoginData data = (LoginData)arg0;
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

		// If we received CreateAccountData, create a new account.
		else if (arg0 instanceof CreateAccountData)
		{
			// Try to create the account.
			CreateAccountData data = (CreateAccountData)arg0;
			Object result;
			if (File.UserCheck(data.getUsername()))
			{
				result = "CreateAccountSuccessful";
				try {
					File.writeFile(data.getUsername() + " " + data.getPassword());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				result = new Error("The username is already in use.", "CreateAccount");
				log.append("Client " + arg1.getId() + " failed to create a new account\n");
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

		else if (arg0 instanceof MenuData) {
			MenuData data = (MenuData)arg0;
			Object result = null;
			Object result2 = null;
			ArrayList<String> firstCards = new ArrayList<String>();


			if(data.getButtonName().equals("2")) {
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
					result = "2waiting";
				}
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
					result = "2waiting";
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
				sendToAllClients("GameTrue");
				sendToAllClients("cardOnTop,"+deck.get(0));
				try {
					clients.get(0).sendToClient("YourTurn");
					clients.get(1).sendToClient("YourTurn");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				usersTurn =0;
				cardsPlaced.add(deck.get(0));
				deck.remove(0);
			}
		}

		else if (arg0 instanceof GameData) {
			GameData data = (GameData)arg0;
			Object result = null;
			Object result2 = null;

			if(clients.get(usersTurn) != arg1) {
				//arg1.sendToClient(Error("Its not your turn"));
			}
			else {

				if(data.getButtonName().equals("DrawCard")) {
					handCardsAdded.add(deck.get(0));
					try {
						arg1.sendToClient(handCardsAdded);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(deck.isEmpty()) {
						deckReplacement();
					}
					deck.remove(0);
					handCardsAdded.removeAll(handCardsAdded);
				}
				else if(data.getButtonName().equals("uno")) {

					for(int i = 0; i< data.getNumPlayers(); i++) {
						if(data.getPlayerNum() != i && twoPlayerCardNum[i].equals("1")) {
							handCardsAdded.add(deck.get(0));
							try {
								clients.get(i).sendToClient(handCardsAdded);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(deck.isEmpty()) {
								deckReplacement();
							}
							deck.remove(0);
							handCardsAdded.removeAll(handCardsAdded);
							result = "uno";
						}
					}

				}
				else {
					//this is where you put the card on the stack
					//This whole thing basically sets the rules for the class

					String tokens[] = deck.get(0).split(",");
					if(data.getCardColor().equals("W") && data.getCardValue().equals("1")) {
						//The color is figured out in the gameControl
						//Send draw 4 to the next player
						for(int i = 0; i < 4; i++) {
							handCardsAdded.add(deck.get(0));
							if(deck.isEmpty()) {
								deckReplacement();
							}
							deck.remove(0);
						}
						try {
							clients.get(usersTurn+1).sendToClient(handCardsAdded);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						handCardsAdded.removeAll(handCardsAdded);

						//This is because when the user places a draw 4
						//the user also has to choose which color they want so it is still their turn
						usersTurn--;
					}

					//Checking if the card being placed is allowed to be placed on the pile
					else if(data.getCardColor().equals(tokens[0]) || data.getCardValue().equals(tokens[1])){

						//If it is 0-9 then place it on top of the pile and send it back to the clients
						//So they can show that card on top of the pile
						if(Integer.parseInt(data.getCardValue()) <= 9 && Integer.parseInt(data.getCardValue()) >= 0) {
							sendToAllClients(data.getCardColor()+","+data.getCardValue());

						}
						else if(Integer.parseInt(data.getCardValue()) == 10) {
							//Draw 2
							//Send Draw 2 to the next player
							for(int i = 0; i < 2; i++) {
								handCardsAdded.add(deck.get(0));
								if(deck.isEmpty()) {
									deckReplacement();
								}
								deck.remove(0);
							}
							try {
								clients.get(usersTurn+1).sendToClient(handCardsAdded);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							handCardsAdded.removeAll(handCardsAdded);
						}
						else if(Integer.parseInt(data.getCardValue()) == 11) {
							//Reverse
							//Reverse the player line up
							int j = data.getNumPlayers();
							for(int i = 0; i < data.getNumPlayers(); i++) {
								reverse.put(i, clients.get(j));
								j--;
							}
							for(int i = 0; i < data.getNumPlayers(); i++) {
								clients.put(i, reverse.get(i));
							}
						}
						else if(Integer.parseInt(data.getCardValue()) == 12) {
							//Skip
							//Skip next person in the lineup
							usersTurn++;
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
						}

						if((usersTurn+1) == 2 && data.getNumPlayers() == 2) {
							usersTurn--;
						}
						else if((usersTurn+1) == 3 && data.getNumPlayers() == 3) {
							usersTurn--;
						}
						else if((usersTurn+1) == 4 && data.getNumPlayers() == 4) {
							usersTurn--;
						}
						else {
							usersTurn++;
						}


						try {
							//ChatClient.message.equals("Your Turn")->GameControl.usersTurn()
							clients.get(usersTurn).sendToClient("Your Turn");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						//Make error if they don't send a card that can be placed
					}

				}
			}

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

	public void firstCardsFunction(ArrayList<String> firstCards) {
		for(int i = 0; i < 7; i++) {
			firstCards.add(deck.get(0));
			deck.remove(0);
		}
	}

	public void deckReplacement() {
		deck.addAll(cardsPlaced);
		d.Shuffle(deck);
		cardsPlaced.removeAll(cardsPlaced);
	}

}