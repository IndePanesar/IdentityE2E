package com.panesaris.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GOV_DVLA_ConfirmVehicle_Page
{
    private WebDriver _driver;

    public GOV_DVLA_ConfirmVehicle_Page(WebDriver p_Driver)
    {
        _driver = p_Driver;
        PageFactory.initElements(_driver, this);
    }

    // Page elements
    @FindBy(how = How.CSS, using = "#pr3 li.list-summary-item")
    private List<WebElement> _vehiclesummarylist;

    @FindBy(how = How.ID, using = "Correct_True")
    private WebElement _rbyes;

    @FindBy(how = How.ID, using = "Correct_False")
    private WebElement _rbno;


    @FindBy(how = How.CSS, using = "button[name=\"Continue\"]")
    private WebElement _btncontinue;

    // Page methods
    // Get the title of the page
    public String GetPageTitle()
    {
        return _driver.getTitle();
    }

    // Get HashMap of vehicle summary details
    public HashMap<String, String> GetVehicleSummaryMap()
    {
        HashMap<String, String> summaryMap = new HashMap<String, String>();
        for(WebElement liItem : _vehiclesummarylist)
        {
            String[] text = liItem.getText().split("\\s+");
            if (text[0].equals("Registration"))
                summaryMap.put(text[0], text[2] + text[3]);
            else
                summaryMap.put(text[0], text[1]);
        }

        return summaryMap;
    }

    // Click radio button Yes to confirm correct details
    public void ConfirmVehicleDetails(boolean p_YesNo)
    {
        WebElement rbElement = p_YesNo ? _rbyes : _rbno;

        rbElement.click();
    }


    // Click the Continue button with confirmed vehicle, return an instance of the GOV_DVLA_ViewVehiclDetails_Page
    public GOV_DVLA_ViewVehicleDetails_Page ClickContinueWithConfirmedVehicle() throws Exception
    {
        try
        {
            // Click StartNow button and give it time to load, 5 secs
            _btncontinue.click();
            TimeUnit.SECONDS.sleep(5);
            return new GOV_DVLA_ViewVehicleDetails_Page(_driver);
        }
        catch(Exception e)
        {
            System.out.println("Error when attempting to click Continue");
            System.out.println(e.getMessage());
            throw e;
        }
    }

    // Click the Continue button with unconfirmed vehicle, return an instance of the GOV_DVLA_VehicleQuery_Page
    public GOV_DVLA_VehicleQuery_Page ClickContinueWithUnCorfirmedVehicle() throws Exception
    {
        try
        {
            // Click Continue button and give it time to load, 5 secs
            _btncontinue.click();
            TimeUnit.SECONDS.sleep(5);
            return new GOV_DVLA_VehicleQuery_Page (_driver);
        }
        catch(Exception e)
        {
            System.out.println("Error when attempting to click Continue");
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
