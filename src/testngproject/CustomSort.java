package testngproject;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CustomSort {
	
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

	@Test
	public static void customSortTest() throws InterruptedException {
				
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
		// avoid operate to quick
		Thread.sleep(5000);
			
		// driver.get("http://photo.163.com/xiangyuantest2");
		
		//find custom sort element and click
		WebElement sortElement = driver.findElement(By.xpath("//a/span[text()='排序']"));
		sortElement.click();
		WebElement customSortElement = driver.findElement(By.xpath("//a[text()='自定义排序']"));
		customSortElement.click();
			
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
				
		//drag album B to between album  and album C
		WebElement element = driver.findElement(By.xpath("//div[@title='B']"));
		WebElement target = driver.findElement(By.xpath("//div[@title='C']"));
		Actions act = new Actions(driver);
		act.dragAndDrop(element, target).perform();
				
		//find save element and click
		WebElement saveElement = driver.findElement(By.xpath("//a[text()='保存']"));
		saveElement.click();
				
		// check sort result
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='item'][1]/div[@class='ln ln2']/span[1]")).getText(), "A");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='item'][2]/div[@class='ln ln2']/span[1]")).getText(), "B");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='item'][3]/div[@class='ln ln2']/span[1]")).getText(), "C");
	}
	
	@AfterClass(alwaysRun=true)
	public void afterClass(){
		//quit browser
		driver.quit();
	}

}
