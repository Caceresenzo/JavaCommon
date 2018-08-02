package caceresenzo.libs.common.test;

import caceresenzo.libs.timer.InstructionTimer;

public class TimerTest {
	
	public static void main(String[] args) {
		InstructionTimer timer = new InstructionTimer();
		
		for (int i = 0; i < 100000000; i++) {
			double a = 998.43678;
			double b = Math.sqrt(a);
			b = a * b / b * a / b * a;
		}
		
		System.out.println(timer);
	}
	
}