package main;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;

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
	
	public static final void switchProxy(final WebDriver driver, final JavascriptExecutor jsExecutor, final String host, final int port) {
		driver.manage().deleteAllCookies();
		driver.get("about:config");
		jsExecutor.executeScript(SWITCH_PROXY_SCRIPT, host, port);
		System.out.println("Switch proxy to " + host + ":" + port);
	}
	
	/**
	 * Returns a standard Firefox profile for use in generating accounts.
	 * 
	 * @return a Firefox profile
	 */
	public static final FirefoxProfile getStandardFirefoxProfile() {
		final FirefoxProfile profile = new FirefoxProfile();
		
		profile.setPreference("permissions.default.image", 2);
		profile.setPreference("privacy.sanitize.sanitizeOnShutdown", true);
		profile.setPreference("signon.rememberSignons", false);
		profile.setPreference("network.cookie.lifetimePolicy", 2);
		profile.setPreference("browser.aboutConfig.showWarning", false);
		profile.setPreference("webdriver.load.strategy", "unstable");
		
		return profile;
	}
	
	public static final FirefoxDriver getDriver(final String firstProxy) {
		final FirefoxProfile profile = getStandardFirefoxProfile();
		final FirefoxOptions options = new FirefoxOptions();
		
		final Proxy proxy = new Proxy().setHttpProxy(firstProxy).setSslProxy(firstProxy);
		options.setCapability(CapabilityType.PROXY, proxy);
		options.setProfile(profile);
		
		final FirefoxDriver driver =  new FirefoxDriver(options);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		
		return driver;
	}
}
