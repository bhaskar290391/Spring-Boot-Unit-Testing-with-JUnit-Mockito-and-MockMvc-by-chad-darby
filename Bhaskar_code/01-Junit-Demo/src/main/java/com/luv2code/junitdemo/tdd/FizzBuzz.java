package com.luv2code.junitdemo.tdd;

public class FizzBuzz {

	public static String compute(int numbers) {

		if ((numbers % 3 == 0) && (numbers % 5 == 0)) {
			return "FizzBuzz";
		} else if (numbers % 3 == 0) {
			return "Fizz";
		} else if (numbers % 5 == 0) {
			return "Buzz";
		} else {
			return String.valueOf(numbers);
		}

	}

	public static String computes(int numbers) {

		StringBuilder br = new StringBuilder();

		if (numbers % 3 == 0) {
			br.append("Fizz");
		}

		if (numbers % 5 == 0) {
			br.append("Buzz");
		}

		if (!((numbers % 3 == 0) || (numbers % 5 == 0)))
			br.append(numbers);

		return br.toString();
	}

}
