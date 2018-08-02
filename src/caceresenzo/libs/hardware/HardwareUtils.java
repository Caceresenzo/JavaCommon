package caceresenzo.libs.hardware;

public class HardwareUtils {

	static int megaByte = 1024 * 1024;

	/**
	 * Returns the number of physical CPU cores available for the JVM.
	 * For example, a typical Intel i7 processor will report 4 cores.
	 */
	public static int availableProcessors() {
		return Runtime.getRuntime().availableProcessors();
	}

	public static long maxMemory() {
		return Runtime.getRuntime().maxMemory();
	}
	
	public static long maxMemoryMegaOctes() {
		return Runtime.getRuntime().maxMemory() / megaByte;
	}

	public static long totalMemory() {
		return Runtime.getRuntime().totalMemory();
	}
	
	public static long totalMemoryMegaOctes() {
		return Runtime.getRuntime().totalMemory() / megaByte;
	}

	public static long freeMemory() {
		return Runtime.getRuntime().freeMemory();
	}
	
	public static long freeMemoryMegaOctes() {
		return Runtime.getRuntime().freeMemory() / megaByte;
	}

	public static long usedMemory() {
		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}
	
	public static long usedMemoryMegaOctes() {
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / megaByte;
	}
	
	public static void hintGarbageCollector() {
		System.gc();
		Runtime.getRuntime().gc();
	}

}