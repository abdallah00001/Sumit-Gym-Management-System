package com.summit.gym.Sumit_Gym_Management_System.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class WhatsAppMessengerService {
    private static final By newChatLocator = By.cssSelector("span[data-icon='new-chat-outline']");

    public static boolean sendCode(String phone, String qrLocation) {

        if (qrLocation == null) {
            return false;
        }

        boolean isSent = true;

        WebDriver driver = startDriver();

        try {
            int waitDuration = 30;
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitDuration));
            Actions actions = new Actions(driver);

            // Navigate to WhatsApp Web
            String title = driver.getTitle();
            if (title != null && !title.contains("WhatsApp")) {
                driver.get("https://web.whatsapp.com");
                System.out.println("link called");
                Thread.sleep(2000);
            }

            System.out.println(title);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement newChatElem = wait.until(ExpectedConditions.
                    elementToBeClickable(newChatLocator));
            newChatElem.click();

            // Search for the contact/phone number
            WebElement searchBox = driver.findElement(By.cssSelector("div.lexical-rich-text-input p"));
            actions.moveToElement(searchBox);
            searchBox.click();
            searchBox.sendKeys(phone);
            Thread.sleep(3000); // Wait for search results

            // Click on the contact after search
//        WebElement chatDev = driver.findElement(By.xpath("//div[contains(text(), 'Chats')]"));

            WebElement contact = findContact(driver, waitDuration);

            actions.moveToElement(contact).click().perform();

            WebElement attachButton = driver.findElement(By.cssSelector("span[data-icon=\"plus\"]"));
            actions.moveToElement(attachButton).click().perform();
            Thread.sleep(2000);

            WebElement imageInput = driver.findElement(By.cssSelector("input[accept=\"image/*,video/mp4,video/3gpp,video/quicktime\"]\n"));
            imageInput.sendKeys(qrLocation);
            Thread.sleep(1000);

            WebElement sendButton = driver.findElement(By.cssSelector("span[data-icon='send']"));
            sendButton.click();
            Thread.sleep(1500); // Wait for the chat to open
            driver.quit();
        } catch (StaleElementReferenceException | InterruptedException e) {
            isSent = false;
            driver.quit();
        }

        return isSent;
    }

    private static WebDriver startDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        WebDriver driver = new ChromeDriver(options);
        return driver;
    }

    private static WebElement findContact(WebDriver driver, int waitSeconds) {
        WebElement chatDev;
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            chatDev = driver.findElement(By.xpath("//div[contains(text(), 'Not in your contacts')]"));
        } catch (NoSuchElementException e) {
            chatDev = driver.findElement(By.xpath("//div[contains(text(), 'Contacts on WhatsApp')]"));
        }
        WebElement contact = driver.findElement(with(By.cssSelector("span[title]")).below(chatDev));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitSeconds));
        return contact;
    }


    public static void main(String[] args) {
        String myPhone = "01151661160";
        String location = "C:\\Users\\abdelrahman\\Downloads\\Untitled 1.png";

        sendCode(myPhone, location);
    }

}
