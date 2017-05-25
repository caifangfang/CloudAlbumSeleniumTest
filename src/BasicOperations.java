import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BasicOperations {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		userNameElement.sendKeys("laotest99@163.com");
		
		//get password input element
		WebElement pwdElement = driver.findElement(By.name("password"));
		//text wrong password
		pwdElement.sendKeys("qa12345");
		//clear wrong password
		pwdElement.clear();
		//text correct password
		pwdElement.sendKeys("qa1234");
		
		//get login button element
		WebElement loginElement = driver.findElement(By.id("dologin"));
		loginElement.click();
		
		//something unknown yet
		driver.switchTo().defaultContent();
		driver.switchTo().defaultContent();
		
		//find my album element
		WebElement albumElement = driver.findElement(By.className("album"));
		
		//print element text,click element
		System.out.println(albumElement.getText());
		albumElement.click();
		
		//get current page url
		System.out.println(driver.getCurrentUrl());
		//get current page title
		System.out.println(driver.getTitle());
		
		//quit browser
		driver.quit();
		
		//System.out.println("Album"+System.currentTimeMillis());
	}

}
