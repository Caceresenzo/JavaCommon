package caceresenzo.libs.color;

import java.awt.Color;

public class ColorHelper {
	
	public static String rgb(final Color color) {
		String hex = color.toString().replace("0x", "");
		String hexRed = hex.substring(0, 2).toUpperCase();
		String hexGreen = hex.substring(2, 4).toUpperCase();
		String hexBlue = hex.substring(4, 6).toUpperCase();
		
		String intRed = Integer.toString(Integer.parseInt(hexRed, 16));
		String intGreen = Integer.toString(Integer.parseInt(hexGreen, 16));
		String intBlue = Integer.toString(Integer.parseInt(hexBlue, 16));
		
		return "rgb(" + intRed + ", " + intGreen + ", " + intBlue + ")";
	}
	
	public static String web(final Color color) {
		return color.toString().replace("0x", "#").substring(0, 7);
	}
	
}