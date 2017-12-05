package snake3D.environment;

/**
 * Holds all the dimensional values that remain constant throughout the
 * {@link Game} offering the value as a float or integer.
 * 
 * @author Joshua_Eddy
 *
 */
public enum Dimension {

	/**
	 * The width/height of the {@link Arena}.
	 */
	ARENA(500),
	/**
	 * The {@link Arena} is separated into these spaces of this width and height
	 * making a pseudo grid.
	 */
	GRID(10);

	/**
	 * The value of this as an integer.
	 */
	public final int i;

	/**
	 * Constructs an new {@link Dimension}.
	 * 
	 * @param f
	 *            The float equivalent of the integer parameter.
	 * @param i
	 *            The integer equivalent of the float parameter.
	 */
	private Dimension(int i) {
		this.i = i;
	}

	/**
	 * Converts specified integer value into a float with respect to the
	 * {@link Dimension#ARENA}.
	 * 
	 * @param value
	 *            Value to be converted.
	 * @return Result.
	 */
	public static float getAbsoluteValue(int value) {
		return (float) (((double) value) / Dimension.ARENA.i);
	}

	/**
	 * Returns the size of this as a proportion of the {@link Dimension#ARENA}.
	 * 
	 * @return result > zero
	 */
	public float asFloat() {
		return getAbsoluteValue(this.i);
	}

}