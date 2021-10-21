package com.jjs.market.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.jjs.market.core.WebDriverUtils.getClickableElementByXpath;
import static com.jjs.market.core.WebDriverUtils.getKeyableElementByXpath;

public class TradingViewUtils {
    public static void login(WebDriver driver) {
        driver.manage().window().maximize();
        driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[2]/div[3]/button[1]"))
                .click();
        getClickableElementByXpath(driver, "//div[text()='Sign in']").click();
        getClickableElementByXpath(driver, "//span[text()='Email']").click();
        getKeyableElementByXpath(driver, "//input[@name='username']").sendKeys(System.getenv("TRADING_VIEW_EMAIL"));
        getKeyableElementByXpath(driver, "//input[@name='password']").sendKeys(System.getenv("TRADING_VIEW_PASSWORD"));
        getClickableElementByXpath(driver, "//button/span[contains(text(), 'Sign in')]/parent::*").click();
    }

    public static void captureChart(WebDriver driver, String stockName) throws IOException {
        if(System.getenv("SKIP_FOLDER").equals("1") &&
                Files.isDirectory( Path.of(System.getenv("OUTPUT_IMAGE_PATH"), stockName))) {
            return;
        }
        System.out.println("Capturing chart :: " + stockName);
        Files.createDirectories(Path.of(System.getenv("OUTPUT_IMAGE_PATH"), stockName));
        getClickableElementByXpath(driver, "//div[@id='header-toolbar-symbol-search']").click();
        getKeyableElementByXpath(driver, "//input[@placeholder='Search']").sendKeys(stockName);
        getClickableElementByXpath(driver, "//span/em[text()='" + stockName + "'][1]").click();

        captureGraph(driver, stockName, "1 month", "D");
        captureGraph(driver, stockName, "1 week", "M");
        captureGraph(driver, stockName, "1 day", "W");
        captureGraph(driver, stockName, "1 hour", "D");
        getClickableElementByXpath(driver, "//div[text()='1Y']").click();
    }

    private static void captureGraph(WebDriver driver, String stockName, String duration, String durationLink) {
        File image = new File(System.getenv("OUTPUT_IMAGE_PATH") + File.separator + stockName + File.separator + stockName + "_" + duration + ".PNG");
        if(System.getenv("SKIP_FILE").equals("1") && image.exists()) {
            return;
        }
        closeAdPopUpsIfPresent(driver);
        navigateChart(driver, duration, durationLink);

        Robot r;
        try {
            Thread.sleep(1000); // Sleep to allow time for screen capture
            r = new Robot();
            BufferedImage screenCapture = r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(screenCapture, "PNG", image);
        } catch (AWTException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        closeAdPopUpsIfPresent(driver);
    }

    private static void navigateChart(WebDriver driver, String duration, String durationLink) {
        getClickableElementByXpath(driver, "//div[text()='" + durationLink + "'][1]").click();
        getClickableElementByXpath(driver, "//div[text()='" + duration + "']").click();
    }

    private static void closeAdPopUpsIfPresent(WebDriver driver) {
        if (driver.findElements(By.xpath("//span[contains(@class, 'close-icon')]")).size() > 0) {
            getClickableElementByXpath(driver, "//span[contains(@class, 'close-icon')]").click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (driver.findElements(By.xpath("//span[contains(@class, 'close-icon')]")).size() > 0) {
                getClickableElementByXpath(driver, "//span[contains(@class, 'close-icon')]").click();
            }
        }
    }
}
