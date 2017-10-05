package snake;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

/**
 * Encapsulates the behaviour of the arena the {@link Snake} is looking for
 * "food" in.
 * 
 * @author Joshua_Eddy
 * 
 * @see Game.
 *
 */
public class Arena {

	/**
	 * The top wall of the arena.
	 */
	private Rectangle topWall;

	/**
	 * The left wall of the arena.
	 */
	private Rectangle leftWall;

	/**
	 * The right wall of the arena.
	 */
	private Rectangle rightWall;

	/**
	 * The bottom wall of the arena.
	 */
	private Rectangle bottomWall;

	/**
	 * The food the {@link Snake} "eats" to grow longer an gain points.s
	 */
	private Rectangle food;

	/**
	 * Constructs a new Arena for the {@link Game}.
	 */
	public Arena() {

		// Top wall
		topWall = new Rectangle();
		topWall.setBounds(Constants.ARENA_X, Constants.ARENA_Y, Constants.WALL_LENGTH, Constants.WALL_WIDTH);

		// Left wall
		leftWall = new Rectangle();
		leftWall.setBounds(Constants.ARENA_X, Constants.ARENA_Y, Constants.WALL_WIDTH, Constants.WALL_LENGTH);

		// Right wall
		bottomWall = new Rectangle();
		bottomWall.setBounds(Constants.ARENA_X + Constants.WALL_LENGTH - Constants.WALL_WIDTH,
				Constants.ARENA_Y + Constants.WALL_WIDTH, Constants.WALL_WIDTH, Constants.WALL_LENGTH);

		// Bottom wall
		rightWall = new Rectangle();
		rightWall.setBounds(Constants.ARENA_X, Constants.ARENA_Y + Constants.WALL_LENGTH, Constants.WALL_LENGTH,
				Constants.WALL_WIDTH);

		// Food
		food = new Rectangle();
		food.setBounds(Constants.ARENA_X + Constants.WALL_WIDTH + (Constants.WALL_LENGTH / 2),
				Constants.ARENA_Y + Constants.WALL_WIDTH + (Constants.WALL_LENGTH / 2), Constants.SPACE_INTERVAL,
				Constants.SPACE_INTERVAL);
	}

	/**
	 * Whether the specified coordinates are the same as the food in the arena.
	 * 
	 * @param x
	 *            Integer x position of the snake head.
	 * @param y
	 *            Integer y position of the snake head.
	 * @return {@code Boolean}
	 */
	public boolean eatedFood(int x, int y) {

		// If the food's coordinates are the same as the parameter coordinates
		// then the food has been eaten.
		if (food.x == x && food.y == y) {

			generateFood();
			return true;
		}
		return false;
	}

	/**
	 * Draws the visual representation of the {@link Arena} using a specified
	 * {@link Graphics2D}.
	 * 
	 * @param g
	 *            {@link Graphics2D}
	 */
	public void draw(Graphics2D g) {

		// Set wall colour
		g.setColor(Color.BLACK);

		// Draw walls of the Arena.
		g.fill(topWall);
		g.fill(leftWall);
		g.fill(rightWall);
		g.fill(bottomWall);

		// Set food colour
		g.setColor(Color.GREEN);

		// Draw food
		g.fill(food);

	}

	/**
	 * Whether the specified coordinates have collided with the walls of the
	 * {@link Arena}.
	 * 
	 * @param x
	 *            Integer x position of the snake head.
	 * @param y
	 *            Integer y position of the snake head.
	 * @param direction
	 *            {@link Direction}
	 * @return {@code Boolean}
	 */
	public boolean hasCollided(int x, int y, Direction direction) {

		switch (direction) {
		case RIGHT:
			return x + Constants.SPACE_INTERVAL > Constants.ARENA_X + Constants.WALL_LENGTH - Constants.WALL_WIDTH
					- Constants.SPACE_INTERVAL;
		case LEFT:
			return x - Constants.SPACE_INTERVAL < Constants.ARENA_X + Constants.WALL_WIDTH;
		case UP:
			return y - Constants.SPACE_INTERVAL < Constants.ARENA_Y + Constants.WALL_WIDTH;
		case DOWN:
			return y + Constants.SPACE_INTERVAL > Constants.ARENA_Y + Constants.WALL_LENGTH
					- Constants.SPACE_INTERVAL;
		default:
			return false;
		}

	}

	/**
	 * Moves the food to another random position.
	 * 
	 * @see #food
	 */
	private void generateFood() {

		int y;
		int x;

		// The number of space intervals in the arena.
		int bound = (Constants.WALL_LENGTH / Constants.SPACE_INTERVAL) - 4;
		Random generate = new Random();

		// A random value in the grid.
		int random;

		random = generate.nextInt(bound);
		x = (random * Constants.SPACE_INTERVAL) + Constants.ARENA_X + Constants.WALL_WIDTH;

		random = generate.nextInt(bound);
		y = (random * Constants.SPACE_INTERVAL) + Constants.ARENA_Y + Constants.WALL_WIDTH;

		food.setLocation(x, y);

	}

}
