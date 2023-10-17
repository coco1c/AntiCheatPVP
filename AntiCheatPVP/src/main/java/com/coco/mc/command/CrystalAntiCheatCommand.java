package com.coco.mc.command;

import com.hakan.core.command.executors.basecommand.BaseCommand;
import com.hakan.core.command.executors.subcommand.SubCommand;
import com.hakan.core.utils.ColorUtil;
import com.coco.mc.LiteMCCrystalAntiCheat;
import com.coco.mc.util.ConfigUtil;
import com.coco.mc.util.PermUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@BaseCommand(
        name = "litemccrystalanticheat",
        aliases = "crystalanticheat",
        description = "Main Command for Litemc Crystal AntiCheat"
)
public class CrystalAntiCheatCommand {

    @SubCommand(
            args = "offduty"
    )
    public void offDuty(CommandSender sender, String[] args){
        if(sender instanceof Player player && PermUtil.hasPermission(player, "crystalanticheat.use")) {
            LiteMCCrystalAntiCheat.instance.offDuty.add(player.getUniqueId());
            player.sendMessage(ColorUtil.colored("&aDone!"));
        }else{
            sender.sendMessage(ConfigUtil.getString("messages.no-permission"));
        }
    }

    @SubCommand(
            args = "reload"
    )
    public void reload(CommandSender sender, String[] args){
        if(PermUtil.hasPermission(sender, "crystalanticheat.reload")) {
            LiteMCCrystalAntiCheat.instance.reloadConfig();
            sender.sendMessage(ColorUtil.colored("&aDone!"));
        }
    }
    @SubCommand(
            args = "onduty"
    )
    public void onDuty(CommandSender sender, String[] args){
        if(sender instanceof Player player && PermUtil.hasPermission(player, "crystalanticheat.use")){
            LiteMCCrystalAntiCheat.instance.offDuty.remove(player.getUniqueId());
            player.sendMessage(ColorUtil.colored("&aDone!"));
        }else{
            sender.sendMessage(ConfigUtil.getString("messages.no-permission"));
        }
    }
}
