package me.authbot.BoT;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;
import java.sql.*;


public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken("NjE4OTY1NDI0MzI3MDk4Mzcw.XXBXsA.oC2L7WaEjMYOeoKXr7zoyeqbuvw"); //setting token
        builder.addEventListeners(new Main());
        builder.build();
        

        //get auth code - auth
    }

    //runs whenever a message is received in chat
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        //prevents bots becoming stuck in infinite loops
        if(event.getAuthor().isBot() || event.getAuthor().isFake()){
            return;
        }

        //temp test command
        if(event.getMessage().getContentRaw().equalsIgnoreCase("test")) {
            String msg = event.getMessage().getContentRaw();
            event.getChannel().sendMessage("We received the string " + msg + " from " + event.getAuthor().getName()).queue();
        }

        //request to verify command
        if(event.getMessage().getContentRaw().equalsIgnoreCase("ll.verify")){
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + "\nHere is your verification code: ").queue();
            event.getMessage().delete().queue(); //deletes the user's command
        }

        //help command
        if(event.getMessage().getContentRaw().equalsIgnoreCase("ll.help")){
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + "\n**AuthBot Commands:** \n*ll.verify:* receive your verification code" +
                    "\n*test:* a temporary test command").queue();
        }
    }
}