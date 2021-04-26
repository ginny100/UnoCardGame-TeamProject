package uno;

public class GameRules {
	private GameControl gc;

	public GameRules(GameControl gc) {
		this.gc = gc;
	}

	// Helper method to check if the player can draw a card from the pile
	public boolean canDraw() {
		// If it isn't the user's turn, they can't draw
		if (gc.isUsersTurn == true) {
			// Iterate through cards, see if they can play
			for (String card : gc.userCards) {
				if (cardCanPlay(card)) {
					return false;
				}
			}

			// If absolutely no cards can play, user can draw
			return true;
		}
		else {
			return false;
		}
	}

	// TODO include wilds (others?) (also remove try catch)
	public boolean cardCanPlay(String card) {
		try {
			if (card.contains(gc.topCardColor))  {
				return true;
			} else if(card.length() > 2 && card.substring(2).equals(Integer.toString(gc.topCardValue))) {
				return true;
			}
			else {
				return false;
			}
		} catch(Exception e) {
			System.out.println("Ran into string problem again. Was doing substring of " + card + " and compared to " + gc.topCardValue);
			e.printStackTrace();
			return false;
		}
	}

}
