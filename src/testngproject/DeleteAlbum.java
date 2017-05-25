package testngproject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeleteAlbum {
	
	public static WebDriver driver;
	public static String albumName;
	
	@BeforeClass
	public void beforeClass(){
		// open browser
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// get url
		driver.get("http://photo.163.com");
		
		//timeout for each
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@src='http://blog.163.com/newpage/ursweb/tmpl2/loginurs.html']")));
		driver.switchTo().frame(driver.findElement(By.id("x-URS-iframe")));
				
		//get username input element
		WebElement userNameElement = driver.findElement(By.name("email"));
		//text username
		userNameElement.sendKeys("xiangyuantest2@163.com");
				
		//get password input element
		WebElement pwdElement = driver.findElement(By.name("password"));
		//text correct password
		pwdElement.sendKeys("xiangyuan163");
				
		//get login button element
		WebElement loginElement = driver.findElement(By.id("dologin"));
		loginElement.click();
		
		//wait for 15 seconds, may have to text verify code
		driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
		
		//something unknown yet
		driver.switchTo().defaultContent();
		driver.switchTo().defaultContent();
		
		//find my album element
		WebElement albumElement = driver.findElement(By.className("album"));
		
		// check element text
		Assert.assertEquals(albumElement.getText(), "进入我的相册","登录后，未显示进入我的相册");
		//click element
		albumElement.click();
		
		//check current url
		Assert.assertEquals(driver.getCurrentUrl(), "http://photo.163.com/xiangyuantest2/#m=0&p=1","登录后，进入我的相册链接错误");
		//check current page title
		Assert.assertEquals(driver.getTitle(), "xiangyuantest2的网易相册_xiangyuantest2个人相册相片存储_网易相册","登录后，进入我的相册错误");
	}
	
	@BeforeMethod
	public static void beforeMethod() throws InterruptedException{
			 
		// get current system time and trans to format string
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dataString = df.format(new Date());

		//find create album element,click element
		WebElement createAlbumElement = driver.findElement(By.xpath("//a[text()='创建相册']"));
		createAlbumElement.click();
				
		//find album name element
		WebElement albumNameElement = driver.findElement(By.xpath("//input[@class='t-x wd0']"));
		albumName = "D" + System.currentTimeMillis();
		//text album name
		albumNameElement.sendKeys(albumName);
				
		//find album description element
		WebElement albumDesElement = driver.findElement(By.xpath("//textarea[@class='wd0 txt']"));
		//text album description
		albumDesElement.sendKeys("Create this Album at "+dataString);
				
		//find confirm button element
		WebElement confirmElement = driver.findElement(By.xpath("//button[@name='fm-a']"));
		//click confirm button
		confirmElement.click();
				
		String albumRealName = driver.findElement(By.className("extra-menu")).findElement(By.tagName("span")).getText();
				
		Assert.assertEquals(albumName, albumRealName,"创建相册失败");
		
		// avoid operate to quick
		Thread.sleep(5000);
		    
	}

	@Test
	public static void deleteAlbumTest() throws InterruptedException {
		
		driver.get("http://photo.163.com/xiangyuantest2");
		
		// move mouse to album
	    WebElement element = driver.findElement(By.xpath("//div[@class='item']/div[@class='ln ln0']/a/img[starts-with(@title, \'"
	    		+ albumName.substring(0,9)+"\')]"));
	    Actions builder = new Actions(driver);
	    Action hover = builder.moveToElement(element).build();
	    hover.perform();
	    
	    // find delete album button and click
	    WebElement deleteAlbumElement = driver.findElement(By.xpath("//a[@title='删除']"));
	    deleteAlbumElement.click();
		
		//find confirm delete button and click
	    WebElement confirmDeleteElement = driver.findElement(By.xpath("//button[text()='确 定']"));
		confirmDeleteElement.click();
		
		Thread.sleep(1000);
		
		// check if album is deleted
		By selector = By.xpath("//div[@class='item']/div[@class='ln ln0']/a/img[starts-with(@title, \'"
	    		+ albumName.substring(0,9)+"\')]");
		Assert.assertFalse(isWebElementExist(driver,selector),"删除相册失败");
	}
	
	public static boolean isWebElementExist(WebDriver driver, By selector)
	{
	    try 
	    { 
	       driver.findElement(selector); 
	       return true; 
	    } 
	    catch (NoSuchElementException e) 
	    { 
	       return false;
	    } 
	}

	@AfterClass(alwaysRun=true)
	public void afterClass(){
		//quit browser
		driver.quit();
	}
}
