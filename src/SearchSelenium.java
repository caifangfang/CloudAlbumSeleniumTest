import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SearchSelenium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建FirefoxDriver实例
		WebDriver driver = new FirefoxDriver();
		//打开有道首页
		driver.get("http://www.youdao.com/n3");
		//找到搜索输入框元素
		WebElement queryInputElement = driver.findElement(By.id("query"));
		//输入Selenium关键字
		queryInputElement.sendKeys("selenium");
		//找到搜索按钮元素，并点击搜索
		WebElement submitBtnElement = driver.findElement(By.id("qb"));
		submitBtnElement.click();
		
		try{
			Thread.sleep(5000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		//关闭浏览器
		//driver.quit();

	}

}
