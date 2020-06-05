package com.example.test;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * 
 * @author Sebastian Siegmann
 *
 */
public class TestCorrectStartPage {

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
		WebElement name = driver
				.findElement(By.xpath("/html/body/vaadin-vertical-layout/vaadin-vertical-layout/vaadin-login-form"));
		assertEquals("http://127.0.0.1:8080/", driver.getCurrentUrl());
		assertEquals("vaadin-login-form", name.getTagName());
	}

	@After
	public void killBrowser() {
		driver.close();
	}
}
