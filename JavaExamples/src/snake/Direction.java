package snake;

import java.awt.event.KeyEvent;

/**
 * The directions that a {@link Snake} can have.
 * 
 * @author Joshua_Eddy
 *
 */
public enum Direction {

	UP() {
		/**
		 * Retrieves the opposite {@link Direction} to UP.
		 */
		public Direction getOpposite() {
			return DOWN;
		}
	},
	DOWN() {
		/**
		 * Retrieves the opposite {@link Direction} to DOWN.
		 */
		public Direction getOpposite() {
			return UP;
		}
	},
	LEFT() {
		/**
		 * Retrieves the opposite {@link Direction} to LEFT.
		 */
		public Direction getOpposite() {
			return RIGHT;
		}
	},
	RIGHT() {
		/**
		 * Retrieves the opposite {@link Direction} to RIGHT.
		 */
		public Direction getOpposite() {
			return LEFT;
		}
	};

	/**
	 * Retrieves the opposite {@link Direction} to the {@link Direction} that overrieds this method.
	 * @return Opposite {@link Direction}
	 */
	public abstract Direction getOpposite();

	/**
	 * Retrieves the {@link Direction} associated with the specified {@link KeyEvent}.
	 * @param code {@link KeyEvent} NOT NULL.
	 * @return {@link Direction}
	 */
	public static Direction getDirection(KeyEvent code){
		
		switch (code.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			return Direction.RIGHT;

		case KeyEvent.VK_LEFT:
			return Direction.LEFT;

		case KeyEvent.VK_DOWN:
			return Direction.DOWN;

		case KeyEvent.VK_UP:
			return Direction.UP;
		}
		return null;
		
	}
}
