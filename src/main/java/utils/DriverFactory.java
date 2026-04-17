package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    public static WebDriver getDriver(String browser) {

        WebDriver driver;
        if (browser.equalsIgnoreCase("chrome")) {
            driver = setChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = setFireFoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = setEdgeDriver();
        } else {
            driver = setChromeDriver();
        }

        driver.manage().window().maximize();

        return driver;
    }

    private static WebDriver setChromeDriver() {
        var options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        return new ChromeDriver(options);
    }

    private static WebDriver setFireFoxDriver() {
        var profile = new FirefoxProfile();
        profile.setPreference("credentials_enable_service", false);
        profile.setPreference("profile.password_manager_enabled", false);
        profile.setPreference("profile.password_manager_leak_detection", false);

        var options = new FirefoxOptions();
        options.setProfile(profile);
        options.addArguments("--disable-notifications");

        return new FirefoxDriver(options);
    }

    private static WebDriver setEdgeDriver() {
        var options = new EdgeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        return new EdgeDriver(options);
    }
}
