package package1;

import java.io.IOException;
import java.util.ArrayList;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.io.IOException;

import javax.security.auth.login.LoginException;

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



    private MessageChannel channel;

    private ArrayList<String> webhookURLList;
    private ArrayList<WebHookSeleniumManager> webhookManagerList;
    private boolean adding;
    private boolean removing;

    InputManager() {

        webhookManagerList = new ArrayList<>();
        webhookURLList = new ArrayList<>();
        adding = false;
        removing = false;

    }


    public void addWebhook(String URL){
        try {
            webhookManagerList.add(new WebHookSeleniumManager(URL, null));
            webhookURLList.add(URL);
        } catch (Exception e) {
            System.out.println("Not a Valid Webhook URL");
        }
    }


    public void addURLtoWebhook(String webhookToAddToURL, String websiteURL){
        if(websiteURL.contains("amazon") || websiteURL.contains("bestbuy") || websiteURL.contains("target") || websiteURL.contains("walmart")) {
            for (int j = 0; j < webhookURLList.size(); j++) {
                if (webhookURLList.get(j).equals(webhookToAddToURL)) {
                    webhookManagerList.get(j).addMonitor(websiteURL);
                }
            }
        }else{
            System.out.println("Not a valid Website URL");
        }
    }

    public void removeWebhook(String webhookURL){
        for (int i = 0; i < webhookURLList.size(); i++) {
            if(webhookURLList.get(i).equals(webhookURL)) {
                webhookManagerList.get(i).removeOutputs();
                webhookManagerList.remove(i);
                webhookURLList.remove(i);
            }
        }
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

    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        channel = msg.getChannel();


        //Can only add each URL once (Helps avoid redundance)
        boolean containsURL = false;
        for (int i = 0; i < webhookURLList.size(); i++) {
            if (webhookURLList.get(i).equals(msg.getContentRaw())) {
                containsURL = true;
                break;
            }
        }

        if (msg.getContentRaw().equals("!Add Webhook") && !adding) {
            channel.sendMessage("Please enter link of Webhook you would like to add: ").queue();
            adding = true;
        }else if (adding && msg.getContentRaw().contains("https://discord.com/api/webhooks")
                && !containsURL) {

            //checks to make sure is valid URL. Throws an error if it is not valid.
            try {
                webhookManagerList.add(new WebHookSeleniumManager(msg.getContentRaw(), event.getChannel()));
                webhookURLList.add(msg.getContentRaw());
                channel.sendMessage("Added new Webhook").queue();
                adding = false;
            } catch (Exception e) {
                channel.sendMessage("Please enter a valid Discord Webhook URL.").queue();
            }

        }else if(msg.getContentRaw().equals("!Remove Webhook") && !removing) {

            channel.sendMessage("Please enter link of Webhook you would like to remove: ").queue();
            removing = true;

        }else if(removing && msg.getContentRaw().contains("https://discordapp.com/api/webhooks")
                && containsURL) {

            //removes notifications from a webhook.
            for (int i = 0; i < webhookURLList.size(); i++) {
                if(webhookURLList.get(i).equals(msg.getContentRaw())) {
                    webhookManagerList.get(i).removeOutputs();
                    webhookManagerList.remove(i);
                    webhookURLList.remove(i);
                }
            }
            channel.sendMessage("Removed All Webhook Messages.").queue();
            removing = false;

        }else if (msg.getContentRaw().contains("Webhook Edit:") && !msg.getContentRaw().equals("!Help") && !msg.getContentRaw().contains("Type !Add") && !msg.getContentRaw().contains("If you wish")) {
            String URLTemp = "";
            String commandTemp = "";
            boolean hasURL = false;
            boolean gettingURL = false;
            boolean hasCommand = false;
            boolean gettingCommand = false;
            boolean urlIsInList = false;

            for (int i = 0; i < webhookURLList.size(); i++) {

                if (msg.getContentRaw().contains(webhookURLList.get(i))) {
                    urlIsInList = true;
                    System.out.println("Has used URL in message");
                    for (int j = 0; j < msg.getContentRaw().length(); j++) {
                        if (msg.getContentRaw().charAt(j) == '(' && !gettingURL && !hasURL) {
                            gettingURL = true;
                        }else if (msg.getContentRaw().charAt(j) == ')' && gettingURL && !hasURL) {
                            gettingURL = false;
                            hasURL = true;
                        }else if(gettingURL) {
                            URLTemp += msg.getContentRaw().charAt(j);
                        } else if (msg.getContentRaw().charAt(j) == '(' && !gettingCommand && !hasCommand) {
                            gettingCommand = true;
                        }else if (msg.getContentRaw().charAt(j) == ')' && gettingCommand && !hasCommand) {
                            gettingCommand = false;
                            hasCommand = false;
                        }else if(gettingCommand) {
                            commandTemp += msg.getContentRaw().charAt(j);


                        }
                    }

                    for (int j = 0; j < webhookURLList.size(); j++) {
                        if (webhookURLList.get(j).equals(URLTemp)) {
                            webhookManagerList.get(j).sendCommand(commandTemp);
                            webhookManagerList.get(j).sendChannel(channel);
                        }
                    }

                }

            }

            if (!urlIsInList) {
                channel.sendMessage(
                        "The URL is not in the list. Please type !Help for instructions on how to make a new Webhook")
                        .queue();
            }

        } else if (webhookURLList.contains(msg.getContentRaw()) && adding) {
            channel.sendMessage("This Webhook has already been created.").queue();
            adding = false;
        } else if (msg.getContentRaw().equals("!Help")) {

            channel.sendMessage(
                    "Type !Add Webhook to add a webhook to a text channel in your server. Then input the URL of the webhook.")
                    .queue();
            channel.sendMessage(
                    "If you wish to edit a Webhook please type     \n'Webhook Edit: (WebhookURL) (Command)'   ")
                    .queue();

        }
    }
}
