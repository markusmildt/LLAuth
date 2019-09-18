package me.authbot.BoT;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;


public class Main extends ListenerAdapter {

    private SQLManager database = new SQLManager();

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken("NjE4OTY1NDI0MzI3MDk4Mzcw.XXuixg.FvMaoY4DutZlgi5MA7cLV_NEScE"); //setting token
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
            String idURL = "https://lotte.link/api.php?id=" + event.getAuthor().getId();
            String inline = "";
            try {
                URL url = new URL(idURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.addRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:18.0) Gecko/20100101 Firefox/18.0 (compatible;)");
                conn.addRequestProperty("Accept", "*/*");
                conn.connect();

                int responseCode = conn.getResponseCode();
                if(responseCode == 521){ //repspond with "down for maintinence" upon a 521 response code
                    event.getChannel().sendMessage("Error: Server down for maintenance.").queue();
                }
                if (responseCode != 200) //if the response code is not 200 throw a runtime exception
                    throw new RuntimeException("HttpResponse Code: " + responseCode);
                else {
                    Scanner scan = new Scanner(url.openStream());
                    while (scan.hasNext()) { //reading contents of hte api into a string
                        inline += scan.nextLine();
                    }
                    System.out.println(inline);
                    scan.close();
                }

                JSONParser parse = new JSONParser();
                JSONObject jobj  = (JSONObject) parse.parse(inline); //parsing the json api
                JSONArray jsonarr = (JSONArray) jobj.get("verified");

                JSONObject verif = (JSONObject)jsonarr.get(0);
                String strVerif = verif.toString();

                if(strVerif.equalsIgnoreCase("true")){
                    event.getChannel().sendMessage("yeet feller!").queue();
                    event.getChannel().sendMessage(strVerif).queue();
                } else {
                    event.getChannel().sendMessage("Not Verified").queue();
                    event.getChannel().sendMessage(strVerif).queue();
                }
                conn.disconnect();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        //checks if the command user has the Admin role
        boolean isAdmin = false;
        List<Role> roles = event.getMember().getRoles();  //storing roles in a List
        for(Role r : roles){ //looping through list to search for the admin role
            if(r.getName().equalsIgnoreCase("Admin")){
                isAdmin = true;
                break;
            }
        }


        //setting a channel for the auth bot
        if(event.getMessage().getContentRaw().equalsIgnoreCase("ll.setchannel")){
            if(isAdmin) {
                String channelID = event.getChannel().getId();
                event.getChannel().sendMessage("Channel " + channelID + " set as AuthBot Channel").queue();
            } else {
                event.getChannel().sendMessage("You must be an Administrator to use this command!").queue();
            }
        }

        if(event.getMessage().getContentRaw().contains("ll.setrole")){
            if(isAdmin) {
                String msg = event.getMessage().getContentRaw();
                String roleID = msg.substring(11);
            } else {
                event.getChannel().sendMessage("You must be an Administrator to use this command!");
            }
        }

        if(event.getMessage().getContentRaw().contains("ll.set")){
            if(isAdmin){
                String msg = event.getMessage().getContentRaw();
                String serverID = msg.substring(7);
            }
        }


        //help command
        if(event.getMessage().getContentRaw().equalsIgnoreCase("ll.help")){
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + "\n**AuthBot Commands:** \n*ll.verify:* receive your verification code" +
                    "\n*test:* a temporary test command").queue();
        }
    }
}