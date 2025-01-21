package common.AutomatedTask;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BasePage {

    protected static Playwright playwright;
    protected static Browser browser;
    protected Page page;


    public BasePage() {
        if (playwright == null) {
            playwright = Playwright.create();
            browser = playwright.chromium().launch();
        }
        this.page = browser.newPage();
    }

    public static void closePlaywright(){
        if(browser!=null){
            browser.close();
        }
    }
}
