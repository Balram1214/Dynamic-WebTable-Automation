package com.webtable;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DynamicWebTable {

	static WebDriver dr = new ChromeDriver();
	static WebDriverWait wait = new WebDriverWait(dr, Duration.ofSeconds(10));

	public static void main(String[] args) {

		dr.manage().window().maximize();

		dr.get("https://practice.expandtesting.com/dynamic-table#google_vignette");

		DynamicWebTable.scrollUptoTable();
		DynamicWebTable.getCPUPercentIndex();

		String chromePercentText = DynamicWebTable.getChromePercentText();
		String predefinedChromeText = DynamicWebTable.getPredefinedChromeText();

		assertEquals(chromePercentText, predefinedChromeText);

	}

	public static void scrollUptoTable() {
		// Scroll upto table
		JavascriptExecutor js = (JavascriptExecutor) dr;
		WebElement scrollText = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='table-description']")));
		js.executeScript("arguments[0].scrollIntoView(true);", scrollText);
	}

	public static int getCPUPercentIndex() {
		List<WebElement> tableHeaders = dr.findElements(By.xpath("//th"));

		int index = 0;
		String header = "CPU";

		for (int i = 0; i < tableHeaders.size(); i++) {
			if (tableHeaders.get(i).getText().equalsIgnoreCase(header)) {
				index = i + 1;
			}
		}

		System.out.println("CPU index in table: " + index);
		return index;

	}

	public static String getChromePercentText() {
		String nameData = "Chrome";

		// Get Chrome CPU percent by index
		WebElement cpuPercentText = dr.findElement(
				By.xpath("//tr[td='" + nameData + "']//following::td[" + DynamicWebTable.getCPUPercentIndex() + "]"));
		String getChromePercentText = cpuPercentText.getText();
		System.out.println("Chrome Percent Text: " + getChromePercentText);

		return getChromePercentText;

	}

	public static String getPredefinedChromeText() {
		// Get predefined chrome percentage below the table
		WebElement chromeCPUPredefinedText = dr.findElement(By.id("chrome-cpu"));
		String getChromeCPUPredefinedText = chromeCPUPredefinedText.getText();
		System.out.println("Predefined Chrome Percent: " + getChromeCPUPredefinedText);

		// Get Exact percentage text only
		int colonIndex = getChromeCPUPredefinedText.indexOf(":");
		int percentIndex = getChromeCPUPredefinedText.indexOf("%");

		System.out.println("Colon index: " + colonIndex);
		System.out.println("Percent index: " + percentIndex);
		// Get exact percent text from predefined text
		String finalPredefinedText = getChromeCPUPredefinedText.substring(colonIndex + 2, percentIndex + 1);
		System.out.println("Final Predefined Text: " + finalPredefinedText);

		return finalPredefinedText;

	}

}
