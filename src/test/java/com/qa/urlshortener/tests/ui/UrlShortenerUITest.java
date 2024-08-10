package com.qa.urlshortener.tests.ui;

import com.qa.urlshortener.base.TestBase;
import com.qa.urlshortener.ui.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

//@Listeners(ListenerClass.class)
public class UrlShortenerUITest extends TestBase {

    HomePage homePage;
    WebDriver driver;
    private static final String LONG_URL = "https://www.amazon.in/Lenovo-IdeaPad-Gaming-39-62cm-82K201V2IN/dp/B0B7RXC1Y1?ref_=Oct_DLandingS_D_416a10e7_70&th=1";

    @BeforeMethod
    @Parameters("browser")
    public void setup(String browserName) {
        driver = webDriverInitialization(browserName);
        homePage = new HomePage();
    }

    @Test (description = "Verify that short url is created and is redirected to original url")
    public void verifyUrlShortener() throws InterruptedException {
        homePage.validateHomePage();
        homePage.shortenUrl(LONG_URL);
        Assert.assertEquals(homePage.getOriginalUrl(), LONG_URL);
        homePage.clickOnShortUrl();
        String redirectedUrl = driver.getCurrentUrl();
        Assert.assertEquals(redirectedUrl, LONG_URL, "Redirected URL is : "+redirectedUrl);
    }

    @AfterMethod
    public void cleanup() {
        tearDown(driver);
    }
}
