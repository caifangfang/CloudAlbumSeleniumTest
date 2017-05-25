package testngproject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CommentAlbum {

	public static WebDriver driver;
	public static String commentText;
	
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
	
	@Test(dataProvider="data")
	public static void commentAlbumTest(String comment) throws InterruptedException{
		Thread.sleep(5000);
		// get current system time and trans to format string
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dataString = df.format(new Date());
		
		driver.get("http://photo.163.com/xiangyuantest2");
		
		// click album
		driver.findElement(By.xpath("//div[@class='item']//a[1]")).click();
		
		// find comment input element
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='j-main']//iframe")));
		WebElement commentArea = driver.findElement(By.xpath("//body"));
		
		// text comment
		commentText = comment + dataString;
		commentArea.sendKeys(commentText);
		
		driver.switchTo().defaultContent();
		
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		
		// find submit comment element
		WebElement submitCommentButton = driver.findElement(By.xpath("id('photo-163-com-container')//input[@value='发表']"));
		
		// click submit comment element
		submitCommentButton.click();		

		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		
		// check comment context and author name		
		String commentRealText = driver.findElement(By.xpath("//div[contains(@class,'nbw-cmt')][1]//div[contains(@class,'js-cnt')]")).getText();
		Assert.assertEquals(commentRealText, commentText);
		String commentAuthor = driver.findElement(By.xpath("//div[contains(@class,'nbw-cmt')][1]//a[contains(@class,'js-title')]")).getText();
		Assert.assertEquals(commentAuthor, "xiangyuantest2");
		
	}
	
	@DataProvider
	public Object[][] data()
	{
		return new Object[][]{
			{"Comment at "},
			{"评论时间"}};
	}
	
	@AfterMethod
	public void afterMethod() throws InterruptedException{	

		int[] retries = {1,2,3};
		for(int retry:retries){
			
			Thread.sleep(5000);
			System.out.println("Try delete comment "+retry+" time.");

			driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);			
			// find delete comment element
			WebElement deleteCommentElement = driver.findElement(By.xpath("//div[contains(@class,'nbw-cmt')][1]//a[text()='删除']"));
			deleteCommentElement.click();
			
		    //switch to alert window
		    Alert alert = driver.switchTo().alert();
		    // System.out.println(alert.getText());
		  
		    //close alert window
		    alert.accept();
			
			try{
				//if delete fail,it will pop up another alert
				Alert alert2 = driver.switchTo().alert();
				alert2.accept();
			}catch(Exception e){
				//if not get delete fail alert,then may be the comment has been deleted
				System.out.println(e);
				break;
			}
			
			
		}
	}
	
	
	@AfterClass(alwaysRun=true)
	public void afterClass(){
		//quit browser
		driver.quit();
	}


}
