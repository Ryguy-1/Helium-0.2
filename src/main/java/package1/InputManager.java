package package1;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.io.IOException;
import java.util.Random;

import javax.security.auth.login.LoginException;
import javax.swing.*;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

//takes the guild's input message and sends the commands to 1.add a new WebHookSeleniumManager to a channel.
//also sends the command to add a new URL to the WebHookSeleniumManager class to add the URL to the list it is monitoring.
public class InputManager extends ListenerAdapter {

    //Discord
    private String guildId;
    private JDA jda;

    private String prefix = "h-"; //has to be 2 in length

    private MessageChannel channel;

    private ArrayList<String> webhookURLList;
    private ArrayList<WebHookSeleniumManager> webhookManagerList;
    private boolean adding;
    private boolean removing;

    //Discord Bot Random
    Random random = new Random();

    InputManager() {

        webhookManagerList = new ArrayList<>();
        webhookURLList = new ArrayList<>();
        adding = false;
        removing = false;

    }

    public ArrayList<WebHookSeleniumManager> getWebhookManagerList(){
        return this.webhookManagerList;
    }


    public void addWebhook(String URL){
        try {
            if(webhookManagerList.size()<=3) {
                webhookManagerList.add(new WebHookSeleniumManager(URL, null));
                webhookURLList.add(URL);
            }else{
                System.out.println("Cannot have more than 3 Webhooks");
            }
        } catch (Exception e) {
            System.out.println("Not a Valid Webhook URL");
            JOptionPane.showMessageDialog(null, "Webhook Not Created Because Not a Valid Webhook URL");
        }

        Runner.panel.updateActiveMonitors();
    }


    public void addURLtoWebhook(String webhookToAddToURL, String websiteURL, boolean isVisible){
        boolean added = false;
        System.out.println("Here2");
        if(websiteURL.contains("amazon") || websiteURL.contains("bestbuy") || websiteURL.contains("target") || websiteURL.contains("walmart")) {
            for (int j = 0; j < webhookURLList.size(); j++) {
                if (webhookURLList.get(j).equals(webhookToAddToURL)) {
                    System.out.println("Here1");
                    webhookManagerList.get(j).addMonitor(websiteURL, isVisible);
                    added = true;
                }
            }
        }else{
            System.out.println("Not a valid Website URL");
            JOptionPane.showMessageDialog(null, "Not a valid Website URL");
        }
        if(!added){
            System.out.println("Was not added because Webhook has Not been Initialized Yet");
        }
        Runner.panel.updateActiveMonitors();
    }


    public void removeURLfromWebhook(String webhookToRemoveFromURL, String websiteURL){
        boolean removed = false;
        if(websiteURL.contains("amazon") || websiteURL.contains("bestbuy") || websiteURL.contains("target") || websiteURL.contains("walmart")) {
            for (int j = 0; j < webhookURLList.size(); j++) {
                if (webhookURLList.get(j).equals(webhookToRemoveFromURL)) {
                    webhookManagerList.get(j).removeMonitor(websiteURL);
                    removed = true;
                }
            }
        }else{
            System.out.println("Not a valid Website URL");
            JOptionPane.showMessageDialog(null, "Not a valid Website URL");
        }
//        if(!removed){
//            System.out.println("Was not removed because Webhook has Not been Initialized Yet");
//            JOptionPane.showMessageDialog(null, "Was not removed because Webhook has Not been Initialized Yet");
//        }
        Runner.panel.updateActiveMonitors();
    }


    public void removeWebhook(String webhookURL){
        for (int i = 0; i < webhookURLList.size(); i++) {
            if(webhookURLList.get(i).equals(webhookURL)) {
                webhookManagerList.get(i).removeOutputs();
                webhookManagerList.remove(i);
                webhookURLList.remove(i);
            }
        }
        Runner.panel.updateActiveMonitors();
    }


    public boolean hasAddedWebhook(String tempWebhookURL){
        for (String s: webhookURLList) {
            if(s.equals(tempWebhookURL)){
                return true;
            }
        }
        return false;
    }



    public String getGuildId() {
        return guildId;
    }

    public void addDiscord(String key) throws LoginException {
        guildId = "";
        //Helium Restocks Code
        jda = JDABuilder.createDefault(key)
                .addEventListeners(this).build();

    }

