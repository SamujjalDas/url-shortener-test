package com.qa.urlshortener.ui.pages;

import com.qa.urlshortener.base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class HomePage extends TestBase {

    @FindBy(xpath = "//h1[contains(text(),'Snaply - URL Shortener')]")
    private WebElement header;

    @FindBy(xpath = "//*[contains(text(),'Paste the URL to be shortened')]")
    private WebElement description;

    @FindBy(id = "url")
    private WebElement inputBox;

    @FindBy(xpath = "//input[contains(@placeholder,'https://www.google.co.in/')]")
    private WebElement inputPlaceholder;

    @FindBy(xpath = "//input[@value='Shorten URL']")
    private WebElement shortenButton;

    @FindBy(xpath = "//p[contains(text(),'Original URL:')]")
    private WebElement originalUrlHeader;

    @FindBy(xpath = "//p[contains(text(),' Short URL: ')]")
    private WebElement shortUrlHeader;

    @FindBy(xpath = "//h7[contains(text(),'The short URL will automatically be deleted after three months')]")
    private WebElement expiryInfo;

    @FindBy(xpath = "//p[contains(text(),' Short URL: ')]//following-sibling::a")
    private WebElement shortUrl;

    @FindBy(xpath = "//p[contains(text(),' Original URL: ')]//following-sibling::a")
    private WebElement originalUrl;

    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    public void shortenUrl(String longUrl) throws InterruptedException {
        inputPlaceholder.sendKeys(longUrl);
        shortenButton.click();
        Thread.sleep(8000);
        Assert.assertTrue(shortUrlHeader.isDisplayed());
        Assert.assertTrue(originalUrlHeader.isDisplayed());
        Assert.assertTrue(expiryInfo.isDisplayed());
        Assert.assertTrue(shortUrl.isDisplayed());
        Assert.assertTrue(originalUrl.isDisplayed());
    }

    public String getOriginalUrl() {
        return originalUrl.getText();
    }

    public String getShortUrl () {
        return shortUrl.getText();
    }

    public void clickOnShortUrl () {
        shortUrl.click();
    }

    public void validateHomePage() {
        Assert.assertTrue(header.isDisplayed());
        Assert.assertTrue(description.isDisplayed());
        Assert.assertTrue(inputBox.isDisplayed());
        Assert.assertTrue(inputPlaceholder.isDisplayed());
        Assert.assertTrue(shortenButton.isDisplayed());
    }

}
