package com.luv2code.junitdemo.tdd;



public class FizzBuzz {

	public static String compute(int numbers) {
		
		if(numbers % 3 ==0) {
			return "Fizz";
		}
		
		if(numbers % 5 ==0) {
			return "Buzz";
		}
		return null;
	}

}