    public void removeDiscord(){
        try{
            jda.shutdownNow();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Could not Shut Down Discord. Please Close Helium and Restart to Force Close.");
        }
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        channel = event.getChannel();


        if(msg.equalsIgnoreCase(prefix+"help")){
            help();
        }else if(msg.contains(prefix+"addWebhook")){
            String webhookURL = "";
            try{webhookURL = msg.substring(prefix.length()-1+12);}catch(Exception e){}
            addWebhook(webhookURL, event);

        }else if(msg.contains(prefix+"removeWebhook")){
            String webhookURL = "";
            try{webhookURL = msg.substring(prefix.length()-1+15);}catch(Exception e){}
            removeWebhook(webhookURL, event);
        }else if(msg.contains(prefix+"addURLToWebhook")){
            String webhookURL = "";
            String websiteURL = "";
            boolean isVisible = false;
            String[] URLs = {};
            try{ URLs = msg.substring(prefix.length()-1+17).split(", ");}catch(Exception e){}
            webhookURL = URLs[0];
            websiteURL = URLs[1];
            try{isVisible = Boolean.parseBoolean(URLs[2]);}catch(Exception e){event.getChannel().sendMessage("(Auto Setting Visibility to <false>)").queue();}
            System.out.println("Webhook: "+webhookURL);
            System.out.println("Website: "+websiteURL);
            System.out.println("IsVisible: "+isVisible);
            addURLtoWebhook(webhookURL, websiteURL, isVisible, event);
        }else if(msg.contains(prefix+"removeURLFromWebhook")){
            String webhookURL = "";
            String websiteURL = "";
            String[] URLs = {};
            try{ URLs = msg.substring(prefix.length()-1+22).split(", ");}catch(Exception e){}
            webhookURL = URLs[0];
            websiteURL = URLs[1];
            removeURLfromWebhook(webhookURL, websiteURL, event);
        }



    }


    private void help(){

        EmbedBuilder eb = new EmbedBuilder();
        MessageEmbed eb3 = new MessageEmbed("", "", "", null, null, 0, null, null, null, null, null, null, null);
        eb.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        eb.setTitle("Helium Commands :desktop:");
        eb.addField("Help :question:", "`"+prefix + "help`", true);
        eb.addField("Add Webhook :spider_web:", "`"+prefix + "addWebhook <WebhookURL>`", true);
        eb.addField("Remove Webhook :spider_web:", "`"+prefix + "removeWebhook <WebhookURL>`", true);
        eb.addField("Add URL to Webhook :page_with_curl:", "`"+prefix + "addURLToWebhook <WebhookURL>, <WebsiteURL>, <IsVisible(true/false)>`", true);
        eb.addField("Remove URL from Webhook :page_with_curl:", "`"+prefix + "removeURLFromWebhook <WebhookURL>, <WebsiteURL>`", true);
        eb.setFooter("Powered By Helium Restocks");
        eb.setThumbnail("https://media1.giphy.com/media/3o7bubkR8SgWlMZewg/giphy.gif"); //gif
        eb3 = eb.build();
        channel.sendMessage(eb3).queue();

    }


    //methods, but for discord
    private void addWebhook(String URL, MessageReceivedEvent event){
        try {
            if(webhookManagerList.size()<=3 && !webhookURLList.contains(URL)) {
                webhookManagerList.add(new WebHookSeleniumManager(URL, null));
                webhookURLList.add(URL);
                //confirmation message
                EmbedBuilder eb = new EmbedBuilder();
                MessageEmbed eb3 = new MessageEmbed("", "", "", null, null, 0, null, null, null, null, null, null, null);
                eb.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                eb.setTitle("Successfully added Webhook! :spider_web: :white_check_mark:");
                eb.setThumbnail("https://jonmgomes.com/wp-content/uploads/2020/02/Checkbox-Icon.gif"); //checkbox gif
                eb.setFooter("Powered By Helium Restocks");
                eb3 = eb.build();
                channel.sendMessage(eb3).queue();
                //
            }else{
                if(webhookManagerList.size()>=3){
                    channel.sendMessage("Cannot Have More than 3 Webhooks in this Version!").queue();
                }else{
                    channel.sendMessage("Webhook Already Added!").queue();
                }
            }
        } catch (Exception e) {
            channel.sendMessage("Webhook Not Created Because Not a Valid Webhook URL!").queue();
        }
        Runner.panel.updateActiveMonitors();
    }


