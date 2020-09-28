package com.comp671.testing.project.seleniumdemo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StepDefs {
    WebDriver driver;

    @Given("User opens Selenium Demo")
    public void user_opens_selenium_demo() {
        System.setProperty("webdriver.gecko.driver", "/Users/srikanthudarapu/selenium-binaries/geckodriver");
        driver = new FirefoxDriver();
        // Browser customization option
        driver.manage().window().maximize();

        // Open the browser with the specified url. Can be a website or a static html
        driver.get("file:///Users/srikanthudarapu/IdeaProjects/selenium-demo/src/main/resources/static/index.html");
    }

    @When("User is on the Home page")
    public void user_is_on_the_home_page() {
        WebElement titleElement = driver.findElement(By.id("selenium-demo-title"));
        Assert.assertTrue(titleElement.isDisplayed());
    }

    @Then("Selenium Demo is displayed as title")
    public void selenium_demo_is_displayed_as_title() {
        WebElement titleElement = driver.findElement(By.id("selenium-demo-title"));
        Assert.assertTrue(titleElement.isDisplayed());
        Assert.assertEquals(titleElement.getText(), "Selenium Demo");
        driver.quit();
    }

    @When("User clicks on Basic features demo button")
    public void user_clicks_on_basic_features_demo_button() {
        WebElement basicFeaturesDemoBtnElement = driver.findElement(By.name("basic-features-demo-btn"));
        WebElement sectionElement = driver.findElement(By.className("basic-features-demo-class"));
        // Click on button
        basicFeaturesDemoBtnElement.click();

        // Wait 3 seconds for element to be displayed
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOf(sectionElement));
    }

    @Then("Basic features demo section is displayed")
    public void basic_features_demo_section_is_displayed() {
        WebElement sectionElement = driver.findElement(By.className("basic-features-demo-class"));
        Assert.assertTrue(sectionElement.isDisplayed());
        driver.quit();
    }
}