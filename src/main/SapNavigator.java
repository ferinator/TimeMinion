package main;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static main.DriverInstance.driver;
import static main.DriverInstance.wait;


public class SapNavigator {

    public static void fillInData(String projectNmbr, String date, String startTime, String endTime, String note){
        SapNavigator.waitFor2Sec();
        driver.findElement(By.id("txtObject")).clear();
        driver.findElement(By.id("txtObject")).sendKeys(projectNmbr);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ui-menu-item")));
        driver.findElement(By.id("txtObject")).sendKeys(Keys.DOWN);
        driver.findElement(By.id("txtObject")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("txtDate")).clear();
        driver.findElement(By.id("txtDate")).click();
        driver.findElement(By.id("txtDate")).sendKeys(date);
        driver.findElement(By.id("lblClient")).click();
        driver.findElement(By.id("txtStartTime")).clear();
        driver.findElement(By.id("txtStartTime")).sendKeys(startTime);
        driver.findElement(By.id("txtEndTime")).click();
        driver.findElement(By.id("txtEndTime")).clear();
        driver.findElement(By.id("txtEndTime")).sendKeys(endTime);
        driver.findElement(By.id("txtCommentExtern")).clear();
        driver.findElement(By.id("txtCommentExtern")).click();
        driver.findElement(By.id("txtCommentExtern")).sendKeys(note);
        SapNavigator.waitFor2Sec();
        driver.findElement(By.id("MainContent_btnApplyAndNew")).click();
        driver.navigate().refresh();
    }

    private void loginAsDorain(){
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("MainContent_LoginUser_UserName")));
        driver.findElement(By.id("MainContent_LoginUser_UserName")).clear();
        driver.findElement(By.id("MainContent_LoginUser_UserName")).sendKeys("u016p37");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("MainContent_LoginUser_UserName")));
        driver.findElement(By.id("MainContent_LoginUser_Password")).clear();
        driver.findElement(By.id("MainContent_LoginUser_Password")).sendKeys("w-sap4Fek");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_LoginUser_LoginButton")));
        driver.findElement(By.id("MainContent_LoginUser_LoginButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("neweffortimg")));
        driver.findElement(By.id("neweffortimg")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lbFavorites")));
    }

    public static void startSelenium() {
        System.out.println("\n\n***********************LAUNCH SPACE SHUTTLE***********************\n");
        driver = DriverInstance.getDriver();
        wait = new WebDriverWait(driver, 5);
        driver.get("http://sapb1.4net.ch/Timeeps/Login.aspx");
        SapNavigator sapNavigator = new SapNavigator();
        sapNavigator.loginAsDorain();
    }

    public static void killDriver () {
        driver.quit();
        driver = null;
    }

    public static void waitFor2Sec(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
