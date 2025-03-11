package selenium_6;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

	public class Assignment2ndWay {
	    ChromeDriver driver;
	    WebDriverWait wait;
	    
	    @BeforeClass
	    public void setup() {
	        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
	        driver = new ChromeDriver();
	        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    }

	    // TEST CASE 1: User Registration
	    @Test
	    public void testSuccessfulRegistration() {
	        driver.get("https://www.adaptiveu.io/signup");
	        
	        WebElement email = driver.findElement(By.id("email"));
	        WebElement password = driver.findElement(By.id("password"));
	        WebElement submit = driver.findElement(By.cssSelector("[type='submit']"));
	        
	        email.sendKeys("testuser+" + System.currentTimeMillis() + "@example.com");
	        password.sendKeys("ValidPass123!");
	        submit.click();
	        
	        // Verify confirmation message
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(text(),'confirmation email sent')]")));
	    }

	    // TEST CASE 2: Login Functionality
	    @Test
	    public void testValidLogin() {
	        driver.get("https://www.adaptiveu.io/login");
	        
	        WebElement email = driver.findElement(By.name("email"));
	        WebElement password = driver.findElement(By.name("password"));
	        email.sendKeys("valid@user.com");
	        password.sendKeys("validPassword");
	        password.submit();
	        
	        wait.until(ExpectedConditions.urlContains("/dashboard"));
	    }

	    // TEST CASE 3: Invalid Login
	    @Test
	    public void testInvalidLogin() {
	        driver.get("https://www.adaptiveu.io/login");
	        
	        driver.findElement(By.name("email")).sendKeys("invalid@user.com");
	        driver.findElement(By.name("password")).sendKeys("wrongpass");
	        driver.findElement(By.cssSelector("[type='submit']")).click();
	        
	        WebElement error = wait.until(ExpectedConditions
	            .visibilityOfElementLocated(By.className("error-message")));
	        assert error.getText().contains("Invalid credentials");
	    }

	    // TEST CASE 5: Course Enrollment
	    @Test(dependsOnMethods = "testValidLogin")
	    public void testCourseEnrollment() {
	        driver.get("https://www.adaptiveu.io/courses");
	        WebElement course = wait.until(ExpectedConditions
	            .elementToBeClickable(By.xpath("//div[contains(text(),'Target Course')]")));
	        course.click();
	        
	        driver.findElement(By.id("enroll-button")).click();
	        WebElement confirmation = wait.until(ExpectedConditions
	            .visibilityOfElementLocated(By.cssSelector(".enrollment-success")));
	        assert confirmation.isDisplayed();
	    }

	    // TEST CASE 17: Logout
	    @Test
	    public void testLogout() {
	        // Assume user is logged in
	        driver.findElement(By.className("profile-menu")).click();
	        WebElement logoutBtn = wait.until(ExpectedConditions
	            .visibilityOfElementLocated(By.linkText("Logout")));
	        logoutBtn.click();
	        
	        wait.until(ExpectedConditions.urlContains("/login"));
	    }

	    @AfterMethod
		@AfterClass
	    public void tearDown() {
	        driver.quit();
	    }
	}


