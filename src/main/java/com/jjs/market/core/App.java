package com.jjs.market.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.jjs.market.core.TradingViewUtils.captureChart;
import static com.jjs.market.core.TradingViewUtils.login;
import static com.jjs.market.core.WebDriverUtils.getClickableElementByXpath;
import static org.openqa.selenium.Keys.ENTER;

public class App {
    public static void main(String[] args) throws AWTException, IOException {
        System.setProperty("webdriver.chrome.driver", System.getenv("DRIVER_PATH"));

        WebDriver driver = new ChromeDriver();
        driver.navigate().to("https://www.tradingview.com/");

        login(driver);
        getClickableElementByXpath(driver, "//li/a[text()='Chart']").sendKeys(ENTER);
        getClickableElementByXpath(driver, "/html/body/div[2]/div[5]/div/div[2]/div/div/div/div/div[1]").click();

        Reader reader = Files.newBufferedReader(Path.of(App.class.getResource("/EQUITY_L.csv").getPath().substring(1)));
        List<String[]> stocks = CSVUtils.readAll(reader);
        for (int i = 1; i < stocks.size(); i ++) {
            String[] stockDetails = stocks.get(i);
            captureChart(driver, stockDetails[0].replaceAll("&", "_").replaceAll("-", "_"));
        }

    }
}
