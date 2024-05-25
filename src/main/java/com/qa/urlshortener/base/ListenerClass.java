package com.qa.urlshortener.base;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

public class ListenerClass extends TestBase implements ITestListener {

    @Test
    public void onTestFailure(ITestResult result) {
        System.out.println("Test is failed");
        try {
            captureScreenshot(result.getName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test is passed");
        try {
            captureScreenshot(result.getName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void onTestStart(ITestResult result) {
        System.out.println("Test is started");
        try {
            captureScreenshot(result.getName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
