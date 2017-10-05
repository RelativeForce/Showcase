package cardTrick.playingCards;

/**
 * Stores the different suits of a standard deck of playing cards and their
 * string representation.
 * 
 * @author Joshua_Eddy
 *
 */
public enum Suit {

	HEARTS("Hearts"), SPADES("Spades"), CLUBS("Clubs"), DIAMONDS("Diamonds");

	/**
	 * Holds the <code>String</code> representation of the {@link Suit}
	 */
	private final String title;

	/**
	 * Constructs a new {@link Suit}.
	 * @param title <code>String</code> representation of the {@link Suit}.
	 */
	private Suit(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return title;
	}

}
