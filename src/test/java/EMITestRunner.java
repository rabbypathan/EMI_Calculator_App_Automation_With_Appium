import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class EMITestRunner extends Setup {

    @BeforeTest
    public void startEMI(){
        EMIScreen emiScreen = new EMIScreen(driver);
        emiScreen.btnStart.click();
    }

    @Test(priority = 1, dataProviderClass = DataSet.class, dataProvider = "data-provider", description = "Calculate EMI")
    public void calculateEMI(int amount, double interest, int year, double processingFee, int monthlyEMI, int totalInterest, int tProcessingFee, int totalPayment){
        EMIScreen emiScreen = new EMIScreen(driver);
        emiScreen.calculateEMI(amount, interest, year, processingFee);

//  String str = "123.45"; // The string you want to convert
//
//  Step 1: Parse the string to get a double
//        double myDouble = Double.parseDouble(str);
//
//  Step 2: Convert the double to an int
//        int roundedInt = (int) Math.round(myDouble);

        double mEMI = Double.parseDouble(emiScreen.lblMonthlyEMI.getText().replace(",", ""));
        double tInterest = Double.parseDouble(emiScreen.lblTotalInterest.getText().replace(",", ""));
        double pFee = Double.parseDouble(emiScreen.lblProcessingFee.getText().replace(",", ""));
        double tPayment = Double.parseDouble(emiScreen.lblTotalPayment.getText().replace(",", ""));

        int actualMonthlyEMI = (int) Math.round(mEMI);
        int actualTotalInterest = (int) Math.round(tInterest);
        int actualProcessingFee = (int) Math.round(pFee);
        int actualTotalPayment = (int) Math.round(tPayment);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualMonthlyEMI,monthlyEMI);
        softAssert.assertEquals(actualTotalInterest,totalInterest);
        softAssert.assertEquals(actualProcessingFee,tProcessingFee);
        softAssert.assertEquals(actualTotalPayment,totalPayment);
        softAssert.assertAll();

        emiScreen.btnReset.click();
    }
}
