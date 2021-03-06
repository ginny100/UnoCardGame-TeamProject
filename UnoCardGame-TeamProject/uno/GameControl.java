package uno;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameControl implements ActionListener
{
	private JPanel container;

	//The client that is using this
	private GameClient client;

	//The label at the top of the GamePanel that tells players what to do
	private JLabel instructionLabel;

	//These are all the buttons for the colored card in the players hand
	//They place the card shown on the pile
	private JButton blueCardButtons[];
	private JButton redCardButtons[];
	private JButton yellowCardButtons[];
	private JButton greenCardButtons[];
	private JButton wildCardButtons[];

	// JLabel to indicate the user is vulnerable to Uno attacks
	private JLabel safetyLabel;

	// JLabels to show the last card played (beside the draw pile)
	private JLabel blueCardLabels[];
	private JLabel redCardLabels[];
	private JLabel yellowCardLabels[];
	private JLabel greenCardLabels[];
	private JLabel wildCardLabels[];

	// JButtons to allow the user to interact with cards
	private JButton[] otherPlayerDeck;
	private JButton deckButton;
	private JButton userPlayButton;

	public ArrayList<String> userCards;
	private int userCardDisplayed;
	private String userCardDisplayedValue[];

	public String topCardColor;
	public int topCardValue;

	private JLabel[] unoLabels;
	private JLabel userCardCount;

	private int userPlayerNum;

	private int numPlayers;

	private JButton chooseColorButtons[];

	public boolean isUsersTurn;
	private GameRules gameRules;

	// Constructor for the game controller.
	public GameControl(JPanel container,GameClient client)
	{
		this.container = container;
		this.client = client;

		// Initialize values
		topCardColor = "";
		topCardValue = 0;
		isUsersTurn = false;

		userCardDisplayedValue = new String[2];
		userCardDisplayedValue[0] = "B";
		userCardDisplayedValue[1] = "0";

		gameRules = new GameRules(this);
	}

	public void setUserPlayerNum(int userPlayerNum) 
	{
		this.userPlayerNum = userPlayerNum;
	}

	public void setNumPlayers(int numPlayers)
	{
		this.numPlayers = numPlayers;
	}

	public void setUserCards(ArrayList<String> userCards) 
	{
		this.userCards = userCards;
	}

	public void setUserCardCount(JLabel userCardCount)
	{
		this.userCardCount = userCardCount;
	}

	public void setChooseColorButtons(JButton chooseColorButtons[]) 
	{
		this.chooseColorButtons = chooseColorButtons;
	}

	public void setDecks(JButton blueCardButtons[], JButton redCardButtons[], JButton yellowCardButtons[], 
			JButton greenCardButtons[], JButton wildCardButtons[], JButton[] otherPlayerDeck, 
			JButton deckButton, JButton userPlayButton) 
	{
		this.blueCardButtons = blueCardButtons;
		this.redCardButtons = redCardButtons;
		this.yellowCardButtons = yellowCardButtons;
		this.greenCardButtons = greenCardButtons;
		this.wildCardButtons = wildCardButtons;
		this.otherPlayerDeck = otherPlayerDeck;
		this.deckButton = deckButton;
		this.userPlayButton =  userPlayButton;
	}

	public void setInstructionLabel(JLabel il) 
	{
		this.instructionLabel = il;
	}

	public void setUnoLabels(JLabel[] unoLabels, JLabel safetyLabel) 
	{
		this.unoLabels= unoLabels;
		this.safetyLabel = safetyLabel;
	}

	// Method to update the user card count (when the player receives a card from the server)
	public void updateCardCount() 
	{
		userCardCount.setText(Integer.toString(userCards.size()));
	}

	// Server sends a list of all user cards, update the labels for the client
	public void updateUnoLabels(Integer[] userCardCounts) 
	{
		for (int i = 0; i < userCardCounts.length; i++) 
		{
			if (i == userPlayerNum)
			{
				unoLabels[i].setText("You | " + userCardCounts[i].toString());
			}
			else
			{
				unoLabels[i].setText("Player " + ((Integer)(i+1)).toString() + " | " + userCardCounts[i].toString());
			}
		}
		// Warn the user if they are in danger
		if (userCardCounts[userPlayerNum] <= 2) 
		{
			safetyLabel.setVisible(true);
			updateSafety(false);
		}
	}

	// Indicates whether or not the user is available to Uno calls
	public void updateSafety(boolean safe) 
	{
		if (safe)
		{
			safetyLabel.setText("You're safe!");
			safetyLabel.setBackground(Color.GREEN);
		}
		else 
		{
			if (userCards.size() == 1) 
			{
				safetyLabel.setText("You're in danger!");
				safetyLabel.setBackground(Color.RED);
			} 
			else
			{
				safetyLabel.setText("You haven't said UNO");
				safetyLabel.setBackground(Color.YELLOW);
			}
		}
	}

	// Setter for cards played (visible to all players when a card is played)
	public void setPlayedLabels(JLabel[] blueLabels, JLabel[] redLabels, JLabel[] yellowLabels, JLabel[] greenLabels, JLabel[] wildLabels) 
	{
		this.blueCardLabels = blueLabels;
		this.redCardLabels = redLabels;
		this.yellowCardLabels = yellowLabels;
		this.greenCardLabels = greenLabels;
		this.wildCardLabels = wildLabels;
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae)
	{
		//Use this later to clean up junk i think
		//GamePanel GamePanel = (GamePanel)container.getComponent(1);

		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// If the user is cycling through their hand
		if(command == "PD") 
		{
			cycleThroughHand();
		}
		// If the user is drawing from the deck
		else if(command == "D") 
		{
			if (gameRules.canDraw()) 
			{
				GameData data = new GameData("DrawCard", client.getUserName(), userCards.size(), userPlayerNum, numPlayers);
				try
				{
					client.sendToServer(data);
				} 
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// If the user is declaring Uno 
		else if(command == "uno") 
		{
			GameData data = new GameData("uno", client.getUserName(), userCards.size(), userPlayerNum, numPlayers);
			JComponent source = (JComponent) ae.getSource();
			data.setTarget((Integer)source.getClientProperty("target"));
			try
			{
				client.sendToServer(data);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// If the user is playing a wild or wild draw four
		else if(isUsersTurn && (command == "W,0" || command == "W,1"))
		{
			for(int i = 0; i < 4; i++) 
			{
				chooseColorButtons[i].setVisible(true);
			}
			playCard(command);
		}
		// If the user is changing the color in play
		else if(command == "Blue" || command == "Red" || command == "Yellow" || command == "Green") 
		{
			GameData data = new GameData(command, client.getUserName(), userCards.size(), userPlayerNum, numPlayers);

			try 
			{
				client.sendToServer(data);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i = 0; i < 4; i++) 
			{
				chooseColorButtons[i].setVisible(false);
			}
			isUsersTurn = false;
			instructionLabel.setText("Waiting for your turn");
		}
		// If user is trying to place a normal card
		else 
		{
			if (isUsersTurn && gameRules.cardCanPlay(command)) 
			{
				playCard(command);
			}
		}
	}

	//Change to newCardOnTop
	public void cardPlaced(String topCardColor, int topCardValue) 
	{

		blueCardLabels[this.topCardValue].setVisible(false);
		redCardLabels[this.topCardValue].setVisible(false);
		yellowCardLabels[this.topCardValue].setVisible(false);
		greenCardLabels[this.topCardValue].setVisible(false);
		if(this.topCardColor.equals("W")) 
		{
			wildCardLabels[this.topCardValue].setVisible(false);
		}

		this.topCardColor = topCardColor;
		this.topCardValue = topCardValue;

		if(topCardColor.equals("B")) 
		{
			blueCardLabels[topCardValue].setVisible(true);
		}
		else if (topCardColor.equals("R")) 
		{
			redCardLabels[topCardValue].setVisible(true);
		}
		else if (topCardColor.equals("Y"))
		{
			yellowCardLabels[topCardValue].setVisible(true);
		}
		else if (topCardColor.equals("G")) 
		{
			greenCardLabels[topCardValue].setVisible(true);
		}
		else if (topCardColor.equals("W")) 
		{
			wildCardLabels[topCardValue].setVisible(true);
		}
	}

	// Each time the user's hand is pressed, cycle through each card on its right
	public void cycleThroughHand() 
	{
		if (userCards.size() != 0) 
		{
			if(userCards.size() <= userCardDisplayed) 
			{
				userCardDisplayed = 0;
			}
			blueCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(false);
			redCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(false);
			yellowCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(false);
			greenCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(false);
			if(userCardDisplayedValue[0].equals("W")) 
			{
				wildCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(false);
			}

			userCardDisplayedValue = userCards.get(userCardDisplayed).split(",");

			if(userCardDisplayedValue[0].equals("B")) 
			{
				blueCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(true);
			}
			else if (userCardDisplayedValue[0].equals("R")) 
			{
				redCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(true);
			}
			else if (userCardDisplayedValue[0].equals("Y")) 
			{
				yellowCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(true);
			}
			else if (userCardDisplayedValue[0].equals("G")) 
			{
				greenCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(true);
			}
			else if (userCardDisplayedValue[0].equals("W"))
			{
				wildCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(true);
			}
			userCardDisplayed++;
		}
	}

	public void StartGame() 
	{
		if(userPlayerNum != 0)
		{
			instructionLabel.setText("Waiting for your turn");
		}
		userCardDisplayed = 0;
		turnDecksOn();
	}

	public void turnDecksOn()
	{
		deckButton.setVisible(true);
		userPlayButton.setVisible(true);
		userCardCount.setVisible(true);

		for(int i = 0; i < numPlayers; i++) 
		{
			otherPlayerDeck[i].setVisible(true);
			unoLabels[i].setVisible(true);
		}
	}

	private void playCard(String card)
	{
		String tokens[] = card.split(",");
		GameData data = new GameData(tokens[0], tokens[1], client.getUserName(), userCards.size(), userPlayerNum, numPlayers);
		try
		{
			userCards.remove(card);
			updateCardCount();
			cycleThroughHand();
			client.sendToServer(data);
			isUsersTurn = false;
			instructionLabel.setText("Waiting for your turn");
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void usersTurn()
	{
		isUsersTurn = true;
		instructionLabel.setText("Your Turn");
	}
	
	// Once the game is over, send the player to the end screen
	// Be sure to clean up all old values in case the player plays again
	public void sendToEndScreen(String playerNum, String username)
	{
		EndPanel endPanel = (EndPanel) container.getComponent(5);

		// Reinitialize values
		topCardColor = "";
		topCardValue = 0;
		isUsersTurn = false;

		userCardDisplayedValue = new String[2];
		userCardDisplayedValue[0] = "B";
		userCardDisplayedValue[1] = "0";

		userCards = new ArrayList<String>();
		
		if (playerNum.equals(((Integer)userPlayerNum).toString()))
		{
			endPanel.setStatus("Win!");
		} 
		else
		{
			endPanel.setStatus("Lose");
		}
		endPanel.winnerInfo(playerNum, username);
		
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "6");
	}
}
