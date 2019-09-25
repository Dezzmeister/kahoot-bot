package main;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class KahootGame implements Runnable {
	private final WebDriver driver;
	private final WebDriverWait waiter;
	private final JavascriptExecutor jsExecutor;
	private final int gameID;
	private final ProxyList proxies;
	private volatile boolean running = true;

	public KahootGame(final WebDriver _driver, final WebDriverWait _waiter, final JavascriptExecutor _jsExecutor, final int _gameID, final ProxyList _proxies) {
		driver = _driver;
		waiter = _waiter;
		jsExecutor = _jsExecutor;
		gameID = _gameID;
		proxies = _proxies;
	}
	
	@Override
	public final void run() {
		String proxy = proxies.removeLast();
		
		while (running) {
			try {
				DriverUtils.switchProxy(jsExecutor, proxy.substring(0, proxy.indexOf(":")), Integer.parseInt(proxy.substring(proxy.indexOf(":") + 1)));
				driver.get("kahoot.it");
				WebElement idField = driver.findElement(By.name("gameID"));
				idField.sendKeys(gameID + "");
								
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stop() {
		running = false;
	}	
}
