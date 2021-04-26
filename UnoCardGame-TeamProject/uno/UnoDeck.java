package uno;

import java.util.ArrayList;
import java.util.Random;

public class UnoDeck {
	private UnoCard[] cards;
	private int cardsInDeck;

	public UnoDeck() {
		cards = new UnoCard[108];
	}

	public void reset() {
		UnoCard.Color[] colors = UnoCard.Color.values();
		cardsInDeck = 0;

		for(int i = 0; i < colors.length-1;i++) {
			UnoCard.Color color = colors[i];
			//0 = 0
			//1-10 = 1-20
			//Drawtwo = 21,22
			//Skip = 23,24
			//Reverse = 25,26
			cards[cardsInDeck++] = new UnoCard(color, UnoCard.Value.getValue(0));

			for(int j = i; j < 10; j++) {
				cards[cardsInDeck++] = new UnoCard(color, UnoCard.Value.getValue(0));
				cards[cardsInDeck++] = new UnoCard(color, UnoCard.Value.getValue(0));
			}

			UnoCard.Value[] values = new UnoCard.Value[] {UnoCard.Value.DrawTwo, UnoCard.Value.Skip, UnoCard.Value.Reverse};
			for(UnoCard.Value value : values) {
				cards[cardsInDeck++] = new UnoCard(color, value);
				cards[cardsInDeck++] = new UnoCard(color, value);
			}
		}

		UnoCard.Value[] values = new UnoCard.Value[] {UnoCard.Value.Wild, UnoCard.Value.Wild_Four};
		for(UnoCard.Value value : values) {
			cards[cardsInDeck++] = new UnoCard(UnoCard.Color.Wild, value);
			cards[cardsInDeck++] = new UnoCard(UnoCard.Color.Wild, value);
		}
	}
//	public void replaceDeckWith(ArrayList<UnoCard> cards) {
//		this.cards = cards.toArray(new UnoCard(cards.si);
//	}

	public void replaceDeckWith(ArrayList<UnoCard> cards) {
		this.cards = cards.toArray(new UnoCard[cards.size()]);
	}

	public boolean isEmpty() {
		return cardsInDeck == 0;
	}

	public void shuffle() {
		int n = cards.length;
		Random random = new Random();

		for (int i = 0; i<cards.length;i++) {
			int randomValue = i +random.nextInt(n-i);
			UnoCard randomCard = cards[randomValue];
			cards[randomValue] = cards[i];
			cards[i] = randomCard;
		}
	}

	public UnoCard drawCard() throws IllegalArgumentException{
		if(isEmpty()) {
			throw new IllegalArgumentException("Cannot draw a card since there are no cards in the deck");
		}
		return cards[--cardsInDeck];
	}

//	public ImageIcon drawCardImage() throws IllegalArgumentException{
//		if(isEmpty()) {
//			throw new IllegalArgumentException("Cannot draw a card since card deck is empty");
//		}
//		return cards[--cardsInDeck];
//	}

	public UnoCard[] drawCard(int n) {
		if(n<0) {
			throw new IllegalArgumentException("Must draw positive cards but tried to draw " + n + " cards.");
		}

		if(n>cardsInDeck) {
			throw new IllegalArgumentException("Cannot draw " + n + " cards since there are only " + cardsInDeck + " cards.");
		}
		
		UnoCard[] ret = new UnoCard[n];
		
		for (int i = 0; i < n; i++) {
			ret[i] = cards[--cardsInDeck];
		}
		return ret;
	}

}
