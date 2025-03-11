package selenium_6;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriver {

	public static void main(String[] args) {
		ChromeDriver driver= new ChromeDriver();
		driver.get("https://www.guru99.com/");
		String actTitle = driver.getTitle();
		System.out.println("actTitle");
		String expTitle = "Guru99: Free Online Tutorials and Business Software Reviews";
		if //Guru99: Free Online Tutorials and Business Software Reviews
			(actTitle == expTitle){
			System.out.println("Pass");
		
		}else {
			System.out.println("FAiled");
		}
		
		
		driver.findElement(By.xpath("(//img[@class=\"custom-logo\"][@alt=\"Guru99\"])[1]")).click();
		
		
		
		
		
		

	}

}
