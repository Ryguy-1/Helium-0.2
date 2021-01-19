package package1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


//creates new GeneralInputManager.
public class Runner implements ActionListener {

    // test github2

    public static ArrayList<String> guildIds;
    public static ArrayList<InputManager> managers;


    //monitoring on the rtx 3080, rtx 3090, quest2, c920s, c920 pro, switch, switch lite, ps5, xbox series x

    //3080, 3090, quest2, c920s, c922 pro
    public static final String[] bestBuyURLs = {
			"https://www.bestbuy.com/site/nintendo-switch-32gb-console-gray-joy-con/6364253.p?skuId=6364253",
			"https://www.bestbuy.com/site/nintendo-switch-32gb-console-neon-red-neon-blue-joy-con/6364255.p?skuId=6364255",
            "https://www.bestbuy.com/site/nvidia-geforce-rtx-3090-24gb-gddr6x-pci-express-4-0-graphics-card-titanium-and-black/6429434.p?skuId=6429434",
            "https://www.bestbuy.com/site/gigabyte-geforce-rtx-3090-24g-gddr6x-pci-express-4-0-graphics-card-black/6430623.p?skuId=6430623",
            "https://www.bestbuy.com/site/oculus-quest-2-advanced-all-in-one-virtual-reality-headset-256gb/6429502.p?skuId=6429502",
            "https://www.bestbuy.com/site/logitech-c920s-hd-webcam/6321794.p?skuId=6321794",
            "https://www.bestbuy.com/site/logitech-c922-pro-stream-webcam/5579380.p?skuId=5579380",
            "https://www.bestbuy.com/site/nvidia-geforce-rtx-3080-10gb-gddr6x-pci-express-4-0-graphics-card-titanium-and-black/6429440.p?skuId=6429440",
            "https://www.bestbuy.com/site/msi-geforce-rtx-3080-ventus-3x-10g-oc-bv-gddr6x-pci-express-4-0-graphic-card-black-silver/6430175.p?skuId=6430175",
            "https://www.bestbuy.com/site/sony-playstation-5-digital-edition-console/6430161.p?skuId=6430161",
            "https://www.bestbuy.com/site/sony-playstation-5-console/6426149.p?skuId=6426149"
    };
    //switch, switch lite
    public static final String[] targetURLs = { "https://www.target.com/p/nintendo-switch-lite-turquoise/-/A-77419248",
            "https://www.target.com/p/nintendo-switch-with-neon-blue-and-neon-red-joy-con/-/A-77464001",
            "https://www.target.com/p/nintendo-switch-lite-yellow/-/A-77419249",
            "https://www.target.com/p/nintendo-switch-with-gray-joy-con/-/A-77464002",
            "https://www.target.com/p/nintendo-switch-lite-coral/-/A-79574296" };
    //disabled in this version
    public static final String[] gameStopURLs = {
            "https://www.gamestop.com/video-games/switch/consoles/products/nintendo-switch-lite-turquoise/11095775.html?rt=productDetailsRedesign&utm_expid=.h77-PyHtRYaskNpc14UbmA.1&utm_referrer=https%3A%2F%2Fwww.gamestop.com%2Fsearch%2F%3Fq%3Dswitch%26lang%3Ddefault",
            "https://www.gamestop.com/video-games/switch/consoles/products/nintendo-switch-with-neon-blue-and-neon-red-joy-con/11095819.html?rt=productDetailsRedesign&utm_expid=.h77-PyHtRYaskNpc14UbmA.1&utm_referrer=https%3A%2F%2Fwww.gamestop.com%2Fsearch%2F%3Fq%3Dswitch%26lang%3Ddefault",
            "https://www.gamestop.com/video-games/switch/consoles/products/nintendo-switch-with-gray-joy-con/11095821.html?rt=productDetailsRedesign&utm_expid=.h77-PyHtRYaskNpc14UbmA.1&utm_referrer=https%3A%2F%2Fwww.gamestop.com%2Fsearch%2F%3Fq%3Dswitch%26lang%3Ddefault",
            "https://www.gamestop.com/video-games/switch/consoles/products/nintendo-switch-lite-yellow/11095776.html?rt=productDetailsRedesign&utm_expid=.h77-PyHtRYaskNpc14UbmA.1&utm_referrer=https%3A%2F%2Fwww.gamestop.com%2Fsearch%2F%3Fq%3Dswitch%26lang%3Ddefault" };
    //disabled in this version
    public static final String[] walmartURLs = {
            "https://www.walmart.com/ip/Sony-PlayStation-5-Digital-Edition/493824815",
            "https://www.walmart.com/ip/PlayStation5-Console/363472942",
            "https://www.walmart.com/ip/Nintendo-Switch-Bundle-with-Mario-Red-Joy-Con-20-Nintendo-eShop-Credit-Carrying-Case/542896404",
    };
    //ps5, xbox series x
    public static final String[] amazonURLs = {
            "https://www.amazon.com/PlayStation-5-Console/dp/B08FC5L3RG/ref=sr_1_1?dchild=1&keywords=ps5&qid=1601069614&refinements=p_n_availability%3A1238048011&rnid=1237984011&s=videogames&sr=1-1",
            "https://www.amazon.com/PlayStation-5-Digital/dp/B08FC6MR62/ref=sr_1_4?dchild=1&keywords=ps5&qid=1601069614&refinements=p_n_availability%3A1238048011&rnid=1237984011&s=videogames&sr=1-4",
            "https://www.amazon.com/Xbox-X/dp/B08H75RTZ8/ref=sr_1_1?dchild=1&keywords=xbox+series+x&qid=1601069659&s=videogames&sr=1-1"
    };

