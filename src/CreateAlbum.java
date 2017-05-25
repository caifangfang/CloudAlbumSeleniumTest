
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CreateAlbum {

	public static void main(String[] args) {
		// get current system time and trans to format string
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dataString = df.format(new Date());
		
		// open browser
		WebDriver driver = new FirefoxDriver();
		
		// get url
		driver.get("http://photo.163.com");
		
		//something unknown yet
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
		
		//print element text,click element
		System.out.println(albumElement.getText());
		albumElement.click();
		
//		//get current page url
//		System.out.println(driver.getCurrentUrl());
//		//get current page title
//		System.out.println(driver.getTitle());	
		
		//find create album element,click element
		WebElement createAlbumElement = driver.findElement(By.linkText("创建相册"));
		createAlbumElement.click();
		
		//wait for 5 seconds
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		
		//find album name element
		WebElement albumNameElement = driver.findElement(By.xpath("//input[@class='t-x wd0']"));
		//text album name
		albumNameElement.sendKeys("Album"+System.currentTimeMillis());
		
		//find album description element
		WebElement albumDesElement = driver.findElement(By.xpath("//textarea[@class='wd0 txt']"));
		//text album description
		albumDesElement.sendKeys("Create this Album at "+dataString);
		
		//find confirm button element
		WebElement confirmElement = driver.findElement(By.xpath("//button[@class='ui-btn ui-btn-sub0']"));
		//click confirm button
		confirmElement.click();
		
		//quit browser
		driver.quit();
	}

}
