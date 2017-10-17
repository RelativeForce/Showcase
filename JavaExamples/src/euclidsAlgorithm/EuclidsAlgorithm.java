package euclidsAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Performs the Euclid's algorithm for finding the Highest Common Factor(HCF) of
 * two integers. Uses viewing the source code must understand the mathematical
 * algorithm first.
 * 
 * 
 * @author Joshua_Eddy
 * @see Phase
 *
 */
public class EuclidsAlgorithm {

	/**
	 * Used to take inputs from the console.
	 */
	private Scanner UserInput;

	/**
	 * Stores the list of {@link Phase}s created by performing Euclid's
	 * algorithm.
	 */
	private List<Phase> phaseList;

	/**
	 * Initialises the nessecary fields for use when {@link #run()} is called.
	 */
	public EuclidsAlgorithm() {

		// Initialise scanner
		UserInput = new Scanner(System.in);
		phaseList = new ArrayList<Phase>();

	}

	/**
	 * Runs the program.
	 */
	public void run() {

		boolean validInputs = false;
		int tempInt;

		int a = 0;
		int b = 0;

		// Iterate until the user inputs two valid integer values.
		do {

			System.out.println("What is the first number?");
			String input1 = UserInput.nextLine();
			System.out.println("What is the second number?");
			String input2 = UserInput.nextLine();

			try {

				a = Integer.parseInt(input1);
				b = Integer.parseInt(input2);

				// Reverse a and be if a is greater than a.
				if (a > b) {
					tempInt = a;
					a = b;
					b = tempInt;
				}

				validInputs = true;

			} catch (Exception e) {
				System.out.println("Error - Non integer value inputted.");
			}

		} while (!validInputs);

		// Feedback values have been accepted.
		System.out.println("Calculating the HCF of " + a + " and " + b);

		partOne(a, b);

		System.out.println("\nDo you want to calculate 'm' and 'n' for ma + nb = hcf(a,b)? [Y/N]");

		String confirmPartTwo = UserInput.nextLine();
		confirmPartTwo = confirmPartTwo.toUpperCase();

		if (confirmPartTwo.equals("Y")) {
			partTwo();
		} else {
			System.out.println("Program Terminated");
		}

	}

	/**
	 * Outputs a phase to the console in the form "a: b: r:"
	 * 
	 * @param phase
	 *            {@link Phase} to be output.
	 */
	private void outputPhase(Phase phase) {
		System.out.println("a: " + phase.a + "\t\tb:" + phase.b + "\t\tr:" + phase.r);
	}

	/**
	 * Calculates and outputs the highest common factor(HCF) of two integers a
	 * and b. b MUST be greater than a.
	 * 
	 * @param a
	 *            A integer value.
	 * @param b
	 *            A integer value.
	 */
	private void partOne(int a, int b) {

		if (b < a) {

			throw new RuntimeException("b must be greater than or equal to a.");

		}

		// Holds the index of the current phase to be added to the phase list.
		int phaseIndex = 0;

		// Holds the remainder of b/a.
		int r;

		// Holds the factor(f) which satisfies b = a*f + r
		int f;

		do {

			// Calculate r.
			r = b % a;

			// Calculate f
			f = (b - (b % a)) / a;

			// Add the new phase to the phase list.
			phaseList.add(new Phase(a, b, r, f));

			// Retrieve and output the new phase.
			outputPhase(phaseList.get(phaseIndex));

			
			// Assign b the value of the current phase's a.
			b = phaseList.get(phaseIndex).a;

			// Assign a the value of the current phase's r.
			a = phaseList.get(phaseIndex).r;
			
			// Increment the counter.
			phaseIndex++;

		} while (r != 0);

		System.out.println("HCF: " + phaseList.get(phaseIndex - 1).a);
	}

	/**
	 * This is the second part of Euclid's Algorithm in which a value for m and
	 * n are calculated which satisfy the equation ma + nb = HCF(a,b). The
	 * {@link #phaseList} is evaluated in reverse order to achieve this.
	 * 
	 * @see #partOne(int, int)
	 * 
	 */
	private void partTwo() {

		// m is initially 1
		int m = 1;

		// n is initially the factor (f) that satisfies the equation b = a*f + r
		int n = -phaseList.get(phaseList.size() - 2).f;

		// a and b are initially the final phase values.
		int a = phaseList.get(phaseList.size() - 2).b;
		int b = phaseList.get(phaseList.size() - 2).a;

		// Display the initial values of m, n, a and b.
		System.out.println("m:" + m + "\t| a:" + a + "\t| n:" + n + "\t| b:" + b + "\t| hcf:"
				+ phaseList.get(phaseList.size() - 1).a);

		// Iterate through each phase if the phase list apart from the final
		// two.
		for (int i = phaseList.size() - 3; i >= 0; i--) {

			// If the r = b then the left hand side of the equation must be
			// under evaluation. Otherwise the right.
			if (phaseList.get(i).r == b) {

				m = m + (-phaseList.get(i).f * n);
				a = phaseList.get(i).a;
				b = phaseList.get(i).b;

			} else if (phaseList.get(i).r == a) {

				n = n + (-phaseList.get(i).f * m);
				b = phaseList.get(i).a;
				a = phaseList.get(i).b;

			}

			// Output the phase values so user can follow logic.
			System.out.println("m:" + m + "\t| a:" + a + "\t| n:" + n + "\t| b:" + b + "\t| hcf:"
					+ phaseList.get(phaseList.size() - 1).a);
		}
		// Output the final result.
		System.out.println("------------------------------------------------------------------------");
		System.out.println("m = " + m + "\tn = " + n);
	}

	public static void main(String[] args) {

		// Display opening message.
		System.out.println("------------------------- Euclid's Algorithm -------------------------");

		// Create a new instance of EuclidsAlgorithm and runs it.
		EuclidsAlgorithm EA = new EuclidsAlgorithm();
		EA.run();

	}
}