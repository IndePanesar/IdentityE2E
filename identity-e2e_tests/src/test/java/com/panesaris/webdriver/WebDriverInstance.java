/**
 * Author : Inderpal Panesar
 * Date   : 03/2018
 * Description :
 *  This is the main WebDriver instance class, will get a single instance of the driver
 */

package com.panesaris.webdriver;

import org.openqa.selenium.WebDriver;

public final class WebDriverInstance
{
	private static WebDriver _driver;

	public static WebDriver Driver() throws Throwable
	{
		if (_driver == null)
		{
			try
			{
				_driver = WebDriverFactory.GetWebDriver();
			}
			catch (Exception e)
			{
				System.out.println("Failed to create instance of webdriver");
				System.out.println(e.getMessage());
				throw e;
			}
		}

		return _driver;
	}

	public static void QuitDriver()
	{
		if (_driver == null) return;
		WebDriverFactory.QuitDriver();
	}
}
