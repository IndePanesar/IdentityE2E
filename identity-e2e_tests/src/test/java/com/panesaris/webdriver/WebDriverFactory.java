/**
 * Author : Inderpal Panesar
 * Date   : 03/2018
 * Description :
 *  This is the main WebDriver factory class that will setup an instance of the required webdriver.
 */

package com.panesaris.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;

public class WebDriverFactory
{
	private static WebDriver _driver;

	// Get an instance of the required driver
	public static WebDriver GetWebDriver() throws Exception
	{
		if (_driver != null) return _driver;

		try
		{
			String driverType = GetRequiredDriverType();
            if (driverType.equals("CHROME" ))
            {
                DesiredCapabilities dcChrome = DesiredCapabilities.chrome();

                // Disable default browser check
                dcChrome.setCapability("chrome.switches", "--no-default-browser-check");

                // Save password pop-up
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("profile.password_manager_enabled", "false");
                dcChrome.setCapability("chrome.prefs", map);
				System.out.println("Present Project Directory : "+
									System.getProperty("user.dir") +
									"/src/main/drivers/chromedriver/");
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/main/drivers/chromedriver/chromedriver.exe");
                _driver = new ChromeDriver(dcChrome);
            }
            else if (driverType.equals("FIREFOX"))
            {
				System.setProperty("webdriver.firefox.driver", "/src/main/drivers/firefoxdriver/geckodriver");
                _driver = new FirefoxDriver(DesiredCapabilities.firefox());
            }
            else if (driverType.equals("IE"))
            {
					DesiredCapabilities dcIE = DesiredCapabilities.internetExplorer();
					dcIE.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
					dcIE.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
					dcIE.setCapability("requireWindowFocus", true);
					_driver = new InternetExplorerDriver(dcIE);
			}
			else
            {
                // default to Firefox
				System.out.println("Stop No browser provided");
				System.exit(1);
            }
			return _driver;
		}
		catch (Exception e)
		{
			System.out.println("Failed to create instance of webdriver");
			System.out.println(e.getMessage());
			throw e;
		}
	}

	// Get the driver type to use from the POM properties, if failed then set the browser to Firefox
	private static String GetRequiredDriverType() throws Exception
	{
		try
		{
			return System.getProperty("browser").toUpperCase();
		}
		catch(Exception e)
		{
			System.out.println("Failed to get browser from POM file");
			System.out.println(e.getMessage());
			return "FIREFOX";
		}
	}

	// Quit the driver
	public static void QuitDriver()
	{
		if (_driver == null)
			return;
		_driver.quit();
		_driver = null;
	}
}
