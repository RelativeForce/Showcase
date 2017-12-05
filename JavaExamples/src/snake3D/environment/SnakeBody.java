package snake3D.environment;

import java.awt.Rectangle;

/**
 * Denotes a body part of the {@link Snake}.
 * 
 * @author Joshua_Eddy
 * @see Snake
 *
 */
public final class SnakeBody {

	/**
	 * The {@link Rectangle} that denotes this {@link SnakeBody}.
	 */
	private Rectangle part;

	/**
	 * The position of this {@link SnakeBody} in the {@link Snake}.
	 */
	private final int position;

	/**
	 * Constructs a new {@link SnakeBody}.
	 * 
	 * @param x
	 *            Integer initial x coordinate.
	 * @param y
	 *            Integer initial y coordinate.
	 */
	public SnakeBody(int x, int y, int position) {

		this.position = position;

		part = new Rectangle();
		part.setBounds(x, y, Dimension.GRID.i, Dimension.GRID.i);

	}

	/**
	 * Sets the location of the {@link SnakeBody}.
	 * 
	 * @param x
	 *            Integer x coordinate.
	 * @param y
	 *            Integer y coordinate.
	 */
	public void setLocation(int x, int y) {
		part.setLocation(x, y);
	}

	/**
	 * Retrieves this {@link SnakeBody}'s position in the snake where zero is the
	 * head of the snake.
	 * 
	 * @return Integer position in the {@link Snake}.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Retrieves the visual {@link Rectangle} assigned to this {@link SnakeBody}.
	 * 
	 * @return {@link Rectangle}
	 */
	public Rectangle getPart() {
		return part;
	}
}