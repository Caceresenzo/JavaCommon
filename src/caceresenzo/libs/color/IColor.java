package caceresenzo.libs.color;

import java.awt.Color;

public interface IColor {
	
	/**
	 * Returns the corresponding AWT color.
	 * 
	 * @return the corresponding AWT color.
	 */
	public Color get();
	
	/**
	 * Returns a String expression from the color with the format: rgb(12, 121, 15)
	 * 
	 * @return the String expression.
	 */
	public String rgb();
	
	/**
	 * Returns a String expression from the color with the format: #AB12CD
	 * 
	 * @return the String expression.
	 */
	public String web();
	
}