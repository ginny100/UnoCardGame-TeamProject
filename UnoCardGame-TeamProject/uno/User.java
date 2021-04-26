package uno;

import ocsf.server.ConnectionToClient;

public class User {
	private boolean saidUno;
	private ConnectionToClient conn;
	private int handSize;
	private int playerNum;
	
	public User(ConnectionToClient conn, int playerNum) {
		this.conn = conn;
		this.playerNum = playerNum;
		saidUno = false;
		handSize = 7;
	}
		
	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}
	
	public void setHandSize(int numCards) {
		handSize = numCards;
	}
	
	public void addNumCards(int numCards) {
		if ((handSize == 1) && (numCards > 0)) {
			// The user is back in jeopardy, safety has worn off
			saidUno = false;
		}
		handSize += numCards;
	}
	
	public boolean hasSaidUno() {
		return saidUno;
	}
	
	public ConnectionToClient getConn() {
		return conn;
	}
	
	public int getNumCards() {
		return handSize;
	}

}
