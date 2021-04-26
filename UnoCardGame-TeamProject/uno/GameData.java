package uno;

import java.io.Serializable;

public class GameData implements Serializable{
	private String button;
	private String user;
	private String cardColor;
	private String cardValue;
	private int numCards;
	private int playerNum;
	private int numPlayers;

	public String getButtonName() {
		return button;
	}

	public String getUserName() {
		return user;
	}

	public String getCardValue() {
		return cardValue;
	}

	public String getCardColor() {
		return cardColor;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public int getNumPlayers() {
		return numPlayers;
	}
	
	public int getNumCards() {
		return numCards;
	}

	public void setButtonName(String button) {
		this.button = button;
	}

	public void setUserName(String user) {
		this.user = user;
	}

	public void setCardValue(String cardValue) {
		this.cardValue = cardValue;
	}

	public void setCardColor(String cardColor) {
		this.cardColor = cardColor;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	
	public void setNumCards(int numCards) {
		this.numCards = numCards;
	}

	public GameData(String button, String user, int numCards, int playerNum, int numPlayers) {
		setButtonName(button);
		setPlayerNum(playerNum);
		setUserName(user);
		setNumPlayers(numPlayers);
		setNumCards(numCards);
	}

	public GameData(String cardColor, String cardValue, String user, int numCards, int playerNum, int numPlayers) {
		setUserName(user);
		setCardValue(cardValue);
		setCardColor(cardColor);
		setPlayerNum(playerNum);
		setNumPlayers(numPlayers);
		setNumCards(numCards);
	}
}
