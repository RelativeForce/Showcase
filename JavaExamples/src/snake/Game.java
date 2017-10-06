package snake;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Plays the retro-game of Snake. This object handles all user inputs while
 * displaying the game world to the user.
 * 
 * 
 * @author Joshua_Eddy
 *
 * @see Applet
 * @see KeyListener
 */
public class Game extends Applet implements KeyListener {

	/**
	 * Unused.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The arena in which the game takes place.
	 */
	private Arena arena;

	/**
	 * The {@link Thread} that caused the snake to move and food to spawn.
	 */
	private Thread environmentThread;

	/**
	 * The player controlled snake.
	 */
	private Snake snake;

	/**
	 * Whether the next input is to be accepted by the game or not.
	 */
	private boolean acceptInput;

	/**
	 * The current score the user has accumulated by playing the game.
	 */
	private int score;

	/**
	 * Whether the game is currently active or not.
	 */
	private static boolean run;

	/**
	 * Whether the game is currently paused or not.
	 */
	private boolean pause;

	/**
	 * The current {@link Direction} the snake is moving.
	 */
	private Direction direction;

	/**
	 * Initialises the {@link Game}.
	 */
	public void init() {

		// Initialise Environment state
		run = true;
		pause = false;
		acceptInput = true;
		score = 0;

		arena = new Arena();

		// Key listener
		this.addKeyListener(this);

		// Snake
		direction = Direction.RIGHT;
		snake = new Snake(direction);

	}

	/**
	 * Moves the player snake along its current trajectory and runs collision
	 * detection.
	 */
	private void move() {

		// The input has been accepted so another may not be accepted until the
		// snake has moved.
		acceptInput = true;

		// Get the current position of the snake head.
		int x = snake.getHead().getPart().x;
		int y = snake.getHead().getPart().y;

		// If the snake has not collided with its own body or the arena.
		if (!snake.isEatingItself() && !arena.hasCollided(x, y, direction)) {
			
			// If the snake has eaten the food. Add one to score and increase
			// the snakes length.
			if (arena.eatedFood(x, y)) {
				snake.addPart();
				score++;
			}
			
			// Move Snake
			snake.moveSnake(direction);
		} else {

			// Intercepted wall or collided with itself.
			run = false;

		}
		repaint();
	}

	/**
	 * Redraws the canvas and all the games components.
	 * 
	 * @param g
	 *            Visual {@link Graphics}
	 */
	public void paint(Graphics g) {

		// Draw environment.
		setSize(Constants.ARENA_WIDTH + 100, Constants.ARENA_HEIGHT + 100);
		Graphics2D g2 = (Graphics2D) g;

		arena.draw(g2);

		snake.draw(g2);

		// Set to text colour.
		g2.setColor(Color.RED);

		// Draw score counter.
		g2.drawString("Score: " + score, Constants.ARENA_X + (Constants.WALL_WIDTH / 2),
				Constants.WALL_LENGTH + Constants.ARENA_Y + (3 * Constants.WALL_WIDTH / 4));

		if (pause) {

			// If the game is paused.
			g2.drawString("PAUSE", (Constants.WALL_LENGTH / 2) - Constants.ARENA_X, Constants.WALL_LENGTH / 2);

		} else if (!run) {

			// If the game is over.
			g2.drawString("GAME OVER", (Constants.WALL_LENGTH / 2) - Constants.ARENA_X, Constants.WALL_LENGTH / 2);

		} else if (environmentThread == null) {

			// If the game has yet to be started.
			g2.drawString("Press ENTER to start", (Constants.WALL_LENGTH / 2) - Constants.ARENA_X,
					Constants.WALL_LENGTH / 2);
		}
	}

	/**
	 * Processes the users inputs for the game.
	 */
	@Override
	public void keyPressed(KeyEvent event) {

		// If the game has not been lost.
		if (run) {

			// If the game has yet to start and the user typed enter start the
			// game.
			if (environmentThread == null && event.getKeyCode() == KeyEvent.VK_ENTER) {

				// Background Thread
				environmentThread = new Thread() {

					@Override
					public void run() {

						while (run) {
							try {
								Thread.sleep(Constants.TIME_INTERVAL);
							} catch (Exception e) {
							}

							if (!pause)
								move();
						}
					}
				};
				environmentThread.start();

			} else {

				// If the user pressed space toggle the pause status of the
				// game.
				if (event.getKeyCode() == KeyEvent.VK_SPACE) {

					pause = !pause;

				}
				// Otherwise if the input is to be accepted and the game is not
				// paused. Process the direction the user pressed.
				else if (acceptInput && !pause) {

					// If the direction the event specifies is not opposite to
					// the snakes current direction.
					if (direction.getOpposite() != Direction.getDirection(event)
							&& Direction.getDirection(event) != null) {
						direction = Direction.getDirection(event);
					} else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
						System.out.println("GAME OVER");
						run = !run;
					}
					acceptInput = false;
				}
			}
		}
		repaint();
	}

	/**
	 * Unused.
	 */
	@Override
	public void keyReleased(KeyEvent event) {
	}

	/**
	 * Unused.
	 */
	@Override
	public void keyTyped(KeyEvent event) {
	}

}