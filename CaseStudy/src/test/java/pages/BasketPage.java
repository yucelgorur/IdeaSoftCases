package pages;

import jdk.jfr.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class BasketPage {
    WebDriver driver;
    public BasketPage(WebDriver driver) {this.driver = driver;PageFactory.initElements(driver, this);}

    @FindBy(xpath = "//input[@aria-label='Search']")
    public WebElement searchBox;
    @FindBy(xpath = "(//div[contains(@class, 'suggestion-title')])[1]")
    public WebElement firstProductSearchResult;
    @FindBy(xpath = "(//div[contains(@class, 'product-title')])")
    public WebElement productTitleElement;
    @FindBy(xpath = "//input[@data-stocktype='Piece']")
    public WebElement quantityBox;
    @FindBy(xpath = "//a[@class='add-to-cart-button']")
    public WebElement addToCartButton;
    @FindBy(xpath = "//div[@class='cart-amount']")
    public WebElement quantityElement;

    public void searchProduct(String productName){
        searchBox.click();
        System.out.println("Clicked Search Box");
        searchBox.sendKeys(productName);
        System.out.println("Send Product Name in Search Box");
    }
    public String searchAndSelectFirtProduct() throws InterruptedException {
        Thread.sleep(800);
        String productText = firstProductSearchResult.getText();
        System.out.println("First Product Text in Search Result: " + productText);
        firstProductSearchResult.click();
        System.out.println("Clicked First Product in Search List");
        return productText;
    }

    public String getProductTitleOnDetailPage() {
        String productText = productTitleElement.getText();
        System.out.println("Product Text on the Product Detail Page " + productText);
        return productText;
    }
    public void verifyProductTitle(String searchTitle, String detailTitle) {
        Assert.assertEquals(detailTitle, searchTitle, "Product names are not the same!");
        System.out.println("Step is Successful! Product Names are the Same");
    }

    public void addToCartWithQuantity(int quantity) {
        quantityBox.clear();
        quantityBox.sendKeys(String.valueOf(quantity));
        addToCartButton.click();
        System.out.println(quantity + " items have been added to the cart.");
    }

    public void verifySuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'SEPETİNİZE EKLENMİŞTİR')]")));
            System.out.println("Success message found: " + successMessage.getText());
            Thread.sleep(2000);
            Assert.assertNotNull("Success message not found", String.valueOf(successMessage));
        } catch (TimeoutException e) {
            System.out.println("Success message did not appear!");
            Assert.fail("Success message did not appear!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void verifyProductQuantityInCart(int expectedQuantity) {
        driver.get("https://qatestcase.myideasoft.com/sepet");
        String quantityValue = quantityBox.getAttribute("value").trim();
        System.out.println("Current quantity in cart: " + quantityValue);
        System.out.println("Expected quantity: " + expectedQuantity);

        boolean quantitiesMatch = quantityValue.equals(String.valueOf(expectedQuantity));

        if (quantitiesMatch) {
            System.out.println("The quantities match.");
            Assert.assertTrue(quantitiesMatch, "The quantities match.");
        } else {
            System.out.println("The quantities do not match. Test failed!");
            Assert.fail("The quantities do not match. Test failed!");
        }
    }



}
