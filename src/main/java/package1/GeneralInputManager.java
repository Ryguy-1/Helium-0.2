package package1;

//import java.io.IOException;
//
//import javax.security.auth.login.LoginException;
//
//import net.dv8tion.jda.api.JDA;
//import net.dv8tion.jda.api.JDABuilder;
//
//import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
//import net.dv8tion.jda.api.hooks.ListenerAdapter;
//
////listens to every input it has access to and sends the input to the input manager for each guild.
//public class GeneralInputManager extends ListenerAdapter {
//
//
//    private String guildId;
//    private JDA jda;
//    private boolean isWrong;
//
//
//    GeneralInputManager(String key) throws LoginException {
//        guildId = "";
//        //Helium Restocks Code
//        jda = JDABuilder.createDefault(key)
//                .addEventListeners(this).build();
//        isWrong = false;
//    }
//
//    public void onMessageReceived(MessageReceivedEvent event) {
//
//        boolean inGuilds = false;
//
//        // checks if in a guild
//        for (int i = 0; i < Runner.guildIds.size(); i++) {
//
//            try {
//                if (Runner.guildIds.get(i).equals(event.getGuild().getId())) {
//                    inGuilds = true;
//                }
//            } catch (Exception e) {
//                // if you try to access the bot by talking directly to it. Also sets isWrong to
//                // make sure second try catch block does not print twice;
//                if (!event.getMessage().getContentRaw().contains("Please enter a server")) {
//                    event.getChannel().sendMessage("Please enter a server, not directly to the bot.").queue();
//                    isWrong = true;
//                    // so loop doesn't keep running and print message more than once.
//                    break;
//                }
//            }
//        }
//        // if not in a guild do this...
//        if (!inGuilds) {
//            // tries to add the new guild if the message was sent from a guild and inGuilds
//            // == false
//            try {
//                Runner.guildIds.add(event.getGuild().getId());
//                try {
//                    // adds new input manager for the new guild
//                    Runner.managers.add(new InputManager(event.getGuild().getId()));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                // sends the event just put in to the latest created inputManager. This ensures
//                // no messages go unheard.
//                Runner.managers.get(Runner.managers.size() - 1).sendEvent(event);
//            } catch (Exception e) {
//                // if you try to access the bot by talking directly to it. And message has not
//                // been printed out already.
//                if (!event.getMessage().getContentRaw().contains("Please enter a server") && isWrong == false) {
//                    event.getChannel().sendMessage("Please enter a server, not directly to the bot.").queue();
//                }
//            }
//            // condition to make sure Please enter a server message is not printed twice. Is
//            // set in first try catch block above.
//            isWrong = false;
//            // if in a guild do this.
//        } else if (inGuilds) {
//            // if the event sent is from a guild with an inputManager already in place, it
//            // directs the event to that inputManager.
//            for (int i = 0; i < Runner.guildIds.size(); i++) {
//                if (Runner.guildIds.get(i).equals(event.getGuild().getId())) {
//                    // managers and guildIds have same indexes
//                    try {
//                        Runner.managers.get(i).sendEvent(event);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//}
