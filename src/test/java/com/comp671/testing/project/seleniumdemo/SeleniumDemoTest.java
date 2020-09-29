package com.comp671.testing.project.seleniumdemo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class SeleniumDemoTest {
    public static void main(String[] args) {

        // Pass browser name as program argument. If nothing is passed default to chrome
        String browser = args.length > 0 ? args[0] : "chrome";

        WebDriver driver = loadDriverForBrowser(browser);

        // Browser customization option
        driver.manage().window().maximize();

        // Open the browser with the specified url. Can be a website or a static html
        driver.get("http://localhost:8080");

        runTest_1(driver);
        runTest_2(driver);
        runTest_3(driver);
        runTest_4(driver);
        runTest_5(driver);
        runTest_6(driver);
        runTest_7(driver);
        runTest_8(driver);
        runTest_9(driver);
        runTest_10(driver);
        runTest_11(driver);
        runTest_12(driver);
        runTest_13(driver);
        runTest_14(driver);
        runTest_15(driver);

        // quit() - closes all the browser windows and terminates the WebDriver session. No memory leaks.
        // close() - closes browser window that is in focus.
        driver.quit();
    }

    private static WebDriver loadDriverForBrowser(String browser) {
        if ("firefox".equals(browser)) {
            System.setProperty("webdriver.gecko.driver", "/Users/srikanthudarapu/selenium-binaries/geckodriver");
            return new FirefoxDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", "/Users/srikanthudarapu/selenium-binaries/chromedriver");
            return new ChromeDriver();
        }
    }

    // When the page is opened "Selenium Demo" title will be displayed
    private static void runTest_1(WebDriver driver) {
        boolean pass = false;
        String reason = null;
        String expected = "Selenium Demo";

        // Element selection strategy - Id
        WebElement titleElement = driver.findElement(By.id("selenium-demo-title"));

        String actual = titleElement.getText();
        if (actual.equals(expected)) {
            pass = true;
        } else {
            pass = false;
            reason = "The page title does not match " + expected + ". Actual value is " + actual;
        }

        printStatus(1, pass, reason);
    }

    // Click on Locating elements, a section with 4 values are displayed
    private static void runTest_2(WebDriver driver) {
        boolean pass = false;
        String reason = null;

        // Element selection strategy - name
        WebElement locatingElements = driver.findElement(By.name("locating-elements"));
        locatingElements.click();

        WebElement list = driver.findElement(By.id("list-home"));

        // Wait 2 seconds for element to be displayed
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOf(list));

        if (!list.isDisplayed()) {
            pass = false;
            reason = "The list is not displayed";
            return;
        } else {
            // Find multiple elements
            // Element selection strategy - xpath
            List<WebElement> listValues = list.findElements(By.xpath("div/a"));
            if (listValues != null && listValues.size() == 4) {
                pass = true;
            } else {
                pass = false;
                reason = "4 values are not listed";
                return;
            }
        }

        printStatus(2, pass, reason);
    }


    // Click on "Basic features demo" will display the section
    private static void runTest_3(WebDriver driver) {
        boolean pass = false;
        String reason = null;

        // Element selection strategy - className
        WebElement sectionElement = driver.findElement(By.className("basic-features-demo-class"));

        // Fail the test if is already displayed
        if (sectionElement.isDisplayed()) {
            reason = "The section is displayed before clicking the button.";
            printStatus(3, pass, reason);
            return;
        }

        WebElement basicFeaturesDemoBtnElement = driver.findElement(By.name("basic-features-demo-btn"));

        // Click on button
        basicFeaturesDemoBtnElement.click();

        // Wait 3 seconds for element to be displayed
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOf(sectionElement));

        if (sectionElement.isDisplayed()) {
            pass = true;
        } else {
            pass = false;
            reason = "Section is not displayed";
        }

        printStatus(3, pass, reason);
    }

    // Click on "Register" with all empty fields will return error
    private static void runTest_4(WebDriver driver) {
        boolean pass = false;
        String reason = null;

        // Element selection strategy - cssSelector
        WebElement errorElement = driver.findElement(By.cssSelector("#email-empty-alert"));

        // Fail the test if is already displayed
        if (errorElement.isDisplayed()) {
            reason = "The error message is displayed before clicking the button.";
            printStatus(4, pass, reason);
            return;
        }

        WebElement registerBtn = driver.findElement(By.id("register-btn"));

        // Wait 2 second for element to be displayed and clickable
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(registerBtn));

        // Click on button
        registerBtn.click();

        if (errorElement.isDisplayed()) {
            pass = true;
        } else {
            pass = false;
            reason = "Error message is not displayed";
        }

        printStatus(4, pass, reason);
    }

    // Enter field values and click "Register" will display confirmation message
    private static void runTest_5(WebDriver driver) {
        boolean pass = false;
        String reason = null;

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
        if (signUpSection.isDisplayed()) {
            pass = false;
            reason = "Sign-up form is still displayed after clicking Register";
            printStatus(5, pass, reason);
            return;
        }

        WebElement confirmation = driver.findElement(By.id("registration-success-alert"));
        if (confirmation.isDisplayed()) {
            pass = true;
        } else {
            pass = false;
            reason = "Confirmation message is not displayed";
        }

        printStatus(5, pass, reason);
    }

    // Click on "Advanced features demo" will display the section
    private static void runTest_6(WebDriver driver) {
        boolean pass = false;
        String reason = null;

        WebElement advancedFeaturesAccordion = driver.findElement(By.id("advancedFeaturesAccordion"));
        advancedFeaturesAccordion.click();

        // Wait 2 seconds for section to be displayed
        WebDriverWait wait1 = new WebDriverWait(driver, 2);

        WebElement sectionElement = driver.findElement(By.className("advanced-features-demo-class"));

        // Fail the test if is already displayed
        if (sectionElement.isDisplayed()) {
            reason = "The section is displayed before clicking the button.";
            printStatus(6, pass, reason);
            return;
        }

        WebElement advancedFeaturesDemoBtnElement = driver.findElement(By.id("advanced-features-demo-btn"));

        // Wait 2 seconds for button to be clickable
        WebDriverWait wait2 = new WebDriverWait(driver, 2);
        wait2.until(ExpectedConditions.elementToBeClickable(advancedFeaturesDemoBtnElement));

        // Click on button
        advancedFeaturesDemoBtnElement.click();

        // Wait 2 seconds for element to be displayed
        WebDriverWait wait3 = new WebDriverWait(driver, 2);
        wait2.until(ExpectedConditions.visibilityOf(advancedFeaturesDemoBtnElement));

        if (sectionElement.isDisplayed()) {
            pass = true;
        } else {
            pass = false;
            reason = "Section is not displayed";
        }

        printStatus(6, pass, reason);
    }


    // Click on "4 seconds" will load the section in 4 seconds - Explicit wait
    private static void runTest_7(WebDriver driver) {
        boolean pass = false;
        String reason = null;

        WebElement fourSecondsBtn = driver.findElement(By.id("time-delay-4sec-btn"));
        WebElement fourSecondsSection = driver.findElement(By.id("div2"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", fourSecondsBtn);

        // Fail the test if the section is displayed even before clicking the button
        if (fourSecondsSection.isDisplayed()) {
            pass = false;
            reason = "The section is displayed even before clicking the button";
            return;
        }

        fourSecondsBtn.click();

        // Explicit wait for 5 seconds
        WebDriverWait wait4Seconds = new WebDriverWait(driver, 5);
        wait4Seconds.until(ExpectedConditions.visibilityOf(fourSecondsSection));

        if (fourSecondsSection.isDisplayed()) {
            pass = true;
        } else {
            pass = false;
            reason = "The section is not displayed even after 4 seconds";
        }

        printStatus(7, pass, reason);
    }

    // Click on "6 seconds" will load the section in 6 seconds - Fluent wait
    private static void runTest_8(WebDriver driver) {
        boolean pass = false;
        String reason = null;

        WebElement sixSecondsBtn = driver.findElement(By.id("time-delay-6sec-btn"));
        WebElement sixSecondsSection = driver.findElement(By.id("div3"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", sixSecondsBtn);

        // Fail the test if the section is displayed even before clicking the button
        if (sixSecondsSection.isDisplayed()) {
            pass = false;
            reason = "The section is displayed even before clicking the button";
            return;
        }

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

        if (sixSecondsSection != null && sixSecondsSection.isDisplayed()) {
            pass = true;
        } else {
            pass = false;
            reason = "The section is not displayed even after 6 seconds";
        }

        printStatus(8, pass, reason);
    }

    // Click on "Add dynamic text boxes" one time will add one text box
    private static void runTest_9(WebDriver driver) {
        boolean pass = false;
        String reason = null;

        WebElement dynamicTextboxBtn = driver.findElement(By.id("add-dynamic-textbox-btn"));
        dynamicTextboxBtn.click();

        // Wait for 2 seconds
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement dynamicTextboxesSection = driver.findElement(By.id("dynamic-textboxes"));
        List<WebElement> dynamicTextboxes = dynamicTextboxesSection.findElements(By.xpath("div/div/input"));
        if (dynamicTextboxes.size() == 1) {
            pass = true;
        } else {
            pass = false;
            reason = "Dynamic textbox is not displayed";
        }

        printStatus(9, pass, reason);
    }

    // Click on "Add dynamic text boxes" two more times, there will be 3 text boxes
    private static void runTest_10(WebDriver driver) {
        boolean pass = false;
        String reason = null;

        WebElement dynamicTextboxBtn = driver.findElement(By.id("add-dynamic-textbox-btn"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", dynamicTextboxBtn);

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
        if (dynamicTextboxes.size() == 3) {
            pass = true;
        } else {
            pass = false;
            reason = "3 Dynamic textboxes are not displayed";
        }

        printStatus(10, pass, reason);
    }

    // Click on "Alert" will display JS alert. Click on OK will display OK text
    private static void runTest_11(WebDriver driver) {
        boolean pass = false;
        String reason = null;

        WebElement btn = driver.findElement(By.id("js-alert-btn"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", btn);

        btn.click();

        // Wait for the alert to be displayed and store it in a variable
        WebDriverWait wait = new WebDriverWait(driver, 3);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        // Store the alert text in a variable
        String text = alert.getText();
        if (!"This is a JS alert".equals(text)) {
            pass = false;
            reason = "Alert text does not match expected value";
            return;
        }

        // Press the OK button
        alert.accept();
        WebDriverWait wait1 = new WebDriverWait(driver, 2);

        WebElement alertResponse = driver.findElement(By.id("alert-response"));
        if ("OK".equals(alertResponse.getText())) {
            pass = true;
        } else {
            pass = false;
            reason = "Alert response is not 'OK'";
        }

        printStatus(11, pass, reason);
    }

    // Click on "Prompt" will display JS Prompt. Enter name and click OK will display the name
    private static void runTest_12(WebDriver driver) {
        boolean pass = false;
        String reason = null;

        WebElement btn = driver.findElement(By.id("js-prompt-btn"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", btn);

        btn.click();

        // Wait for the alert to be displayed and store it in a variable
        WebDriverWait wait = new WebDriverWait(driver, 3);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys("Group 3");

        // Press the OK button
        alert.accept();
        WebDriverWait wait1 = new WebDriverWait(driver, 2);

        WebElement promptResponse = driver.findElement(By.id("prompt-response"));
        if ("Group 3".equals(promptResponse.getText())) {
            pass = true;
        } else {
            pass = false;
            reason = "Prompt response does not match 'Group 3'";
        }

        printStatus(12, pass, reason);
    }

    // Click on "Confirmation" will display JS Confirmation. Cancel will display the Cancel text
    private static void runTest_13(WebDriver driver) {
        boolean pass = false;
        String reason = null;

        driver.findElement(By.id("js-confirmation-btn")).click();

        // Wait for the alert to be displayed and store it in a variable
        WebDriverWait wait = new WebDriverWait(driver, 3);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        // Store the alert text in a variable
        String text = alert.getText();
        if (!"Press a button!".equals(text)) {
            pass = false;
            reason = "Confirm text does not match expected value";
            return;
        }

        // Press the Cancel button
        alert.dismiss();
        WebDriverWait wait1 = new WebDriverWait(driver, 2);

        WebElement alertResponse = driver.findElement(By.id("confirm-response"));
        if ("Cancel".equals(alertResponse.getText())) {
            pass = true;
        } else {
            pass = false;
            reason = "Alert response is not 'Cancel'";
        }

        printStatus(13, pass, reason);
    }

    // Click on "Upload" without choosing a file will display an error message
    private static void runTest_14(WebDriver driver) {
        boolean pass = false;
        String reason = null;

        WebElement btn = driver.findElement(By.id("upload-btn"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", btn);

        WebElement alert = driver.findElement(By.id("file-empty-alert"));
        WebElement image = driver.findElement(By.id("image-section"));

        if (alert.isDisplayed() || image.isDisplayed()) {
            pass = false;
            reason = "Alert or image is displayed even before the Upload button is clicked";
            return;
        }

        btn.click();

        WebDriverWait wait = new WebDriverWait(driver, 2);

        if (alert.isDisplayed()) {
            pass = true;
        } else {
            pass = false;
            reason = "Alert is not displayed";
        }

        printStatus(14, pass, reason);
    }

    // Choose "selenium_logo_large.png" and click on "Upload" will display the image
    private static void runTest_15(WebDriver driver) {
        boolean pass = false;
        String reason = null;

        WebElement btn = driver.findElement(By.id("upload-btn"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", btn);

        WebElement file = driver.findElement(By.id("file-upload"));
        WebElement alert = driver.findElement(By.id("file-empty-alert"));
        WebElement image = driver.findElement(By.id("image-section"));

        file.sendKeys("/Users/srikanthudarapu/IdeaProjects/selenium-demo/src/main/resources/static/images/selenium_logo_large.png");
        WebDriverWait wait1 = new WebDriverWait(driver, 2);

        btn.click();

        WebDriverWait wait2 = new WebDriverWait(driver, 2);
        wait2.until(ExpectedConditions.visibilityOf(image));

        if (alert.isDisplayed()) {
            pass = false;
            reason = "Alert is displayed";
            return;
        }

        if (image.isDisplayed()) {
            pass = true;
        } else {
            pass = false;
            reason = "Image is not displayed";
        }

        printStatus(15, pass, reason);
    }

    private static void printStatus(int num, boolean pass, String reason) {
        StringBuilder message = new StringBuilder("Test case ");
        message.append(num);
        message.append(": ");
        message.append(pass ? "Pass" : "Fail");

        if (reason != null) {
            message.append(". ");
            message.append("Reason: ");
            message.append(reason);
        }
        System.out.println(message);
    }
}