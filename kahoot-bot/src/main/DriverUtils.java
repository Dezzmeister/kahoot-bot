package main;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;

public final class DriverUtils {
	private static final String SWITCH_PROXY_SCRIPT;
	
	static {
		String temp = "";
		
		try {
			final List<String> lines = Files.readAllLines(Paths.get("scripts/newProxy.js"));
			
			for (String line : lines) {
				temp += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SWITCH_PROXY_SCRIPT = temp;
	}
	
	public static final void switchProxy(final JavascriptExecutor jsExecutor, final String host, final int port) {
		jsExecutor.executeScript(SWITCH_PROXY_SCRIPT, host, port);
	}
}
