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
 * 
 * @author Sebastian Siegmann
 *
 */
public class TestLoggedOut {

	WebDriver driver;

	@Before
	public void buildBrowser() {
		System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.addArguments("--headless");
		driver = new FirefoxDriver(firefoxOptions);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void test() {
		driver.get("http://127.0.0.1:8080/");
		WebElement tfUser = driver.findElement(By.cssSelector("#vaadinLoginUsername > input[type=text]"));
		WebElement tfPassword = driver.findElement(By.cssSelector("#vaadinLoginPassword > input[type=password]"));
		tfUser.sendKeys("1");
		tfPassword.sendKeys("password");
		tfPassword.sendKeys(Keys.RETURN);
		WebElement btnClientmanagement = driver.findElement(By.cssSelector(
				"body > vaadin-vertical-layout > vaadin-vertical-layout > vaadin-vertical-layout > vaadin-button.btnClientmanagement"));
		btnClientmanagement.click();
		WebElement btnLogOut = driver.findElement(By.cssSelector(".btnTopBar"));
		btnLogOut.click();
		WebElement name = driver
				.findElement(By.xpath("/html/body/vaadin-vertical-layout/vaadin-vertical-layout/vaadin-login-form"));
		assertEquals("http://127.0.0.1:8080/", driver.getCurrentUrl());
		assertEquals("vaadin-login-form", name.getTagName());
		driver.get("http://127.0.0.1:8080/clientmanagement");
		WebElement lblBackToLogin = driver.findElement(By.cssSelector(".lblBackToLogin"));
		assertEquals("Bitte melden Sie sich an:", lblBackToLogin.getText());

	}

	@After
	public void killBrowser() {
		driver.close();
	}
}
