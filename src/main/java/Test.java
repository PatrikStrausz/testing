
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.text.DecimalFormat;
import java.util.Random;

import static java.lang.Math.PI;

public class Test {
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private WebDriver driver;

    WebElement errorMessage;

    private  WebElement amount;
    private WebElement interest;
    private WebElement periodRng;
    private WebElement periodLbl;
    private WebElement taxYes;
    private WebElement taxNo;
    private WebElement agreement;
    private WebElement resetBtn;
    private WebElement calculateBtn;
    private WebElement result;


    private Random rnd = new Random();
    private String amountErrorMsg = "Amount must be a number between 0 and 1000000 !";
    private String interestErrorMsg = "Interest must be a number between 0 and 100 !";
    private String agreementErrorMsg = "You must agree to the processing !";
    private String periodString;
    private String red = "rgba(255, 0, 0, 1)";
    private String darkGreen = "rgba(0, 100, 0, 1)";


    public Test(WebDriver driver) {
        this.driver = driver;


        errorMessage = driver.findElement(By.id("error"));
        amount = driver.findElement(By.id("amount"));
        interest = driver.findElement(By.id("interest"));
        periodRng = driver.findElement(By.id("period"));
        periodLbl = driver.findElement(By.id("lblPeriod"));
        taxYes = driver.findElement(By.cssSelector("input[value=\"y\"]"));
        taxNo = driver.findElement(By.cssSelector("input[value=\"n\"]"));
        agreement = driver.findElement(By.id("confirm"));
        resetBtn = driver.findElement(By.id("btnreset"));
        calculateBtn = driver.findElement(By.id("btnsubmit"));
        result = driver.findElement(By.id("result"));


    }

    public void defaultState(){
        Assert.assertFalse(errorMessage.isDisplayed());
        Assert.assertEquals(amount.getText(), "");
        Assert.assertEquals(interest.getText(), "");
        Assert.assertEquals(periodRng.getAttribute("value"), "1");
        periodString = periodLbl.getText();
        Assert.assertEquals(String.valueOf(periodString.charAt(periodString.length()-1)), "1");
        Assert.assertTrue(taxYes.isSelected());
        Assert.assertFalse(taxNo.isSelected());
        Assert.assertFalse(agreement.isSelected());
        Assert.assertFalse(result.isDisplayed());
    }

