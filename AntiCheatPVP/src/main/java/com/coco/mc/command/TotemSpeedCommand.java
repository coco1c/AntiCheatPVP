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
        name = "totemSpeed",
        aliases = "tspeed",
        description = "Display's the player's totem speed",
        tabComplete = false
)
public class TotemSpeedCommand {

    @SubCommand(
    )
    public void totemSpeedCommand(CommandSender sender, String[] args){
        if(PermUtil.hasPermission(sender, "totemspeed.use")){
            if(sender instanceof Player player && args.length == 0){
                PlayerData playerData = PlayerData.get(player.getUniqueId());
                if(playerData.getTotemSpeed() == 0){
                    player.sendMessage(ConfigUtil.getString("messages.no-data"));
                    return;
                }
                player.sendMessage(ConfigUtil.getString("messages.totem-speed")
                        .replace("{player}", player.getName())
                        .replace("{speed}", String.valueOf(playerData.getTotemSpeed())));
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
                if(playerData.getTotemSpeed() == 0){
                    sender.sendMessage(ConfigUtil.getString("messages.no-data"));
                    return;
                }
                sender.sendMessage(ConfigUtil.getString("messages.totem-speed")
                        .replace("{player}", player.getName())
                        .replace("{speed}", String.valueOf(playerData.getTotemSpeed())));
            }
        }
    }
}
