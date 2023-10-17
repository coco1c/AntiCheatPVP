package com.coco.mc.util;

import com.hakan.core.utils.ColorUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermUtil {

    public static boolean hasPermission(Player player, String perm){
        if(!player.hasPermission(perm)){
            player.sendMessage(ColorUtil.colored(ConfigUtil.getString("messages.no-permission")));
            return false;
        }
        return true;
    }

    public static boolean hasPermission(CommandSender player, String perm){
        if(!player.hasPermission(perm)){
            player.sendMessage(ColorUtil.colored(ConfigUtil.getString("messages.no-permission")));
            return false;
        }
        return true;
    }
}