    public void amountNotFilled(){
        interest.sendKeys(String.valueOf(rnd.nextInt(100)));
        agreement.click();
        calculateBtn.click();
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getCssValue("color"), red);
        Assert.assertEquals(errorMessage.getText(), amountErrorMsg);
        resetBtn.click();
    }


    public void interestNotFilled() {
        amount.sendKeys(String.valueOf(rnd.nextInt(1000000)));
        for (int i = 1; i < 6; i++) {
            Assert.assertEquals(periodRng.getAttribute("value"), String.valueOf(i));
            periodString = periodLbl.getText();
            Assert.assertEquals(String.valueOf(periodString.charAt(periodString.length() - 1)), String.valueOf(i));
            periodRng.sendKeys(Keys.RIGHT);
        }
        taxYes.click();
        agreement.click();
        calculateBtn.click();
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(interestErrorMsg, "Interest must be a number between 0 and 100 !");
        resetBtn.click();
    }


    public void disagree(){
        amount.sendKeys(String.valueOf(rnd.nextInt(1000000)));
        interest.sendKeys(String.valueOf(rnd.nextInt(100)));
        for (int i = 1; i < 6; i++) {
            Assert.assertEquals(periodRng.getAttribute("value"), String.valueOf(i));
            periodString = periodLbl.getText();
            Assert.assertEquals(String.valueOf(periodString.charAt(periodString.length()-1)), String.valueOf(i));
            periodRng.sendKeys(Keys.RIGHT);
        }
        taxYes.click();
        calculateBtn.click();
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(agreementErrorMsg, "You must agree to the processing !");

        resetBtn.click();
    }

    public void fillNothing(){
        calculateBtn.click();
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getCssValue("color"), red);
        Assert.assertEquals(errorMessage.getText(), amountErrorMsg + "\n" + interestErrorMsg + "\n" + agreementErrorMsg);
    }

    public void negativeAmount(){
        amount.sendKeys("-"+String.valueOf(rnd.nextInt(1000000)));
        interest.sendKeys(String.valueOf(rnd.nextInt(100)));

        for (int i = 1; i < 6; i++) {
            Assert.assertEquals(periodRng.getAttribute("value"), String.valueOf(i));
            periodString = periodLbl.getText();
            Assert.assertEquals(String.valueOf(periodString.charAt(periodString.length()-1)), String.valueOf(i));
            periodRng.sendKeys(Keys.RIGHT);
        }

        taxYes.click();
        agreement.click();
        calculateBtn.click();
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(amountErrorMsg, "Amount must be a number between 0 and 1000000 !");
        resetBtn.click();
    }


    public void negativeInterest(){

        amount.sendKeys(String.valueOf(rnd.nextInt(1000000)));
        interest.sendKeys("-"+String.valueOf(rnd.nextInt(100)));

        for (int i = 1; i < 6; i++) {
            Assert.assertEquals(periodRng.getAttribute("value"), String.valueOf(i));
            periodString = periodLbl.getText();
            Assert.assertEquals(String.valueOf(periodString.charAt(periodString.length()-1)), String.valueOf(i));
            periodRng.sendKeys(Keys.RIGHT);
        }
        taxYes.click();
        agreement.click();
        calculateBtn.click();
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(interestErrorMsg, "Interest must be a number between 0 and 100 !");
        resetBtn.click();
    }

    public void negativeInterestAndAmount(){
        amount.sendKeys("-"+String.valueOf(rnd.nextInt(1000000)));
        interest.sendKeys("-"+String.valueOf(rnd.nextInt(100)));

        for (int i = 1; i < 6; i++) {
            Assert.assertEquals(periodRng.getAttribute("value"), String.valueOf(i));
            periodString = periodLbl.getText();
            Assert.assertEquals(String.valueOf(periodString.charAt(periodString.length()-1)), String.valueOf(i));
            periodRng.sendKeys(Keys.RIGHT);
        }
        taxYes.click();
        agreement.click();
        calculateBtn.click();
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(amountErrorMsg, "Amount must be a number between 0 and 1000000 !");
        Assert.assertEquals(interestErrorMsg, "Interest must be a number between 0 and 100 !");
        resetBtn.click();
    }


    public void amountOverMillion(){
        amount.sendKeys(String.valueOf(rnd.nextInt(1000000)+10000000));
        interest.sendKeys(String.valueOf(rnd.nextInt(100)));

        for (int i = 1; i < 6; i++) {
            Assert.assertEquals(periodRng.getAttribute("value"), String.valueOf(i));
            periodString = periodLbl.getText();
            Assert.assertEquals(String.valueOf(periodString.charAt(periodString.length()-1)), String.valueOf(i));
            periodRng.sendKeys(Keys.RIGHT);
        }

        taxYes.click();
        agreement.click();
        calculateBtn.click();
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(amountErrorMsg, "Amount must be a number between 0 and 1000000 !");
        resetBtn.click();
    }

    public void interestOverHundredPercent(){
        amount.sendKeys(String.valueOf(rnd.nextInt(1000000)));
        interest.sendKeys(String.valueOf(rnd.nextInt(100)+200));

        for (int i = 1; i < 6; i++) {
            Assert.assertEquals(periodRng.getAttribute("value"), String.valueOf(i));
            periodString = periodLbl.getText();
            Assert.assertEquals(String.valueOf(periodString.charAt(periodString.length()-1)), String.valueOf(i));
            periodRng.sendKeys(Keys.RIGHT);
        }
        taxYes.click();
        agreement.click();
        calculateBtn.click();
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(interestErrorMsg, "Interest must be a number between 0 and 100 !");
        resetBtn.click();
    }

    public void interestAndAmountOverValue(){
        amount.sendKeys("-"+String.valueOf(rnd.nextInt(1000000)+10000000));
        interest.sendKeys("-"+String.valueOf(rnd.nextInt(100)+200));

        for (int i = 1; i < 6; i++) {
            Assert.assertEquals(periodRng.getAttribute("value"), String.valueOf(i));
            periodString = periodLbl.getText();
            Assert.assertEquals(String.valueOf(periodString.charAt(periodString.length()-1)), String.valueOf(i));
            periodRng.sendKeys(Keys.RIGHT);
        }
        taxYes.click();
        agreement.click();
        calculateBtn.click();
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(amountErrorMsg, "Amount must be a number between 0 and 1000000 !");
        Assert.assertEquals(interestErrorMsg, "Interest must be a number between 0 and 100 !");
        resetBtn.click();

    }

    public void testResult(){

        amount.sendKeys(String.valueOf(10000));
        interest.sendKeys(String.valueOf(4));




        periodRng.sendKeys(String.valueOf(1));


        taxNo.click();

        agreement.click();


        calculateBtn.click();




        double value =  calculateResult(amount.getAttribute("value"), interest.getAttribute("value")
                ,periodRng.getAttribute("value"));

        double profit = value - Double.parseDouble(amount.getAttribute("value"));


        String total = String.format("%.2f", value).replace(",",".");
        String totalProfit = String.format("%.2f", profit).replace(",",".");

        Assert.assertEquals(result.getText(),"Total amount : "+ total+" , net profit : "+totalProfit);




        resetBtn.click();

    }

    public void testResult1(){

        amount.sendKeys(String.valueOf(0));
        interest.sendKeys(String.valueOf(0));


        periodRng.sendKeys(String.valueOf(1));
        taxNo.click();
        agreement.click();
        calculateBtn.click();

        double value =  calculateResult(amount.getAttribute("value"), interest.getAttribute("value")
                ,periodRng.getAttribute("value"));

        double profit = value - Double.parseDouble(amount.getAttribute("value"));


        String total = String.format("%.2f", value).replace(",",".");
        String totalProfit = String.format("%.2f", profit).replace(",",".");

        Assert.assertEquals(result.getText(),"Total amount : "+ total+" , net profit : "+totalProfit);

        resetBtn.click();

    }
    public void testResult2(){

        amount.sendKeys(String.valueOf(0.1));
        interest.sendKeys(String.valueOf(1));


        periodRng.sendKeys(String.valueOf(1));
        taxNo.click();
        agreement.click();
        calculateBtn.click();

        double value =  calculateResult(amount.getAttribute("value"), interest.getAttribute("value")
                ,periodRng.getAttribute("value"));

        double profit = value - Double.parseDouble(amount.getAttribute("value"));


        String total = String.format("%.2f", value).replace(",",".");
        String totalProfit = String.format("%.2f", profit).replace(",",".");

        Assert.assertEquals(result.getText(),"Total amount : "+ total+" , net profit : "+totalProfit);

        resetBtn.click();

    }

    public void testResult3(){

        amount.sendKeys(String.valueOf(1000));
        interest.sendKeys(String.valueOf(2));


        periodRng.sendKeys(String.valueOf(1));
        taxNo.click();
        agreement.click();
        calculateBtn.click();

        double value =  calculateResult(amount.getAttribute("value"), interest.getAttribute("value")
                ,periodRng.getAttribute("value"));

        double profit = value - Double.parseDouble(amount.getAttribute("value"));


        String total = String.format("%.2f", value).replace(",",".");
        String totalProfit = String.format("%.2f", profit).replace(",",".");

        Assert.assertEquals(result.getText(),"Total amount : "+ total+" , net profit : "+totalProfit);


        resetBtn.click();

    }

    public void testResult4(){

        amount.sendKeys(String.valueOf(80000));
        interest.sendKeys(String.valueOf(1.1));


        periodRng.sendKeys(String.valueOf(1));
        taxNo.click();
        agreement.click();
        calculateBtn.click();

        double value =  calculateResult(amount.getAttribute("value"), interest.getAttribute("value")
                ,periodRng.getAttribute("value"));

        double profit = value - Double.parseDouble(amount.getAttribute("value"));


        String total = String.format("%.2f", value).replace(",",".");
        String totalProfit = String.format("%.2f", profit).replace(",",".");

        Assert.assertEquals(result.getText(),"Total amount : "+ total+" , net profit : "+totalProfit);

        resetBtn.click();

    }

    public void testResult5(){

        amount.sendKeys(String.valueOf(999999));
        interest.sendKeys(String.valueOf(0.8));


        periodRng.sendKeys(String.valueOf(1));
        taxNo.click();
        agreement.click();
        calculateBtn.click();

        double value =  calculateResult(amount.getAttribute("value"), interest.getAttribute("value")
                ,periodRng.getAttribute("value"));

        double profit = value - Double.parseDouble(amount.getAttribute("value"));


        String total = String.format("%.2f", value).replace(",",".");
        String totalProfit = String.format("%.2f", profit).replace(",",".");

        Assert.assertEquals(result.getText(),"Total amount : "+ total+" , net profit : "+totalProfit);

        resetBtn.click();

    }


    public void testResult6(){

        amount.sendKeys(String.valueOf(1000000));
        interest.sendKeys(String.valueOf(1));


        periodRng.sendKeys(String.valueOf(1));
        taxNo.click();
        agreement.click();
        calculateBtn.click();

        double value =  calculateResult(amount.getAttribute("value"), interest.getAttribute("value")
                ,periodRng.getAttribute("value"));

        double profit = value - Double.parseDouble(amount.getAttribute("value"));


        String total = String.format("%.2f", value).replace(",",".");
        String totalProfit = String.format("%.2f", profit).replace(",",".");

        Assert.assertEquals(result.getText(),"Total amount : "+ total+" , net profit : "+totalProfit);

        resetBtn.click();

    }

    public void testResult7(){

        amount.sendKeys(String.valueOf(-41));
        interest.sendKeys(String.valueOf(1));


        periodRng.sendKeys(String.valueOf(4));
        taxNo.click();
        agreement.click();
        calculateBtn.click();


        Assert.assertEquals(amountErrorMsg,"Amount must be a number between 0 and 1000000 !");

        resetBtn.click();

    }

    public void testResult8(){

        amount.sendKeys(String.valueOf(897879));
        interest.sendKeys(String.valueOf(-1));


        periodRng.sendKeys(String.valueOf(4));
        taxNo.click();
        agreement.click();
        calculateBtn.click();

        Assert.assertEquals(amountErrorMsg,"Amount must be a number between 0 and 1000000 !");


        resetBtn.click();

    }

    double calculateResult(String amount, String interest, String period){
        double k = Double.parseDouble(amount);
        int t = Integer.parseInt(period);

        double d =  Math.pow(1 + Double.parseDouble(interest) / 100,t );
        return k * d;
    }






}
