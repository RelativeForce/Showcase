package pong;

import java.awt.Rectangle;

public class ArtificialIntelligence {

	private int level;
	private int nextY;
	private Rectangle paddleSpace;

	public ArtificialIntelligence(int level) {
		this.level = level;

		paddleSpace = new Rectangle();
		paddleSpace.setBounds(Constants.environmentX, Constants.environmentY,
				Constants.wallWidth + (Constants.paddleWidth * 2), Constants.wallLength);
	}

	public int getMove(Paddle paddle) {

		// Path
		// Above paddle : 1
		// Below paddle : -1
		// In bound on paddle: 0

		boolean isBellow = nextY >= paddle.getY() + Constants.paddleLength - Constants.ballDimension;
		boolean isAbove = nextY <= paddle.getY();

		if (isBellow) {
			return -1;
		} else if (isAbove) {
			return 1;
		} else {
			return 0;
		}

	}

	public int getLevel() {
		return level;
	}

	public void predictNextY(Arena arena) {

		Ball ball = Ball.getInstance().clone();

		boolean predicted = false;

		while (!predicted) {

			Collision status = ball.move(arena);

			if (status != null) {
				predicted = true;
			}

		}
		System.out.println("Predicted");
		nextY = ball.getRect().y;
	}
}
