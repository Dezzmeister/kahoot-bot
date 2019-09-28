package main;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public final class KahootGame implements Runnable {
	private final WebDriver driver;
	private final JavascriptExecutor jsExecutor;
	private final int gameID;
	private final String name;
	private final ProxyList proxies;
	private volatile boolean running = true;
	
	
	/**
	 * Test login:
	 * <br> Username: aaaaaaa69
	 * <br> Email: aaaaaaaa@sharklasers.com
	 * <br> Password: anusblaster69
	 * <br>
	 * 
	 * @param _driver
	 * @param _jsExecutor
	 * @param _gameID
	 * @param _name
	 * @param _proxies
	 */
	public KahootGame(final WebDriver _driver, final JavascriptExecutor _jsExecutor, final int _gameID, final String _name, final ProxyList _proxies) {
		driver = _driver;
		jsExecutor = _jsExecutor;
		gameID = _gameID;
		name = _name;
		proxies = _proxies;
	}
	
	@Override
	public final void run() {		
		while (running) {			
			try {
				String proxy = proxies.removeLast();
				proxies.save();
				
				DriverUtils.switchProxy(driver, jsExecutor, proxy.substring(0, proxy.indexOf(":")), Integer.parseInt(proxy.substring(proxy.indexOf(":") + 1)));
				driver.get("https://kahoot.it/");
				WebElement idField = driver.findElement(By.name("gameId"));
				idField.sendKeys(gameID + "");
				
				WebElement gameIDButton = driver.findElement(By.cssSelector("button[data-functional-selector=join-game-pin]"));
				gameIDButton.click();
				
				WebElement nameField = ExpectedConditions.presenceOfElementLocated(By.id("nickname")).apply(driver);
				nameField.sendKeys(name);
				
				WebElement submitName = driver.findElement(By.cssSelector("button[data-functional-selector=join-button-username]"));
				submitName.click();
				
				stop();
				return;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public final boolean isRunning() {
		return running;
	}
	
	public final void stop() {
		running = false;
	}	
}
