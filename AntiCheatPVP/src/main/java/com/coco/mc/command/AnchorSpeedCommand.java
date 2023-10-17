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
        name = "anchorspeed",
        aliases = "aspeed",
        description = "Display's the player's anchor speed",
        tabComplete = false
)
public class AnchorSpeedCommand {

    @SubCommand(
    )
    public void anchorSpeedCommand(CommandSender sender, String[] args){
        if(PermUtil.hasPermission(sender, "anchorspeed.use")){
            if(sender instanceof Player player && args.length == 0){
                PlayerData playerData = PlayerData.get(player.getUniqueId());
                if(playerData.getAnchorSpeed() == 0){
                    player.sendMessage(ConfigUtil.getString("messages.no-data"));
                    return;
                }
                player.sendMessage(ConfigUtil.getString("messages.anchor-speed")
                        .replace("{player}", player.getName())
                        .replace("{speed}", String.valueOf(playerData.getAnchorSpeed())));
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
                if(playerData.getAnchorSpeed() == 0){
                    sender.sendMessage(ConfigUtil.getString("messages.no-data"));
                    return;
                }
                sender.sendMessage(ConfigUtil.getString("messages.anchor-speed")
                        .replace("{player}", player.getName())
                        .replace("{speed}", String.valueOf(playerData.getAnchorSpeed())));
            }
        }
    }
}
