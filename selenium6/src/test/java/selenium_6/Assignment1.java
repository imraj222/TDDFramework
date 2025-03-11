package selenium_6;


	import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dev.failsafe.internal.util.Assert;

	public class Assignment1 {
	    private WebDriver driver;
	    private String baseUrl = "https://www.adaptiveu.io/";

	    @BeforeMethod
		@BeforeClass
	    public void setUp() {
	        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	    }

	    @Test
	    public void testUserRegistration() {
	        driver.get(baseUrl + "register");
	        driver.findElement(By.id("name")).sendKeys("Test User");
	        driver.findElement(By.id("email")).sendKeys("testuser@example.com");
	        driver.findElement(By.id("password")).sendKeys("Password123");
	        driver.findElement(By.id("confirmPassword")).sendKeys("Password123");
	        driver.findElement(By.id("registerButton")).click();
	        WebElement confirmationMessage = driver.findElement(By.id("confirmationMessage"));
	        Assert.isTrue(confirmationMessage.isDisplayed(), "Confirmation email should be sent.");
	    }

	    @Test
	    public void testValidLogin() {
	        driver.get(baseUrl + "login");
	        driver.findElement(By.id("email")).sendKeys("testuser@example.com");
	        driver.findElement(By.id("password")).sendKeys("Password123");
	        driver.findElement(By.id("loginButton")).click();
	        WebElement dashboard = driver.findElement(By.id("dashboard"));
	        Assert.isTrue(dashboard.isDisplayed(), "User should be redirected to dashboard.");
	    }

	    @Test
	    public void testInvalidLogin() {
	        driver.get(baseUrl + "login");
	        driver.findElement(By.id("email")).sendKeys("wronguser@example.com");
	        driver.findElement(By.id("password")).sendKeys("WrongPassword");
	        driver.findElement(By.id("loginButton")).click();
	        WebElement errorMessage = driver.findElement(By.id("errorMessage"));
	        Assert.isTrue(errorMessage.isDisplayed(), "Error message should be displayed.");
	    }
	    
	    @Test
	    public void testPasswordReset() {
	        driver.get(baseUrl + "forgot-password");
	        driver.findElement(By.id("email")).sendKeys("testuser@example.com");
	        driver.findElement(By.id("resetButton")).click();
	        WebElement resetMessage = driver.findElement(By.id("resetMessage"));
	        Assert.isTrue(resetMessage.isDisplayed(), "Reset email should be sent.");
	    }

	    @Test
	    public void testCourseEnrollment() {
	        driver.get(baseUrl + "courses");
	        driver.findElement(By.id("courseEnrollButton")).click();
	        WebElement enrolledCourse = driver.findElement(By.id("enrolledCourse"));
	        Assert.isTrue(enrolledCourse.isDisplayed(), "Course should appear in dashboard.");
	    }

	    @Test
	    public void testCourseCompletionTracking() {
	        driver.get(baseUrl + "dashboard");
	        WebElement progress = driver.findElement(By.id("progressBar"));
	        Assert.isTrue(progress.getText().contains("%"), "Progress percentage should increase.");
	    }

	    @Test
	    public void testCertificateGeneration() {
	        driver.get(baseUrl + "completedCourses");
	        driver.findElement(By.id("generateCertificateButton")).click();
	        WebElement certificate = driver.findElement(By.id("downloadCertificate"));
	        Assert.isTrue(certificate.isDisplayed(), "Certificate should be available.");
	    }

	    @Test
	    public void testVideoPlayback() {
	        driver.get(baseUrl + "course/video");
	        WebElement video = driver.findElement(By.tagName("video"));
	        Assert.isTrue(video.isDisplayed(), "Video should play smoothly.");
	    }

	    @Test
	    public void testQuizFunctionality() {
	        driver.get(baseUrl + "quiz");
	        driver.findElement(By.id("startQuizButton")).click();
	        driver.findElement(By.id("submitQuizButton")).click();
	        WebElement result = driver.findElement(By.id("quizResult"));
	        Assert.isTrue(result.isDisplayed(), "Quiz should be graded.");
	    }

	    @Test
	    public void testLeaderboardUpdates() {
	        driver.get(baseUrl + "leaderboard");
	        WebElement leaderboard = driver.findElement(By.id("leaderboardTable"));
	        Assert.isTrue(leaderboard.isDisplayed(), "Leaderboard should update correctly.");
	    }

	    @Test
	    public void testAdminDashboard() {
	        driver.get(baseUrl + "admin");
	        WebElement adminPanel = driver.findElement(By.id("adminPanel"));
	        Assert.isTrue(adminPanel.isDisplayed(), "Admin should be able to manage courses.");
	    }

	    @Test
	    public void testUserProfileUpdate() {
	        driver.get(baseUrl + "profile");
	        driver.findElement(By.id("editProfileButton")).click();
	        driver.findElement(By.id("name")).clear();
	        driver.findElement(By.id("name")).sendKeys("Updated User");
	        driver.findElement(By.id("saveProfileButton")).click();
	        WebElement successMessage = driver.findElement(By.id("profileUpdateMessage"));
	        Assert.isTrue(successMessage.isDisplayed(), "Profile should be updated.");
	    }

	    @Test
	    public void testLogoutFunctionality() {
	        driver.get(baseUrl + "dashboard");
	        driver.findElement(By.id("logoutButton")).click();
	        WebElement loginPage = driver.findElement(By.id("login"));
	        Assert.isTrue(loginPage.isDisplayed(), "User should be redirected to login page.");
	    }


	    @AfterMethod
		@AfterClass
	    public void tearDown() {
	        driver.quit();
	    }
	}



