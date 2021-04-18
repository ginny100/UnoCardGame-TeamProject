package Uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
	private ArrayList<String> deck;
	private String randomCard;
	private String[] colors;

	public Deck() {

		deck = new ArrayList<String>();
		colors = new String[4];
		colors[0] = "B";
		colors[1] = "R";
		colors[2] = "Y";
		colors[3] = "G";


		for(int i = 0; i < 4; i++) {
			deck.add(colors[i]+","+0);
			for(int j = 1; j < 13;j++) {
				deck.add(colors[i]+","+j);
				deck.add(colors[i]+","+j);
			}
		}

		for(int i = 0; i < 4; i++) {
			deck.add("W"+","+0);
			deck.add("W"+","+1);
		}

		Shuffle(deck);
	}

	public ArrayList<String> getDeck(){
		return deck;
	}

	public ArrayList<String> Shuffle(ArrayList<String> d){
		deck = d;
		Collections.shuffle(deck, new Random());

		return deck;
	}
}
