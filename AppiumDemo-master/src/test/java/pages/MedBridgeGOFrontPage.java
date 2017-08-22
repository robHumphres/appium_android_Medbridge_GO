package pages;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MedBridgeGOFrontPage extends BasePage {

    public MedBridgeGOFrontPage(WebDriver driver) {
        super(driver);
    }

    private static final String temp = "76X9G37Z";

    public void logOn(){

        driver.findElement(By.id("button_start")).click();
        waitForVisibilityOf(By.id("terms_checkbox"));
        driver.findElement(By.id("terms_checkbox")).click();

        char [] tempc = temp.toCharArray();

        for(int x = 1; x < 9; x++){
            driver.findElement(By.id("token_char_"+x)).sendKeys(Character.toString(tempc[x-1]));
        }

        driver.findElement(By.id("button_next")).click();


        waitForVisibilityOf(By.id("exercise_button"));
        /*
        driver.findElement(By.id("exercise_button")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.id("my_activity_button")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.id("resources_button")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        driver.findElement(By.id("exercise_button")).click();
        driver.findElement(By.id("go_button")).click();

*/
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
