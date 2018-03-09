package com.panesaris.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class GOV_DVLA_VehicleQuery_Page
{
	private WebDriver _driver;

	public GOV_DVLA_VehicleQuery_Page(WebDriver p_Driver)
	{
		_driver = p_Driver;
		PageFactory.initElements(_driver, this);
	}

	// Page elements
	@FindBy(how = How.ID, using = "Vrm")
	private WebElement _inputvehiclereg;
	
	@FindBy(how = How.CSS, using = "button[name=\"Continue\"]")
	private WebElement _btncontinue;
	
	// Page methods
	// Get the title of the page
	public String GetPageTitle()
	{
		return _driver.getTitle();
	}

	public void EnterVehicleRegistration(String p_VRN)
	{
		_inputvehiclereg.clear();
		_inputvehiclereg.sendKeys(p_VRN);
	}

	// Click the Continue button and return an instance of the GOV_DVLA_ConfirmVehicle_Page
	public GOV_DVLA_ConfirmVehicle_Page ClickContinueButton() throws Exception
	{
		try
		{
			// Click StartNow button and give it time to load, 5 secs
			_btncontinue.click();
			TimeUnit.SECONDS.sleep(5);
			return new GOV_DVLA_ConfirmVehicle_Page(_driver);
		}
		catch(Exception e)
		{
			System.out.println("Error when attempting to click Continue");
			System.out.println(e.getMessage());
			throw e;
		}
	}
}
