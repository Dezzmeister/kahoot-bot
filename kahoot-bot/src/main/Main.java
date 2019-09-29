package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Main {
	
	public static void main(String[] args) throws IOException {
		System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
		
		final String proxyFile = args[0];
		final String nameFile = args[1];
		final int gameID = Integer.parseInt(args[2]);
		final boolean useProxies = !proxyFile.equals("-noproxies");
		
		final boolean headless = (args.length >= 4 && args[3].equals("-headless"));
		
		System.out.println("Adding names from " + nameFile + " to Kahoot game ID " + gameID);
		if (useProxies) {
			System.out.println("Using proxies from proxy file " + proxyFile);
		} else {
			System.out.println("Not using proxies");
		}
		
		final List<String> names = Files.readAllLines(Paths.get(nameFile));
		
		final ExecutorService executor = Executors.newFixedThreadPool(names.size());
		final KahootGame[] players = new KahootGame[names.size()];
		
		final ProxyList proxies = useProxies ? new ProxyList(proxyFile) : null;
		
		for (int i = 0; i < players.length; i++) {
			final WebDriver driver = DriverUtils.getDriver(useProxies ? proxies.removeLast() : null, headless);
			if (useProxies) proxies.save();
			
			final JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			players[i] = new KahootGame(driver, jsExecutor, gameID, names.get(i), proxies, i);
			executor.execute(players[i]);
		}
		
		boolean playersDone = false;
		mainLoop: while (!playersDone) {
			playersDone = true;
			for (int i = 0; i < players.length; i++) {
				if (players[i].isRunning()) {
					playersDone = false;
					continue mainLoop;
				}
			}
		}
		System.out.println("Done");
	}	
}
