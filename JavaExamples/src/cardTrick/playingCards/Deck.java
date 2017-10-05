package cardTrick.playingCards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A deck of playing cards.
 * 
 * @author Joshua_Eddy
 *
 * @see playingCards.Rank
 * @see playingCards.Suit
 * @see playingCards.Card
 */
public class Deck {

	/**
	 * Holds the {@link Card}s that make up the deck.
	 */
	private List<Card> deck;

	/**
	 * Constructs a new {@link Deck}.
	 */
	public Deck() {
		deck = new ArrayList<Card>();
		newDeck();
	}

	/**
	 * Populates {@link #deck} with all the combinations of {@link Suit} and
	 * {@link Rank}.
	 */
	private void newDeck() {

		// Empty the deck.
		deck.clear();

		// Iterate through all the suits.
		for (Suit suit : Suit.values()) {

			// Iterate through all the ranks.
			for (Rank rank : Rank.values()) {
				// Use the suit and rank to construct an new card the add it to
				// the deck.S
				deck.add(new Card(rank, suit));
			}
		}
	}

	/**
	 * Retrieves a card at a specified location in the deck.
	 * 
	 * @param index
	 *            Integer position in the deck.
	 * @return <code>Card</code>
	 */
	public Card getCard(int index) {

		// Check the parameter index is with in the bounds of the array.
		if (index < 0 || index >= deck.size()) {
			throw new IndexOutOfBoundsException();
		}
		return deck.get(index);
	}

	/**
	 * Shuffles the deck into a random order.
	 */
	public void suffle() {
		// Holds the random number generator.
		Random random = new Random();

		// The positions of the two cards that will be swapped.
		int position1;
		int position2;

		// Temporary card holder.
		Card tempCard;

		// Iterate for each card in the deck.
		for (int i = 0; i < deck.size(); i++) {

			// Get two random positions in the deck.
			position1 = random.nextInt(deck.size());
			position2 = random.nextInt(deck.size());

			// If they are the same.
			if (position1 == position2) {

				// If the second position is at the lower bound of the deck add
				// one to its position. Otherwise subtract one from its
				// position.
				if (position2 == 0) {
					position2++;
				} else {
					position2--;
				}

			}
			// Swap the cards at the two positions using the tempCard variable.
			tempCard = deck.get(position1);
			deck.set(position1, deck.get(position2));
			deck.set(position2, tempCard);
		}
	}

	/**
	 * Displays the deck to the console.
	 */
	public void outputDeck() {

		System.out.println("Deck (Start)----------------------------------------------");

		for (Card card : deck) {
			System.out.println("Suit: " + card.suit + "\tRank: " + card.rank);
		}

		System.out.println("Deck (End)------------------------------------------------");
	}

}
