package main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverInstance {
    public static WebDriver driver;
    public static WebDriverWait wait;

    private DriverInstance() {}

    public static WebDriver getDriver() {
            System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Selenium Driver\\chromedriver-v2.24-win32\\chromedriver.exe");
            driver = new ChromeDriver();
            return driver;
    }
}
