package selenium_6;


import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

	public class Assignment2Way {
	    ChromeDriver driver;
	    WebDriverWait wait;
	    String baseUrl = "https://www.adaptiveu.io/";
	    
	    @BeforeClass
	    public void setup() {
	        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
	        ChromeOptions options = new ChromeOptions();
	        driver = new ChromeDriver(options);
	        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	        driver.manage().window().maximize();
	    }

	    // Test Case 1: User Registration
	    @Test(priority = 1)
	    public void testUserRegistration() throws InterruptedException {
	        driver.get(baseUrl + "signup");
	        String timestamp = String.valueOf(System.currentTimeMillis());
	        driver.findElement(By.id("name")).sendKeys("Test User");
	        driver.findElement(By.id("email")).sendKeys("testuser" + timestamp + "@example.com");
	        driver.findElement(By.id("password")).sendKeys("ValidPass123!");
	        driver.findElement(By.cssSelector(".register-btn")).click();
	        wait.until(ExpectedConditions.textToBePresentInElementLocated(
	            By.cssSelector(".confirmation-message"), "Confirmation email sent"));
	    }

	    // Test Case 2: Login Functionality
	    @Test(priority = 2)
	    public void testValidLogin() {
	        performLogin("valid@user.com", "ValidPass123!");
	        wait.until(ExpectedConditions.urlContains("/dashboard"));
	    }

	    // Test Case 3: Login with Invalid Credentials
	    @Test(priority = 3)
	    public void testInvalidLogin() {
	        performLogin("invalid@user.com", "wrongpass");
	        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.cssSelector(".error-message")));
	        assert error.getText().contains("Invalid credentials");
	    }

	    // Test Case 4: Password Reset
	    @Test(priority = 4)
	    public void testPasswordReset() {
	        driver.get(baseUrl + "forgot-password");
	        String resetEmail = "user@example.com";
	        driver.findElement(By.id("reset-email")).sendKeys(resetEmail);
	        driver.findElement(By.cssSelector(".reset-btn")).click();
	        wait.until(ExpectedConditions.textToBePresentInElementLocated(
	            By.cssSelector(".reset-success"), "Reset email sent"));
	        // Add email verification logic here
	    }

	    // Test Case 5: Course Enrollment
	    @Test(priority = 5, dependsOnMethods = "testValidLogin")
	    public void testCourseEnrollment() {
	        driver.get(baseUrl + "courses");
	        WebElement course = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//div[contains(text(),'Test Course')]")));
	        course.click();
	        driver.findElement(By.cssSelector(".enroll-btn")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.cssSelector(".enrollment-success")));
	    }

	    // Test Case 6: Course Completion Tracking
	    @Test(priority = 6, dependsOnMethods = "testCourseEnrollment")
	    public void testCourseCompletion() {
	        driver.get(baseUrl + "course/123");
	        int initialProgress = Integer.parseInt(driver.findElement(
	            By.cssSelector(".progress-percent")).getText().replace("%", ""));
	        
	        // Complete a module
	        driver.findElement(By.cssSelector(".complete-module-btn")).click();
	        wait.until(ExpectedConditions.textToBePresentInElementLocated(
	            By.cssSelector(".progress-percent"), String.valueOf(initialProgress + 25)));
	    }

	    // Test Case 7: Certificate Generation
	    @Test(priority = 7, dependsOnMethods = "testCourseCompletion")
	    public void testCertificateGeneration() {
	        driver.findElement(By.cssSelector(".get-certificate-btn")).click();
	        wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.cssSelector(".certificate-download-link")));
	    }

	    // Test Case 8: Video Playback
	    @Test(priority = 8)
	    public void testVideoPlayback() {
	        driver.get(baseUrl + "course/123/video");
	        WebElement video = wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.cssSelector("video")));
	        video.click(); // Play
	        assert (Boolean) ((JavascriptExecutor) driver)
	            .executeScript("return arguments[0].paused == false", video);
	    }

	    // Test Case 9: Quiz Functionality
	    @Test(priority = 9)
	    public void testQuizFunctionality() {
	        driver.get(baseUrl + "course/123/quiz");
	        // Answer questions
	        driver.findElements(By.cssSelector(".quiz-option")).get(0).click();
	        driver.findElement(By.cssSelector(".submit-quiz")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.cssSelector(".quiz-score")));
	    }

	    // Test Case 10: Leaderboard Updates
	    @Test(priority = 10)
	    public void testLeaderboardUpdates() {
	        driver.get(baseUrl + "leaderboard");
	        String initialPosition = driver.findElement(
	            By.cssSelector(".user-position")).getText();
	        
	        // Complete some progress
	        testCourseCompletion();
	        
	        driver.navigate().refresh();
	        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(
	            By.cssSelector(".user-position"), initialPosition)));
	    }

	    // Test Case 11: Admin Dashboard
	    @Test(priority = 11)
	    public void testAdminDashboard() {
	        // Admin login
	        performLogin("admin@adaptiveu.io", "adminPass123!");
	        driver.get(baseUrl + "admin/courses");
	        
	        // Create new course
	        driver.findElement(By.id("new-course-title")).sendKeys("New Course");
	        driver.findElement(By.cssSelector(".save-course-btn")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(text(),'New Course')]")));
	    }

	    // Test Case 12: User Profile Update
	    @Test(priority = 12)
	    public void testProfileUpdate() {
	        driver.get(baseUrl + "profile");
	        WebElement nameField = driver.findElement(By.id("name"));
	        nameField.clear();
	        nameField.sendKeys("Updated Name");
	        driver.findElement(By.cssSelector(".save-profile-btn")).click();
	        wait.until(ExpectedConditions.textToBePresentInElementLocated(
	            By.cssSelector(".profile-name"), "Updated Name"));
	    }

	    // Test Case 13: Notification System
	    @Test(priority = 13)
	    public void testNotifications() {
	        // Trigger a notification event
	        driver.get(baseUrl + "courses");
	        driver.findElement(By.cssSelector(".subscribe-btn")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.cssSelector(".notification-badge")));
	    }

	    // Test Case 14: Mobile Responsiveness
	    @Test(priority = 14)
	    public void testMobileResponsiveness() {
	        Map<String, String> mobileEmulation = new HashMap<String, String>();
	        mobileEmulation.put("deviceName", "iPhone 12 Pro");
	        
	        ChromeOptions options = new ChromeOptions();
	        options.setExperimentalOption("mobileEmulation", mobileEmulation);
	        ChromeDriver mobileDriver = new ChromeDriver(options);
	        
	        mobileDriver.get(baseUrl);
	        assert mobileDriver.findElement(By.cssSelector(".mobile-menu")).isDisplayed();
	        mobileDriver.quit();
	    }

	    // Test Case 15: Discussion Forum
	    @Test(priority = 15)
	    public void testDiscussionForum() {
	        driver.get(baseUrl + "course/123/forum");
	        String postContent = "Test post " + System.currentTimeMillis();
	        driver.findElement(By.cssSelector(".new-post")).sendKeys(postContent);
	        driver.findElement(By.cssSelector(".submit-post")).click();
	        wait.until(ExpectedConditions.textToBePresentInElementLocated(
	            By.cssSelector(".forum-posts"), postContent));
	    }

	    // Test Case 16: File Upload
	    @Test(priority = 16)
	    public void testFileUpload() {
	        driver.get(baseUrl + "assignment/upload");
	        WebElement fileInput = driver.findElement(By.cssSelector("input[type='file']"));
	        fileInput.sendKeys("/path/to/testfile.pdf");
	        driver.findElement(By.cssSelector(".upload-btn")).click();
	        wait.until(ExpectedConditions.textToBePresentInElementLocated(
	            By.cssSelector(".upload-status"), "Upload successful"));
	    }

	    // Test Case 17: Logout
	    @Test(priority = 17)
	    public void testLogout() {
	        driver.findElement(By.cssSelector(".profile-menu")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.cssSelector(".logout-btn"))).click();
	        wait.until(ExpectedConditions.urlContains("/login"));
	    }

	    // Test Case 18: Session Timeout
	    @Test(priority = 18)
	    public void testSessionTimeout() throws InterruptedException {
	        performLogin("valid@user.com", "ValidPass123!");
	        // Wait for session timeout duration + buffer
	        Thread.sleep(1860000); // 31 minutes if timeout is 30
	        driver.get(baseUrl + "dashboard");
	        assert driver.getCurrentUrl().contains("/login");
	    }

	    // Test Case 19: Accessibility
	    @Test(priority = 19)
	    public void testAccessibility() {
	        driver.get(baseUrl);
	        // Using axe-core
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("const axe = require('axe-core'); axe.run().then(results => { return results; });");
	        // Add accessibility assertions
	    }

	    // Test Case 20: Payment Gateway
	    @Test(priority = 20)
	    public void testPaymentGateway() {
	        driver.get(baseUrl + "course/premium-course/payment");
	        fillPaymentDetails("4242424242424242", "12/28", "123");
	        driver.findElement(By.cssSelector(".pay-now-btn")).click();
	        wait.until(ExpectedConditions.urlContains("/payment-success"));
	    }

	    // Helper Methods
	    private void performLogin(String email, String password) {
	        driver.get(baseUrl + "login");
	        driver.findElement(By.id("email")).sendKeys(email);
	        driver.findElement(By.id("password")).sendKeys(password);
	        driver.findElement(By.cssSelector(".login-btn")).click();
	    }

	    private void fillPaymentDetails(String cardNumber, String expiry, String cvv) {
	        driver.findElement(By.id("card-number")).sendKeys(cardNumber);
	        driver.findElement(By.id("expiry-date")).sendKeys(expiry);
	        driver.findElement(By.id("cvv")).sendKeys(cvv);
	    }

	    @AfterMethod
		@AfterClass
	    public void tearDown() {
	        driver.quit();
	    }
	}


