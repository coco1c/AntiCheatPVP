package com.coco.mc.command;


import com.hakan.core.command.executors.basecommand.BaseCommand;
import com.hakan.core.command.executors.subcommand.SubCommand;
import com.coco.mc.util.ConfigUtil;
import com.coco.mc.util.PermUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@BaseCommand(
        name = "pop",
        description = "Pop's the player's totem",
        tabComplete = false
)
public class PopCommand {

    @SubCommand()
    public void pop(CommandSender sender, String[] args){
        if(PermUtil.hasPermission(sender, "pop.use")) {
            if(args.length == 1){
                String playerName = args[0];
                Player player =  Bukkit.getPlayer(playerName);
                if(player == null){
                    sender.sendMessage(ConfigUtil.getString("messages.player-not-found"));
                    return;
                }
                if((player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() == Material.TOTEM_OF_UNDYING)){
                   player.damage(player.getMaxHealth());
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                }
                if((player.getInventory().getItemInOffHand() != null && player.getInventory().getItemInOffHand().getType() == Material.TOTEM_OF_UNDYING)){
                    player.damage(player.getMaxHealth());
                    player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                }
                sender.sendMessage(ConfigUtil.getString("messages.pop"));
            }else{
                sender.sendMessage(ConfigUtil.getString("messages.invalid-command"));
            }
        }
    }
}
