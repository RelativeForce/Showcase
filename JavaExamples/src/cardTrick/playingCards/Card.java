package cardTrick.playingCards;

import cardTrick.CardTrick;

/**
 * Describes a playing card in {@link CardTrick}.
 * @author Joshua_Eddy
 * 
 * @see playingCards.Deck
 * @see playingCards.Suit
 * @see playingCards.Rank
 *
 */
public class Card {

	/**
	 * The {@link Rank} of this card. 
	 */
	public final Rank rank;
	
	/**
	 * The {@link Suit} of the card.
	 */
	public final Suit suit;

	/**
	 * Constructs a new {@link Card}.
	 * @param rank {@link Rank}
	 * @param suit {@link Suit}
	 */
	public Card(Rank rank, Suit suit) {
		this.suit = suit;
		this.rank = rank;
	}

}
