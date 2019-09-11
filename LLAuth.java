// ====================================================
//               LLAuth Version 1.0
//                   by DonuT (Markus)
// ====================================================
package me.donut.llauth.llauth;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class LLAuth extends JavaPlugin {

    //runs on plugin enable
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("onEnable has been invoked!");

    }

    //commands
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        //test command
        if(cmd.getName().equalsIgnoreCase("test")){   // "/test"
            sender.sendMessage("TEST!!");
            return true;
        }

        //ignite command
        if(cmd.getName().equalsIgnoreCase("ignite")){
            Player target = Bukkit.getServer().getPlayer(args[0]);
            target.setFireTicks(1000);
        }


        return false;
    }

    //runs on plugin disable
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("onDiable has been envoked!");
    }
}