    public static ArrayList<SeleniumBot> bestBuyBots;
    public static ArrayList<SeleniumBot> targetBots;
    public static ArrayList<SeleniumBot> gameStopBots;
    public static ArrayList<SeleniumBot> walmartBots;
    public static ArrayList<SeleniumBot> amazonBots;

    public static JFrame frame;
    public static JPanel panel;
    public static JButton b1;

    public static void main(String[] args) throws LoginException, IOException {

        // BestBuyAPI api = new BestBuyAPI("2588445, 6364255, 6343150, 6321794,
        // 6362130");

        // RUNNER CODE
        guildIds = new ArrayList<String>();
        managers = new ArrayList<InputManager>();
        // initializes bot arraylists
        bestBuyBots = new ArrayList<SeleniumBot>();
        targetBots = new ArrayList<SeleniumBot>();
        gameStopBots = new ArrayList<SeleniumBot>();
        walmartBots = new ArrayList<SeleniumBot>();
        amazonBots = new ArrayList<SeleniumBot>();

        System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");

        Runner r = new Runner();
        r.setup();
        r.initializeBots();

        GeneralInputManager generalManager = new GeneralInputManager();

        // my best buy api key: RJJ50e5KfPYCfjKjHjVgDuvG

        // Rate Limit
//		Best Buy limits the number of calls that can be made using each assigned API Key during a given time period.
//
//		If a request is made after the limit is reached, it results in an error response with a 403 status code. Refer to Errors and Mock Data for descriptions of the various error codes returned. The user is notified via email indicating the number of calls exceeded.
//
//		Calls Per Day	Calls Per Second
//		Products, Reviews, Stores, Categories, Recommendations, Buying Options	50,000	5
//		Pro tip: We support the in operator that lets you search for a list of values based on an attribute. This functionality will help you avoid Calls Per Second errors. You can use this functionality with almost any attribute; it is mostly commonly used with SKU. The below example searches for three products based on a list of SKUs in single request:
//		https://api.bestbuy.com/v1/products(sku in(43900,2088495,7150065))?apiKey=YourAPIKey

//		https://www.gamestop.com/nav-accessories-vr/products/oculus-quest-64gb/10176627.html?rt=productDetailsRedesign&utm_expid=.h77-PyHtRYaskNpc14UbmA.1&utm_referrer=
//		https://www.bestbuy.com/site/nintendo-geek-squad-certified-refurbished-switch-gray-joy-con/6376684.p?skuId=6376684
//			https://www.bestbuy.com/site/nintendo-switch-32gb-console-neon-red-neon-blue-joy-con/6364255.p?skuId=6364255
//			https://www.bestbuy.com/site/oculus-rift-s-pc-powered-vr-gaming-headset-black/6343150.p?skuId=6343150
//			https://www.bestbuy.com/site/logitech-hd-webcam-c615-black/2588445.p?skuId=2588445
//			https://www.bestbuy.com/site/logitech-c920s-hd-webcam/6321794.p?skuId=6321794
//			https://www.target.com/p/blaze/-/A-13252212
//			Logitech C270 3.0MP Webcam - Black (960-000694)
//			Free shipping on orders of $35+ from Target. Read reviews and buy Logitech C270 3.0MP Webcam - Black (960-000694) at Target. Get it today with Same Day Delivery, Order Pickup or Drive Up.
//
//			https://www.bestbuy.com/site/logitech-c922-pro-stream-webcam/5579380.p?skuId=5579380
//			https://www.bestbuy.com/site/logitech-streamcam-plus-webcam-graphite/6366565.p?skuId=6366565

        // ===========================================================

        // webhook code will put somewhere else.
//		DiscordWebhook webhook = new DiscordWebhook(
//				"https://discordapp.com/api/webhooks/734290424931549238/mn2t4RP388-zju29KAjgfHf9Lb3ckOGwIsrpqBllrk5ai98-ZiAEIet5ab08IkYyxEAD");
//		webhook.setContent(""); // subtitle
//		webhook.setAvatarUrl(
//				"https://lh3.googleusercontent.com/proxy/wDL51F9eYWEZdMHJOAlsoRe5D9Qv27jjneanz8q_fUoOCc_4TYo9zLfd24ZfeVDB1CzDUHUbecU3TcZ4QqNuVOQZLqrwUH8EM3DWDSDui9LH1PHovL4a90DCopSHwOqDyrweGLq4UZHId77EyfDr3YiFbQaKMms");
//		webhook.setUsername("Amazon Restock Bot");
//		webhook.setTts(true);
//		webhook.addEmbed(new DiscordWebhook.EmbedObject().setTitle("The Man of Men") // Item Listing Name
//				.setDescription("Ruler of Men") // Subtitle for listing name
//				.setColor(Color.BLUE).addField("1st Field", "Inline", true) // ex: Price(as 1st Field) &amount(as Inline)
//				.addField("2nd Field", "Inline", true)
//				.addField("3rd Field", "No-Inline", false)
//				.setThumbnail("https://www.nintendo.com/content/dam/noa/en_US/hardware/switch/nintendo-switch-new-package/gallery/bundle_color_portable%20(1).jpg") //thumnail picture of switch
//				.setFooter("Footer text", "https://i2.wp.com/ceklog.kindel.com/wp-content/uploads/2013/02/firefox_2018-07-10_07-50-11.png") // apple picture probably for my profile
//				.setImage("https://www.apple.com/ac/structured-data/images/open_graph_logo.png?201809210816")
//				.setAuthor("Author Name", "https://kryptongta.com", "https://kryptongta.com/images/kryptonlogowide.png")
//				.setUrl("https://kryptongta.com")); //Actual URL of the product to link the person to it.
//		webhook.addEmbed(new DiscordWebhook.EmbedObject().setDescription("Just another added embed object!"));
//		webhook.execute(); // Handle exception
//

    }

