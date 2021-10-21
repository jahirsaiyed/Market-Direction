package com.jjs.market.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtils {
    public static WebElement getClickableElementByXpath(WebDriver driver, String xpath) {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public static WebElement getKeyableElementByXpath(WebDriver driver, String xpath) {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }
}
