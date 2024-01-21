package com.qa.urlshortener.ui.pages;

import com.qa.urlshortener.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.xml.xpath.XPath;

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
    private WebElement originalUrlText;

    @FindBy(xpath = "//p[contains(text(),' Short URL: ')]")
    private WebElement shortUrlText;

    @FindBy(xpath = "//h7[contains(text(),'The short URL will automatically be deleted after three months')]")
    private WebElement expiryInfo;

    @FindBy(xpath = "//p[contains(text(),' Short URL: ')]//following-sibling::a")
    private WebElement shortUrl;

    @FindBy(xpath = "//p[contains(text(),' Original URL: ')]//following-sibling::a")
    private WebElement originalUrl;

    By shortUrlTextXpath = By.xpath("//p[contains(text(),' Short URL: ')]");

    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    public void shortenUrl(String longUrl) throws InterruptedException {
        inputPlaceholder.sendKeys(longUrl);
        shortenButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(shortUrlTextXpath));
        Assert.assertTrue(shortUrlText.isDisplayed());
        Assert.assertTrue(originalUrlText.isDisplayed());
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
