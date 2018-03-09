package com.panesaris.pageobjects;

import com.google.common.base.Joiner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class GOV_DVLA_ViewVehicleDetails_Page
{
	private WebDriver _driver;

	public GOV_DVLA_ViewVehicleDetails_Page(WebDriver p_Driver)
	{
		_driver = p_Driver;
		PageFactory.initElements(_driver, this);
	}

	// Page elements
	@FindBy(how = How.CSS, using = "div.isValidMot")
	private List<WebElement> _validmottax;

	@FindBy(how = How.CSS, using = "#pr3 li.list-summary_item")
	private List<WebElement> _detailsummaylist;

	// Page methods
	// Get the vehicle mot and tax details as a comma separated string
	public String GetVehicleMotTax()
	{
		ArrayList<String> mottax = new ArrayList<String>();

		for(WebElement webele : _validmottax)
		{
			mottax.add(webele.getText());
		}

		return Joiner.on(',').join(mottax);
	}

	// Get the vehicle details as a comma separated string
	public String GetVehicleSummaryList()
	{
		ArrayList<String> vehicleDetails = new ArrayList<String>();

		for(WebElement webele : _detailsummaylist)
		{
			vehicleDetails.add(webele.getText());
		}

		return Joiner.on(',').join(vehicleDetails);
	}
}
