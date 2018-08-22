package caceresenzo.libs.math.expression;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JSExpression {
	
	private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
	private static ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
	
	public static Object parse(String expression) throws ScriptException {
		return scriptEngine.eval(expression);
	}
	
}