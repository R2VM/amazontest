package com.test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.StaleElementReferenceException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AmazonBookBuyTest {

    WebDriver driver;
    Select drpCategory;
    WebElement searchbox;
    WebElement searchclick;
    WebElement bestSellertag;
    WebElement paperbackformat;
    WebElement kindleformat;
    WebElement audiblebooks;
    WebElement kindlelink;
    WebElement buybutton;
    WebElement createacc;
    WebElement customername;
    WebElement customeremail;
    WebElement password;
    WebElement repassword;
    WebElement submit;
    WebElement signin;
    WebElement createmazonenewacc;
    String currentHandle;

    @BeforeSuite
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\avkin\\Downloads\\chromedriver_win32 (2)\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        System.out.println("The setup process is completed");
    }

    @BeforeTest
    public void profileSetup() {
        driver.manage().window().maximize();
        System.out.println("Screen is maximise successfully");

    }

    @BeforeClass
    public void setup() {
        driver.get("https:www.amazon.co.uk");
        System.out.println("successfully  load website");
    }

    // Scenario1: Search book via searchbox
    @Test(priority = 1)
    public void searchBook() {
        // Accept the pop up for cookies
        driver.findElement(By.id("sp-cc-accept")).click();

        // Click on drop-down
        driver.findElement(By.id("searchDropdownBox")).click();

        // To select the category
        drpCategory = new Select(driver.findElement(By.id("searchDropdownBox")));
        drpCategory.selectByValue("search-alias=stripbooks");

        searchbox = driver.findElement(By.id("twotabsearchtextbox"));
        searchbox.clear();
        searchbox.sendKeys("harry potter and the philosophers stone book");
        searchclick = driver.findElement(By.id("nav-search-submit-button"));
        searchclick.submit();
    }

    // Scenario2: check book in best seller list or not
    @Test(dependsOnMethods = "searchBook")
    public void BookInBestSeller() throws InterruptedException {
        bestSellertag = driver.findElement(By.id("B017V51FEG-best-seller-label"));
        Assert.assertEquals(true, bestSellertag.isDisplayed());
    }

    // Scenario 3: Verify other author and book related information on Search page
    @Test(dependsOnMethods = "searchBook")
    public void checkBookInfo() {
        try {
            searchbox.clear();
            searchbox.sendKeys("Humankind: A Hopeful History");
        } catch (StaleElementReferenceException e) {

            searchbox = driver.findElement(By.id("twotabsearchtextbox"));
            searchbox.clear();
            searchbox.sendKeys("Humankind: A Hopeful History");
            // obtain value entered
            String s = searchbox.getAttribute("value");
            System.out.println("Value entered is: " + s);
        }

        // Click on search
        try {
            searchclick.submit();
        } catch (StaleElementReferenceException e) {
            searchclick = driver.findElement(By.id("nav-search-submit-button"));
            searchclick.submit();
        }

        // Scenario 3.2: Check PaperBack Format
        paperbackformat = driver.findElement(By.xpath(
                ".//span[text()='13 May 2021']/parent::div/parent::div/parent::div/following-sibling::div//a[text()='Paperback']"));

        if (paperbackformat.isDisplayed()) {
            System.out.println("PaperBack format is available");
            if (paperbackformat.isEnabled()) {
                System.out.println("PaperBack format is clickable");
            } else {
                System.out.println("PaperBack format is not clickable");
            }
        } else {
            System.out.println("PaperBack format is not available");
        }

        // Scenario 3.3: Check Kindle Format
        kindleformat = driver.findElement(By.xpath(
                ".//span[text()='13 May 2021']/parent::div/parent::div/parent::div/following-sibling::div//a[text()='Kindle Edition']"));

        if (kindleformat.isDisplayed()) {
            System.out.println("Kindle format is available");
            if (kindleformat.isEnabled()) {
                System.out.println("Kindle is clickable");
            } else {
                System.out.println("Kindle is not clickable");
            }
        } else {
            System.out.println("kindle is not available");
        }

        // Scenario 3.4: Audible AudioBooks Format

        audiblebooks = driver.findElement(By.xpath(
                ".//span[text()='13 May 2021']/parent::div/parent::div/parent::div/following-sibling::div//a[text()='Audible Audiobooks']"));

        if (audiblebooks.isDisplayed()) {
            System.out.println("Audible audiobook is available");
            if (audiblebooks.isEnabled()) {
                System.out.println("Audible audiobooklink is clickable");
            } else {
                System.out.println("Audible audiobook is not clickable");
            }
        } else {
            System.out.println("Audible audiobook is not available");
        }

    }

    // Scenario 4: Check Kindle Edition
    @Test(dependsOnMethods = "searchBook")

    public void checkKindleformat() {
        try {
            searchbox.clear();
            searchbox.sendKeys("harry potter and the philosophers stone book");
        } catch (StaleElementReferenceException e) {

            searchbox = driver.findElement(By.id("twotabsearchtextbox"));
            searchbox.clear();
            searchbox.sendKeys("harry potter and the philosophers stone book");
            // obtain value entered
            String s = searchbox.getAttribute("value");
            System.out.println("Value entered is: " + s);
        }

        // Click on search
        try {
            searchclick.submit();
        } catch (StaleElementReferenceException e) {
            searchclick = driver.findElement(By.id("nav-search-submit-button"));
            searchclick.submit();
        }
        String bookformat = "Kindle Edition";

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        if (driver.getPageSource().contains("Kindle Edition")) {

            kindlelink = driver.findElement(By.xpath(
                    ".//div/following-sibling::div/div/a[contains(@class, 'a-size-base a-link-normal a-text-bold') and .//text() ='Kindle Edition']"));
            kindlelink.click();
            System.out.println("Text: " + bookformat + " is present. ");
        } else {
            System.out.println("Text: " + bookformat + " is not present. ");
        }

        buybutton = driver.findElement(By.id("one-click-button"));
        buybutton.click();
        createacc = driver.findElement(By.id("createAccountSubmit"));
        createacc.click();
    }

    // Scenario 5: Verify all elements on sign up page

    @Test(dependsOnMethods = "checkKindleformat")
    public void verifyAllElements() {
        customername = driver.findElement(By.xpath("//input[@name='customerName']//parent::div/label"));
        customername.isDisplayed();
        System.out.println("Your Name field is present");

        customeremail = driver.findElement(By.xpath("//input[@name='email']//parent::div/label"));
        customeremail.isDisplayed();
        System.out.println("Email field is prsent");

        password = driver.findElement(By.xpath("//input[@name='password']//parent::div/label"));
        password.isDisplayed();
        System.out.println("Password textbox is present");

        repassword = driver.findElement(By.xpath("//input[@name='passwordCheck']//parent::div/label"));
        repassword.isDisplayed();
        System.out.println("Re-entered password ttexxt box  is present");

        submit = driver.findElement(By.id("continue"));
        submit.isEnabled();
        System.out.println("Create your Amazon Account button is Enable");
    }

    // Scenario 5.2 :Sign up page with all correct details

    @Test(dependsOnMethods = "verifyAllElements")
    public void correctUserdetails() {
        customername = driver.findElement(By.id("ap_customer_name"));
        customername.click();
        customername.clear();
        customername.sendKeys("newuser");

        customeremail = driver.findElement(By.id("ap_email"));
        customeremail.click();
        customeremail.clear();
        customeremail.sendKeys("nyoamazonacc@gmail.com");

        password = driver.findElement(By.id("ap_password"));
        password.click();
        password.clear();
        password.sendKeys("nyosaa26");

        repassword = driver.findElement(By.id("ap_password_check"));
        repassword.click();
        repassword.clear();
        repassword.sendKeys("nyosaa26");

        submit = driver.findElement(By.id("continue"));
        submit.click();

        // Set <String> allWindows = driver.getWindowHandles();
        //
        // for(String handle : allWindows)
        // {
        // driver.switchTo().window(handle);
        // }
        // String expectedURL = "https://www.amazon.co.uk/ap/register";
        // String actualURL = driver.getCurrentUrl();
        // System.out.println(actualURL);
        // Assert.assertEquals(actualURL, expectedURL);

        signin = driver.findElement(By.xpath("//div/a[contains(text(), 'Sign-In')]"));
        signin.click();

        // Click on create amazon
        createmazonenewacc = driver.findElement(By.xpath("//span[@id='auth-create-account-link']"));
        createmazonenewacc.click();

    }

    // Scenario 5.3: Registration without providing Name field
    @Test(dependsOnMethods = "correctUserdetails")
    public void emptyCustomerName() {
        customername = driver.findElement(By.id("ap_customer_name"));
        customername.click();
        customername.clear();
        customername.sendKeys("");

        customeremail = driver.findElement(By.id("ap_email"));
        customeremail.click();
        customeremail.clear();
        customeremail.sendKeys("nyoamazonacc@gmail.com");

        password = driver.findElement(By.id("ap_password"));
        password.click();
        password.clear();
        password.sendKeys("nyosaa26");

        repassword = driver.findElement(By.id("ap_password_check"));
        repassword.click();
        repassword.clear();
        repassword.sendKeys("nyosaa26");

        submit = driver.findElement(By.id("continue"));
        submit.click();

        String expectedErrorMsg = "Enter your name";

        WebElement exp = driver.findElement(By.xpath("//div[contains(text(), 'Enter your name')]"));
        String actualErrorMsg = exp.getText();

        Assert.assertEquals(actualErrorMsg, expectedErrorMsg);
        System.out.println(actualErrorMsg);
        driver.navigate().refresh();
    }

    // Scenario 5.4: Registration without providing user email field
    @Test(dependsOnMethods = "emptyCustomerName")
    public void emptyemail() {
        customername = driver.findElement(By.id("ap_customer_name"));
        customername.click();
        customername.clear();
        customername.sendKeys("newuser");

        customeremail = driver.findElement(By.id("ap_email"));
        customeremail.click();
        customeremail.clear();
        customeremail.sendKeys("");

        password = driver.findElement(By.id("ap_password"));
        password.click();
        password.clear();
        password.sendKeys("nyosaa26");

        repassword = driver.findElement(By.id("ap_password_check"));
        repassword.click();
        repassword.clear();
        repassword.sendKeys("nyosaa26");

        submit = driver.findElement(By.id("continue"));
        submit.click();

        String expectedErrorMsg2 = "Enter your e-mail";
        WebElement exp2 = driver.findElement(
                By.xpath("//div[@id='auth-email-missing-alert']/div/div[contains(text(), 'Enter your e-mail')]"));
        String actualErrorMsg2 = exp2.getText();
        Assert.assertEquals(actualErrorMsg2, expectedErrorMsg2);
        System.out.println(actualErrorMsg2);
        driver.navigate().refresh();
    }

    // Registration with email id which already/invalid have account
    @Test(dependsOnMethods = "emptyemail")
    public void invalidemail() {
        customername = driver.findElement(By.id("ap_customer_name"));
        customername.click();
        customername.clear();
        customername.sendKeys("newuser");

        customeremail = driver.findElement(By.id("ap_email"));
        customeremail.click();
        customeremail.clear();
        customeremail.sendKeys("amazon@test.com");

        password = driver.findElement(By.id("ap_password"));
        password.click();
        password.clear();
        password.sendKeys("nyosaa26");

        repassword = driver.findElement(By.id("ap_password_check"));
        repassword.click();
        repassword.clear();
        repassword.sendKeys("nyosaa26");

        submit = driver.findElement(By.id("continue"));
        submit.click();

        String expectedErrorMsg3 = "E-mail address already in use";
        WebElement exp3 = driver.findElement(By.xpath("//div/h4[contains(text(), 'E-mail address already in use')]"));
        String actualErrorMsg3 = exp3.getText();
        Assert.assertEquals(actualErrorMsg3, expectedErrorMsg3);
        System.out.println(actualErrorMsg3);

        signin = driver.findElement(By.xpath("//div/a[contains(text(), 'Sign-In')]"));
        signin.click();

        // Click on create amazon
        createmazonenewacc = driver.findElement(By.xpath("//span[@id='auth-create-account-link']"));
        createmazonenewacc.click();
    }

    // Scenario 5.5: Registration without providing password field

    @Test(dependsOnMethods = "invalidemail")
    public void withoutPassword() {

        customername = driver.findElement(By.id("ap_customer_name"));
        customername.click();
        customername.clear();
        customername.sendKeys("newuser");

        customeremail = driver.findElement(By.id("ap_email"));
        customeremail.click();
        customeremail.clear();
        customeremail.sendKeys("nyoamazonacc@gmail.com");

        password = driver.findElement(By.id("ap_password"));
        password.click();
        password.clear();
        password.sendKeys("");

        repassword = driver.findElement(By.id("ap_password_check"));
        repassword.click();
        repassword.clear();
        repassword.sendKeys("nyosaa26");

        submit = driver.findElement(By.id("continue"));
        submit.click();

        String expectedErrorMsg4 = "Enter your password";
        WebElement exp4 = driver.findElement(By.xpath("//div[contains(text(), 'Enter your password')]"));
        String actualErrorMsg4 = exp4.getText();
        Assert.assertEquals(actualErrorMsg4, expectedErrorMsg4);
        System.out.println(actualErrorMsg4);
        driver.navigate().refresh();
    }

    // Scenario 5.6: Registration with invalid password
    @Test(dependsOnMethods = "withoutPassword")
    public void invalidPassword() {
        customername = driver.findElement(By.id("ap_customer_name"));
        customername.click();
        customername.clear();
        customername.sendKeys("newuser");

        customeremail = driver.findElement(By.id("ap_email"));
        customeremail.click();
        customeremail.clear();
        customeremail.sendKeys("nyoamazonacc@gmail.com");

        password = driver.findElement(By.id("ap_password"));
        password.click();
        password.clear();
        password.sendKeys("nyosaa27");

        repassword = driver.findElement(By.id("ap_password_check"));
        repassword.click();
        repassword.clear();
        repassword.sendKeys("nyo26");

        submit = driver.findElement(By.id("continue"));
        submit.click();

        String expectedErrorMsg5 = "Passwords do not match";

        WebElement exp5 = driver.findElement(By.xpath("//div[@id='auth-password-mismatch-alert']/div"));
        String actualErrorMsg5 = exp5.getText();

        Assert.assertEquals(actualErrorMsg5, expectedErrorMsg5);
        System.out.println(actualErrorMsg5);

    }

    @AfterClass()
    public void tearDown() {
        driver.close();
    }
}

