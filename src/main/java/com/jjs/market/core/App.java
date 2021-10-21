package com.jjs.market.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", System.getenv("DRIVER_PATH"));

        WebDriver driver=new ChromeDriver();
        driver.navigate().to("https://www.tradingview.com/");

        TradingViewUtils.login(driver);

    }
}
