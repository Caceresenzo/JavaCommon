package caceresenzo.libs.scripts.javascript.implementations;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import caceresenzo.libs.scripts.javascript.AbstractJavaScriptExecutor;

public class MainJavaScriptExecutor extends AbstractJavaScriptExecutor {
	
	/* Variables */
	private ScriptEngine engine;
	
	/* Constructor */
	public MainJavaScriptExecutor() {
		this.engine = new ScriptEngineManager().getEngineByName("JavaScript");
	}
	
	@Override
	public Object eval(String code) throws Exception {
		return engine.eval(code);
	}
	
}