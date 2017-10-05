package euclidsAlgorithm;

/**
 * An immutable object that stores a set of values pertaining to a phase in the
 * Euclid's Algorithm.
 * 
 * @author Joshua_Eddy
 * @see EuclidsAlgorithm
 *
 */
class Phase {

	public final int a;
	public final int b;
	public final int f;
	public final int r;

	/**
	 * Constructs an new Phase and assigns the value zero to {@link #a},
	 * {@link #b}, {@link #r} and {@link #f}.
	 */
	public Phase(int a, int b, int r, int f) {

		this.a = a;
		this.b = b;
		this.r = r;
		this.f = f;

	}

}