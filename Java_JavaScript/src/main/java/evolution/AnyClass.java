package evolution;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class AnyClass {
	public static void main(String[] args) throws ScriptException, FileNotFoundException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("nashorn");
		Object numericResult = engine.eval("1 + 2");
		System.out.println(numericResult);
		engine.eval("print('Hello World')");
		engine.eval(new InputStreamReader(AnyClass.class.getClassLoader().getResourceAsStream("evolution.js")));// Put it under src/main/resources rather than src/test/resources
	} 
}
