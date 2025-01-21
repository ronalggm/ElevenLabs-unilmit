package common.AutomatedTask;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BasePage {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static Page page;
    private static String textCopy;

    public BasePage() {
        if (playwright == null) {
            playwright = Playwright.create();
            browser = playwright.chromium().launch();
        }
        //Crea una nueva pagina para cada instancia de la clase BasePage
        this.page = browser.newPage();
    }

    //Metodo para cerrrar Playwright y el navegador
    public static void closePlaywright() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    public static void navigate(String url) {
        page.navigate(url);
    }

    public static void clickElement(String selector) {
        page.locator(selector).click();
    }

    public static void getText(String locator){
       textCopy= page.locator(locator).textContent();
    }
public static void pasteText(String locator){
        page.locator(locator).fill(textCopy);
}






}

