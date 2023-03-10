package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.DeviceConfig;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;


public class BrowserstackDriver implements WebDriverProvider {

    static DeviceConfig config = ConfigFactory.create(DeviceConfig.class);

    @SneakyThrows
    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {

        MutableCapabilities mutableCapabilities = new MutableCapabilities();
        mutableCapabilities.merge(capabilities);
        // Set your access credentials
        mutableCapabilities.setCapability("browserstack.user", config.getLogin());
        mutableCapabilities.setCapability("browserstack.key", config.getPassword());
        // Set URL of the application under test
        mutableCapabilities.setCapability("app", config.getAppUrl());
        // Specify device and os_version for testing
        mutableCapabilities.setCapability("device", config.getDeviceName());
        mutableCapabilities.setCapability("os_version", config.getDeviceVersion());
        // Set other BrowserStack capabilities
        mutableCapabilities.setCapability("project", config.projectName());
        mutableCapabilities.setCapability("build", config.buildName());
        mutableCapabilities.setCapability("name", config.testName());
        // Initialise the remote Webdriver using BrowserStack remote URL
        // and desired capabilities defined above
        return new RemoteWebDriver(config.getBaseUrl(), mutableCapabilities);
    }
}