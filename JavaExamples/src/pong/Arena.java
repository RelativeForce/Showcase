package pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

public class Arena {

	// Global Variables
	public Rectangle topWall;
	public Rectangle leftWall;
	public Rectangle bottomWall;
	public Rectangle rightWall;
	private Paddle leftPaddle;
	private Paddle rightPaddle;
	private HitDetectionHandler hitDetectionHandler;

	public Arena(Paddle left, Paddle right) {

		leftPaddle = left;
		rightPaddle = right;

		hitDetectionHandler = HitDetectionHandler.getInstance();

		// Top wall
		topWall = new Rectangle();
		topWall.setBounds(Constants.environmentX, Constants.environmentY, Constants.wallLength, Constants.wallWidth);

		// Left wall
		leftWall = new Rectangle();
		leftWall.setBounds(Constants.environmentX, Constants.environmentY, Constants.wallWidth, Constants.wallLength);

		// Right wall
		rightWall = new Rectangle();
		rightWall.setBounds(Constants.environmentX + Constants.wallLength - Constants.wallWidth,
				Constants.environmentY + Constants.wallWidth, Constants.wallWidth, Constants.wallLength);

		// Bottom wall
		bottomWall = new Rectangle();
		bottomWall.setBounds(Constants.environmentX, Constants.environmentY + Constants.wallLength,
				Constants.wallLength, Constants.wallWidth);

	}

	public void draw(Graphics2D g) {

		// Walls
		g.setColor(Color.BLACK);
		g.fill(topWall);
		g.fill(leftWall);
		g.fill(bottomWall);
		g.fill(rightWall);

	}

	public Collision hasCollided(Ball ball, Point finalPosition) {

		List<Rectangle> potentialObstructions = getBallObstructions(
				new Point(finalPosition.x - ball.getRect().x, finalPosition.y - ball.getRect().y));

		Collision obstruction = hitDetectionHandler.checkObstructions(potentialObstructions, ball.getRect(),
				finalPosition);

		if (obstruction != null) {
			
			boolean behindLeftPaddle = obstruction.collisionPoint.x < leftPaddle.getX();
			boolean behindRightPaddle = obstruction.collisionPoint.x > rightPaddle.getX();

			if (behindLeftPaddle||behindRightPaddle) {
				if(behindLeftPaddle){
					obstruction.setWinner(Winner.RIGHT);
				}else if(behindRightPaddle){
					obstruction.setWinner(Winner.LEFT);
				}
				obstruction.isGameEnding = true;
			}
		}
		// If the obstructions list was empty then no collision took place.
		return obstruction;

	}

	public boolean hasCollided(Paddle paddle, Point finalPosition) {

		List<Rectangle> potentialObstructions = getObstructions(
				new Point(finalPosition.x - paddle.getX(), finalPosition.y - paddle.getY()));

		return hitDetectionHandler.checkObstructions(potentialObstructions, paddle.getRect(), finalPosition) != null;

	}

	/**
	 * Retrieves a list of all the {@link Rectangle}s the {@link Ball} can
	 * collide with based on a specified vector.
	 * 
	 * @param vector
	 *            {@link Point} denoting a specified vector which is assumed to
	 *            belong to the {@link Ball}.
	 * @return {@link List}&lt;{@link Rectangle}&gt; of possible obstructions.
	 */
	private List<Rectangle> getBallObstructions(Point vector) {

		// Holds the objects that the object may collide with.
		List<Rectangle> collidable = new LinkedList<>();

		// Add the paddle as the ball can collide with them.
		collidable.add(rightPaddle.getRect());
		collidable.add(leftPaddle.getRect());

		// Add the rest of the walls to the list.
		collidable.addAll(getObstructions(vector));

		return collidable;
	}

	/**
	 * Gathers the list of {@link Rectangle}s that an object with the specified
	 * vector may collide with. <br>
	 * Assuming the object is inside the arena the objects it may collide with
	 * are based on its direction of travel. Therefore the only objects that it
	 * may collide with are on its trajectory not objects behind it.
	 * 
	 * @return
	 */
	private List<Rectangle> getObstructions(Point vector) {

		// Holds the objects that the object may collide with.
		List<Rectangle> collidable = new LinkedList<>();

		if (vector.x > 0) {
			// If it is travelling to the right it may collide with the right
			// wall.
			collidable.add(rightWall);
		} else if (vector.x < 0) {
			// If it is travelling to the left it may collide with the left
			// wall.
			collidable.add(leftWall);
		}

		if (vector.y > 0) {
			// If it is travelling to the right it may collide with the right
			// wall.
			collidable.add(bottomWall);
		} else if (vector.y < 0) {
			// If it is travelling to the left it may collide with the left
			// wall.
			collidable.add(topWall);
		}

		return collidable;

	}

}
