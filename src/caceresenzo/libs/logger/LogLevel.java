package caceresenzo.libs.logger;

import java.awt.Color;

public enum LogLevel {
	SUCCESS		(Color.white, 	Color.green.darker(), "SUCCESS"),
	INFO		(Color.black, 	Color.orange.darker(), "INFO"),
	WARNING		(Color.black,	Color.pink.darker(), "WARNING"),
	ERROR		(Color.black,	Color.red, "ERROR"),
	CRITICAL	(Color.white, 	Color.red.darker(), "CRITICAL"),
	FATAL		(Color.white, 	Color.black, "FATAL"),
	DEBUG		(Color.magenta, Color.white, "DEBUG"),
	VOID		(Color.black, Color.white, "////////");
	
	public Color fontColor = Color.white;
	public Color backColor = Color.black;
	public String display = "LOG";

	private LogLevel(Color fontColor, Color backColor, String displayText) {
		this.fontColor = fontColor;
		this.backColor = backColor;
		this.display = displayText;
	}

	private LogLevel() {
		this(Color.black, Color.white, "LOG");
	}
	
	public String getDisplayText() {
		return display;
	}

}