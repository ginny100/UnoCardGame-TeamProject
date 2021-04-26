package uno;

import java.io.IOException;

import ocsf.server.ConnectionToClient;

public class User {
	private boolean saidUno;
	private ConnectionToClient conn;
	private int handSize;
	private int playerNum;
	private String username;
	
	public User(ConnectionToClient conn, int playerNum) {
		this.conn = conn;
		this.playerNum = playerNum;
		saidUno = false;
		handSize = 3;
	}
	
	public User(ConnectionToClient conn, int playerNum, String username) {
		this.conn = conn;
		this.playerNum = playerNum;
		this.username = username;
		saidUno = false;
		handSize = 3;
	}
	
	public void setUsername(String un) {
		username = un;
	}
	
	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}
	
	public void sayUno() {
		saidUno = true;
		sendUnoStatus();
	}
	
	public void addNumCards(int numCards) {
		if ((handSize == 1) && (numCards > 0)) {
			// The user is back in jeopardy, safety has worn off
			saidUno = false;
			sendUnoStatus();
		}
		handSize += numCards;
	}
	
	public void sendUnoStatus() {
		try {
			conn.sendToClient("Safe,"+saidUno);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
