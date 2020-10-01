package com.comp671.testing.project.seleniumdemo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.tools.ant.util.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StepDefs {
    WebDriver driver;

    @Given("User opens Selenium Demo")
    public void user_opens_selenium_demo() {
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        System.setProperty("webdriver.gecko.driver", "/Users/srikanthudarapu/selenium-binaries/geckodriver");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        driver = new FirefoxDriver(firefoxOptions);

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
    public void user_clicks_on_basic_features_demo_button() throws IOException {
        WebElement basicFeaturesDemoBtnElement = driver.findElement(By.name("basic-features-demo-btn"));
        WebElement sectionElement = driver.findElement(By.className("basic-features-demo-class"));
        // Click on button
        basicFeaturesDemoBtnElement.click();

        // Wait 3 seconds for element to be displayed
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOf(sectionElement));
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        File dest = new File("/Users/srikanthudarapu/IdeaProjects/selenium-demo/screenshots/" +System.currentTimeMillis()+".png");
        Files.copy(src.toPath(), dest.toPath());
    }

    @Then("Basic features demo section is displayed")
    public void basic_features_demo_section_is_displayed() {
        WebElement sectionElement = driver.findElement(By.className("basic-features-demo-class"));
        Assert.assertTrue(sectionElement.isDisplayed());
        driver.quit();
    }
}