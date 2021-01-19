package package1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

//is a WebHook. has a list of all the URL's it should monitor and starts new SeleniumBots in accordance with what it has in its list.
//also has a member variable as to which site it is monitoring to pass to the SeleniumBot.
public class WebHookSeleniumManager {

    private String webhookURL;
    private MessageChannel channel;
    private ArrayList<String> monitoringLinks;
    private ArrayList<SeleniumBot> botsLocal;
    private boolean addingProductURL;
    private DiscordWebhook hook;
    private boolean stoppingProductURL;
    private boolean bestBuyAdded;
    private boolean targetAdded;
    private boolean gameStopAdded;
    private boolean walmartAdded;
    private boolean amazonAdded;

    WebHookSeleniumManager(String webhookURL, MessageChannel channel) throws IOException {
        this.channel = channel;
        addingProductURL = false;
        stoppingProductURL = false;
        bestBuyAdded = false;
        targetAdded = false;
        gameStopAdded = false;
        walmartAdded = false;
        amazonAdded = false;
        this.webhookURL = webhookURL;
        monitoringLinks = new ArrayList<String>();
        botsLocal = new ArrayList<SeleniumBot>();
        hook = new DiscordWebhook(webhookURL);
        hook.setContent("The Webhook added to this channel!");
        hook.execute();
        hook.setContent("");

    }

    public void sendChannel(MessageChannel channel) {
        this.channel = channel;
    }

    public void sendCommand(String command) {

        // this.bot = new SeleniumBot(this);

        System.out.println("CORRECT CHANNEL");
        System.out.println(command + " is the command");

//		if user wants to set channel and checking has not begun


        if (command.equals("!Add URL") && !addingProductURL) {
            channel.sendMessage(
                    "Please enter the URL as the command parameter for the product you would like to monitor.").queue();
            addingProductURL = true;
        } else if (command.contains("https://www.") && addingProductURL && !monitoringLinks.contains(command)) {
            channel.sendMessage("Creating new Monitor... Please wait").queue();
            monitoringLinks.add(command);
            botsLocal.add(new SeleniumBot(command)); // creates a new, local, non stock bot.
            botsLocal.get(botsLocal.size() - 1).addManager(this); // adds this webhook as a manager for that bot.
            botsLocal.get(botsLocal.size()-1).setIsActiveTrue();
            addingProductURL = false;
            channel.sendMessage("Monitor Successfully Created!").queue();
            // adding best buy start
        } else if (command.equals("!Add BestBuy") && bestBuyAdded == false) {

            channel.sendMessage("Adding Best Buy stock URLs...").queue();
            for (int i = 0; i < Runner.bestBuyBots.size(); i++) {

                Runner.bestBuyBots.get(i).addManager(this);
                monitoringLinks.add(Runner.bestBuyURLs[i]);
            }

            channel.sendMessage("Best Buy successfully added!").queue();

            bestBuyAdded = true;
            for (int i = 0; i < Runner.bestBuyBots.size(); i++) {
                Runner.bestBuyBots.get(i).setIsActiveTrue();
            }

        } else if (command.equals("!Add BestBuy") && bestBuyAdded) {

            channel.sendMessage("You have already added Best Buy to this webhook.").queue();
            // adding best buy end
            // adding target start
        } else if (command.equals("!Add Target") && targetAdded == false) {

            channel.sendMessage("Adding Target stock URLs...").queue();
            for (int i = 0; i < Runner.targetBots.size(); i++) {

                Runner.targetBots.get(i).addManager(this);
                monitoringLinks.add(Runner.targetURLs[i]);
            }

            channel.sendMessage("Target successfully added!").queue();

            targetAdded = true;
            for (int i = 0; i < Runner.targetBots.size(); i++) {
                Runner.targetBots.get(i).setIsActiveTrue();
            }

        } else if (command.equals("!Add Target") && targetAdded) {

            channel.sendMessage("You have already added Target to this webhook.").queue();
            // adding target end
        } else if(command.equals("!Add Walmart") && walmartAdded == false) {

            channel.sendMessage("Adding Walmart stock URLs...").queue();
            for (int i = 0; i < Runner.walmartBots.size(); i++) {

                Runner.walmartBots.get(i).addManager(this);
                monitoringLinks.add(Runner.walmartURLs[i]);
            }

            channel.sendMessage("Walmart successfully added!").queue();

            walmartAdded = true;
            for (int i = 0; i < Runner.walmartBots.size(); i++) {
                Runner.walmartBots.get(i).setIsActiveTrue();
            }


        }else if(command.equals("!Add Walmart") && walmartAdded) {


            channel.sendMessage("You have already added Walmart to this webhook.").queue();



        }else if (command.equals("!Add GameStop") && gameStopAdded == false) {
            channel.sendMessage("Adding GameStop stock URLs...").queue();
            for (int i = 0; i < Runner.gameStopBots.size(); i++) {

                Runner.gameStopBots.get(i).addManager(this);
                monitoringLinks.add(Runner.gameStopURLs[i]);
            }

            channel.sendMessage("GameStop successfully added!").queue();

            gameStopAdded = true;
            for (int i = 0; i < Runner.gameStopBots.size(); i++) {
                Runner.gameStopBots.get(i).setIsActiveTrue();
            }

        } else if (command.equals("!Add GameStop") && gameStopAdded) {
            channel.sendMessage("You have already added GameStop to this webhook.").queue();
        }else if (command.equals("!Add Amazon") && amazonAdded == false) {
            System.out.println("Amazonbots size is "+ Runner.amazonBots.size());
            channel.sendMessage("Adding Amazon stock URLs...").queue();
            for (int i = 0; i < Runner.amazonBots.size(); i++) {

                Runner.amazonBots.get(i).addManager(this);
                monitoringLinks.add(Runner.amazonURLs[i]);
            }

            channel.sendMessage("Amazon successfully added!").queue();

            amazonAdded = true;
            for (int i = 0; i < Runner.amazonBots.size(); i++) {
                System.out.println("amazonBots active loop ran  on i="+i);
                Runner.amazonBots.get(i).setIsActiveTrue();
            }

        } else if (command.equals("!Add Amazon") && amazonAdded) {
            channel.sendMessage("You have already added Amazon to this webhook.").queue();
        }else if (command.equals("!Help")) {
            System.out.println("HELP WEBHOOK");
            channel.sendMessage("Webhook edit commands: \n!Add URL   -   Add a URL for the webhook to monitor.")
                    .queue();
        }

    }

