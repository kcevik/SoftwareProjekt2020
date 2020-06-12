package com.example.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import junit.framework.Assert;
/**
 * 
 * @author LucasEickmann
 *
 */
public class TestAddProjectWithInvalidName {

	WebDriver driver;

	@Before
	public void buildBrowser() {
		System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
		FirefoxOptions firefoxOptions = new FirefoxOptions();
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
		WebElement btnProjectmanagement = driver.findElement(By.cssSelector("body > vaadin-vertical-layout > vaadin-vertical-layout > vaadin-vertical-layout > vaadin-button.btnProjectmanagement"));
		btnProjectmanagement.click();
		WebElement btnCreateClient = driver.findElement(By.cssSelector(".btnCreateProject"));
		btnCreateClient.click();
		WebElement tfProjectName = driver.findElement(By.cssSelector("body > vaadin-vertical-layout > vaadin-vertical-layout > div > vaadin-form-layout > vaadin-text-field.tfProjectName"));
		//tfProjectNameById.sendKeys("VielzulangerNamefuerdasprojekt");
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("document.getElementById('tfProjectName').value='VielzulangerNamefuerdasprojekt';");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tfProjectName.sendKeys(Keys.RETURN);
		
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertTrue("Text not found!", bodyText.contains("Größe muss zwischen 1 und 20 sein"));
	}
	
	@After
	public void closeBrowser() {
		driver.close();
	}
}
