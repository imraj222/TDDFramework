package selenium_6;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class selenium_one {

	public static void main(String[] args) throws Exception {
		
	WebDriver driver = new ChromeDriver();
	Thread.sleep(2000);
	driver.get("https://www.w3schools.com/");
	Thread.sleep(2000);
	String actTitle = driver.getTitle();
	System.out.println(actTitle);
	String expTitle = "W3Schools Online Web Tutorials";
	
	if(actTitle.equals(expTitle)){
		System.out.println("passed");
	}
		
		else {
			System.out.println("fail");
		}
	
	driver.findElement(By.xpath("(//span[@class='rct-checkbox'])[1]")).click();
	}

	//"W3Schools Online Web Tutorials"
}

