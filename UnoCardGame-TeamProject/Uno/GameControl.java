package Uno;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameControl implements ActionListener{
	// Private data fields for the container and chat client.
	private JPanel container;

	//The client that is using this
	private ChatClient client;

	//The label at the top of the GamePanel that tells players what to do
	private JLabel instructionLabel;

	//These are all the buttons for the colored card in the players hand
	//They place the card shown on the pile
	private JButton blueCardButtons[];
	private JButton redCardButtons[];
	private JButton yellowCardButtons[];
	private JButton greenCardButtons[];
	private JButton wildCardButtons[];

	private JButton[] otherPlayerDeck;
	private JButton deckButton;
	private JButton userPlayButton;

	private ArrayList<String> userCards;
	private int userCardDisplayed;
	private String userCardDisplayedValue[];

	private String topCardColor;
	private int topCardValue;

	private String cardTryingToPlace;

	private JLabel[] unoLabels;
	private JLabel userUnoLabel;

	private int userPlayerNum;

	private int numPlayers;

	private JButton chooseColorButtons[];

	//The color the player before changed it to
	private String colorChangedTo;
	//This tells whether or not someone changed the color becasue I dont want to write an if statement 
	//checking all the colors again
	private boolean colorChanged;

	public void setUserPlayerNum(int userPlayerNum) {
		this.userPlayerNum = userPlayerNum;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public void setUserCards(ArrayList<String> userCards) {
		this.userCards = userCards;
	}

	public void setUserUnoLabel(JLabel userUnoLabel){
		this.userUnoLabel = userUnoLabel;
	}

	public void setChooseColorButtons(JButton chooseColorButtons[]) {
		this.chooseColorButtons = chooseColorButtons;
	}

	public void setDecks(JButton blueCardButtons[], JButton redCardButtons[], JButton yellowCardButtons[], 
			JButton greenCardButtons[], JButton wildCardButtons[], JButton[] otherPlayerDeck, 
			JButton deckButton, JButton userPlayButton) {

		this.blueCardButtons = blueCardButtons;
		this.redCardButtons = redCardButtons;
		this.yellowCardButtons = yellowCardButtons;
		this.greenCardButtons = greenCardButtons;
		this.wildCardButtons = wildCardButtons;
		this.otherPlayerDeck = otherPlayerDeck;
		this.deckButton = deckButton;
		this.userPlayButton =  userPlayButton;
	}

	public void setInstructionLabel(JLabel il) {
		this.instructionLabel = il;
	}

	public void setUnoLabels(JLabel[] unoLabels) {
		this.unoLabels= unoLabels;
	}

	// Constructor for the login controller.
	public GameControl(JPanel container,ChatClient client)
	{
		this.container = container;
		this.client = client;
		userCardDisplayedValue = new String[2];
		userCardDisplayedValue[0] = "B";
		userCardDisplayedValue[1] = "0";
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae)
	{
		//Use this later to clean up junk i think
		//GamePanel GamePanel = (GamePanel)container.getComponent(1);

		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		if(command == "PD") {

			cycleThroughHand();
		}
		else if(command == "D") {
			GameData data = new GameData("DrawCard", client.getUserName(), userPlayerNum, numPlayers);
			try {
				client.sendToServer(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command == "uno") {
			GameData data = new GameData("uno", client.getUserName(), userPlayerNum, numPlayers);
			try {
				client.sendToServer(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command == "W,0" || command == "W,1"){
			String tokens[] = command.split(",");
			GameData data = new GameData(tokens[0], tokens[1], client.getUserName(), userPlayerNum, numPlayers);
			for(int i = 0; i < 4; i++) {
				chooseColorButtons[i].setVisible(true);
			}
			if(command == "W,1") {
				try {
					client.sendToServer(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(command == "Blue" || command == "Red" || command == "Yellow" || command == "Green") {
			GameData data = new GameData(command, client.getUserName(), userPlayerNum, numPlayers);

			try {
				client.sendToServer(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = 0; i < 4; i++) {
				chooseColorButtons[i].setVisible(false);
			}
		}
		else if(colorChanged){

			String tokens[] = command.split(",");
			if(tokens[0] != colorChangedTo) {
				//Display an error
			}
			else {
				GameData data = new GameData(tokens[0], tokens[1], client.getUserName(), userPlayerNum, numPlayers);
				try {
					client.sendToServer(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			colorChanged = false;
		}
		else {

			cardTryingToPlace = command;
			String tokens[] = command.split(",");
			GameData data = new GameData(tokens[0], tokens[1], client.getUserName(), userPlayerNum, numPlayers);
			try {
				client.sendToServer(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void colorChange(String color) {
		colorChangedTo = color;
		colorChanged = true;
	}

	public void canPlaceCard() {
		cycleThroughHand();
		userCards.remove(cardTryingToPlace);
	}

	public void unoCall(int i) {
		userUnoLabel.setText("2");
	}

	public void changeNumCards(int i, String numCards) {
		unoLabels[i].setText(numCards);
	}

	//Change to newCardOnTop
	public void CardPlaced(String topCardColor, int topCardValue) {

		blueCardButtons[this.topCardValue].setVisible(false);
		redCardButtons[this.topCardValue].setVisible(false);
		yellowCardButtons[this.topCardValue].setVisible(false);
		greenCardButtons[this.topCardValue].setVisible(false);
		if(this.topCardColor.equals("W")) {
			wildCardButtons[this.topCardValue].setVisible(false);
		}




		this.topCardColor = topCardColor;
		this.topCardValue = topCardValue;




		if(topCardColor.equals("B")) {
			blueCardButtons[topCardValue].setVisible(true);
		}
		else if (topCardColor.equals("R")) {
			redCardButtons[topCardValue].setVisible(true);
		}
		else if (topCardColor.equals("Y")) {
			yellowCardButtons[topCardValue].setVisible(true);
		}
		else if (topCardColor.equals("G")) {
			greenCardButtons[topCardValue].setVisible(true);
		}
		else if (topCardColor.equals("W")) {
			wildCardButtons[topCardValue].setVisible(true);
		}
	}

	public void cycleThroughHand() {
		blueCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(false);
		redCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(false);
		yellowCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(false);
		greenCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(false);
		if(userCardDisplayedValue[0].equals("W")) {
			wildCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(false);
		}

		userCardDisplayedValue = userCards.get(userCardDisplayed).split(",");

		if(userCardDisplayedValue[0].equals("B")) {
			blueCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(true);
		}
		else if (userCardDisplayedValue[0].equals("R")) {
			redCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(true);
		}
		else if (userCardDisplayedValue[0].equals("Y")) {
			yellowCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(true);
		}
		else if (userCardDisplayedValue[0].equals("G")) {
			greenCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(true);
		}
		else if (userCardDisplayedValue[0].equals("W")) {
			wildCardButtons[Integer.parseInt(userCardDisplayedValue[1])].setVisible(true);
		}
		userCardDisplayed++;
		if(userCards.size() == userCardDisplayed) {
			userCardDisplayed = 0;
		}
	}

	public void StartGame() {
		if(userPlayerNum != 0) {
			instructionLabel.setText("Waiting for your turn");
		}
		userCardDisplayed = 0;
		turnDecksOn();
	}

	public void turnDecksOn() {
		deckButton.setVisible(true);
		userPlayButton.setVisible(true);
		userUnoLabel.setVisible(true);

		for(int i = 0; i < numPlayers-1; i++) {
			otherPlayerDeck[i].setVisible(true);
			unoLabels[i].setVisible(true);
		}
	}

	public void usersTurn() {
		instructionLabel.setText("Your Turn");
	}

}