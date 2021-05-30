package package1;

import java.time.Duration;
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

    //Development changes...

    private ArrayList<WebHookSeleniumManager> myManagers;

    private WebDriver driver;
    private String monitoringURL;
    private Thread t1;
    private boolean isAvailable;
    private int currentPrice;
    private boolean isActive;
    private boolean isVisible;


    //Timings For How Long to Wait for Different Retailers -> If wrong can cause false positives or time an IP out. These work well as Stock. Adjust at Your Own Risk
    public static final int amazonIn = 30; //30
    public static final int amazonOut = 360; //360

    public static final int bestBuyIn = 4; //15
    public static final int bestBuyOut = 30; //30
    public static final int checkoutTimer = 86400; //after card is added to cart, it waits this long (seconds) for you to checkout before continuing

    public static final int targetIn = 15; //15
    public static final int targetOut = 30; //30

    public static final int walmartIn = 60; //60
    public static final int walmartOut = 360; //360

    public static final int timeAfterRefresh = 2; //after refresh finishes, time wait for other elements to load


    SeleniumBot(String monitoringURL, boolean isVisible) {
        this.monitoringURL = monitoringURL;
        currentPrice = 0;
        isActive = false;
        this.isVisible = isVisible;
        this.myManagers = new ArrayList<>();
        initializeBot();
        runBot();
    }

    private void sendAmazon(){

        //initialize variables for try/catch
        String URL, price, website, productTitle, imageURL, SKU;
        URL = "Not Found"; price = "Not Found"; website = "Not Found"; productTitle = "Not Found"; imageURL = ""; SKU = "Not Found";

        //Always Correct
        URL = monitoringURL;
        website = "Amazon";
        SKU = null;

        //Viariable Values
        try{price = driver.findElement(By.id("priceblock_ourprice")).getText();}catch(Exception e){}
        try{productTitle = driver.findElement(By.id("productTitle")).getText();}catch(Exception e){}
        try{imageURL = driver.findElement(By.xpath("//*[@id=\"landingImage\"]")).getAttribute("src");}catch(Exception e){}
        System.out.println("Sent from Amazon method");
        sendMyManagers(URL, price, website, productTitle, imageURL, SKU);
    }

    private void sendBestBuy(){

        //initialize variables for try/catch
        String URL, price, website, productTitle, imageURL, SKU;
        URL = "Not Found"; price = "Not Found"; website = "Not Found"; productTitle = "Not Found"; imageURL = ""; SKU = "Not Found";

        //Always Correct
        URL = monitoringURL;
        website = "Best Buy";

        //Variable Values
        try{SKU = monitoringURL.substring(monitoringURL.length()-7); }catch(Exception e){}  //SKU is in the URL
        //try{price = driver.findElement(By.xpath("//*[@class=\"priceView-hero-price priceView-customer-price\"]/span[1]")).getText();}catch(Exception e){} System.out.println(price);
        //need to fix bestbuy price. The elements move around and the order changes up quite a bit.
        price = "See Website"; //temporary until fix price info
        try{productTitle = driver.findElements(By.className("sku-title")).get(0).getText();}catch(Exception e){}
        try{imageURL = driver.findElements(By.className("primary-image")).get(0).getAttribute("src");}catch(Exception e){}

        System.out.println("Sent from BestBuy method");
        sendMyManagers(URL, price, website, productTitle, imageURL, SKU);

    }

    public void sendTarget(){
        //initialize variables for try/catch
        String URL, price, website, productTitle, imageURL, SKU;
        URL = "Not Found"; price = "Not Found"; website = "Not Found"; productTitle = "Not Found"; imageURL = ""; SKU = "Not Found";

        //Always Correct
        URL = monitoringURL;
        website = "Target";

        //Variable Values

        try {
            Integer.parseInt(URL.substring(URL.length() - 19, URL.length() - 12));
            SKU = URL.substring(URL.length() - 19, URL.length() - 12);
        }catch(Exception e) {
            SKU = URL.substring(URL.length()-8);
        }
        //just decided to use Xpaths for these ones
        try{price = driver.findElement(By.xpath("//*[@id=\"viewport\"]/div[5]/div/div[2]/div[2]/div[1]/div[1]/div[1]")).getText();}catch(Exception e){} System.out.println(price);
        try{productTitle = driver.findElement(By.xpath("//*[@id=\"viewport\"]/div[5]/div/div[1]/div[2]/h1/span")).getText();}catch(Exception e){}
        try{imageURL = driver.findElement(By.xpath("//*[@id=\"viewport\"]/div[5]/div/div[2]/div[1]/div/div/div/div[1]/div/div[1]/button/img")).getAttribute("src");}catch(Exception e){}

        System.out.println("Sent from Target method");
        sendMyManagers(URL, price, website, productTitle, imageURL, SKU);
    }


    public void runBot() {
        System.out.println("About to Get URL");
        try {
            driver.get(monitoringURL);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Closed Because Could Not Get URL. Please Report Error to Developers.");
            quitBot();
            removeManager(myManagers.get(0)); //Change if there are multiple monitors in the future. Not for this version.
        }
        System.out.println("Gotten URL");
        pause(4);
        if (monitoringURL.contains("amazon")) {
            t1 = new Thread(() -> {
                while(true) {
                    while (isActive) {
                        while(!isAvailable){
                            try{
                                WebElement availability = driver.findElement(By.xpath("//*[@id=\"availability\"]/span"));
                                if(!availability.getText().contains("Currently unavailable.")){
                                    //if there is an availability element and it does not say currently unavailable
                                    System.out.println("Sending 1");
                                    sendAmazon();
                                    isAvailable = true;
                                }
                            }catch(Exception e){
                                    //if there is no availability tag
                                    System.out.println("Sending 2");
                                    sendAmazon();
                                    isAvailable = true;
                            }
                            //does neither (try -> if) // (catch) blocks if there is availability tag and it says "Currently unavailable."
                            pause(amazonIn);
                            try {
                                if(isActive) {
                                    driver.navigate().refresh();
                                    pause(timeAfterRefresh);
                                }
                            }catch(Exception e){
                                //for timeouts
                                driver.quit();
                                pause(5);
                                initializeBot();
                                driver.get(monitoringURL);
                                pause(4);
                            }

                        }
                        while(isAvailable){
                            try{
                                WebElement availability = driver.findElement(By.xpath("//*[@id=\"availability\"]/span"));
                                if(availability.getText().contains("Currently unavailable.")){
                                    isAvailable = false;
                                }
                            }catch(Exception e){
                            }
                            pause(amazonOut);
                            try {
                                if(isActive) {
                                    driver.navigate().refresh();
                                    pause(timeAfterRefresh);
                                }
                            }catch(Exception e){
                                //for timeouts
                                driver.quit();
                                pause(5);
                                initializeBot();
                                driver.get(monitoringURL);
                                pause(4);
                            }

                        }
                    }
                    pause (2);
                }
            });
            t1.start();


        }
        else if (monitoringURL.contains("bestbuy")) {

            //LEFT OFF HERE TRYING TO FIGURE OUT TIMEOUTS -> BEST BUY IS LOADING INSANELY SLOW

            t1 = new Thread(() -> {
                while(true) {
                    while (isActive) {
                        while(!isAvailable){
                            //catch the no thanks
                            try {
                                WebElement noThx = driver.findElement(By.id("survey_invite_no"));
                                noThx.click();
                            } catch (Exception e) {}
                            //waiting until page is loaded
                            boolean pageLoaded = false;
                            while(!pageLoaded){
                                try{
                                    WebElement availability = driver.findElements(By.className("fulfillment-add-to-cart-button")).get(0); //Only One Though
                                    if(availability.getText().contains("Add to Cart")){
                                        //clicks add to cart to put you in queue
                                        availability.click();
//                                        boolean cartLoading = true;
//                                        while(cartLoading){
//                                            try {
//                                                driver.findElement(By.xpath("//*[text()='Go to Cart']")).click();//go to cart
//                                                cartLoading = false;
//                                            } catch(Exception e){}
//                                        }
//                                        //will only work if the store is available near you
//                                        boolean checkoutLoading = true;
//                                        while(checkoutLoading){
//                                            try {
//                                                driver.findElement(By.xpath("//*[text()='Checkout']")).click();//go to checkout
//                                                checkoutLoading = false;
//                                            } catch(Exception e){}
//                                        }

                                        //if there is an availability element and it does not say currently unavailable
                                        System.out.println("Sending 1");
                                        sendBestBuy();
                                        isAvailable = true;
                                        //Pause for long while and wait for user to checkout
                                        pause(checkoutTimer);
                                    }
                                    pageLoaded = true;
                                }catch(Exception e) {
                                    //page still loading
                                }
                            }

                            //does neither (try -> if) // (catch) blocks if there is availability tag and it says "Currently unavailable."
                            pause(bestBuyIn);
                            try {
                                if(isActive) {
                                    driver.get(monitoringURL);
                                    //pause(timeAfterRefresh);
                                }
                            }catch(Exception e){
                                //for timeouts
                                driver.quit();
                                pause(5);
                                initializeBot();
                                driver.get(monitoringURL);
                                pause(4);
                            }

                        }
                        while(isAvailable){
                            //catch the no thanks
                            try {
                                WebElement noThx = driver.findElement(By.id("survey_invite_no"));
                                noThx.click();
                                pause(1);
                            } catch (Exception e) {}

                            try{
                                WebElement availability = driver.findElement(By.className("fulfillment-add-to-cart-button"));
                                if(!availability.getText().contains("Add to Cart")){
                                    isAvailable = false;
                                }
                            }catch(Exception e){
                            }
                            pause(bestBuyOut);
                            try {
                                if(isActive) {
                                    driver.navigate().refresh();
                                    pause(timeAfterRefresh);
                                }
                            }catch(Exception e){
                                //for timeouts
                                driver.quit();
                                pause(5);
                                initializeBot();
                                driver.get(monitoringURL);
                                pause(4);
                            }

                        }
                    }
                    pause (2);
                }
            });
            t1.start();
        }
        else if (monitoringURL.contains("target")) {

            //LEFT OFF HERE TRYING TO FIGURE OUT TIMEOUTS -> BEST BUY IS LOADING INSANELY SLOW

            t1 = new Thread(() -> {
                while(true) {
                    while (isActive) {
                        while(!isAvailable){
                            boolean avail1;
                            try {
                                //driver.findElement(By.xpath("//*[text()='Ship it']")).click(); // "availability":"OutOfStock"
                                driver.findElement(By.xpath("//*[text()='Ship it']"));
                                // ->
                                // Broken feature on target werido
                                // this dismiss message pops up if it is not available even though the ship it
                                // button pops up.
//                                pause(1);
//
//                                try{ //not always coverage
//                                    driver.findElement(By.xpath("//*[text()='Decline coverage']")).click(); //Decline coverage
//                                    pause(1);
//                                }catch(Exception e){}
//
//
//                                driver.findElement(By.xpath("//*[text()='Continue shopping']")).click(); //Continue shopping
//                                pause(1);

                                //avail1 = true; //if continue shopping runs then it is available, if not it is false;

                                try {
                                    driver.findElement(By.xpath("//*[text()='Refresh']")); // Dismiss
                                    avail1 = false;
                                } catch (Exception e) {
                                    // if there is no dismiss button
                                    avail1 = true;
                                }
                                // if no error is thrown, avail1 is true.
                            } catch (Exception e) {
                                avail1 = false;
                            }

                            if (avail1) {

                                // makes current price for next session when monitoring price
                                sendTarget();
                                System.out.println("sent from 1");
                                isAvailable = true;
                            }
                            pause(targetIn);
                            try {
                                if(isActive) {
                                    driver.navigate().refresh();
                                    pause(timeAfterRefresh);
                                }
                            }catch(Exception e){
                                //for timeouts
                                driver.quit();
                                pause(5);
                                initializeBot();
                                driver.get(monitoringURL);
                                pause(4);
                            }

                        }
                        while(isAvailable){
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
                            pause(targetOut);
                            try {
                                if(isActive) {
                                    driver.navigate().refresh();
                                    pause(timeAfterRefresh);
                                }
                            }catch(Exception e){
                                //for timeouts
                                driver.quit();
                                pause(5);
                                initializeBot();
                                driver.get(monitoringURL);
                                pause(4);
                            }
                        }
                    }
                    pause (2);
                }
            });
            t1.start();

//
//            t1 = new Thread(() -> {
//                while (true) {
//                    while (isActive) {
//                        while (!isAvailable) {
//                            boolean avail1;
//                            try {
//                                driver.findElement(By.xpath("//*[text()='Ship it']")).click(); // "availability":"OutOfStock"
//                                // ->
//                                // Broken feature on target werido
//                                // this dismiss message pops up if it is not available even though the ship it
//                                // button pops up.
//                                pause(1);
//                                try {
//                                    driver.findElement(By.xpath("//*[text()='Dismiss']")); // Dismiss
//                                    avail1 = false;
//                                } catch (Exception e) {
//                                    // if there is no dismiss button
//                                    avail1 = true;
//                                }
//                                // if no error is thrown, avail1 is true.
//                            } catch (Exception e) {
//                                avail1 = false;
//                            }
//                            // substring gets rid of price sign
//                            String priceText = driver
//                                    .findElement(By.xpath(
//                                            "//*[@id=\"viewport\"]/div[5]/div/div[2]/div[2]/div[1]/div[1]/div[1]"))
//                                    .getText()
//                                    .substring(1);
//
//                            // if at least one of the two buttons exists. Product is available.
//                            if (avail1) { // was: avail1 == true || avail2 == true
//                                sendMyManagers(monitoringURL, priceText, "Target", true, false,
//                                        driver.findElement(
//                                                By.xpath("//*[@id=\"viewport\"]/div[5]/div/div[1]/div[2]/h1"))
//                                                .getText(),
//                                        driver.findElement(By.xpath(
//                                                "//*[@id=\"viewport\"]/div[5]/div/div[2]/div[1]/div/div/div/div[1]/div/div[1]/button/img"))
//                                                .getAttribute("src"),
//                                        "N/A",
//                                        "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
//                                // makes current price for next session when monitoring price
//                                currentPrice = Integer.parseInt(driver
//                                        .findElement(By.xpath(
//                                                "//*[@id=\"viewport\"]/div[5]/div/div[2]/div[2]/div[1]/div[1]/div[1]"))
//                                        .getText()
//                                        .substring(1, driver.findElement(By.xpath(
//                                                "//*[@id=\"viewport\"]/div[5]/div/div[2]/div[2]/div[1]/div[1]/div[1]"))
//                                                .getText().length() - 3));
//                                System.out.println("sent from 1");
//                                isAvailable = true;
//                            }
//                            driver.navigate().refresh();
//                            pause(targetIn);
//                        }
//                        while (isAvailable) {
//                            boolean avail1;
//                            try {
//                                driver.findElement(By.xpath("//*[text()='Ship it']"));
//                                avail1 = true;
//                            } catch (Exception e) {
//                                avail1 = false;
//                            }
//                            if (avail1 == false) {
//                                isAvailable = false;
//                            }
//                            driver.navigate().refresh();
//                            pause(targetOut);
//                        }
//                    }
//                    pause(2);
//                }
//            });
//            t1.start();

        }
//        else if (monitoringURL.contains("walmart")) {
//
//            // can use xpaths only if window stays a size which doesn't affect the html
//            t1 = new Thread(() -> {
//                while (true) {
//                    while (isActive) {
//
//                        while (!isAvailable) {
//
//                            boolean avail1;
//
//                            try {
//                                driver.findElement(By.xpath("//*[text()='Get in-stock alert']")); // "availability":"OutOfStock"
//                                // ->
//                                // Broken feature on target werido
//                                // only false if the out of stock element cannot be found.
//                                avail1 = false;
//                            } catch (Exception e) {
//                                avail1 = true;
//                            }
//                            // substring gets rid of price sign
//                            String priceText;
//                            try {
//                                priceText = driver.findElement(By.xpath("//*[@id=\"price\"]/div/span[1]/span/span[2]/span[2]"))
//                                        .getAttribute("content");
//                                System.out.println("priceText is " + priceText);
//                            }catch(Exception e) {
//                                priceText = "No Listed Price";
//                            }
//                            // if at least one of the two buttons exists. Product is available.
//                            if (avail1) { // was: avail1 == true || avail2 == true
//                                // JOptionPane.showMessageDialog(null, "avail1 is true now.");
//                                sendMyManagers(monitoringURL, priceText, "Walmart", true, false,
//                                        driver.findElement(
//                                                By.xpath("//*[@id=\"product-overview\"]/div/div[3]/div/h1"))
//                                                .getText(),
//                                        driver.findElement(By.className("prod-alt-image-carousel-image--left"))
//                                                .getAttribute("src"),
//                                        driver.findElement(By.xpath("//*[contains(text(),'Walmart #')]")).getText().substring(driver.findElement(By.xpath("//*[contains(text(),'Walmart #')]")).getText().length()-8),
//                                        "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg");
//                                // makes current price for next session when monitoring price
//                                isAvailable = true;
//                            }
//                            driver.navigate().refresh();
//                            pause(walmartIn);
//                        }
//                        while (isAvailable) {
//                            try {
//                                driver.findElement(By.xpath("//*[text()='Get in-stock alert']"));
//                                isAvailable = false;
//                            } catch (Exception e) {
//                                isAvailable = true;
//                            }
//                            driver.navigate().refresh();
//                            pause(walmartOut);
//                        }
//                    }
//                    pause(2);
//                }
//            });
//            t1.start();
//        }
    }

    private void sendMyManagers(String URL, String price, String website, String productTitle, String imageURL, String SKU) {

        for (int i = 0; i < myManagers.size(); i++) {
            myManagers.get(i).sendProductAvailable(URL, price, website, productTitle, imageURL, SKU);
            System.out.println("Sent");
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
		    //temporarily cannot use headless with BestBuy
            if(!monitoringURL.contains("bestbuy")) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless", "--silent", "--disable-gpu", "--window-size=1500,800", "--lang=en_US"); //"--headless", "--silent"
                //end test
                driver = new ChromeDriver(options);// would put "options" as the parameter for testing
                //resizeWindow(driver, 1500, 800);
            }else{
                ChromeOptions options = new ChromeOptions();
//		    String proxy = "192.46.228.131:8000";
//            options.addArguments("--proxy-server=http://" + proxy);
                driver = new ChromeDriver(options);// would put "options" as the parameter for testing
                //driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(3));
                resizeWindow(driver, 1500, 800);
            }
        }else{
		    ChromeOptions options = new ChromeOptions();
//		    String proxy = "192.46.228.131:8000";
//            options.addArguments("--proxy-server=http://" + proxy);
            driver = new ChromeDriver(options);// would put "options" as the parameter for testing
            //driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(3));
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
