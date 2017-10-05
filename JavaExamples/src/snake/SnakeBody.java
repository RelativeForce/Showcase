package snake;

import java.awt.Rectangle;

/**
 * Denotes a body part of the {@link Snake}.
 * @author Joshua_Eddy
 * @see Snake
 *
 */
public class SnakeBody {

	/**
	 * The {@link Direction} to the next {@link SnakeBody} in the chain.
	 */
	private Direction directionToNext;
	
	/**
	 * The {@link Rectangle} that denotes this {@link SnakeBody}.
	 */
	private Rectangle part;

	/**
	 * Constructs a new {@link SnakeBody}.
	 * @param directionToNext {@link Direction}
	 * @param x Integer initial x coordinate.
	 * @param y Integer initial y coordinate.
	 */
	public SnakeBody(Direction directionToNext, int x, int y) {

		this.directionToNext = directionToNext;

		part = new Rectangle();
		part.setBounds(x, y, Constants.SPACE_INTERVAL, Constants.SPACE_INTERVAL);

	}

	/**
	 * Sets the {@link Direction} from this {@link SnakeBody} to the next one in the chain.
	 * @param directionToNext {@link Direction}
	 */
	public void setDirectionToNext(Direction directionToNext) {
		this.directionToNext = directionToNext;
	}

	/**
	 * Sets the location of the {@link SnakeBody}.
	 * @param x Integer x coordinate.
	 * @param y Integer y coordinate.
	 */
	public void setLocation(int x, int y) {
		part.setLocation(x, y);
	}

	/**
	 * Retrieves the {@link Direction} from this to the next {@link SnakeBody}.
	 * @return {@link Direction}
	 */
	public Direction getDirectionToNext() {
		return directionToNext;
	}

	/**
	 * Retrieves the visual {@link Rectangle} assigned to this {@link SnakeBody}.
	 * @return {@link Rectangle}
	 */
	public Rectangle getPart() {
		return part;
	}
}