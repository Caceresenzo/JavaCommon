package caceresenzo.libs.string;

public class SimpleLineStringBuilder {
	
	private String string, newLineChar;
	
	public SimpleLineStringBuilder() {
		this("", "\n");
	}
	
	public SimpleLineStringBuilder(String start) {
		this(start, "\n");
	}
	
	public SimpleLineStringBuilder(String start, String newLineChar) {
		this.string = start;
		this.newLineChar = newLineChar;
	}
	
	public SimpleLineStringBuilder append(Object value) {
		this.string += String.valueOf(value);
		
		return this;
	}
	
	public SimpleLineStringBuilder appendln(Object value) {
		return append(String.valueOf(value) + newLineChar);
	}
	
	public void appendln() {
		appendln("");
	}
	
	public void clear() {
		this.string = "";
	}
	
	public String toString() {
		return string;
	}
	
	public void setNewLineChar(String newLine) {
		this.newLineChar = newLine;
	}
	
}