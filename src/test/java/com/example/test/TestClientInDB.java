package com.example.test;

import static org.junit.Assert.assertTrue;

import java.util.List;
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
import org.openqa.selenium.interactions.SendKeysAction;

import com.vaadin.flow.component.textfield.testbench.TextFieldElement;

import de.fhbielefeld.pmt.DatabaseManagement.DatabaseService;
import de.fhbielefeld.pmt.JPAEntities.Client;

/**
 * 
 * @author Sebastian Siegmann
 *
 */
public class TestClientInDB {

	WebDriver driver;

	@Before
	public void buildBrowser() {
		System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		//firefoxOptions.addArguments("--headless");
		driver = new FirefoxDriver(firefoxOptions);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	
	@Test
	public void test() {
		driver.get("http://127.0.0.1:8080/");
		WebElement tfUser = driver.findElement(By.cssSelector("#vaadinLoginUsername > input[type=text]"));
		WebElement tfPassword = driver.findElement(By.cssSelector("#vaadinLoginPassword > input[type=password]"));
		tfUser.sendKeys("21");
		tfPassword.sendKeys("password");
		tfPassword.sendKeys(Keys.RETURN);
		WebElement btnClientmanagement = driver.findElement(By.cssSelector("body > vaadin-vertical-layout > vaadin-vertical-layout > vaadin-vertical-layout > vaadin-button.btnClientmanagement"));
		btnClientmanagement.click();
		WebElement btnCreateClient = driver.findElement(By.cssSelector(".btnCreateClient"));
		btnCreateClient.click();
		WebElement tfName = driver.findElement(By.cssSelector(".tfName"));
		WebElement tfTelephonenumber = driver.findElement(By.cssSelector(".tfTelephonenumber"));
		WebElement tfStreet = driver.findElement(By.cssSelector(".tfStreet"));
		WebElement tfHouseNumber = driver.findElement(By.cssSelector(".tfHouseNumber"));
		WebElement tfZipCode = driver.findElement(By.cssSelector(".tfZipCode"));
		WebElement tfTown = driver.findElement(By.cssSelector(".tfTown"));
		WebElement btnSave = driver.findElement(By.cssSelector(".btnSave"));
		tfName.click();
		tfName.sendKeys("JUnitTestKunde");
		tfTelephonenumber.click();
		tfTelephonenumber.sendKeys("0123456789");
		tfStreet.click();
		tfStreet.sendKeys("JUnitTestKunde");
		tfHouseNumber.click();
		tfHouseNumber.sendKeys("1234");
		tfZipCode.click();
		tfZipCode.sendKeys("1234");
		tfTown.click();
		tfTown.sendKeys("JUnitTestKunde");
		btnSave.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		List<Client> allClients = DatabaseService.DatabaseServiceGetInstance().readClient();
//		boolean comparative = false;
//		for(Client c : allClients) {
//			if(c.getName() == "JUnitTestKunde") {
//				comparative = true;
//			}
//		}
//		assertTrue(comparative);
		
	}
	
	@After
	public void killBrowser() {
		//driver.close();
	}
}
