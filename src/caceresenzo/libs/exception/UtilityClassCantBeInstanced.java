package caceresenzo.libs.exception;

public class UtilityClassCantBeInstanced extends RuntimeException {
	
	public UtilityClassCantBeInstanced() {
		super("This utility class can't be instanced");
	}
	
}