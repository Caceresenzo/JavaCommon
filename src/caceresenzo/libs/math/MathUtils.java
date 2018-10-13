package caceresenzo.libs.math;

public class MathUtils {
	
	public static double round(double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}
	
	public static float pourcent(int value) {
		return pourcent((long) value, 100);
	}
	
	public static float pourcent(long value) {
		return pourcent(value, 100);
	}
	
	public static float pourcent(int value, int max) {
		return pourcent((long) value, (long) max);
	}
	
	public static float pourcent(long value, long max) {
		return (value * 100.0f) / max;
	}
	
}