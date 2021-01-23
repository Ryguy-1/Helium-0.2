package package1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

//creates new Selenium Monitoring Bots. Has a member variable for which site it is monitoring.
public class SeleniumBot {

    private ArrayList<WebHookSeleniumManager> myManagers;

    private WebDriver driver;
    private String monitoringURL;
    private Thread t1;
    private boolean isAvailable;
    private int currentPrice;
    private boolean isActive;
    private boolean isVisible;


    //Timings For How Long to Wait for Different Retailers -> If wrong can cause false positives or time an IP out. These work well as Stock. Adjust at Your Own Risk
    public static int amazonIn = 30;
    public static int amazonOut = 180;

    public static int bestBuyIn = 12;
    public static int bestBuyOut = 20;

    public static int targetIn = 12;
    public static int targetOut = 20;

    public static int walmartIn = 40;
    public static int walmartOut = 120;


    SeleniumBot(String monitoringURL, boolean isVisible) {
        this.monitoringURL = monitoringURL;
        currentPrice = 0;
        isActive = false;
        this.isVisible = isVisible;
        this.myManagers = new ArrayList<>();
        initializeBot();
        runBot();
    }


    public void runBot() {
        driver.get(monitoringURL);
        System.out.println("Gotten URL");
        pause(4);
        if (monitoringURL.contains("amazon")) {
            t1 = new Thread(() -> {
                WebElement price = driver.findElement(By.xpath("//*[@id=\"availability\"]/span"));
                System.out.println(price.getText());
                while(true) {
                    while (isActive) {
                        while (!isAvailable) {
                            // when the product is not available and need to check when the product becomes
                            // available
                            try {
                                WebElement price2 = driver.findElement(By.xpath("//*[@id=\"availability\"]/span"));
                                if (!price2.getText().contains("Currently unavailable.")) {
                                    try {
                                        sendMyManagers(monitoringURL,
                                                driver.findElement(By.id("priceblock_ourprice")).getText(), "Amazon", true,
                                                false, driver.findElement(By.id("productTitle")).getText(),
                                                driver.findElement(By.xpath("//*[@id=\"landingImage\"]"))
                                                        .getAttribute("data-old-hires"),
                                                "N/A",
                                                "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                        // makes current price for next session when monitoring price
                                        currentPrice = Integer.parseInt(
                                                driver.findElement(By.id("priceblock_ourprice")).getText().substring(1,
                                                        driver.findElement(By.id("priceblock_ourprice")).getText().length()
                                                                - 3));
                                    } catch (Exception e) {
                                        sendMyManagers(monitoringURL, "Unknown", "Amazon", true, false,
                                                driver.findElement(By.id("productTitle")).getText(),
                                                driver.findElement(By.xpath("//*[@id=\"landingImage\"]"))
                                                        .getAttribute("data-old-hires"),
                                                "N/A",
                                                "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                    }
                                    isAvailable = true;
                                }
                            } catch (Exception e) {
                                try {
                                    sendMyManagers(monitoringURL,
                                            driver.findElement(By.id("priceblock_ourprice")).getText(), "Amazon", true,
                                            false, driver.findElement(By.id("productTitle")).getText(),
                                            driver.findElement(By.xpath("//*[@id=\"landingImage\"]"))
                                                    .getAttribute("data-old-hires"),
                                            "N/A",
                                            "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                    // makes current price for next session when monitoring price
                                    currentPrice = Integer.parseInt(
                                            driver.findElement(By.id("priceblock_ourprice")).getText().substring(1,
                                                    driver.findElement(By.id("priceblock_ourprice")).getText().length()
                                                            - 3));
                                } catch (Exception e2) {
                                    sendMyManagers(monitoringURL, "Unknown", "Amazon", true, false,
                                            driver.findElement(By.id("productTitle")).getText(),
                                            driver.findElement(By.xpath("//*[@id=\"landingImage\"]"))
                                                    .getAttribute("data-old-hires"),
                                            "N/A",
                                            "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                }
                                isAvailable = true;
                            }
                            driver.navigate().refresh();
                            pause(amazonIn); //pause for in stock
                        }
                        while (isAvailable) {
                            // while product is available and need to check if the product becomes
                            // unavailable
                            try {
                                WebElement price2 = driver.findElement(By.id("priceblock_ourprice"));
//							// checks to see if the price has lowered or if it became unavailable.
                                System.out.println("Price with substring = "
                                        + Integer.parseInt(price2.getText().substring(1, price2.getText().length() - 3)));
                                if (Integer.parseInt(
                                        price2.getText().substring(1, price2.getText().length() - 3)) != currentPrice) {
                                    System.out.println("Product is available for lower price!!!!!!");
                                    sendMyManagers(monitoringURL, price2.getText(), "Amazon", false, true,
                                            driver.findElement(By.id("productTitle")).getText(),
                                            driver.findElement(By.xpath("//*[@id=\"landingImage\"]"))
                                                    .getAttribute("data-old-hires"),
                                            "N/A",
                                            "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                    currentPrice = Integer
                                            .parseInt(price2.getText().substring(1, price2.getText().length() - 3));
                                }
                            } catch (Exception e) {
                                isAvailable = false;
                            }
                            driver.navigate().refresh();
                            pause(amazonOut);//pause for out of stock
                        }
                    }
                    pause (2);
                }
            });

            t1.start();

        }
        else if (monitoringURL.contains("bestbuy")) {
            t1 = new Thread(() -> {

                while (true) {
                    while (isActive) {
                        while (!isAvailable) {
                            System.out.println("Is not Available");
                            // when the product is not available and need to check when the product becomes
                            // available
                            try {
                                WebElement noThx = driver.findElement(By.id("survey_invite_no"));
                                noThx.click();
                                pause(1);
                            } catch (Exception e) {}

                            List<WebElement> elements = driver.findElements(By.className("sr-only"));
                            int idx = 0;
                            int counter = 0;
                            for (int i = 0; i < elements.size(); i++) {
                                if (elements.get(i).getText().contains("Your price for this item")) {
                                    idx = i;
                                    counter++;
                                    break;
                                }
                            }
                            WebElement price = elements.get(idx);
                            List<WebElement> buttonsAddToCart = driver
                                    .findElements(By.xpath("//*[@class='product-data-value body-copy']"));
                            String SKU = "";

                            for (int i = 0; i < buttonsAddToCart.size(); i++) {
                                if (buttonsAddToCart.get(i).getText().length() == 7) {
                                    SKU = buttonsAddToCart.get(i).getText();
                                }
                            }
                            try {
                                WebElement buttonAddToCart = driver
                                        .findElement(By.className("fulfillment-add-to-cart-button"));
                                System.out.println("ran this");
                                if (!buttonAddToCart.getText().contains("Sold Out")
                                        && !buttonAddToCart.getText().contains("Find a Store") && !buttonAddToCart.getText().contains("Pre-Order") && !buttonAddToCart.getText().contains("Coming Soon")) {
                                    try {
                                        sendMyManagers(monitoringURL,
                                                price.getText().substring(28,
                                                        price.getText().length()),
                                                "Best Buy", true, false,
                                                driver.findElement(By.className("sku-title")).getText(),
                                                driver.findElement(By.className("primary-image")).getAttribute("src"),
                                                SKU,
                                                "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                        // makes current price for next session when monitoring price
                                        currentPrice = Integer.parseInt(price.getText()
                                                .substring(price.getText().length() - 5, price.getText().length() - 3));
                                        System.out.println("sent from 1");

                                    } catch (Exception e) {
                                        sendMyManagers(monitoringURL,
                                                price.getText().substring(28,
                                                        price.getText().length()),
                                                "Best Buy", true, false,
                                                driver.findElement(By.className("sku-title")).getText(),
                                                driver.findElement(By.className("primary-image")).getAttribute("src"),
                                                SKU,
                                                "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                        System.out.println("sent from 2");
                                    }
                                    isAvailable = true;
                                }
                            } catch (Exception e) {
                                try {
                                    sendMyManagers(monitoringURL,
                                            price.getText().substring(28,
                                                    price.getText().length()),
                                            "Best Buy", true, false,
                                            driver.findElement(By.className("sku-title")).getText(),
                                            driver.findElement(By.className("primary-image")).getAttribute("src"), SKU,
                                            "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                    // makes current price for next session when monitoring price
                                    currentPrice = Integer.parseInt(price.getText()
                                            .substring(price.getText().length() - 5, price.getText().length() - 3));
                                } catch (Exception e2) {
                                    System.out.println(price.getText());
                                    sendMyManagers(monitoringURL,
                                            price.getText().substring(28),
                                            "Best Buy", true, false,
                                            driver.findElement(By.className("sku-title")).getText(),
                                            driver.findElement(By.className("primary-image")).getAttribute("src"), SKU,
                                            "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                }
                                isAvailable = true;
                            }
                            driver.navigate().refresh();
                            pause(bestBuyIn);
                        }

                        while (isAvailable) {
                            // while product is available and need to check if the product becomes
                            // unavailable
                            try {
                                WebElement noThx = driver.findElement(By.id("survey_invite_no"));
                                noThx.click();
                                pause(1);
                                System.out.println("caught no thanks");
                            } catch (Exception e) {}

                            try {
                                List<WebElement> elements = driver.findElements(By.className("sr-only"));
                                int idx = 0;
                                int counter = 0;
                                for (int i = 0; i < elements.size(); i++) {
                                    if (elements.get(i).getText().contains("Your price for this item")) {
                                        idx = i;
                                        counter++;
                                        break;
                                    }
                                }
                                WebElement price = elements.get(idx);
                                List<WebElement> buttonsAddToCart = driver
                                        .findElements(By.xpath("//*[@class='product-data-value body-copy']"));
                                String SKU = "";

                                for (int i = 0; i < buttonsAddToCart.size(); i++) {
                                    if (buttonsAddToCart.get(i).getText().length() == 7) {
                                        SKU = buttonsAddToCart.get(i).getText();
                                    }
                                }
                                if (!driver.findElement(By.className("fulfillment-add-to-cart-button")).getText()
                                        .contains("Add to Cart")) {
                                    System.out.println("Sold out again");
                                    isAvailable = false;
                                } else if (Integer.parseInt(price.getText().substring(price.getText().length() - 5,
                                        price.getText().length() - 3)) != currentPrice) {
                                    System.out.println("Product is available for lower price!!!!!!");
                                    sendMyManagers(monitoringURL,
                                            price.getText().substring(28,
                                                    price.getText().length()),
                                            "Best Buy", false, true,
                                            driver.findElement(By.className("sku-title")).getText(),
                                            driver.findElement(By.className("primary-image")).getAttribute("src"), SKU,
                                            "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                    currentPrice = Integer.parseInt(price.getText()
                                            .substring(price.getText().length() - 5, price.getText().length() - 3));
                                }
                            } catch (Exception e) {
                                isAvailable = false;
                            }
                            driver.navigate().refresh();
                            pause(bestBuyOut);
                        }
                    }
                    pause(2);
                }
            });
            t1.start();

        }
        else if (monitoringURL.contains("target")) {

            t1 = new Thread(() -> {
                while (true) {
                    while (isActive) {
                        while (!isAvailable) {
                            boolean avail1;
                            try {
                                driver.findElement(By.xpath("//*[text()='Ship it']")).click(); // "availability":"OutOfStock"
                                // ->
                                // Broken feature on target werido
                                // this dismiss message pops up if it is not available even though the ship it
                                // button pops up.
                                pause(1);
                                try {
                                    driver.findElement(By.xpath("//*[text()='Dismiss']")); // Dismiss
                                    avail1 = false;
                                } catch (Exception e) {
                                    // if there is no dismiss button
                                    avail1 = true;
                                }
                                // if no error is thrown, avail1 is true.
                            } catch (Exception e) {
                                avail1 = false;
                            }
                            // substring gets rid of price sign
                            String priceText = driver
                                    .findElement(By.xpath(
                                            "//*[@id=\"viewport\"]/div[5]/div/div[2]/div[2]/div[1]/div[1]/div[1]"))
                                    .getText()
                                    .substring(1);

                            // if at least one of the two buttons exists. Product is available.
                            if (avail1) { // was: avail1 == true || avail2 == true
                                sendMyManagers(monitoringURL, priceText, "Target", true, false,
                                        driver.findElement(
                                                By.xpath("//*[@id=\"viewport\"]/div[5]/div/div[1]/div[2]/h1"))
                                                .getText(),
                                        driver.findElement(By.xpath(
                                                "//*[@id=\"viewport\"]/div[5]/div/div[2]/div[1]/div/div/div/div[1]/div/div[1]/button/img"))
                                                .getAttribute("src"),
                                        "N/A",
                                        "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                // makes current price for next session when monitoring price
                                currentPrice = Integer.parseInt(driver
                                        .findElement(By.xpath(
                                                "//*[@id=\"viewport\"]/div[5]/div/div[2]/div[2]/div[1]/div[1]/div[1]"))
                                        .getText()
                                        .substring(1, driver.findElement(By.xpath(
                                                "//*[@id=\"viewport\"]/div[5]/div/div[2]/div[2]/div[1]/div[1]/div[1]"))
                                                .getText().length() - 3));
                                System.out.println("sent from 1");
                                isAvailable = true;
                            }
                            driver.navigate().refresh();
                            pause(targetIn);
                        }
                        while (isAvailable) {
                            boolean avail1;
                            try {
                                driver.findElement(By.xpath("//*[text()='Ship it']"));
                                avail1 = true;
                            } catch (Exception e) {
                                avail1 = false;
                            }
                            if (avail1 == false) {
                                isAvailable = false;
                            }
                            driver.navigate().refresh();
                            pause(targetOut);
                        }
                    }
                    pause(2);
                }
            });
            t1.start();

        }
        else if (monitoringURL.contains("walmart")) {

            // can use xpaths only if window stays a size which doesn't affect the html
            t1 = new Thread(() -> {
                while (true) {
                    while (isActive) {

                        while (!isAvailable) {

                            boolean avail1;

                            try {
                                driver.findElement(By.xpath("//*[text()='Get in-stock alert']")); // "availability":"OutOfStock"
                                // ->
                                // Broken feature on target werido
                                // only false if the out of stock element cannot be found.
                                avail1 = false;
                            } catch (Exception e) {
                                avail1 = true;
                            }
                            // substring gets rid of price sign
                            String priceText;
                            try {
                                priceText = driver.findElement(By.xpath("//*[@id=\"price\"]/div/span[1]/span/span[2]/span[2]"))
                                        .getAttribute("content");
                                System.out.println("priceText is " + priceText);
                            }catch(Exception e) {
                                priceText = "No Listed Price";
                            }
                            // if at least one of the two buttons exists. Product is available.
                            if (avail1) { // was: avail1 == true || avail2 == true
                                // JOptionPane.showMessageDialog(null, "avail1 is true now.");
                                sendMyManagers(monitoringURL, priceText, "Walmart", true, false,
                                        driver.findElement(
                                                By.xpath("//*[@id=\"product-overview\"]/div/div[3]/div/h1"))
                                                .getText(),
                                        driver.findElement(By.className("prod-alt-image-carousel-image--left"))
                                                .getAttribute("src"),
                                        driver.findElement(By.xpath("//*[contains(text(),'Walmart #')]")).getText().substring(driver.findElement(By.xpath("//*[contains(text(),'Walmart #')]")).getText().length()-8),
                                        "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                // makes current price for next session when monitoring price
                                isAvailable = true;
                            }
                            driver.navigate().refresh();
                            pause(walmartIn);
                        }
                        while (isAvailable) {
                            try {
                                driver.findElement(By.xpath("//*[text()='Get in-stock alert']"));
                                isAvailable = false;
                            } catch (Exception e) {
                                isAvailable = true;
                            }
                            driver.navigate().refresh();
                            pause(walmartOut);
                        }
                    }
                    pause(2);
                }
            });
            t1.start();
        }
    }

    private void sendMyManagers(String URL, String price, String website, boolean isRestock, boolean isPriceChange,
                                String productTitle, String imageURL, String SKU, String logo) {

        for (int i = 0; i < myManagers.size(); i++) {
            myManagers.get(i).sendProductAvailable(URL, price, website, isRestock, isPriceChange, productTitle,
                    imageURL, SKU, logo);
        }

    }

    public void addManager(WebHookSeleniumManager manager) {

        myManagers.add(manager);
    }

    public void setIsActiveTrue() {
        isActive = true;
    }

    public void removeManager(WebHookSeleniumManager manager) {
        for (int i = 0; i < myManagers.size(); i++) {
            if (myManagers.get(i).equals(manager)) {
                myManagers.remove(i);
            }
        }

    }

    public void quitBot() {

        isActive = false;
        driver.quit();

    }

    public void initializeBot() {
		//test invisible
		if(!isVisible) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--silent");
            //end test

            driver = new ChromeDriver(options);// would put "options" as the parameter for testing
            resizeWindow(driver, 1500, 800);
        }else{
            driver = new ChromeDriver();// would put "options" as the parameter for testing
            resizeWindow(driver, 1500, 800);
        }




    }

    public void resizeWindow(WebDriver driver, int width, int height) {
        Dimension d = new Dimension(width, height);
        // Resize current window to the set dimension
        driver.manage().window().setSize(d);

    }

    public static void pause(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
