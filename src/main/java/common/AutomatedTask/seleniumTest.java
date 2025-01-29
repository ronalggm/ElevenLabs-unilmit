package common.AutomatedTask;


import org.openqa.selenium.WebDriver;

public class seleniumTest extends BasePage {


    seleniumTest(WebDriver driver) {
        super(driver);
    }

    public static void main(String[] args) {
        seleniumTest.navigate("https://www.google.com/");
    }

}
