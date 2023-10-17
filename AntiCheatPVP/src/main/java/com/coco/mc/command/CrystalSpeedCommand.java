package com.coco.mc.command;

import com.hakan.core.command.executors.basecommand.BaseCommand;
import com.hakan.core.command.executors.subcommand.SubCommand;
import com.coco.mc.data.PlayerData;
import com.coco.mc.util.ConfigUtil;
import com.coco.mc.util.PermUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@BaseCommand(
        name = "crystalspeed",
        aliases = "cspeed",
        description = "Display's the player's crystal speed"
)

public class CrystalSpeedCommand {

    @SubCommand(
    )
    public void crystalCommand(CommandSender sender, String[] args){
        if(PermUtil.hasPermission(sender, "crystalspeed.use")){
           if(sender instanceof Player player && args.length == 0){
               PlayerData playerData = PlayerData.get(player.getUniqueId());
               if(playerData.getCrystalSpeed() == 0){
                   player.sendMessage(ConfigUtil.getString("messages.no-data"));
                   return;
               }
               player.sendMessage(ConfigUtil.getString("messages.crystal-speed")
                       .replace("{player}", player.getName())
                       .replace("{speed}", String.valueOf(playerData.getCrystalSpeed())));
           }else if(args.length == 0){
               sender.sendMessage(ConfigUtil.getString("messages.invalid-command"));

           }
           if(args.length == 1){
               String playerName = args[0];
               Player player =  Bukkit.getPlayer(playerName);
               if(player == null){
                   sender.sendMessage(ConfigUtil.getString("messages.player-not-found"));
                   return;
               }
               PlayerData playerData = PlayerData.get(player.getUniqueId());
               if(playerData.getCrystalSpeed() == 0){
                   sender.sendMessage(ConfigUtil.getString("messages.no-data"));
                   return;
               }
               sender.sendMessage(ConfigUtil.getString("messages.crystal-speed")
                       .replace("{player}", player.getName())
                       .replace("{speed}", String.valueOf(playerData.getCrystalSpeed())));
           }
        }
    }
}
