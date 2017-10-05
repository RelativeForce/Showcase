package cardTrick;

import java.util.Scanner;

/**
 * Performs a simulation of a card trick in which the user picks a card. Then is
 * told to select the column their card is in three times before the computer
 * guesses the user's card.
 * 
 * @author Joshua_Eddy
 * 
 * @see CardMatrix
 *
 */
public class CardTrick {

	/**
	 * Holds the Scanner used for user inputs.
	 */
	private Scanner UserInput;

	/**
	 * Constructs a new {@link CardTrick}.
	 */
	public CardTrick() {
		UserInput = new Scanner(System.in);
	}

	/**
	 * Runs the card trick for the user.
	 */
	private void run() {

		int selectedColumn;

		CardMatrix deck = new CardMatrix();
		deck.output();
		
		// Ask the user if they have picked a card.
		System.out.println("Pick a card and remember it. When you know your card press ENTER");
		UserInput.nextLine();
		
		int guessIndex = 0;
		
		while (guessIndex < 3) {
			
			System.out.println("Which coloum is your card in? [1, 2 or 3]");
			
			try {
				
				// Get the users column number.
				selectedColumn = UserInput.nextInt();
				
				// If the user inputed column number is valid.
				if (selectedColumn < 4 && selectedColumn > 0) {
					
					// If the guessIndex is 2 then the computer must guess the card.
					if (guessIndex == 2) {
						
						// Display last guess.
						System.out.println("\nYour card is: [Suit: " + deck.getCard(selectedColumn - 1, 0).suit + "\tRank: "
								+ deck.getCard(selectedColumn - 1, 0).rank + "]");
						
						guessIndex++;
						
					} else {
						// Rearrange the cards for the next guess.
						deck.rearrage(selectedColumn);
						deck.output();
					
						guessIndex++;
					}
				} else {
					// Feedback the 
					System.out.println("Error - Invalid Input");
				}
			} catch (Exception e) {
				System.out.println("Error - Invalid Input");
			}
		}
	}

	/**
	 * Runs program.
	 * @param args Unused.
	 */
	public static void main(String[] args) {

		System.out.println("------------------------- Card Trick -------------------------");

		CardTrick ct = new CardTrick();
		ct.run();

	}
}