    public void removeOutputs() {

        System.out.println("Now removing outputs");

        //disables general bots.
        for (int i = 0; i < Runner.bestBuyBots.size(); i++) {
            Runner.bestBuyBots.get(i).removeManager(this);
        }
        for (int i = 0; i < Runner.targetBots.size(); i++) {
            Runner.targetBots.get(i).removeManager(this);
        }
        for (int i = 0; i < Runner.gameStopBots.size(); i++) {
            Runner.gameStopBots.get(i).removeManager(this);
        }
        for (int i = 0; i < Runner.walmartBots.size(); i++) {
            Runner.walmartBots.get(i).removeManager(this);
        }
        for (int i = 0; i < Runner.amazonBots.size(); i++) {
            Runner.amazonBots.get(i).removeManager(this);
        }


        //removes private bots
        for (int i = 0; i < monitoringLinks.size(); i++) {
            monitoringLinks.remove(i);
        }
        for (int i = 0; i < botsLocal.size(); i++) {
            botsLocal.get(i).removeManager(this);
            botsLocal.get(i).quitBot();
            botsLocal.remove(i);
        }


    }


    public void sendProductAvailable(String URL, String price, String website, boolean isRestock, boolean isPriceChange,
                                     String productTitle, String imageURL, String SKU, String logo) {

        // will be website + " Restock Bot"
//			hook.addEmbed(new DiscordWebhook.EmbedObject().setTitle("The Man of Men") // Item Listing Name
//					.setDescription("Ruler of Men") // Subtitle for listing name
//					.setColor(Color.BLUE).addField("1st Field", "Inline", true) // ex: Price(as 1st Field) &amount(as Inline)
//					.addField("2nd Field", "Inline", true)
//					.addField("3rd Field", "No-Inline", false)
//					.setThumbnail("https://www.nintendo.com/content/dam/noa/en_US/hardware/switch/nintendo-switch-new-package/gallery/bundle_color_portable%20(1).jpg") //thumnail picture of switch
//					.setFooter("Footer text", "https://i2.wp.com/ceklog.kindel.com/wp-content/uploads/2013/02/firefox_2018-07-10_07-50-11.png") // apple picture probably for my profile
//					.setImage("https://www.apple.com/ac/structured-data/images/open_graph_logo.png?201809210816")
//					.setAuthor("Author Name", "https://kryptongta.com", "https://kryptongta.com/images/kryptonlogowide.png")
//					.setUrl("https://kryptongta.com")); //Actual URL of the product to link the person to it.

        if(productTitle.length()>50) {
            productTitle = productTitle.substring(0, 50);
        }

        System.out.println("Will try to make embed");
        if (hook.getEmbedList().size() > 0) {
            hook.removeEmbeds();
        }

        if (isRestock) {

            hook.setUsername(website);
            hook.setTts(true);
            hook.setAvatarUrl("https://www.pngitem.com/pimgs/m/403-4031699_letter-h-png-stock-photo-letter-h-png.png");

            if (website.equals("Best Buy")) {

                if (price.contains("$")) {
                    hook.addEmbed(new DiscordWebhook.EmbedObject().setTitle(productTitle)
                            .addField("**Price**", price, true).setThumbnail(imageURL).setUrl(URL)
                            .addField("**SKU**", SKU, true).setColor(new Color(237, 17, 96))
                            .setFooter("Powered by Helium Restocks",
                                    "https://www.pngitem.com/pimgs/m/403-4031699_letter-h-png-stock-photo-letter-h-png.png"));
                } else {
                    hook.addEmbed(new DiscordWebhook.EmbedObject().setTitle(productTitle)
                            .addField("**Price**", "$" + price, true).setThumbnail(imageURL).setUrl(URL)
                            .addField("**SKU**", SKU, true).setColor(new Color(237, 17, 96))
                            .setFooter("Powered by Helium Restocks",
                                    "https://www.pngitem.com/pimgs/m/403-4031699_letter-h-png-stock-photo-letter-h-png.png"));
                }

            } else if (website.equals("Target")) {

                // target has the sku from the url as the second field

                String skuTemp;
                try {
                    Integer.parseInt(URL.substring(URL.length() - 19, URL.length() - 12));
                    skuTemp = URL.substring(URL.length() - 19, URL.length() - 12);
                }catch(Exception e) {
                    skuTemp = URL.substring(URL.length()-8);
                }



                if (price.contains("$")) {
                    hook.addEmbed(new DiscordWebhook.EmbedObject().setTitle(productTitle)
                            .addField("**Price**", price, true)
                            .addField("**SKU**", skuTemp, true).setThumbnail(imageURL)
                            .setUrl(URL).setColor(new Color(237, 17, 96)).setFooter("Powered by Helium Restocks",
                                    "https://www.pngitem.com/pimgs/m/403-4031699_letter-h-png-stock-photo-letter-h-png.png"));

                } else {
                    hook.addEmbed(new DiscordWebhook.EmbedObject().setTitle(productTitle)
                            .addField("**Price**", "$" + price, true)
                            .addField("**SKU**", skuTemp, true).setThumbnail(imageURL)
                            .setUrl(URL).setColor(new Color(237, 17, 96)).setFooter("Powered by Helium Restocks",
                                    "https://www.pngitem.com/pimgs/m/403-4031699_letter-h-png-stock-photo-letter-h-png.png"));
                }

            } else if (website.equals("GameStop")) {

                if (price.contains("$")) {
                    hook.addEmbed(new DiscordWebhook.EmbedObject().setTitle(productTitle)
                            .addField("**Price**", price, true).setThumbnail(imageURL).setUrl(URL)
                            .setColor(new Color(237, 17, 96)).setFooter("Powered by Helium Restocks",
                                    "https://www.pngitem.com/pimgs/m/403-4031699_letter-h-png-stock-photo-letter-h-png.png"));

                } else {

                    hook.addEmbed(new DiscordWebhook.EmbedObject().setTitle(productTitle)
                            .addField("**Price**", "$" + price, true).setThumbnail(imageURL).setUrl(URL)
                            .setColor(new Color(237, 17, 96)).setFooter("Powered by Helium Restocks",
                                    "https://www.pngitem.com/pimgs/m/403-4031699_letter-h-png-stock-photo-letter-h-png.png"));
                }

            }else if(website.equals("Amazon")) {

                if (price.contains("$")) {
                    hook.addEmbed(new DiscordWebhook.EmbedObject().setTitle(productTitle)
                            .addField("**Price**", price, true).setThumbnail(imageURL).setUrl(URL)
                            .setColor(new Color(237, 17, 96)).setFooter("Powered by Helium Restocks",
                                    "https://www.pngitem.com/pimgs/m/403-4031699_letter-h-png-stock-photo-letter-h-png.png"));

                } else {

                    hook.addEmbed(new DiscordWebhook.EmbedObject().setTitle(productTitle)
                            .addField("**Price**", "$" + price, true).setThumbnail(imageURL).setUrl(URL)
                            .setColor(new Color(237, 17, 96)).setFooter("Powered by Helium Restocks",
                                    "https://www.pngitem.com/pimgs/m/403-4031699_letter-h-png-stock-photo-letter-h-png.png"));
                }




            }else if(website.equals("Walmart")) {

                if (price.contains("$")) {
                    hook.addEmbed(new DiscordWebhook.EmbedObject().setTitle(productTitle)
                            .addField("**Price**", price, true).addField("**SKU**", SKU, true).setThumbnail(imageURL).setUrl(URL)
                            .setColor(new Color(237, 17, 96)).setFooter("Powered by Helium Restocks",
                                    "https://www.pngitem.com/pimgs/m/403-4031699_letter-h-png-stock-photo-letter-h-png.png"));

                } else {

                    hook.addEmbed(new DiscordWebhook.EmbedObject().setTitle(productTitle)
                            .addField("**Price**", "$" + price, true).addField("**SKU**", SKU, true).setThumbnail(imageURL).setUrl(URL)
                            .setColor(new Color(237, 17, 96)).setFooter("Powered by Helium Restocks",
                                    "https://www.pngitem.com/pimgs/m/403-4031699_letter-h-png-stock-photo-letter-h-png.png"));
                }




            }

//			hook.addEmbed(new DiscordWebhook.EmbedObject()
//					.setAuthor("Powered by ZipBot",
//							"https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg",
//							"https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg")
//					.setColor(new Color(0, 137, 255)));

            try {
                hook.execute();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                channel.sendMessage(
                        "Could Not Execute Webhook. Please report error and developers will fix as soon as possible!")
                        .queue();
            }
        }

//		} else if (isPriceChange) {
//
//			hook.setUsername(website + " Monitoring Bot");
//			hook.setTts(true);
//			hook.setAvatarUrl(logo);
//			hook.addEmbed(new DiscordWebhook.EmbedObject()
//					.setTitle(productTitle).addField("Price", price, true)
//					.setImage(imageURL).setUrl(URL).addField("SKU", SKU, true));
//			hook.addEmbed(new DiscordWebhook.EmbedObject().setAuthor(
//					"Powered by ZipBot", "https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg",
//					"https://cdn.dribbble.com/users/1597648/screenshots/3379974/z_dribbble.jpg"));
//
//			try {
//				hook.execute();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				channel.sendMessage(
//						"Could Not Execute Webhook. Please report error and developers will fix as soon as possible!")
//						.queue();
//			}
//
//		}

    }

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
//
//
}
