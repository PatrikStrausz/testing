import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Main {

    public static void main(String[] args) {


        String url = "http://itsovy.sk/testing";

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\patko\\Desktop\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get(url);


        Test test = new Test(driver);


        test.defaultState();

        test.amountNotFilled();

        test.interestNotFilled();

        test.disagree();

        test.fillNothing();

        test.negativeAmount();
        test.negativeInterest();
        test.negativeInterestAndAmount();
        test.amountOverMillion();
        test.interestOverHundredPercent();
        test.interestAndAmountOverValue();

        test.testResult();
        test.testResult1();
        test.testResult2();
        test.testResult3();
        test.testResult4();
        test.testResult5();
        test.testResult6();
        test.testResult7();
        test.testResult8();


        driver.quit();

    }


}
