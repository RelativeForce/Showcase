package snake3D.environment;

import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import snake3D.graphics.Colour;

/**
 * Encapsulates the behaviour of the Snake in the {@link Game}.
 * 
 * @author Joshua_Eddy
 *
 */
public class Snake {

	/**
	 * Holds the {@link SnakeBody} parts that make up the {@link Snake}.
	 */
	private List<SnakeBody> body;

	/**
	 * The current {@link Direction} the snake is moving.
	 */
	private Direction direction;

	/**
	 * Constructs an new {@link Snake}.
	 * 
	 * @param direction
	 *            The initial {@link Direction} of the {@link Snake}.
	 */
	public Snake(Direction direction) {

		this.body = new LinkedList<>();
		this.direction = direction;

		// Add head in centre of the arena.
		this.body.add(new SnakeBody(Dimension.GRID.i * 10, Dimension.GRID.i * 5, 0));

	}

	/**
	 * Retrieves the first {@link SnakeBody} of the {@link Snake}.
	 * 
	 * @return {@link SnakeBody}
	 */
	public SnakeBody getHead() {
		return body.get(0);
	}

	/**
	 * Retrieves the @{@link SnakeBody}s from the {@link Snake}.
	 * 
	 * @return <code>List&lt;SnakeBody&gt;</code>
	 */
	public List<SnakeBody> getBody() {
		return body;
	}

	/**
	 * Increases the length of the {@link Snake} but one {@link SnakeBody} and adds
	 * it to the specified {@link Plane}.
	 * 
	 * @param plane
	 *            {@link Plane} the snake is in.
	 */
	public void addPart(Plane plane) {

		// Holds the coordinates of the new part.
		int x;
		int y;

		// Holds the SnakeBody of the previous body part.
		SnakeBody snakeTail = body.get(body.size() - 1);

		// Holds the Rectangle of the previous body part.
		Rectangle tailPart = snakeTail.getPart();

		Direction toNext;

		if (snakeTail.equals(getHead())) {
			toNext = direction;
		} else {
			toNext = getDirectionTo(snakeTail.getPart(), body.get(body.size() - 2).getPart());
		}
		/*
		 * The direction that the previous body part denotes the position that the new
		 * body part will require.
		 */
		switch (toNext) {
		case RIGHT:
			// New part to the left of the previous.
			x = tailPart.x - Dimension.GRID.i;
			y = tailPart.y;
			break;
		case LEFT:
			// New part to the right of the previous.
			x = tailPart.x + Dimension.GRID.i;
			y = tailPart.y;
			break;
		case UP:
			// New part below the previous.
			x = tailPart.x;
			y = tailPart.y + Dimension.GRID.i;
			break;
		case DOWN:
			// New part above the previous.
			x = tailPart.x;
			y = tailPart.y - Dimension.GRID.i;
			break;
		default:
			x = 0;
			y = 0;
			break;
		}

		// Initialise the new part of the snake then add it to the plane and the snake.
		SnakeBody newPart = new SnakeBody(x, y, body.size());
		plane.addObject(newPart.getPart(), Colour.CYAN);
		body.add(newPart);

	}

	/**
	 * Moves the head of the snake along a specified {@link Direction}.
	 * 
	 * @param direction
	 *            {@link Direction}.
	 */
	public void moveSnake() {

		// Holds the previous body part's position.
		int previousX = 0;
		int previousY = 0;

		// Holds the position of the next body part in the chain.
		int nextX = 0;
		int nextY = 0;

		// Iterate through the snake
		for (SnakeBody currentPart : body) {

			// If the current part is the head.
			if (currentPart.equals(body.get(0))) {

				// Set the coordinates of the next part in the chain to the
				// heads position.
				previousX = currentPart.getPart().x;
				previousY = currentPart.getPart().y;

				int moveDistance = Dimension.GRID.i;

				// Move the head along the specified direction.
				switch (direction) {
				case RIGHT:
					currentPart.setLocation(currentPart.getPart().x + moveDistance, currentPart.getPart().y);
					break;
				case LEFT:
					currentPart.setLocation(currentPart.getPart().x - moveDistance, currentPart.getPart().y);
					break;
				case UP:
					currentPart.setLocation(currentPart.getPart().x, currentPart.getPart().y - moveDistance);
					break;
				case DOWN:
					currentPart.setLocation(currentPart.getPart().x, currentPart.getPart().y + moveDistance);
					break;
				}

			} else {

				// Assign the coordinates of the part before the current part in
				// the chain as this parts next position.
				nextX = previousX;
				nextY = previousY;

				// Assign the current parts coordinates as the next parts
				// position.
				previousX = currentPart.getPart().x;
				previousY = currentPart.getPart().y;

				// Move the current part to the new location.
				currentPart.setLocation(nextX, nextY);

			}

		}

	}

	/**
	 * Checks if the {@link Snake}'s head has collided with any of its body parts.
	 * 
	 * @return {@code Boolean}
	 */
	public boolean isEatingItself() {

		// Holds the head of the snake.
		SnakeBody head = getHead();

		// Iterate through the entire snake.
		for (SnakeBody currentPart : body) {

			// If the current body part is not the head of the snake.
			if (currentPart.getPosition() > 2) {

				// If the coordinates of the head and the current body part are
				// the same they have collided.
				if (currentPart.getPart().x == head.getPart().x && currentPart.getPart().y == head.getPart().y) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Retrieves the {@link Direction} that the {@link Snake} is currently
	 * travelling in.
	 * 
	 * @return {@link Direction}
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Sets the {@link Direction} of the snake. NOT NULL
	 * 
	 * @param direction
	 *            New {@link Direction} of the {@link Snake}.S
	 */
	public void setDirection(Direction direction) {
		if (direction == null) {
			throw new IllegalArgumentException("Direction cannot be null");
		}
		this.direction = direction;
	}

	/**
	 * Adds the {@link Snake} to the specified {@link Plane}.
	 * 
	 * @param plane
	 *            The {@link Plane} that the {@link Snake} will be added to.
	 */
	public void addToPlane(Plane plane) {

		if (plane == null) {
			throw new IllegalArgumentException("Plane cannot be null.");
		}

		// Iterate through the snakes body and add all the parts to the plane.
		for (SnakeBody sb : body) {
			plane.addObject(sb.getPart(), Colour.CYAN);
		}

	}

	/**
	 * Calculates the {@link Direction} from one {@link SnakeBody} to another that
	 * is connected to it.
	 * 
	 * @param currentPart
	 *            The part that is closes to the tail of the {@link Snake}.
	 * @param forPart
	 *            The part closes to the head of the {@link Snake}.
	 * @return {@link Direction} from currentPart to forPart.
	 */
	private Direction getDirectionTo(Rectangle currentPart, Rectangle forPart) {

		// If the x coordinates of both parts are the same they must be moving
		// vertically. Otherwise they are moving horizontally.
		if (currentPart.x == forPart.x) {

			// If the currentPart has a greater y coordinate that the forPart then the
			// direction is UP, otherwise it is DOWN.
			if (currentPart.y > forPart.y) {
				return Direction.UP;
			} else {
				return Direction.DOWN;
			}
		} else {

			// If the currentPart has a greater x coordinate that the forPart then the
			// direction is RIGHT, otherwise it is LEFT.
			if (currentPart.x > forPart.x) {
				return Direction.RIGHT;
			} else {
				return Direction.LEFT;
			}
		}

	}

}