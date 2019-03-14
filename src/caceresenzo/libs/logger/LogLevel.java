package caceresenzo.libs.logger;

@Deprecated
public enum LogLevel {
	SUCCESS("SUCCESS"), //
	INFO("INFO"), //
	WARNING("WARNING"), //
	ERROR("ERROR"), //
	CRITICAL("CRITICAL"), //
	FATAL("FATAL"), //
	DEBUG("DEBUG"), //
	VOID("////////"); //
	
	private final String display;
	
	private LogLevel(String displayText) {
		this.display = displayText;
	}
	
	public String getDisplayText() {
		return display;
	}
	
}