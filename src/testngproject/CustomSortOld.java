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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CustomSortOld {
	
	public static WebDriver driver;
	
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
		 String[] names = {"C","B","A"};

		 for (String name : names) {
			 
			driver.get("http://photo.163.com/xiangyuantest2/");
			 
			// get current system time and trans to format string
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dataString = df.format(new Date());

			//find create album element,click element
			WebElement createAlbumElement = driver.findElement(By.xpath("//a[text()='创建相册']"));
			createAlbumElement.click();
				
			//find album name element
			WebElement albumNameElement = driver.findElement(By.xpath("//input[@class='t-x wd0']"));
			//text album name
			albumNameElement.sendKeys(name);
				
			//find album description element
			WebElement albumDesElement = driver.findElement(By.xpath("//textarea[@class='wd0 txt']"));
			//text album description
			albumDesElement.sendKeys("Create this Album at "+dataString);
				
			//find confirm button element
			WebElement confirmElement = driver.findElement(By.xpath("//button[@name='fm-a']"));
			//click confirm button
			confirmElement.click();
				
			String albumRealName = driver.findElement(By.className("extra-menu")).findElement(By.tagName("span")).getText();
				
			Assert.assertEquals(name, albumRealName);
			
			// avoid operate to quick
			Thread.sleep(10000);
			
		}
		    
	}

	@Test
	public static void customSortTest(){
		driver.get("http://photo.163.com/xiangyuantest2");
				
		//find custom sort element and click
		WebElement sortElement = driver.findElement(By.xpath("//a/span[text()='排序']"));
		sortElement.click();
		WebElement customSortElement = driver.findElement(By.xpath("//a[text()='自定义排序']"));
		customSortElement.click();
		
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		
		//drag album A to between album B and album C
		WebElement element = driver.findElement(By.xpath("//div[@title='A']"));
		WebElement target = driver.findElement(By.xpath("//div[@title='C']"));
		Actions act = new Actions(driver);
		act.dragAndDrop(element, target).perform();
		
		//find save element and click
		WebElement saveElement = driver.findElement(By.xpath("//a[text()='保存']"));
		saveElement.click();
		
		// check sort result
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='item'][1]/div[@class='ln ln2']/span[1]")).getText(), "B");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='item'][2]/div[@class='ln ln2']/span[1]")).getText(), "A");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='item'][3]/div[@class='ln ln2']/span[1]")).getText(), "C");

	}
	
	@AfterMethod
	public void afterMethod() throws InterruptedException{
		
		 String[] names = {"B","A","C"};

		 for (String name : names) {
			 driver.get("http://photo.163.com/xiangyuantest2");
			 
			 // if album exist then delete
//			 String albumPath = "//div[@class='item']/div[@class='ln ln0']/a/img[@title=\'"+name+"\']";
			 String albumPath = "//a/img[@title=\'"+name+"\']";
			 By selector = By.xpath(albumPath);
			 if(isWebElementExist(driver, selector)){
				 // move mouse to created album
				 WebElement element = driver.findElement(By.xpath(albumPath));
				 Actions builder = new Actions(driver);
				 Action hover = builder.moveToElement(element).build();
				 hover.perform();
				 
				 String albumParentPath = albumPath + "/parent::*/parent::*/parent::*";
				 WebElement parentElement = driver.findElement(By.xpath(albumParentPath));
		    
				 // find delete album button and click
				 WebElement deleteAlbumElement = parentElement.findElement(By.xpath("//a[@title='删除']"));
				 deleteAlbumElement.click();
			
				 //find confirm delete button and click
				 WebElement confirmDeleteElement = parentElement.findElement(By.xpath("//button[text()='确 定']"));
				 confirmDeleteElement.click();
				 
				 Thread.sleep(5000);
			 }
		 }
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