    public void removeWebhook(String webhookURL, MessageReceivedEvent event){
        boolean wasRemoved = false;
        for (int i = 0; i < webhookURLList.size(); i++) {
            if(webhookURLList.get(i).equals(webhookURL)) {
                webhookManagerList.get(i).removeOutputs();
                webhookManagerList.remove(i);
                webhookURLList.remove(i);
                //confirmation message
                EmbedBuilder eb = new EmbedBuilder();
                MessageEmbed eb3 = new MessageEmbed("", "", "", null, null, 0, null, null, null, null, null, null, null);
                eb.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                eb.setTitle("Successfully Removed Webhook! :spider_web: :white_check_mark:");
                eb.setThumbnail("https://jonmgomes.com/wp-content/uploads/2020/02/Checkbox-Icon.gif"); //checkbox gif
                eb.setFooter("Powered By Helium Restocks");
                eb3 = eb.build();
                channel.sendMessage(eb3).queue();
                //
                wasRemoved = true;
            }
        }
        Runner.panel.updateActiveMonitors();
        if(!wasRemoved){
            event.getChannel().sendMessage("Webhook Not Removed Because Never Added!").queue();
        }
    }

    public void addURLtoWebhook(String webhookToAddToURL, String websiteURL, boolean isVisible, MessageReceivedEvent event){
        boolean added = false;
        boolean notValidURL = false;
        if(websiteURL.contains("amazon") || websiteURL.contains("bestbuy") || websiteURL.contains("target")) { //had "walmart" as fourth option, but removed for now...
            for (int j = 0; j < webhookURLList.size(); j++) {
                if (webhookURLList.get(j).equals(webhookToAddToURL)) {
                    webhookManagerList.get(j).addMonitor(websiteURL, isVisible);
                    //confirmation message
                    EmbedBuilder eb = new EmbedBuilder();
                    MessageEmbed eb3 = new MessageEmbed("", "", "", null, null, 0, null, null, null, null, null, null, null);
                    eb.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                    eb.setTitle("Successfully added URL to Webhook! :page_with_curl: :white_check_mark:");
                    eb.setThumbnail("https://jonmgomes.com/wp-content/uploads/2020/02/Checkbox-Icon.gif"); //checkbox gif
                    eb.setFooter("Powered By Helium Restocks");
                    eb3 = eb.build();
                    channel.sendMessage(eb3).queue();
                    //
                    added = true;
                }
            }
        }else{
            System.out.println("Not a valid Website URL");
            event.getChannel().sendMessage("Not a valid Website URL").queue();
            notValidURL = true;
        }

        if(!added && !notValidURL){ //notValidURL is to make sure doesn't print both not valiud website url and that webhook has not been initialized yet.
            System.out.println("Was not added because Webhook has Not been Initialized Yet");
            event.getChannel().sendMessage("Was not added because Webhook has Not been Initialized Yet").queue();
        }

        Runner.panel.updateActiveMonitors();
    }

    public void removeURLfromWebhook(String webhookToRemoveFromURL, String websiteURL, MessageReceivedEvent event){
        boolean removed = false;
        if(websiteURL.contains("amazon") || websiteURL.contains("bestbuy") || websiteURL.contains("target")) { //had "walmart" as fourth
            for (int j = 0; j < webhookURLList.size(); j++) {
                if (webhookURLList.get(j).equals(webhookToRemoveFromURL)) {
                    webhookManagerList.get(j).removeMonitor(websiteURL);
                    removed = true;
                }
            }
        }else{
            event.getChannel().sendMessage("Not a valid Website URL").queue();
        }
        if(!removed){
            event.getChannel().sendMessage("Was not removed because Webhook has Not been Initialized Yet").queue();
        }
        //confirmation message
        EmbedBuilder eb = new EmbedBuilder();
        MessageEmbed eb3 = new MessageEmbed("", "", "", null, null, 0, null, null, null, null, null, null, null);
        eb.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        eb.setTitle("Successfully Removed URL from Webhook! :page_with_curl: :white_check_mark:");
        eb.setThumbnail("https://jonmgomes.com/wp-content/uploads/2020/02/Checkbox-Icon.gif"); //checkbox gif
        eb.setFooter("Powered By Helium Restocks");
        eb3 = eb.build();
        channel.sendMessage(eb3).queue();
        //
        Runner.panel.updateActiveMonitors();
    }



}



