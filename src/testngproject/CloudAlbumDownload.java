package testngproject;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CloudAlbumDownload {
	public static WebDriver driver;
	
	@BeforeClass
	public void beforeClass(){
		// open browser
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// get url
		driver.get("http://photo.163.com");
		
		// find learn more element
		WebElement learnMoreLinkElement = driver.findElement(By.partialLinkText("了解更多"));
		// check element text
		Assert.assertEquals(learnMoreLinkElement.getText(), "了解更多","云相册页面无了解更多按钮");		
	}
	
	@Test
	public static void cloudAlbumDownloadTest() {
		// find learn more element
		WebElement learnMoreLinkElement = driver.findElement(By.partialLinkText("了解更多"));
		
		// click button to know more about cloud album
		learnMoreLinkElement.click();
		
		// switch to new window
		SwitchWindow(driver);
		
		// System.out.println(driver.getCurrentUrl());
		// System.out.println(driver.getTitle());
		
		//check current url
		Assert.assertEquals(driver.getCurrentUrl(), "http://photo.163.com/cloudphotos/","点击了解更多后，进入云相册链接错误");
		//check current page title
		Assert.assertEquals(driver.getTitle(), "网易云相册-为散落的相片找个家-iPhone/iPad/Android/PC相片轻松统一管理和浏览","点击了解更多后，进入云相册页面错误");
		
		// click iphone download button
		WebElement iphoneDownloadLinkElement = driver.findElement(By.id("J-iphone"));
		iphoneDownloadLinkElement.click();
		
		WebElement iphoneDownloadPageElement = driver.findElement(By.xpath("//div[@id='J-xbox-title']"));
		// check redirect div text
		Assert.assertEquals(iphoneDownloadPageElement.getText(), "iPhone版下载","点击下载后，未弹出iPhone版下载页面");		

	}

	private static void SwitchWindow(WebDriver driver) {
		//得到当前窗口的句柄 
	    String currentWindow = driver.getWindowHandle();
	  
	    //得到所有窗口的句柄
	    Set<String> handles = driver.getWindowHandles();
	    
	    //不包括当前窗口
	    handles.remove(currentWindow);
	    
	    for(String winHandle:handles){
	        driver.switchTo().window(winHandle);
		}
	}
	
	@AfterClass(alwaysRun=true)
	public void afterClass(){
		//quit browser
		driver.quit();
	}
}
