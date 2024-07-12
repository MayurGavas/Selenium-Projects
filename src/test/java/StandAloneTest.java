import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.swing.*;
import java.time.Duration;
import java.util.List;

public class StandAloneTest {

    public static <WebElements> void main(){

        ChromeOptions options =  new ChromeOptions();
        options.addArguments("--Start-maximized","--incognito");
        options.addArguments("--force-device-scale-factor=1.9");
        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("https://rahulshettyacademy.com/client");

        driver.findElement(By.id("userEmail")).sendKeys("mayurgavas1998@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Mayur@123");
        driver.findElement(By.id("login")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".mb-3")));


        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        //WebElement element = driver.findElement(By.xpath("//b[text()='ZARA COAT 3']"));
        WebElement prod = products.stream().filter(product -> product.findElement(By.xpath("//b[text()='ZARA COAT 3']")).getText().equals("ZARA COAT 3")).findFirst().orElse(null);
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));

        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".cartSection")));
        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));

        Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase("ZARA COAT 3"));

        Assert.assertTrue(match);
        /* The Assert.assertTrue(match);
        statement in TestNG is used to assert that the condition match is true.
        If match is false, the test will fail. T
        his is a common way to ensure that certain conditions hold true during the execution of a test case. */

        driver.findElement(By.cssSelector(".totalRow button")).click();

        Actions a = new Actions(driver);
        a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")),"india").build().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();

        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ta-results")));

        //wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".action__submit")));
        driver.findElement(By.cssSelector(".action__submit")).click();

        String confirmmessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmmessage.equalsIgnoreCase("Thankyou for the order."));
        driver.close();






    }
}