//WAS IN ONMESSAGERECEIVED OVERRIDE
//        //Can only add each URL once (Helps avoid redundance)
//        boolean containsURL = false;
//        for (int i = 0; i < webhookURLList.size(); i++) {
//            if (webhookURLList.get(i).equals(msg.getContentRaw())) {
//                containsURL = true;
//                break;
//            }
//        }
//
//        if (msg.getContentRaw().equals("!Add Webhook") && !adding) {
//            channel.sendMessage("Please enter link of Webhook you would like to add: ").queue();
//            adding = true;
//        }else if (adding && msg.getContentRaw().contains("https://discord.com/api/webhooks")
//                && !containsURL) {
//
//            //checks to make sure is valid URL. Throws an error if it is not valid.
//            try {
//                webhookManagerList.add(new WebHookSeleniumManager(msg.getContentRaw(), event.getChannel()));
//                webhookURLList.add(msg.getContentRaw());
//                channel.sendMessage("Added new Webhook").queue();
//                adding = false;
//            } catch (Exception e) {
//                channel.sendMessage("Please enter a valid Discord Webhook URL.").queue();
//            }
//
//        }else if(msg.getContentRaw().equals("!Remove Webhook") && !removing) {
//
//            channel.sendMessage("Please enter link of Webhook you would like to remove: ").queue();
//            removing = true;
//
//        }else if(removing && msg.getContentRaw().contains("https://discordapp.com/api/webhooks")
//                && containsURL) {
//
//            //removes notifications from a webhook.
//            for (int i = 0; i < webhookURLList.size(); i++) {
//                if(webhookURLList.get(i).equals(msg.getContentRaw())) {
//                    webhookManagerList.get(i).removeOutputs();
//                    webhookManagerList.remove(i);
//                    webhookURLList.remove(i);
//                }
//            }
//            channel.sendMessage("Removed All Webhook Messages.").queue();
//            removing = false;
//
//        }else if (msg.getContentRaw().contains("Webhook Edit:") && !msg.getContentRaw().equals("!Help") && !msg.getContentRaw().contains("Type !Add") && !msg.getContentRaw().contains("If you wish")) {
//            String URLTemp = "";
//            String commandTemp = "";
//            boolean hasURL = false;
//            boolean gettingURL = false;
//            boolean hasCommand = false;
//            boolean gettingCommand = false;
//            boolean urlIsInList = false;
//
//            for (int i = 0; i < webhookURLList.size(); i++) {
//
//                if (msg.getContentRaw().contains(webhookURLList.get(i))) {
//                    urlIsInList = true;
//                    System.out.println("Has used URL in message");
//                    for (int j = 0; j < msg.getContentRaw().length(); j++) {
//                        if (msg.getContentRaw().charAt(j) == '(' && !gettingURL && !hasURL) {
//                            gettingURL = true;
//                        }else if (msg.getContentRaw().charAt(j) == ')' && gettingURL && !hasURL) {
//                            gettingURL = false;
//                            hasURL = true;
//                        }else if(gettingURL) {
//                            URLTemp += msg.getContentRaw().charAt(j);
//                        } else if (msg.getContentRaw().charAt(j) == '(' && !gettingCommand && !hasCommand) {
//                            gettingCommand = true;
//                        }else if (msg.getContentRaw().charAt(j) == ')' && gettingCommand && !hasCommand) {
//                            gettingCommand = false;
//                            hasCommand = false;
//                        }else if(gettingCommand) {
//                            commandTemp += msg.getContentRaw().charAt(j);
//
//
//                        }
//                    }
//
//                    for (int j = 0; j < webhookURLList.size(); j++) {
//                        if (webhookURLList.get(j).equals(URLTemp)) {
//                            webhookManagerList.get(j).sendCommand(commandTemp);
//                            webhookManagerList.get(j).sendChannel(channel);
//                        }
//                    }
//
//                }
//
//            }
//
//            if (!urlIsInList) {
//                channel.sendMessage(
//                        "The URL is not in the list. Please type !Help for instructions on how to make a new Webhook")
//                        .queue();
//            }
//
//        } else if (webhookURLList.contains(msg.getContentRaw()) && adding) {
//            channel.sendMessage("This Webhook has already been created.").queue();
//            adding = false;
//        } else if (msg.getContentRaw().equals("!Help")) {
//
//            channel.sendMessage(
//                    "Type !Add Webhook to add a webhook to a text channel in your server. Then input the URL of the webhook.")
//                    .queue();
//            channel.sendMessage(
//                    "If you wish to edit a Webhook please type     \n'Webhook Edit: (WebhookURL) (Command)'   ")
//                    .queue();
//
//        }
