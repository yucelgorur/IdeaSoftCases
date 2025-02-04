package BasketPageTests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BasketPage;
import utilities.DriverManager;

public class BasketPageTestsSet {
    WebDriver driver;
    BasketPage basketPage;

    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        driver.get("https://qatestcase.myideasoft.com/");
        basketPage = new BasketPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            DriverManager.driver.quit();
        }
    }

    @Test
    public void checkQuantityofProductsAddedtoCart() throws InterruptedException {
        basketPage.searchProduct("ürün");
        String searchPageProductTitle = basketPage.searchAndSelectFirtProduct();
        String detailPageTitle = basketPage.getProductTitleOnDetailPage();
        basketPage.verifyProductTitle(searchPageProductTitle, detailPageTitle);
        basketPage.addToCartWithQuantity(5);
        basketPage.verifySuccessMessage();
        basketPage.verifyProductQuantityInCart(5);
    }
}
