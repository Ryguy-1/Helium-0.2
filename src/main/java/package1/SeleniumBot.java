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
    private int counter;
    private WebDriver driver;
    private String monitoringURL;
    private Thread t1;
    private boolean isAvailable;
    private int currentPrice;
    private String[] proxyIPs = { "209.127.191.180", "45.72.30.159", "84.21.191.63", "45.130.125.157", "45.134.184.43",
            "45.136.172.123", "193.8.215.243", "45.136.231.226", "45.130.255.198", "45.95.99.226" };
    private String[] proxyPorts = { "80", "80", "80", "80", "80", "80", "80", "80", "80", "80" };
    private int proxyIdx;
    private boolean isActive;

    SeleniumBot(String monitoringURL) {
        this.monitoringURL = monitoringURL;
        currentPrice = 0;
        proxyIdx = 0;
        isActive = false;
        this.myManagers = new ArrayList<WebHookSeleniumManager>();
        // isAvailable = false;
        initializeBot();
        runBot();
    }

    // target, gamestop -> Never restocks so no and kicks easily, walmart(maybe)
    public void runBot() {
        System.out.println("runBot() working");
        driver.get(monitoringURL);
        pause(4);
        System.out.println("bot waiting for link to equal");
        if (monitoringURL.contains("amazon")) {
            t1 = new Thread(() -> {
                System.out.println("IT'S AMAZON FOR SURE");
                // System.out.println("trying to get url");

                //// *[@id="priceblock_ourprice"]
                // *[@id="availability"]/span
                // *[@id="availability"]/span
                WebElement price = driver.findElement(By.xpath("//*[@id=\"availability\"]/span"));
                System.out.println(price.getText());
                while(true) {
                    while (isActive) {
                        System.out.println("IT'S AMAZON ACTIVE FOR SURE");
                        while (!isAvailable) {
                            // when the product is not available and need to check when the product becomes
                            // available
                            try {
                                WebElement price2 = driver.findElement(By.xpath("//*[@id=\"availability\"]/span"));
                                if (!price2.getText().contains("Currently unavailable.")) {
                                    // System.out.println("ERRORRR");

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
                                        System.out.println("sent from 1");

                                    } catch (Exception e) {
                                        sendMyManagers(monitoringURL, "Unknown", "Amazon", true, false,
                                                driver.findElement(By.id("productTitle")).getText(),
                                                driver.findElement(By.xpath("//*[@id=\"landingImage\"]"))
                                                        .getAttribute("data-old-hires"),
                                                "N/A",
                                                "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                        System.out.println("sent from 2");
                                    }

                                    isAvailable = true;
                                }

                            } catch (Exception e) {
                                // System.out.println("ERRORRR");

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
                                    System.out.println("sent from 3");
                                } catch (Exception e2) {
                                    sendMyManagers(monitoringURL, "Unknown", "Amazon", true, false,
                                            driver.findElement(By.id("productTitle")).getText(),
                                            driver.findElement(By.xpath("//*[@id=\"landingImage\"]"))
                                                    .getAttribute("data-old-hires"),
                                            "N/A",
                                            "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                    System.out.println("sent from 4");
                                }

                                isAvailable = true;
                            }

                            counter++;
                            // System.out.println(counter);
                            driver.navigate().refresh();

                            pause(30);

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
                                // System.out.println("Lower price did not work!!!!!!!");

                                isAvailable = false;
                            }

                            counter++;
                            // System.out.println(counter);
                            driver.navigate().refresh();

                            pause(180); //once every 3 mins when the product is available cause amazon doesn't restock very fast.

                        }

                    }
                    pause (2);
                }

            });

            t1.start();

        } else if (monitoringURL.contains("bestbuy")) {

            t1 = new Thread(() -> {

                // int timesDown = 6;
                while (true) {
                    while (isActive) {
                        while (!isAvailable) {
                            // when the product is not available and need to check when the product becomes
                            // available

                            try {

                                WebElement noThx = driver.findElement(By.id("survey_invite_no"));
                                noThx.click();
                                pause(1);
                                System.out.println("caught no thanks");
                            } catch (Exception e) {

                            }

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

                            // WebElement price = driver.findElement(By.className("priceView-hero-price
                            // priceView-customer-price"));

                            try {
                                WebElement buttonAddToCart = driver
                                        .findElement(By.className("fulfillment-add-to-cart-button"));
                                System.out.println("ran this");
                                if (!buttonAddToCart.getText().contains("Sold Out")
                                        && !buttonAddToCart.getText().contains("Find a Store") && !buttonAddToCart.getText().contains("Pre-Order") && !buttonAddToCart.getText().contains("Coming Soon")) {
                                    //System.out.println("this product is sold out");

                                    try {
//										System.out.println(monitoringURL + " || " + price.getText().substring(price.getText().length() - 6,
//												price.getText().length()) + " || " + "Best Buy" + " || " + "true" + "  ||  "+ "false" + "  ||  "+driver.findElement(By.className("sku-title")).getText()  + "  ||  "+ driver.findElement(By.className("primary-image")).getAttribute("src")
//												+ "  ||  "+ SKU + "  ||  "+ "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
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
                                    System.out.println(price.getText());
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
                                    System.out.println("sent from 3");
                                } catch (Exception e2) {
                                    System.out.println(price.getText());
                                    sendMyManagers(monitoringURL,
                                            price.getText().substring(28,
                                                    price.getText().length()),
                                            "Best Buy", true, false,
                                            driver.findElement(By.className("sku-title")).getText(),
                                            driver.findElement(By.className("primary-image")).getAttribute("src"), SKU,
                                            "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
                                    System.out.println("sent from 4");
                                }

                                isAvailable = true;

                            }

                            counter++;
                            // System.out.println(counter);
                            driver.navigate().refresh();

                            swapProxy();

                            pause(12);

                        }

                        while (isAvailable) {
                            // while product is available and need to check if the product becomes
                            // unavailable

                            try {

                                WebElement noThx = driver.findElement(By.id("survey_invite_no"));
                                noThx.click();
                                pause(1);
                                System.out.println("caught no thanks");
                            } catch (Exception e) {

                            }

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

                                // WebElement price = driver.findElement(By.className("priceView-hero-price
                                // priceView-customer-price"));

                                // System.out.println(currentPrice + " = currentPrice");
                                // System.out.println("substring price = " + Integer.parseInt(price.getText()
                                // .substring(price.getText().length() - 5, price.getText().length() - 3)));

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
                                System.out.println("Lower price did not work!!!!!!!");

                                isAvailable = false;
                            }

                            counter++;
                            // System.out.println(counter);
                            driver.navigate().refresh();

                            swapProxy();

                            pause(20);

                        }

                    }
                    pause(2);
                }

            });

            t1.start();

        } else if (monitoringURL.contains("target")) {

            // can use xpaths only if window stays a size which doesn't affect the html
            t1 = new Thread(() -> {
                while (true) {
                    while (isActive) {

                        while (!isAvailable) {
                            // when the product is not available and need to check when the product becomes
                            // available

                            // if one or both exists, this means it is available -> the larger if statement
                            // should be true
                            // if neither exists, this means it is not available -> the larger if statement
                            // should be false

//						boolean avail1 = true;
//						boolean avail2 = true;
//
//						try {
//							WebElement deliverIt = driver.findElement(By.xpath(
//									"//*[@id=\"viewport\"]/div[5]/div/div[2]/div[3]/div[1]/div/div[2]/div[1]/div[2]/button"));
//							//all 3
//									//*[@id="viewport"]/div[5]/div/div[2]/div[3]/div[1]/div/div[1]/div/div[1]/div[2]/button -> pick it up
//									//*[@id="viewport"]/div[5]/div/div[2]/div[3]/div[1]/div/div[2]/div[1]/div[2]/button -> deliver it
//									//*[@id="viewport"]/div[5]/div/div[2]/div[3]/div[1]/div/div[3]/div[1]/div[2]/button -> ship it
//
//							//just ship it
//									//*[@id="viewport"]/div[5]/div/div[2]/div[3]/div[1]/div/div[3]/div[1]/div[2]/button -> ship it
//
//							//pick up here and ship it
//									//*[@id="viewport"]/div[5]/div/div[2]/div[3]/div[1]/div/div[1]/div/div[2]/div[1]/div[2]/button -> pick up here
//									//*[@id="viewport"]/div[5]/div/div[2]/div[3]/div[1]/div/div[3]/div[1]/div[2]/button -> ship it
//									//*[@id="viewport"]/div[5]/div/div[2]/div[3]/div[1]/div/div[1]/div[1]/div[2]/button
//						} catch (Exception e) {
//							avail1 = false;
//						}
//
//						try {
//							WebElement shipIt = driver.findElement(By.xpath(
//									"//*[@id=\"viewport\"]/div[5]/div/div[2]/div[3]/div[1]/div/div[3]/div[1]/div[2]/button"));
//
//						} catch (Exception e) {
//							avail2 = false;
//						}

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
                                    System.out.println("CAUGHT FALSE POSITIVE WITH DISMISS IDENTIFICATION");
                                    avail1 = false;
                                } catch (Exception e) {
                                    // if there is no dismiss button
                                    avail1 = true;
                                }

                                // if no error is thrown, avail1 is true.

                            } catch (Exception e) {
                                avail1 = false;
                                // System.out.println("ship it could not find");
                            }

                            // substring gets rid of price sign
                            String priceText = driver
                                    .findElement(By.xpath(
                                            "//*[@id=\"viewport\"]/div[5]/div/div[2]/div[2]/div[1]/div[1]/div[1]"))
                                    .getText()
                                    .substring(1, driver.findElement(By.xpath(
                                            "//*[@id=\"viewport\"]/div[5]/div/div[2]/div[2]/div[1]/div[1]/div[1]"))
                                            .getText().length());

                            // if at least one of the two buttons exists. Product is available.
                            if (avail1) { // was: avail1 == true || avail2 == true
                                // JOptionPane.showMessageDialog(null, "avail1 is true now.");
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

                            counter++;
                            // System.out.println(counter);
                            driver.navigate().refresh();

                            swapProxy();

                            pause(12);

                        }

                        while (isAvailable) {
                            // while product is available and need to check if the product becomes
                            // unavailable

//						boolean avail1 = true;
//						boolean avail2 = true;
//
//						try {
//							WebElement deliverIt = driver.findElement(By.xpath(
//									"//*[@id=\"viewport\"]/div[5]/div/div[2]/div[3]/div[1]/div/div[2]/div[1]/div[2]/button"));
//						} catch (Exception e) {
//							avail1 = false;
//						}
//
//						try {
//							WebElement shipIt = driver.findElement(By.xpath(
//									"//*[@id=\"viewport\"]/div[5]/div/div[2]/div[3]/div[1]/div/div[3]/div[1]/div[2]/button"));
//						} catch (Exception e) {
//							avail2 = false;
//						}

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

                            counter++;
                            // System.out.println(counter);
                            driver.navigate().refresh();

                            swapProxy();

                            pause(20);

                        }

                    }
                    pause(2);
                }

            });

            t1.start();

        } else if (monitoringURL.contains("gamestop")) {

            // can use xpaths only if window stays a size which doesn't affect the html
            t1 = new Thread(() -> {
                while (true) {
                    while (isActive) {

                        while (!isAvailable) {

                            try {

                                WebElement noThx = driver.findElement(By.xpath("/html/body/div[15]/div[10]/div/div"));
                                noThx.click();
                                pause(1);
                                System.out.println("caught no thanks");
                            } catch (Exception e) {

                            }

                            String data = (driver
                                    .findElement(By.xpath(
                                            "//*[@id=\"primary-details\"]/div[4]/div[12]/div[3]/div/div[1]/button"))
                                    .getAttribute("data-gtmdata"));

                            if (data.contains("\"Available\"")) {
                                System.out.println("available");
                                // error on finding the title -> fixing now. Because of best seller.

                                // generate webhook send information and send it to the input manager here.

                                //// *[@id="primary-details"]/div[4]/div[2]/div/div[2]/span/span[2]/span
                                //// *[@id="primary-details"]/div[4]/div[2]/div/div[2]/span/span[2]/span

                                System.out.println(driver.findElement(By.className("product-name-section")).getText()
                                        + " is nameFull");
                                String nameFull = driver.findElement(By.className("product-name-section")).getText();
                                String name = "";
                                for (int i = 0; i < nameFull.length(); i++) {
                                    if (nameFull.charAt(i) == '\n') {
                                        break;
                                    }
                                    name += nameFull.charAt(i);
                                }
                                // System.out.println(nameFull + " is name");
                                sendMyManagers(monitoringURL, driver.findElement(By.xpath(
                                        "//*[@id=\"primary-details\"]/div[4]/div[2]/div/div[2]/span/span[2]/span"))
                                                .getText()
                                                .substring(1, driver.findElement(By.xpath(
                                                        "//*[@id=\"primary-details\"]/div[4]/div[2]/div/div[2]/span/span[2]/span"))
                                                        .getText().length()),
                                        "GameStop", true, false, name,
                                        driver.findElement(By.xpath("//*[@id=\"0\"]")).getAttribute("src"), "N/A",
                                        "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");

                                isAvailable = true;

                            }

                            counter++;
                            // System.out.println(counter);
                            driver.navigate().refresh();

                            swapProxy();

                            pause(30);

                        }

                        while (isAvailable) {
                            // while product is available and need to check if the product becomes
                            // unavailable

                            try {

                                WebElement noThx = driver.findElement(By.xpath("/html/body/div[15]/div[10]/div/div"));
                                noThx.click();
                                pause(1);
                                System.out.println("caught no thanks");
                            } catch (Exception e) {

                            }

                            String data = (driver
                                    .findElement(By.xpath(
                                            "//*[@id=\"primary-details\"]/div[4]/div[12]/div[3]/div/div[1]/button"))
                                    .getAttribute("data-gtmdata"));

                            System.out.println(data);

                            if (data.contains("Not Available")) {

                                isAvailable = false;

                            }

                            counter++;
                            // System.out.println(counter);
                            driver.navigate().refresh();

                            swapProxy();

                            pause(60);

                        }

                    }
                    pause(2);
                }
            });

            t1.start();

        } else if (monitoringURL.contains("walmart")) {

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
                                // System.out.println("ship it could not find");
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

                                System.out.println("sent from 1");
                                System.out.println(driver.findElement(By.xpath("//*[contains(text(),'Walmart #')]")).getText().substring(driver.findElement(By.xpath("//*[contains(text(),'Walmart #')]")).getText().length()-8));

                                isAvailable = true;

                            }

                            counter++;
                            // System.out.println(counter);
                            driver.navigate().refresh();

                            swapProxy();

                            pause(40);

                        }

                        while (isAvailable) {

                            try {

                                driver.findElement(By.xpath("//*[text()='Get in-stock alert']"));
                                isAvailable = false;
                            } catch (Exception e) {
                                isAvailable = true;
                            }



                            counter++;
                            // System.out.println(counter);
                            driver.navigate().refresh();

                            swapProxy();

                            pause(120);

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

        System.out.println("removing manager attempt");
        for (int i = 0; i < myManagers.size(); i++) {
            if (myManagers.get(i).equals(manager)) {
                myManagers.remove(i);
                System.out.println("manager removed");
            }
        }

    }

    public void quitBot() {

        isActive = false;
        driver.quit();

    }

    private void swapProxy() {
        // proxies
//		System.clearProperty("http.proxyHost");
//		System.setProperty("http.proxyHost", proxyIPs[proxyIdx]);
//		// System.setProperty("http.proxyPort", proxyPorts[proxyIdx]);
//
//		if (proxyIdx == proxyIPs.length - 1) {
//			proxyIdx = 0;
//		} else {
//			proxyIdx++;
//		}
//
//		// System.out.println("now using proxy " +
//		// System.getProperty("http.proxyHost"));

    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void initializeBot() {
//		//test invisible
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors", "--silent");
//		//end test

        driver = new ChromeDriver();// would put "options" as the parameter for testing
        resizeWindow(driver, 1500, 800);

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
