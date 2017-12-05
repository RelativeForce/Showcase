package pong;

import java.awt.*;
import java.util.Random;

import pong.Point;

public class Ball {

	private static Ball INSTANCE = new Ball((Constants.wallLength / 2) + Constants.environmentX,
			Constants.wallLength / 2);

	// Instance Variables
	private Rectangle ball;
	private double xSpeed;
	private int ySpeed;

	// Constructor
	private Ball(int x, int y) {

		ball = new Rectangle();
		ball.setBounds(x, y, Constants.ballDimension, Constants.ballDimension);

		xSpeed = 1;
		ySpeed = 0;

	}

	public static Ball getInstance() {
		return INSTANCE;
	}

	@Override
	public Ball clone() {

		Ball clone = new Ball(ball.x, ball.y);
		clone.xSpeed = this.xSpeed;
		clone.ySpeed = this.ySpeed;

		return clone;

	}

	// Set
	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	public void setYSpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public void setX(int x) {
		ball.x = x;
	}

	public void setY(int y) {
		ball.y = y;
	}

	public Collision move(Arena arena) {

		int x = this.getRect().x;
		int y = this.getRect().y;

		if (getXSpeed() != 0) {

			int nextX = x + (int) this.getXSpeed();
			int nextY = y + (int) this.getYSpeed();

			Collision collision = arena.hasCollided(this, new Point(nextX, nextY));
			
			if (collision == null) {

				this.setX(nextX);
				this.setY(nextY);

			} else if (collision.isGameEnding) {
				
				ball.x = collision.collisionPoint.x;
				ball.y = collision.collisionPoint.y;
				
				this.setXSpeed(0);
				this.setYSpeed(0);

			} else {
				
				richochet(collision);
				
				this.setX((int) (x + this.getXSpeed()));
				this.setY((int) (y + this.getYSpeed()));
			}
			return collision;
		}
		return null;

	}

	private void reboundOffPaddle() {

		Random generator = new Random();
		int nextYSpeed;
		int verticalDirection;

		setXSpeed((getXSpeed() * -1.05));
		verticalDirection = (int) Math.pow(-1, (double) generator.nextInt(2));
		nextYSpeed = verticalDirection * generator.nextInt(5);
		setYSpeed(getYSpeed() < 0 ? -nextYSpeed : nextYSpeed);

	}

	public void reboundOffWall() {

		setYSpeed(-getYSpeed());

	}

	public void richochet(Collision collision) {

		Point collisionPoint = collision.collisionPoint;
		Rectangle obstruction = collision.obstruction;

		boolean withinX = checkBoundry(collisionPoint.x, (int) (obstruction.x - ball.getWidth()),
				(int) (obstruction.x + obstruction.getWidth()));

		boolean withinY = checkBoundry(collisionPoint.y, (int) (obstruction.y - ball.getHeight()),
				(int) (obstruction.y + obstruction.getHeight()));

		boolean above = withinX && collisionPoint.y <= obstruction.y;

		boolean below = withinX && collisionPoint.y >= obstruction.y + obstruction.height;

		boolean right = withinY && collisionPoint.x >= obstruction.x + obstruction.width;

		boolean left = withinX && collisionPoint.x <= obstruction.x;

		setPosition(collisionPoint);

		if ((above || below) && (right || left)) {
			reboundOffPaddle();
		} else if (above || below) {
			reboundOffWall();
		} else if (right || left) {
			reboundOffPaddle();
		}

	}

	private void setPosition(Point collisionPoint) {

		ball.x = collisionPoint.x;
		ball.y = collisionPoint.y;

	}

	public boolean checkBoundry(int position, int lowerBound, int upperBound) {

		boolean condition1;
		boolean condition2;

		condition1 = position >= lowerBound;
		condition2 = position <= upperBound;

		if (condition1 && condition2) {
			return true;
		}

		return false;
	}

	// Get
	public double getXSpeed() {
		return xSpeed;
	}

	public int getYSpeed() {
		return ySpeed;
	}

	public Rectangle getRect() {
		return ball;
	}

	public void initaliseRally(String lastWin) {

		ball.x = Constants.wallLength / 2;
		ball.y = Constants.wallLength / 2;

		setYSpeed(0);
		setXSpeed(lastWin.equals("left") ? 1 : -1);

	}

}