    private void initializeBots() {
        // make all bots right when program starts up.

        // initializes all of the bots for each website with their URLs. Will add the
        // webhooks to the myManagers list later when wish to add best buy.

//		System.out.println("initializing");


        //RUN THESE TWO FOR LAUNCH
        for (int i = 0; i < bestBuyURLs.length; i++) {
            bestBuyBots.add(new SeleniumBot(bestBuyURLs[i]));
            System.out.println("added");
        }

        for (int i = 0; i < targetURLs.length; i++) {
            targetBots.add(new SeleniumBot(targetURLs[i]));
        }







        //AMAZON BY REQUEST RIGHT NOW
//		for (int i = 0; i < amazonURLs.length; i++) {
//		amazonBots.add(new SeleniumBot(amazonURLs[i]));
//	}





//		for (int i = 0; i < walmartURLs.length; i++) {
//		walmartBots.add(new SeleniumBot(walmartURLs[i]));
//	}

//		for (int i = 0; i < gameStopURLs.length; i++) {
//			gameStopBots.add(new SeleniumBot(gameStopURLs[i]));
//		}

    }

    private void setup() {

        frame = new JFrame();
        panel = new JPanel();
        b1 = new JButton("Quit Helium Restocks");
        b1.addActionListener(this);
        frame.add(panel);
        panel.add(b1);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        if (e.getSource() == b1) {
            System.clearProperty("http.proxyHost");
            System.exit(0);
        }

    }

}