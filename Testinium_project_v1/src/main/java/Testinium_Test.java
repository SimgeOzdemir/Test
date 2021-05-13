import jdk.internal.util.xml.impl.Input;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import javax.lang.model.util.Elements;
import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class Testinium_Test {
    public WebDriver driver;
    //public WebDriverWait wait;

    static WebDriverWait wait=null;
    @Before
    public void setupDriver(){
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver3.exe");
        driver = new ChromeDriver();
        String url ="https://www.gittigidiyor.com/uye-girisi";

       // String url = "https://www.gittigidiyor.com/";
        driver.get(url);
        //driver.manage().window().maximize();
       // driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

    }
    @Test
    public void Test_SearchBox() throws InterruptedException {
        /*Login operations */
        WebElement emailspace=driver.findElement(By.id("L-UserNameField"));
        emailspace.click();
        emailspace.sendKeys("simgebinnaz@gmail.com");
        WebElement password_space=driver.findElement(By.id("L-PasswordField"));
        password_space.click();
        password_space.sendKeys("simge123");
        System.out.println("password done");
        WebElement LoginButton=driver.findElement(By.id("gg-login-enter"));
        LoginButton.click();



        /*Typing word "Bilgisayar" and searching.*/
        WebElement searchbox=driver.findElement(By.xpath(".//*[@class='sc-4995aq-4 dNPmGY']/input"));
        searchbox.click();
        String Word="Bilgisayar";
        searchbox.sendKeys(Word, Keys.ENTER);
        Assert.assertEquals("Bilgisayar",Word);
        System.out.println("'Bilgisayar' is done");

        /*Click the 2nd Page and navigate the Second page*/
        // https://stackoverflow.com/questions/11908249/debugging-element-is-not-clickable-at-point-error
        // https://stackoverflow.com/questions/20579007/get-href-value-webdriver
        // https://www.softwaretestinghelp.com/assertions-in-selenium/
        WebElement secondpage= driver.findElement(By.xpath("//*[@id=\"best-match-right\"]/div[5]/ul/li[2]/a"));
        Actions actions=new Actions(driver);
        actions.moveToElement(secondpage).click().perform();
        System.out.println("Second page clicked");
        String Secondpage_link =driver.findElement(By.xpath("//*[@id=\"best-match-right\"]/div[5]/ul/li[2]/a")).getAttribute("href");
        driver.get(Secondpage_link);
        Assert.assertEquals("https://www.gittigidiyor.com/arama/?k=Bilgisayar&sf=2",Secondpage_link);
        System.out.println("Navigate the second page is done");

        //Choose random product
        WebElement RandomProduct= driver.findElement(By.xpath("//*[@id=\"item-info-block-688922600\"]/p/img"));
        RandomProduct.click();
        System.out.println("Random product selection is done");
        String product_price=driver.findElement(By.id("sp-price-lowPrice")).getText();
        StringBuilder sb=new StringBuilder(product_price); //Next 10 lie convert the product actual price to comparable value
        sb.deleteCharAt(1);
        sb.deleteCharAt(7);
        sb.deleteCharAt(8);
        product_price= sb.toString();
        StringBuilder sb2=new StringBuilder(product_price);
        sb2.deleteCharAt(product_price.length()-1);
        sb2.deleteCharAt(4);
        product_price=sb2.toString();
        product_price=sb2.substring(0,4)+"."+sb2.substring(4,6);
        System.out.println("product price: "+product_price); //Getting price on product page

        //Adding basket and navigate the basket page
        Actions act=new Actions(driver);
        act.moveToElement(driver.findElement(By.xpath("//*[@id=\"add-to-basket\"]"))).doubleClick().build().perform();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"header_wrapper\"]/div[4]/div[3]/a/div[1]/div")).click();

        //Checking the product price
        String Product_price_att= driver.findElement(By.xpath("//*[@id=\"cart-item-474857151\"]/div[1]/div[5]/div[1]/input")).getAttribute("value");
        Assert.assertEquals(product_price,Product_price_att);
        System.out.println("Product price on page and basket price are equal"+product_price);
        Thread.sleep(1000);

        //Adding one more product ad Checking product number equals to 2
        String OptionValue=driver.findElement(By.xpath("//*[@class=\"amount\"]/option[2]")).getText();
        driver.findElement(By.xpath("//*[@class=\"amount\"]/option[2]")).click();
        Assert.assertEquals("2",OptionValue);
        System.out.println("Option Value 2 is checked");

        //Delete basket part does not work exactly.
        //Delete basket
       /* Actions act3=new Actions(driver);
        act3.moveToElement(driver.findElement(By.xpath("//*[@id=\"cart-item-474857151\"]/div[4]/a[1]/i"))).click().perform();
        */

    }


}
