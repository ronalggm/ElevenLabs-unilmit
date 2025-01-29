package common.AutomatedTask;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.Arrays;

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


    private String SOdetector() {
        return System.getProperty("os.name").toLowerCase();
    }


    public String getBrowsers() {

        String[] paths = {
                "/usr/bin/google-chrome",
                "/usr/bin/chromium",
                "/usr/bin/firefox",
                "/usr/bin/brave-browser",
                "/usr/bin/opera"
        };
        String[] pathsWindows = {
                "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                "C:\\Program Files\\Mozilla Firefox\\firefox.exe",
                "C:\\Program Files\\Opera\\launcher.exe",
                "C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe",
                "C:\\Program Files\\Microsoft\\Edge\\Application\\msedge.exe"
        };

        String[] browsersavailable = new String[0];
        int contador = 0;
        if (SOdetector().contains("windows")) {
            for (String path : pathsWindows) {
                if (new File(path).exists()) {
                    browsersavailable = Arrays.copyOf(browsersavailable, ++contador);
                    browsersavailable[contador++] = path; //NO SIRVE CONTINUAR AQUI
                }

            }
        }
        return "";
    }
}

