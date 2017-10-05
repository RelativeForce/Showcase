package cardTrick;

import cardTrick.playingCards.Card;
import cardTrick.playingCards.Deck;

/**
 * Handler the matrix of {@link Card}s that are displayed to the user each round
 * of the card trick.
 * 
 * @author Joshua_Eddy
 *
 */
public class CardMatrix {

	/**
	 * Holds the {@link Card}s for the magic trick.
	 */
	private Card[][] matrix;

	/**
	 * The number of rows in {@link #matrix}.
	 */
	private final int numberOfRows;

	/**
	 * The number of columns in {@link #matrix}.
	 */
	private final int numberOfColumns;

	/**
	 * Constructs a new {@link CardMatrix} using a new {@link Deck}.
	 */
	public CardMatrix() {

		// Initialise fields.
		numberOfRows = 7;
		numberOfColumns = 3;
		matrix = new Card[numberOfColumns][numberOfRows];

		// Holds a new deck of cards.
		Deck deck = new Deck();

		// Shuffle the deck.
		deck.suffle();

		// Holds the position in the deck.
		int deckIndex = 0;

		// This nested loop populates the matrix of cards.
		for (int coloumnIndex = 0; coloumnIndex < numberOfColumns; coloumnIndex++) {

			// Iterate for the number of rows.
			for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {

				// Add the card at the deckIndex to the matrix.
				matrix[coloumnIndex][rowIndex] = deck.getCard(deckIndex);
				deckIndex++;
			}
		}
	}

	/**
	 * Retrieves a {@link Card} at a set position in the {@link #matrix}.
	 * 
	 * @param column
	 *            Integer index that specifies the column.
	 * @param row
	 *            Integer index that specifies the row.
	 * @return {@link Card}
	 */
	public Card getCard(int column, int row) {
		return matrix[column][row];
	}

	/**
	 * Outputs the {@link CardMatrix} to the console.
	 */
	public void output() {

		System.out.println("Deck (Start)------------------------------------------------------------------");

		// Output all the cards on each row.
		for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
			System.out.print("Suit: " + matrix[0][rowIndex].suit + "\tRank: " + matrix[0][rowIndex].rank + " \t|\t");
			System.out.print("Suit: " + matrix[1][rowIndex].suit + "\tRank: " + matrix[1][rowIndex].rank + " \t|\t");
			System.out.print("Suit: " + matrix[2][rowIndex].suit + "\tRank: " + matrix[2][rowIndex].rank);
			System.out.print("\n");
			;
		}
		System.out.println("Deck (End)--------------------------------------------------------------------");
	}

	/**
	 * Assigns a card at a specified row and column a new value. The card
	 * currently at this position will be erased.
	 * 
	 * @param column
	 *            Integer index that specifies the column.
	 * @param row
	 *            Integer index that specifies the row.
	 * @param card
	 *            {@link Card} to be assigned.
	 */
	public void setCard(int column, int row, Card card) {
		matrix[column][row] = card;
	}

	/**
	 * Rearranges the {@link Card}s in {@link #matrix} to move the
	 * {@link CardTrick} to the next stage.
	 * 
	 * @param selectedColoumn
	 *            The index of the column the user selected. Must be > 0.
	 */
	public void rearrage(int selectedColoumn) {

		// Store each column of the matrix as a column.
		Column col1 = new Column(cloneColumn(matrix[0]));
		Column col2 = new Column(cloneColumn(matrix[1]));
		Column col3 = new Column(cloneColumn(matrix[2]));

		/*
		 * If the first column needs to be the current second column swap the
		 * first and second columns. Or if the first column needs to be the
		 * current third column then reverse the columns' orders. Otherwise the
		 * columns are already in order.
		 */
		if (selectedColoumn == 2) {

			// Swap first and second column.
			swapColumns(col1, col2);

		} else if (selectedColoumn == 3) {

			// Swap first and third column.
			swapColumns(col1, col3);

			// Swap third and second column.
			swapColumns(col2, col3);
		}

		// Put the cards in the matrix in order of column.
		reorderCards(col1.column, col2.column, col3.column);

	}

	/**
	 * Clones a specified {@link Card}{@code []}.
	 * 
	 * @param toClone
	 *            {@link Card}{@code []} to be cloned.
	 * @return {@link Card}{@code []} clone.
	 */
	private Card[] cloneColumn(Card[] toClone) {

		// Initialise the card array that will contain the clone.
		Card[] clone = new Card[numberOfRows];

		// Copy all the cards to the clone.
		for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {

			clone[rowIndex] = toClone[rowIndex];

		}

		return clone;

	}

	/**
	 * Places {@link Card}s in {@link #matrix} by order of columns filling each
	 * row before moving to the next.
	 * 
	 * @param firstColumn
	 *            {@link Card}<code>[]</code> column
	 * @param secondColumn
	 *            {@link Card}<code>[]</code> column
	 * @param thirdColumn
	 *            {@link Card}<code>[]</code> column
	 */
	private void reorderCards(Card[] firstColumn, Card[] secondColumn, Card[] thirdColumn) {

		int rowIndex = 0;
		int columnIndex = 0;

		matrix = new Card[numberOfColumns][numberOfRows];

		for (int y = 0; y < numberOfRows; y++) {

			for (int x = 0; x < numberOfColumns; x++) {

				if (rowIndex == numberOfRows) {
					rowIndex = 0;
					columnIndex++;
				}

				if (columnIndex == 0) {
					matrix[x][y] = firstColumn[rowIndex];
				} else if (columnIndex == 1) {
					matrix[x][y] = secondColumn[rowIndex];
				} else {
					matrix[x][y] = thirdColumn[rowIndex];
				}

				rowIndex++;
			}
		}
	}

	/**
	 * Swaps the {@link Card}s in two columns.
	 * 
	 * @param column1
	 *            {@link Card}<code>[]</code> column
	 * @param column2
	 *            {@link Card}<code>[]</code> column
	 */
	private void swapColumns(Column column1, Column column2) {
		Card[] tempColumn = column1.column;
		column1.column = column2.column;
		column2.column = tempColumn;
	}

	/**
	 * A wrapper class for use in the {@link CardMatrix#rearrage(int)} and
	 * {@link CardMatrix#swapColumns(Column, Column)}. This serves no othe
	 * purpose than to allow multiple columns to be returned at once.
	 * 
	 * @author Joshua_Eddy
	 *
	 */
	private class Column {

		public Card[] column;

		public Column(Card[] column) {
			this.column = column;
		}

	}
}
