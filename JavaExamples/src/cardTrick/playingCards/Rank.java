package cardTrick.playingCards;

/**
 * Stores the different ranks of a standard deck of playing cards and their
 * string representation.
 * 
 * @author Joshua_Eddy
 *
 */
public enum Rank {

	ACE("A"),
	TWO("2"),
	THREE("3"),
	FOUR("4"),
	FIVE("5"),
	SIX("6"),
	SEVEN("7"),
	EIGHT("8"),
	NINE("9"),
	TEN("10"),
	JACK("J"),
	QUEEN("Q"),
	KING("K");

	/**
	 * Holds the <code>String</code> representation of the {@link Rank}
	 */
	private final String title;

	/**
	 * Constructs a new {@link Suit}.
	 * @param title <code>String</code> representation of the {@link Rank}.
	 */
	private Rank(String title) {

		this.title = title;
	}

	@Override
	public String toString() {
		return title;
	}
}
