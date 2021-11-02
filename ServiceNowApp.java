package week4.projects;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ServiceNowApp {

	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver=new ChromeDriver();
		driver.get("https://dev69192.service-now.com/navpage.do");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.switchTo().frame(0);
		driver.findElement(By.id("user_name")).sendKeys("admin");
		driver.findElement(By.id("user_password")).sendKeys("Amazon@123");
		driver.findElement(By.id("sysverb_login")).click();
		
		driver.switchTo().defaultContent();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@ng-model='filterTextValue']")).sendKeys("incident");
		driver.findElement(By.xpath("(//div[text()='All'])[2]")).click();
		WebElement incident = driver.findElement(By.xpath("//iframe[contains(@title,'Incidents')]"));
		
		driver.switchTo().frame(incident);
		driver.findElement(By.id("sysverb_new")).click();
		driver.findElement(By.xpath("//button[@id='lookup.incident.caller_id']")).click();
		Set<String> winSet = driver.getWindowHandles();
		List<String> winList= new ArrayList<String>(winSet);
		driver.switchTo().window(winList.get(1));
		
		driver.findElement(By.xpath("//a[text()='Abel Tuter']")).click();
		driver.switchTo().window(winList.get(0));
		WebElement frameSD = driver.findElement(By.xpath("//iframe[contains(@title,'Incident')]"));
		driver.switchTo().frame(frameSD);
		driver.findElement(By.xpath("//a[@id='lookup.incident.short_description']")).click();
		Set<String> winSet1 = driver.getWindowHandles();
		List<String> winList1= new ArrayList<String>(winSet1);
		driver.switchTo().window(winList1.get(1));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[text()='Issue with a web page']")).click();
		driver.switchTo().window(winList1.get(0));
		Thread.sleep(2000);
		driver.switchTo().frame(incident);
	    WebElement number = driver.findElement(By.xpath("//input[@id='incident.number']")); 
		String numberID = number.getAttribute("value"); 
		System.out.println("Number :" +numberID);
		  
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@id='sysverb_insert']")).click();
		
		driver.findElement(By.xpath("(//input[@class='form-control'])[1]")).sendKeys(numberID,Keys.ENTER);
		WebElement num = driver.findElement(By.xpath("//tbody[@class='list2_body']//td[3]/a"));
		String numberMatch = num.getText();
		System.out.println("Matching Number :" +numberMatch);
		if(numberID.equals(numberMatch)) {
			System.out.println("Incident created successfully");
		}
		else {
			System.out.println("Incident not created");
		}
		File src = num.getScreenshotAs(OutputType.FILE);
		File dest=new File("./snaps/pic2.png");
		FileUtils.copyFile(src, dest);
		
		driver.quit();
		
	}

}
