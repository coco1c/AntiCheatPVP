package com.coco.mc.util;

import com.hakan.core.HCore;
import com.hakan.core.utils.ColorUtil;
import com.coco.mc.LiteMCCrystalAntiCheat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HackerUtil {

    public static void hacker(Player victim, String type, long speed){
        if(LiteMCCrystalAntiCheat.instance.messageCooldown.containsKey(victim.getUniqueId())) return;
        LiteMCCrystalAntiCheat.instance.messageCooldown.put(victim.getUniqueId(), System.currentTimeMillis());
        HCore.asyncScheduler().after(LiteMCCrystalAntiCheat.instance.getConfig().getInt("cooldown", 10), TimeUnit.SECONDS).run(() -> {
           LiteMCCrystalAntiCheat.instance.messageCooldown.remove(victim.getUniqueId());
        });
        LiteMCCrystalAntiCheat.instance.logMessage(victim.getName() + " is hacking with type " + type + " with Speed " + speed + " ms!");
        String sendToSUSPlayer = ConfigUtil.getString("auto-mod.sus-player-msg")
                .replace("{victim}", victim.getName())
                .replace("{type}", type)
                .replace("{speed}", String.valueOf(speed))
                .replace("{options}", " ");
        if(!sendToSUSPlayer.isEmpty()){
            victim.sendMessage(ColorUtil.colored(sendToSUSPlayer));
        }
        String actionBarMSG = ConfigUtil.getString("auto-mod.sus-player-actionbar")
                .replace("{victim}", victim.getName())
                .replace("{type}", type)
                .replace("{speed}", String.valueOf(speed))
                .replace("{options}", " ");
        if(!actionBarMSG.isEmpty()){
            victim.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ColorUtil.colored(actionBarMSG)));
        }
        String title = ConfigUtil.getString("auto-mod.sus-player-title")
                .replace("{victim}", victim.getName())
                .replace("{type}", type)
                .replace("{speed}", String.valueOf(speed))
                .replace("{options}", " ");
        String subtitle = ConfigUtil.getString("auto-mod.sus-player-sub-title")
                .replace("{victim}", victim.getName())
                .replace("{type}", type)
                .replace("{speed}", String.valueOf(speed))
                .replace("{options}", " ");
        if(!title.isEmpty() && !subtitle.isEmpty()){
            victim.sendTitle(ColorUtil.colored(title), ColorUtil.colored(subtitle), 20, 20, 20);
        }

        List<String> punishments = LiteMCCrystalAntiCheat.instance.getConfig().getStringList("auto-mod.punishment-commands");
        if(!punishments.isEmpty()){
            for(String punishment : punishments){
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), punishment
                        .replace("{victim}", victim.getName())
                        .replace("{type}", type)
                        .replace("{speed}", String.valueOf(speed))
                        .replace("{options}", " "));
            }
        }


        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
           if(onlinePlayer.hasPermission("hacker.see") && !LiteMCCrystalAntiCheat.instance.offDuty.contains(onlinePlayer.getUniqueId())){
               TextComponent options = new TextComponent("");
               List<TextComponent> textComponents = new ArrayList<>();
               ConfigurationSection section = LiteMCCrystalAntiCheat.instance.getConfig().getConfigurationSection("buttons");
               if(section != null){
                   for (String key : section.getKeys(false)) {
                       TextComponent textComponent = new TextComponent(ColorUtil.colored(section.getString(key + ".name")));
                       textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, section.getString(key + ".command")
                               .replace("%p%", onlinePlayer.getName())
                               .replace("{victim}", victim.getName())
                               .replace("{type}", type)
                               .replace("{speed}", String.valueOf(speed))));
                       textComponents.add(textComponent);
                   }
               }
               int i = 0;
               for (TextComponent textComponent : textComponents) {
                   if(i == 0){
                       options.addExtra(textComponent);
                   }else{
                       options.addExtra(" ");
                       options.addExtra(textComponent);
                   }
                   i++;

               }

               TextComponent textComponent = new TextComponent("");
               textComponent.addExtra(ConfigUtil.getString("messages.sus-player")
                       .replace("{victim}", victim.getName())
                       .replace("{type}", type)
                       .replace("{speed}", String.valueOf(speed))
                       .replace("{options}", " "));
               textComponent.addExtra(options);
               onlinePlayer.spigot().sendMessage(textComponent);
           }
        }
    }
}
