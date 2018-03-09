/*
This is the main Vehicle Details test class, written as data-driven tests.
A method is declared to be the data provider and used in the test method using the dataProvider
attribute of the Test annotation.
 */
package com.panesaris.tests;

import com.panesaris.main.ProcessExcelCSVFile;
import com.panesaris.pageobjects.GOV_DVLA_ConfirmVehicle_Page;
import com.panesaris.pageobjects.GOV_DVLA_VehicleQuery_Page;
import com.panesaris.webdriver.WebDriverFactory;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class GOV_DVLA_VehicleDetailsTest
{
	private static String _browser;
	private static String _url;
	private static WebDriver _driver;
	@BeforeClass
	public static void SetUp()
	{
		try
		{
			// From the POM get the browser to run against and the base url
			 _url = System.getProperty("url");


			_driver = WebDriverFactory.GetWebDriver();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	// Data provider for the test method. Loads the configured data from the excel file located by the ScanForFilesService
	@DataProvider(name = "GOV_DVLA_VehicleData_Provider")
	public static Object[][] GOV_DVLA_VehicleData_Provider()
	{
	 
		// For the number of rows of data present in the excel, test will be executed the same no. of times
		ProcessExcelCSVFile fileService = new ProcessExcelCSVFile();
		
		Object[][] vehicleTestData = null;
		
		try
		{
			String dataDir = System.getProperty("user.dir") + "/src/main/testdata";
			vehicleTestData = fileService.ReadExcelCSVFile(dataDir, "GOV_DVLA_VehicleTestData.xlsx", "VehicleData");
		}
		catch (Exception e)
		{
			System.out.println("Failed to read Excel vehicle data file");
			System.out.println(e.getMessage());
		}
		 
        return vehicleTestData;
	}

	// Test vehicle info using the data provider
	@Test(dataProvider = "GOV_DVLA_VehicleData_Provider")
	public void AssertVehicleInfo(String p_RegistrationNum, String p_VehicleMake, String p_VehicleColour)
	{
		try
		{
			if (p_RegistrationNum == null)
				return;
			System.out.println(String.format("vehicleRegistrationNum = %s make = %s, colour = %s",
					p_RegistrationNum, p_VehicleMake, p_VehicleColour));
			_driver.navigate().to(_url);
			//Enter vehicle reg and click start button
			GOV_DVLA_VehicleQuery_Page vehicleQuery_page = new GOV_DVLA_VehicleQuery_Page(_driver);

			vehicleQuery_page.EnterVehicleRegistration(p_RegistrationNum);


			GOV_DVLA_ConfirmVehicle_Page vehicleConfirm_page = vehicleQuery_page.ClickContinueButton();
			vehicleConfirm_page.ConfirmVehicleDetails(true);
			HashMap<String, String> vehicleSummaryMap = vehicleConfirm_page.GetVehicleSummaryMap();

			// Assert the vehicle make
			Assert.assertEquals(p_VehicleMake, vehicleSummaryMap.get("Make"));

			// Assert the vehicle colour
			Assert.assertEquals(p_VehicleColour, vehicleSummaryMap.get("Colour"));
			TakeScreenShot(vehicleSummaryMap.get("Registration"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Take a picture
	private void TakeScreenShot(String p_Name) throws IOException
	{
		try
		{
			String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
			String screenshotName = "snapshot_" + p_Name + timeStamp + ".jpg";
			File screenshot = ((TakesScreenshot) _driver).getScreenshotAs(OutputType.FILE);

			String pathName = System.getProperty("user.dir") + "/src/main/screenshots/" + screenshotName;
			FileUtils.copyFile(screenshot, new File(pathName));
			System.out.println("SnapshotName -> " + screenshotName);
		}
		catch (IOException e)
		{
			System.out.println("Taking screenshot failed");
			System.out.println((e.getMessage()));
			throw e;
		}
	}

	@AfterSuite
	public void TearDown()
	{
		WebDriverFactory.QuitDriver();
	}

}
