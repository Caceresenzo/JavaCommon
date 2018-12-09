package caceresenzo.libs.scripts.javascript;

import caceresenzo.libs.scripts.javascript.implementations.MainJavaScriptExecutor;

public abstract class AbstractJavaScriptExecutor {
	
	/* Static */
	private static AbstractJavaScriptExecutor EXECUTOR;
	
	/**
	 * Executing code when javascript execution is needed.
	 * 
	 * @param code
	 *            Target code.
	 * @return Javascript response.
	 * @throws Exception
	 *             If an exception append
	 */
	public abstract Object eval(String code) throws Exception;
	
	/**
	 * Set default executor.
	 * 
	 * @param executor
	 *            New default exetutor.
	 */
	public static void set(AbstractJavaScriptExecutor executor) {
		EXECUTOR = executor;
	}
	
	/**
	 * Get executor, and create one if is has been already set. (default is {@link MainJavaScriptExecutor})
	 * 
	 * @return Stored executor
	 */
	public static AbstractJavaScriptExecutor get() {
		if (EXECUTOR == null) {
			EXECUTOR = new MainJavaScriptExecutor();
		}
		
		return EXECUTOR;
	}
	
}