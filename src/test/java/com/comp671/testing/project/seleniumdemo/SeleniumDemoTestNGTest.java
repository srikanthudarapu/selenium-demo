package com.comp671.testing.project.seleniumdemo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class SeleniumDemoTestNGTest {

    public WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    public void setup(String browser) {
        if ("firefox".equals(browser)) {
            System.setProperty("webdriver.gecko.driver", "/Users/srikanthudarapu/selenium-binaries/geckodriver");
            driver = new FirefoxDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", "/Users/srikanthudarapu/selenium-binaries/chromedriver");
            driver = new ChromeDriver();
        }

        // Browser customization option
        driver.manage().window().maximize();

        // Open the browser with the specified url. Can be a website or a static html
        driver.get("file:///Users/srikanthudarapu/IdeaProjects/selenium-demo/src/main/resources/static/index.html");
    }

    // When the page is opened "Selenium Demo" title will be displayed
    @Test
    public void runTest_1() {
        String expected = "Selenium Demo";

        // Element selection strategy - Id
        WebElement titleElement = driver.findElement(By.id("selenium-demo-title"));

        String actual = titleElement.getText();
        Assert.assertEquals(actual, expected, "The page title does not match the requirement");
    }

    // Click on Locating elements, a section with 4 values are displayed
    @Test
    public void runTest_2() {
        // Element selection strategy - name
        WebElement locatingElements = driver.findElement(By.name("locating-elements"));
        locatingElements.click();

        WebElement list = driver.findElement(By.id("list-home"));

        // Wait 2 seconds for element to be displayed
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOf(list));

        Assert.assertTrue(list.isDisplayed(), "This list is not displayed");

        if (list.isDisplayed()) {
            // Find multiple elements
            // Element selection strategy - xpath
            List<WebElement> listValues = list.findElements(By.xpath("div/a"));

            Assert.assertTrue(listValues != null && listValues.size() == 4, "The list does not contain 4 items");
        }
    }


    // Click on "Basic features demo" will display the section
    @Test
    public void runTest_3() {
        // Element selection strategy - className
        WebElement sectionElement = driver.findElement(By.className("basic-features-demo-class"));

        // Fail the test if is already displayed
        Assert.assertFalse(sectionElement.isDisplayed(), "The section is displayed before clicking the button.");

        WebElement basicFeaturesDemoBtnElement = driver.findElement(By.name("basic-features-demo-btn"));

        // Click on button
        basicFeaturesDemoBtnElement.click();

        // Wait 3 seconds for element to be displayed
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOf(sectionElement));

        Assert.assertTrue(sectionElement.isDisplayed(), "Section is not displayed");
    }

    // Click on "Register" with all empty fields will return error
    @Test
    public void runTest_4() {
        // Element selection strategy - cssSelector
        WebElement errorElement = driver.findElement(By.cssSelector("#email-empty-alert"));

        // Fail the test if is already displayed

        Assert.assertFalse(errorElement.isDisplayed(), "The error message is displayed before clicking the button.");

        WebElement registerBtn = driver.findElement(By.id("register-btn"));

        // Wait 2 second for element to be displayed and clickable
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(registerBtn));

        // Click on button
        registerBtn.click();

        Assert.assertTrue(errorElement.isDisplayed(), "Error message is not displayed");
    }

    // Enter field values and click "Register" will display confirmation message
    @Test
    public void runTest_5() {
        WebElement email = driver.findElement(By.id("email"));
        // Enter text in Email address field - Text box
        email.sendKeys("test@gmail.com");

        // Element selection strategy - xpath
        WebElement genderMale = driver.findElement(By.xpath("//input[@value='option1']"));
        // Select a Gender - Radio button
        genderMale.click();

        // Select a Country - Dropdown
        Select country = new Select(driver.findElement(By.id("inputCountry")));
        country.selectByVisibleText("India");

        WebElement registerBtn = driver.findElement(By.id("register-btn"));

        // Click on button
        registerBtn.click();

        WebElement signUpSection = driver.findElement(By.id("sign-up-card"));
        // Fail the test if the form is still displayed
        Assert.assertFalse(signUpSection.isDisplayed(), "Sign-up form is still displayed after clicking Register");

        WebElement confirmation = driver.findElement(By.id("registration-success-alert"));
        Assert.assertTrue(confirmation.isDisplayed(), "Confirmation message is not displayed");
    }

    // Click on "Advanced features demo" will display the section
    @Test
    public void runTest_6() {
        WebElement advancedFeaturesAccordion = driver.findElement(By.id("advancedFeaturesAccordion"));
        advancedFeaturesAccordion.click();

        // Wait 2 seconds for section to be displayed
        WebDriverWait wait1 = new WebDriverWait(driver, 2);

        WebElement sectionElement = driver.findElement(By.className("advanced-features-demo-class"));

        // Fail the test if is already displayed
        Assert.assertFalse(sectionElement.isDisplayed(), "The section is displayed before clicking the button.");

        WebElement advancedFeaturesDemoBtnElement = driver.findElement(By.id("advanced-features-demo-btn"));

        // Wait 2 seconds for button to be clickable
        WebDriverWait wait2 = new WebDriverWait(driver, 2);
        wait2.until(ExpectedConditions.elementToBeClickable(advancedFeaturesDemoBtnElement));

        // Click on button
        advancedFeaturesDemoBtnElement.click();

        // Wait 2 seconds for element to be displayed
        WebDriverWait wait3 = new WebDriverWait(driver, 3);
        wait2.until(ExpectedConditions.visibilityOf(advancedFeaturesDemoBtnElement));

        Assert.assertTrue(sectionElement.isDisplayed(), "Section is not displayed");
    }


    // Click on "4 seconds" will load the section in 4 seconds - Explicit wait
    @Test
    public void runTest_7() {
        WebElement fourSecondsBtn = driver.findElement(By.id("time-delay-4sec-btn"));
        WebElement fourSecondsSection = driver.findElement(By.id("div2"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", fourSecondsBtn);

        // Fail the test if the section is displayed even before clicking the button
        Assert.assertFalse(fourSecondsSection.isDisplayed(), "The section is displayed even before clicking the button");

        fourSecondsBtn.click();

        // Explicit wait for 5 seconds
        WebDriverWait wait4Seconds = new WebDriverWait(driver, 5);
        wait4Seconds.until(ExpectedConditions.visibilityOf(fourSecondsSection));

        Assert.assertTrue(fourSecondsSection.isDisplayed(), "The section is not displayed even after 4 seconds");
    }

    // Click on "6 seconds" will load the section in 6 seconds - Fluent wait
    @Test
    public void runTest_8() {
        WebElement sixSecondsBtn = driver.findElement(By.id("time-delay-6sec-btn"));
        WebElement sixSecondsSection = driver.findElement(By.id("div3"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", sixSecondsBtn);

        // Fail the test if the section is displayed even before clicking the button
        Assert.assertFalse(sixSecondsSection.isDisplayed(), "The section is displayed even before clicking the button");

        sixSecondsBtn.click();

        // Fluent wait for 6 seconds
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(8))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);

        sixSecondsSection = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                WebElement section = driver.findElement(By.id("div3"));
                if (section.isDisplayed()) {
                    return section;
                } else {
                    return null;
                }
            }
        });

        Assert.assertTrue(sixSecondsSection != null && sixSecondsSection.isDisplayed(), "The section is not displayed even after 6 seconds");
    }

    // Click on "Add dynamic text boxes" one time will add one text box
    @Test
    public void runTest_9() {
        WebElement dynamicTextboxBtn = driver.findElement(By.id("add-dynamic-textbox-btn"));
        dynamicTextboxBtn.click();

        // Wait for 2 seconds
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement dynamicTextboxesSection = driver.findElement(By.id("dynamic-textboxes"));
        List<WebElement> dynamicTextboxes = dynamicTextboxesSection.findElements(By.xpath("div/div/input"));
        Assert.assertTrue(dynamicTextboxes.size() == 1, "Dynamic textbox is not displayed");
    }

    // Click on "Add dynamic text boxes" two more times, there will be 3 text boxes
    @Test
    @Ignore
    public void runTest_10() {
        WebElement dynamicTextboxBtn = driver.findElement(By.id("add-dynamic-textbox-btn"));

        // First click
        dynamicTextboxBtn.click();

        // Wait for 1 second
        WebDriverWait wait1 = new WebDriverWait(driver, 1);

        // Second click
        dynamicTextboxBtn.click();

        // Wait for 1 second
        WebDriverWait wait2 = new WebDriverWait(driver, 1);

        WebElement dynamicTextboxesSection = driver.findElement(By.id("dynamic-textboxes"));
        List<WebElement> dynamicTextboxes = dynamicTextboxesSection.findElements(By.xpath("div/div/input"));
        Assert.assertEquals(dynamicTextboxes.size(), 3, "3 Dynamic textboxes are not displayed");
    }

    // Click on "Alert" will display JS alert. Click on OK will display OK text
    @Test
    @Ignore
    public void runTest_11() {
        WebElement btn = driver.findElement(By.id("js-alert-btn"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", btn);

        btn.click();

        // Wait for the alert to be displayed and store it in a variable
        WebDriverWait wait = new WebDriverWait(driver, 3);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        // Store the alert text in a variable
        String text = alert.getText();
        Assert.assertEquals("This is a JS alert", text, "Alert text does not match expected value");

        // Press the OK button
        alert.accept();
        WebDriverWait wait1 = new WebDriverWait(driver, 2);

        WebElement alertResponse = driver.findElement(By.id("alert-response"));
        Assert.assertEquals("OK", alertResponse.getText(), "Alert response is not 'OK'");
    }

    // Click on "Prompt" will display JS Prompt. Enter name and click OK will display the name
    @Test
    @Ignore
    public void runTest_12() {
        WebElement btn = driver.findElement(By.id("js-prompt-btn"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", btn);

        btn.click();

        // Wait for the alert to be displayed and store it in a variable
        WebDriverWait wait = new WebDriverWait(driver, 3);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys("Group 3");

        // Press the OK button
        alert.accept();
        WebDriverWait wait1 = new WebDriverWait(driver, 2);

        WebElement promptResponse = driver.findElement(By.id("prompt-response"));
        Assert.assertEquals("Group 3", promptResponse.getText(), "Prompt response does not match 'Group 3'");
    }

    // Click on "Confirmation" will display JS Confirmation. Cancel will display the Cancel text
    @Test
    @Ignore
    public void runTest_13() {
        driver.findElement(By.id("js-confirmation-btn")).click();

        // Wait for the alert to be displayed and store it in a variable
        WebDriverWait wait = new WebDriverWait(driver, 3);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        // Store the alert text in a variable
        String text = alert.getText();
        Assert.assertNotEquals("Press a button!", text, "Confirm text does not match expected value");

        // Press the Cancel button
        alert.dismiss();
        WebDriverWait wait1 = new WebDriverWait(driver, 2);

        WebElement alertResponse = driver.findElement(By.id("confirm-response"));
        Assert.assertEquals("Cancel", alertResponse.getText());
    }

    // Click on "Upload" without choosing a file will display an error message
    @Test
    @Ignore
    public void runTest_14() {
        WebElement btn = driver.findElement(By.id("upload-btn"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", btn);

        WebElement alert = driver.findElement(By.id("file-empty-alert"));
        WebElement image = driver.findElement(By.id("image-section"));

        Assert.assertFalse(alert.isDisplayed() || image.isDisplayed(), "Alert or image is displayed even before the Upload button is clicked");

        btn.click();

        WebDriverWait wait = new WebDriverWait(driver, 2);

        Assert.assertTrue(alert.isDisplayed(), "Alert is not displayed");
    }

    // Choose "selenium_logo_large.png" and click on "Upload" will display the image
    @Test
    @Ignore
    public void runTest_15() {
        WebElement btn = driver.findElement(By.id("upload-btn"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", btn);

        WebElement file = driver.findElement(By.id("file-upload"));
        WebElement alert = driver.findElement(By.id("file-empty-alert"));
        WebElement image = driver.findElement(By.id("image-section"));

        file.sendKeys("/Users/srikanthudarapu/IdeaProjects/selenium-demo/src/main/resources/static/images/selenium_logo_large.png");
        WebDriverWait wait1 = new WebDriverWait(driver, 2);

        btn.click();

        WebDriverWait wait2 = new WebDriverWait(driver, 2);
        wait2.until(ExpectedConditions.visibilityOf(image));

        Assert.assertFalse(alert.isDisplayed(), "Alert is displayed");

        Assert.assertTrue(image.isDisplayed(), "Image is not displayed");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
