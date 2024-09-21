package test1;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;

public class AutomationTestStore {

    ChromeOptions options;
    WebDriver driver;
    
    @BeforeTest
    public void setup() {
        options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);

        driver.get("https://automationteststore.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test(groups = {"smoke"}, priority = 1) 
    public void register() {
        driver.findElement(By.linkText("Login or register")).click();
        driver.findElement(By.xpath("//*[@title='Continue']")).click();
        driver.findElement(By.id("AccountFrm_firstname")).sendKeys(generateRandomFName());
        driver.findElement(By.name("lastname")).sendKeys(generateRandomLName());
        driver.findElement(By.id("AccountFrm_email")).sendKeys(generateRandomEmail());
        driver.findElement(By.id("AccountFrm_telephone")).sendKeys(String.valueOf(generateRandomTelephone()));
        driver.findElement(By.id("AccountFrm_fax")).sendKeys(generateRandomFaxNumber());
        driver.findElement(By.id("AccountFrm_company")).sendKeys("Google Inc");
        driver.findElement(By.id("AccountFrm_address_1")).sendKeys("A/17 Harimandir Socity");
        driver.findElement(By.id("AccountFrm_address_2")).sendKeys("opp. Elite hotel");
        driver.findElement(By.id("AccountFrm_city")).sendKeys("Ahmedabad");
        selectCountry(By.id("AccountFrm_country_id"), "India");
        selectState(By.id("AccountFrm_zone_id"), "Gujarat");        
        driver.findElement(By.id("AccountFrm_postcode")).sendKeys("382424");
        driver.findElement(By.id("AccountFrm_loginname")).sendKeys(generateRandomFName()+"2024"+generateRandomLName());
        driver.findElement(By.id("AccountFrm_password")).sendKeys("Admin@123");
        driver.findElement(By.id("AccountFrm_confirm")).sendKeys("Admin@123");
        
        selectRadioButton(By.id("AccountFrm_newsletter0"));
        selectRadioButton(By.id("AccountFrm_agree"));       
        driver.findElement(By.xpath("//*[@title='Continue']")).click();
      
        assertElementText(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! Your new account has been success')]")), "Congratulations! Your new account has been successfully created!");
        driver.findElement(By.cssSelector("a[title='Continue']")).click();

        Actions action = new Actions(driver);

		action.moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Apparel & accessories')][@href]"))).perform();
		action.moveToElement(driver.findElement(By.xpath("//a[contains(text(), 'T-shirts')]"))).click().perform();

        driver.findElement(By.xpath("//a[@title='Designer Men Casual Formal Double Cuffs Grandad Band Collar Shirt Elegant Tie']")).click();
        WebElement productQuantity = driver.findElement(By.id("product_quantity"));
        productQuantity.clear();
        productQuantity.sendKeys("2");

        driver.findElement(By.cssSelector("a[class='cart']")).click();
        driver.findElement(By.id("cart_checkout2")).click();
        driver.findElement(By.id("checkout_btn")).click(); //continue automation script from here
        assertElementText(driver.findElement(By.xpath("//span[@class='maintext']")), " Your Order Has Been Processed!");          
        action.moveToElement( driver.findElement(By.cssSelector("*[class='top menu_account']"))).perform();
        action.moveToElement( driver.findElement(By.cssSelector("*[class='fa fa-lock fa-fw']"))).click().perform();

		driver.findElement(By.cssSelector("*[title='Continue']")).click();
        
    }
    
    @Test(groups = {"smoke"}, priority = 2)
    public void login(){   	
		
				
		driver.findElement(By.id("loginFrm_loginname")).sendKeys("rahul2024");
		driver.findElement(By.id("loginFrm_password")).sendKeys("Admin@123");
		driver.findElement(By.cssSelector("*[title='Login']")).click();
		
		Actions action = new Actions(driver);	
		WebElement topMenu = driver.findElement(By.cssSelector("*[class='top menu_account']"));
		action.moveToElement(topMenu).perform();
		WebElement logOut = driver.findElement(By.cssSelector("*[class='fa fa-lock fa-fw']"));
		action.moveToElement(logOut).click().build().perform();
		driver.findElement(By.cssSelector("*[title='Continue']")).click();
    	
    }
    

    public String generateRandomEmail() {
        String emailPrefix = "John";
        String emailDomain = "@yopmail.com";
        int randomNum = new Random().nextInt(1000); // Generate a random number
        return emailPrefix + randomNum + emailDomain;
    }

    public long generateRandomTelephone() {
        Random random = new Random();
        // Generate a 10-digit number
        long number = 1000000000L + random.nextLong(9000000000L);
        return number;
    }

    public String generateRandomFName() {
        String[] firstNames = { "John", "Jane", "Alex", "Emily", "Michael", "Sarah" };
        Random random = new Random();
        String firstName = firstNames[random.nextInt(firstNames.length)];
        return firstName;
    }

    public String generateRandomLName() {
        String[] lastNames = { "Doe", "Smith", "Johnson", "Williams", "Jones", "Brown" };
        Random random = new Random();
        String lastName = lastNames[random.nextInt(lastNames.length)];
        return lastName;
    }

    public String generateRandomFaxNumber() {
        Random random = new Random();
        // Generate a 3-digit area code (e.g., 555)
        int areaCode = 100 + random.nextInt(900);
        // Generate a 3-digit prefix (e.g., 123)
        int prefix = 100 + random.nextInt(900);
        // Generate a 4-digit line number (e.g., 4567)
        int lineNumber = 1000 + random.nextInt(9000);
        // Format the fax number as +1 (XXX) YYY-ZZZZ
        return String.format("+1 (%03d) %03d-%04d", areaCode, prefix, lineNumber);
    }
    
    
    public void selectRadioButton(By locator) {
    	
    	WebElement radiobutton = driver.findElement(locator);
    	
    	if (!radiobutton.isSelected()) {
    		radiobutton.click();
        }
    }
    
    public void selectCountry(By dropdownLocator, String countryName) {
        WebElement dropdownElement = driver.findElement(dropdownLocator);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(countryName);
    }
    
    public void selectState(By dropdownLocator, String stateName) {
        WebElement dropdownElement = driver.findElement(dropdownLocator);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(stateName);
    }
    
    public void moveToElement(WebElement element) {
        Actions action = new Actions(driver);
		action.moveToElement(element).perform();
    }
    
    public void assertElementText(WebElement element, String expectedText) {
        String actualText = element.getText();
        Assert.assertEquals(actualText, expectedText);
    }
   //Add any new method here 
}
