package snake;

import java.util.LinkedList;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

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
	 * Constructs an new {@link Snake}.
	 * 
	 * @param direction
	 *            The initial {@link Direction} of the {@link Snake}.
	 */
	public Snake(Direction direction) {

		body = new LinkedList<SnakeBody>();

		// Add head in top left corner of the arena.
		body.add(new SnakeBody(direction, Constants.ARENA_X + 10 + Constants.WALL_WIDTH,
				Constants.ARENA_Y + 10 + Constants.WALL_WIDTH,0));

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
	 * Increases the length of the {@link Snake} but one {@link SnakeBody}.
	 */
	public void addPart() {

		System.out.println("Part Added");

		// Holds the coordinates of the new part.
		int x;
		int y;

		// Holds the SnakeBody of the previous body part.
		SnakeBody previousSnakeBody = body.get(body.size() - 1);

		// Holds the Rectangle of the previous body part.
		Rectangle previousPart = previousSnakeBody.getPart();

		/*
		 * The direction that the previous body part denotes the position that
		 * the new body part will require.
		 */
		switch (previousSnakeBody.getDirectionToNext()) {
		case RIGHT:
			// New part to the left of the previous.
			x = previousPart.x - Constants.SPACE_INTERVAL;
			y = previousPart.y;
			break;
		case LEFT:
			// New part to the right of the previous.
			x = previousPart.x + Constants.SPACE_INTERVAL;
			y = previousPart.y;
			break;
		case UP:
			// New part below the previous.
			x = previousPart.x;
			y = previousPart.y + Constants.SPACE_INTERVAL;
			break;
		case DOWN:
			// New part above the previous.
			x = previousPart.x;
			y = previousPart.y - Constants.SPACE_INTERVAL;
			break;
		default:
			x = 0;
			y = 0;
			break;
		}

		body.add(new SnakeBody(previousSnakeBody.getDirectionToNext(), x, y, body.size()));

	}

	/**
	 * Moves the head of the snake along a specified {@link Direction}.
	 * 
	 * @param direction
	 *            {@link Direction}.
	 */
	public void moveSnake(Direction direction) {

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

				// Move the head along the specified direction.
				switch (direction) {
				case RIGHT:
					currentPart.setLocation(currentPart.getPart().x + Constants.SPACE_INTERVAL, currentPart.getPart().y);
					break;
				case LEFT:
					currentPart.setLocation(currentPart.getPart().x - Constants.SPACE_INTERVAL, currentPart.getPart().y);
					break;
				case UP:
					currentPart.setLocation(currentPart.getPart().x, currentPart.getPart().y - Constants.SPACE_INTERVAL);
					break;
				case DOWN:
					currentPart.setLocation(currentPart.getPart().x, currentPart.getPart().y + Constants.SPACE_INTERVAL);
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
	 * Checks if the {@link Snake}'s head has collided with any of its body
	 * parts.
	 * 
	 * @return {@code Boolean}
	 */
	public boolean isEatingItself() {

		// Holds the head of the snake.
		SnakeBody head = getHead();

		// Iterate through the entire snake.
		for (SnakeBody currentPart : body) {

			// If the current body part is not the head of the snake.
			if (currentPart.getPosition() != 0) {

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
	 * Draws the visual representation of the {@link Snake} using a specified {@link Graphics2D}.
	 * @param g {@link Graphics2D}
	 */
	public void draw(Graphics2D g) {

		// Set Snake colour to blue.
		g.setColor(Color.BLUE);
		
		// Iterate through each snake body part.
		for (SnakeBody bodyPart : body) {
			g.fill(bodyPart.getPart());
		}

	}
}