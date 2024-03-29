package com.example.test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Testklasse, die überprüft, ob die TeamView aufgerufen werden kann
 * @author David Bistron
 *
 */
public class TestCorrectTeamViewPage {
 
	WebDriver driver;

	/**
	 * Parameter, die vor Ausführung des Tests initiiert werden müssen
	 */
	@Before
	public void buildBrowser() {
		System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		//firefoxOptions.addArguments("--headless");
		driver = new FirefoxDriver(firefoxOptions);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	/**
	 * Ausführung des eigentlichen Tests
	 */
	@Test
	public void test() {
		driver.get("http://127.0.0.1:8080/");
		WebElement tfUser = driver.findElement(By.cssSelector("#vaadinLoginUsername > input[type=text]"));
		WebElement tfPassword = driver.findElement(By.cssSelector("#vaadinLoginPassword > input[type=password]"));
		tfUser.sendKeys("1");
		tfPassword.sendKeys("password");
		tfPassword.sendKeys(Keys.RETURN);
		WebElement btnClientmanagement = driver.findElement(By.cssSelector("body > vaadin-vertical-layout > vaadin-vertical-layout > vaadin-vertical-layout > vaadin-button.btnTeammanagement"));
		btnClientmanagement.click();
		WebElement btnCreateClient = driver.findElement(By.cssSelector(".btnCreateTeam"));
		assertEquals("Neues Team anlegen", btnCreateClient.getText());
	}
	
	@After
	public void closeBrowser() {
		//driver.close();
	}
}

	

