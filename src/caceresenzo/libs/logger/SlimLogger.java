package caceresenzo.libs.logger;

public class SlimLogger extends AbstractLogger {
	
	private static SlimLogger LOGGER = new SlimLogger();
	
	private boolean noLine = false;
	private String lastLine = "";
	
	@Override
	protected void log(LogLevel level, Object format, Object... args) {
		lastLine = (args == null || args.length == 0 ? String.valueOf(format) : String.format(String.valueOf(format), args));
		if (noLine) {
			System.out.print(lastLine);
			noLine = false;
		} else {
			System.out.println(lastLine);
		}
	}
	
	public SlimLogger noLine() {
		noLine = true;
		return LOGGER;
	}
	
	public void clearLastLine() {
		if (lastLine == "") {
			return;
		}
		String backspace = "";
		for (int i = 0; i < lastLine.length(); i++) {
			backspace += "\b";
		}
		System.out.print(backspace);
		lastLine = "";
	}
	
	public static SlimLogger getLogger() {
		return LOGGER;
	}
	
}