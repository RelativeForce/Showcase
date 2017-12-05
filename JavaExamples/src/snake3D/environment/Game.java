package snake3D.environment;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import snake3D.graphics.Graphic;
import snake3D.graphics.Window;

import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * The game of snake in which the 2D retro game of snake rotates in a 3D space
 * adding difficulty through the increase of the rotational speed of the game.
 * 
 * @author Joshua_Eddy
 * 
 * @see Graphic
 * @see Thread
 * @see LinkedTransferQueue
 *
 */
public final class Game extends Graphic {

	/**
	 * The number of milliseconds between each movement of the {@link Snake}.
	 */
	public static final long TIME_INTERVAL = 250;

	/**
	 * The arena in which the game takes place.
	 */
	private Arena arena;

	/**
	 * The {@link Thread} that caused the snake to move and food to spawn.
	 */
	private Thread movementThread;

	/**
	 * The player controlled snake.
	 */
	private Snake snake;

	/**
	 * The inter-thread buffer that allows the {@link Game#movementThread} to modify
	 * objects on the main thread.
	 */
	public Queue<Integer> buffer;

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
	private volatile boolean pause;

	/**
	 * The {@link Plane} that displays the {@link Game} in 3D.
	 */
	private Plane plane;

	/**
	 * Constructs a new {@link Game}.
	 */
	public Game() {

		super(Window.MEDIUM);

		// Initialise Environment state
		run = true;
		pause = false;
		score = 0;

		// Initialise arena
		arena = new Arena();

		// Initialise Snake
		snake = new Snake(Direction.RIGHT);

		// Holds the offset of the plane so that the arena appears in the centre of the
		// screen.
		float centerOffset = -Dimension.getAbsoluteValue((Dimension.ARENA.i / 2));

		// Initialise the plane and add the arena and snake to it.
		plane = new Plane(centerOffset, centerOffset, -Dimension.getAbsoluteValue(Dimension.GRID.i / 2));
		arena.addToPlane(plane);
		snake.addToPlane(plane);

		Keyboard.enableRepeatEvents(true);

		// Initialise the inter-thread buffer queue.
		buffer = new LinkedTransferQueue<Integer>();

		// Initialise the movement thread.
		movementThread = new Thread() {

			/**
			 * Performs that designated task of this thread.
			 */
			@Override
			public void run() {

				// Constantly iterate until the game ends.
				while (run) {

					// Wait a predetermined amount of time.
					try {
						Thread.sleep(TIME_INTERVAL);
					} catch (Exception e) {
					}

					// Move the snake if the game is not paused.
					if (!pause)
						moveSnake();
				}
				System.out.println("GAME OVER");
				System.out.println("Score: " + score);
			}
		};
		movementThread.start();

	}

	/**
	 * Runs the {@link Game}.
	 * 
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {

		// Create an instance of the game and run it.
		Game game = new Game();
		game.run(WINDOWED, "Snake 2 - THE ONE THAT BREAKS YOU", 0.01f);
	}

	@Override
	protected void initScene() throws Exception {
		GL11.glDisable(GL11.GL_LINE);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}

	@Override
	protected void checkSceneInput() {
		// If the game has not been lost.
		if (run) {

			int key = getCurrentKey();

			// If the user pressed space toggle the pause status of the
			// game.
			if (key == Keyboard.KEY_SPACE) {

				pause = !pause;

			} else if (key == Keyboard.KEY_ESCAPE) {
				run = !run;
			}
			// Otherwise if the input is to be accepted and the game is not
			// paused. Process the direction the user pressed.
			else if (!pause) {

				// If the direction the event specifies is not opposite to
				// the snakes current direction.
				if (snake.getDirection().getOpposite() != Direction.getDirection(key)
						&& Direction.getDirection(key) != null) {
					snake.setDirection(Direction.getDirection(key));
				}
			}
		}
	}

	@Override
	protected void updateScene() {

		// If the game is running the move the plane.
		if (run) {
			plane.move();
		}
		checkBuffer();

	}

	@Override
	protected void renderScene() {
		plane.draw();
	}

	/**
	 * Checks the {@link Game#buffer} to see if there is any actions that the main
	 * thread must undertake. If there is they are executed in this method.
	 */
	private void checkBuffer() {

		// If there are item(s) in the buffer.
		if (!buffer.isEmpty()) {

			// Iterate through all the elements in the buffer.
			while (!buffer.isEmpty()) {

				// Get the head of the buffer.
				int head = buffer.poll();

				// If the item is zero this means that the snake needs to increase in size.
				if (head == 0) {
					snake.addPart(plane);
				}
			}

		}
	}

	/**
	 * Moves the player snake along its current trajectory and runs collision
	 * detection.
	 */
	private void moveSnake() {

		// Get the current position of the snake head.
		int x = snake.getHead().getPart().x;
		int y = snake.getHead().getPart().y;

		// If the game is not paused.
		if (!pause) {

			// If the snake has not collided with its own body or the arena.
			if (!snake.isEatingItself() && !arena.hasCollided(x, y, snake.getDirection())) {

				// If the snake has eaten the food. Add one to score and increase
				// the snakes length.
				if (arena.eatedFood(x, y)) {

					// Tell the other thread to increase the snakes size.
					buffer.add(0);

					plane.increaseSpeed();

					score++;
				}

				// Move Snake
				snake.moveSnake();

			} else {

				// Intercepted wall or collided with itself.
				run = false;

			}
		}
	}

	/**
	 * Checks which key is held down using {@link Keyboard} and retrieves the
	 * integer key code..
	 * 
	 * @return Integer key code.
	 */
	private int getCurrentKey() {

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			return Keyboard.KEY_LEFT;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			return Keyboard.KEY_RIGHT;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			return Keyboard.KEY_UP;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			return Keyboard.KEY_DOWN;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			return Keyboard.KEY_SPACE;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			return Keyboard.KEY_ESCAPE;
		} else {
			return 0;
		}
	}

}
