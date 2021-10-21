package com.jjs.market.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.jjs.market.core.WebDriverUtils.getClickableElementByXpath;
import static com.jjs.market.core.WebDriverUtils.getKeyableElementByXpath;

public class TradingViewUtils {
    public static void login(WebDriver driver) {
        driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[2]/div[3]/button[1]"))
                .click();
        getClickableElementByXpath(driver, "//div[text()='Sign in']").click();
        getClickableElementByXpath(driver, "//span[text()='Email']").click();
        getKeyableElementByXpath(driver, "//input[@name='username']").sendKeys(System.getenv("TRADING_VIEW_EMAIL"));
        getKeyableElementByXpath(driver, "//input[@name='password']").sendKeys(System.getenv("TRADING_VIEW_PASSWORD"));
        getClickableElementByXpath(driver, "//button/span[contains(text(), 'Sign in')]/parent::*").click();
    }
}
