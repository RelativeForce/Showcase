package snake3D.environment;

import java.util.Random;

import snake3D.graphics.Colour;

import java.awt.Rectangle;

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
	 * The food the {@link Snake} "eats" to grow longer an gain points.
	 */
	private Rectangle food;

	/**
	 * Constructs a new Arena for the {@link Game}.
	 */
	public Arena() {

		// Top wall
		topWall = new Rectangle();
		topWall.setBounds(0, 0, Dimension.ARENA.i, Dimension.GRID.i);

		// Bottom wall
		bottomWall = new Rectangle();
		bottomWall.setBounds(0, Dimension.ARENA.i, Dimension.ARENA.i, Dimension.GRID.i);

		// Left wall
		leftWall = new Rectangle();
		leftWall.setBounds(Dimension.ARENA.i, 0, Dimension.GRID.i, Dimension.ARENA.i);

		// Right wall
		rightWall = new Rectangle();
		rightWall.setBounds(0, 0, Dimension.GRID.i, Dimension.ARENA.i);

		int center = Dimension.GRID.i * 10;

		// Food
		food = new Rectangle();
		food.setBounds(center, center, Dimension.GRID.i, Dimension.GRID.i);
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
	 * Adds the members of the {@link Arena} to the specified {@link Plane}.
	 * 
	 * @param plane
	 *            {@link Plane} that the {@link Arena} is a member of.
	 */
	public void addToPlane(Plane plane) {

		// Adds the members of the arena to the plane.
		plane.addObject(bottomWall, Colour.BLUE);
		plane.addObject(leftWall, Colour.BLUE);
		plane.addObject(rightWall, Colour.BLUE);
		plane.addObject(topWall, Colour.BLUE);
		plane.addObject(food, Colour.GREEN);

		// Create a rectangle to represent (max,max) in the game so the user has a point of
		// reference. Then add it to the plane.
		Rectangle referencePoint = new Rectangle();
		referencePoint.setBounds(Dimension.ARENA.i, Dimension.ARENA.i, Dimension.GRID.i, Dimension.GRID.i);
		plane.addObject(referencePoint, Colour.YELLOW);

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
			return x + Dimension.GRID.i >= Dimension.ARENA.i;
		case LEFT:
			return x - Dimension.GRID.i <= 0;
		case UP:
			return y - Dimension.GRID.i <= 0;
		case DOWN:
			return y + Dimension.GRID.i >= Dimension.ARENA.i;
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
		int bound = (Dimension.ARENA.i / Dimension.GRID.i) - 2;
		Random generate = new Random();

		// A random value in the grid.
		int random;

		random = generate.nextInt(bound);
		x = (random * Dimension.GRID.i) + Dimension.GRID.i;

		random = generate.nextInt(bound);
		y = (random * Dimension.GRID.i) + Dimension.GRID.i;

		food.setLocation(x, y);

	}

}
