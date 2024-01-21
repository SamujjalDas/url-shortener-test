package com.qa.urlshortener.tests.ui;

import com.qa.urlshortener.base.TestBase;
import com.qa.urlshortener.ui.pages.HomePage;
import org.junit.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UrlShortenerUITest extends TestBase {

    HomePage homePage;
    private static final String LONG_URL = "https://www.amazon.in/Lenovo-IdeaPad-Gaming-39-62cm-82K201V2IN/dp/B0B7RXC1Y1?ref_=Oct_DLandingS_D_416a10e7_70&th=1";

    @BeforeMethod
    public void setup() {
        webDriverInitialization();
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
        tearDown();
    }
}
