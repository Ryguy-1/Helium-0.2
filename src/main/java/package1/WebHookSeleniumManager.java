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
        hook.setContent("Channel Recognized by Helium");
        hook.execute();
        hook.setContent("");
    }

    public void sendChannel(MessageChannel channel) {
        this.channel = channel;
    }



    public void addMonitor(String URL){
        monitoringLinks.add(URL);
        botsLocal.add(new SeleniumBot(URL)); // creates a new, local, non stock bot.
        botsLocal.get(botsLocal.size() - 1).addManager(this); // adds this webhook as a manager for that bot.
        botsLocal.get(botsLocal.size()-1).setIsActiveTrue();
    }



    public void sendCommand(String command) {
        if (command.equals("!Add URL") && !addingProductURL) {
            try{channel.sendMessage("Please enter the URL as the command parameter for the product you would like to monitor.").queue();}catch(Exception e){}
            addingProductURL = true;
        } else if (command.contains("https://www.") && addingProductURL && !monitoringLinks.contains(command)) {
            try{channel.sendMessage("Creating new Monitor... Please wait").queue();}catch(Exception e){}
            monitoringLinks.add(command);
            botsLocal.add(new SeleniumBot(command)); // creates a new, local, non stock bot.
            botsLocal.get(botsLocal.size() - 1).addManager(this); // adds this webhook as a manager for that bot.
            botsLocal.get(botsLocal.size()-1).setIsActiveTrue();
            addingProductURL = false;
                try{channel.sendMessage("Monitor Successfully Created!").queue();}catch(Exception e){}
        }else if (command.equalsIgnoreCase("!Help")) {
            System.out.println("HELP WEBHOOK");
                    try{channel.sendMessage("Webhook edit commands: \n!Add URL   -   Add a URL for the webhook to monitor.")
                    .queue();}catch(Exception e){}
        }
    }

    public void removeOutputs() {

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

        if(productTitle.length()>50) {
            productTitle = productTitle.substring(0, 50);
        }

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



            }
            else if (website.equals("Target")) {
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



            }
            else if(website.equals("Amazon")) {
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



            }
            else if(website.equals("Walmart")) {
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

            //try to send the webhook
            try {
                hook.execute();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                try{channel.sendMessage(
                        "Could Not Execute Webhook. Please report error and developers will fix as soon as possible!")
                        .queue();}catch(Exception e2){}
            }
        }
    }
}
