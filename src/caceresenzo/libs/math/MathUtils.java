package caceresenzo.libs.math;

public class MathUtils {
	
	public static double round(double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}
	
	public static float pourcent(int value) {
		return pourcent(value, 100);
	}
	
	public static float pourcent(int value, int max) {
		return (value * 100.0f) / max;
	}
	
}