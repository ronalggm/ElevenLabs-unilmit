package common.AutomatedTask;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasePage {

    protected static WebDriver driver;

    static {
        driver = new ChromeDriver();

    }

    BasePage(WebDriver driver) {
        BasePage.driver = driver;
    }


    public static void navigate(String url) {
        BasePage.driver.get(url);
    }


    public String SOdetector() {
        return System.getProperty("os.name").toLowerCase();
    }


}


