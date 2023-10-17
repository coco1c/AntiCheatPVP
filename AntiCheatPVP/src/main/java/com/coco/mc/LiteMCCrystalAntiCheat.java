package com.coco.mc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hakan.core.HCore;
import com.coco.mc.command.*;
import com.coco.mc.data.PlayerData;
import com.coco.mc.listener.CrystalListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class LiteMCCrystalAntiCheat extends JavaPlugin {

    public List<PlayerData> playerData;
    public static LiteMCCrystalAntiCheat instance;
    public List<UUID> offDuty;
    public HashMap<UUID, Long> messageCooldown;

    Logger logger;

    @Override
    public void onEnable() {
        playerData = new ArrayList<>();
        offDuty = new ArrayList<>();
        instance = this;
        HCore.initialize(instance);
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        logger = getLogger();
        messageCooldown = new HashMap<>();
        if(getConfig().contains("plugin-do-not-edit-1")){
            playerData = new Gson().fromJson(getConfig().getString("plugin-do-not-edit-1"), new TypeToken<List<PlayerData>>(){}.getType());
        }
        if(getConfig().contains("plugin-do-not-edit-2")){
            offDuty = new Gson().fromJson(getConfig().getString("plugin-do-not-edit-2"), new TypeToken<List<UUID>>(){}.getType());
        }

        HCore.registerCommands(new AnchorSpeedCommand(), new CrystalSpeedCommand(), new TotemSpeedCommand(), new PopCommand(), new CrystalAntiCheatCommand());
        HCore.registerListeners(new CrystalListener());

        createLogFile();
    }


    public void addTabComplete(){
        TabCompleter tabCompleter = new TabCompleter() {
            @Override
            public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
                List<String> players = new ArrayList<>();
                for(Player player : Bukkit.getOnlinePlayers()){
                    players.add(player.getName());
                }
                return players;
            }
        };


        getServer().getPluginCommand("crystalspeed").setTabCompleter(tabCompleter);
        getServer().getPluginCommand("anchorspeed").setTabCompleter(tabCompleter);
        getServer().getPluginCommand("totemspeed").setTabCompleter(tabCompleter);
        getServer().getPluginCommand("pop").setTabCompleter(tabCompleter);

    }
    public void createLogFile() {
        // Create the plugin directory if it doesn't exist
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Create the log file
        File logFile = new File(getDataFolder(), "AntiCheat.log");
        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            // Configure the logger to write to the log file
            FileHandler fileHandler = new FileHandler(logFile.getAbsolutePath(), true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logMessage(String message) {
        // Get current time and date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String currentTime = timeFormat.format(new Date());
        String currentDate = dateFormat.format(new Date());

        // Log the message with timestamp
        String formattedMessage = String.format("[TIME:%s | DATE:%s] %s", currentTime, currentDate, message);
        logger.log(Level.INFO, formattedMessage);
    }

    @Override
    public void onDisable() {
        getConfig().set("plugin-do-not-edit-1", new Gson().toJson(playerData));
        getConfig().set("plugin-do-not-edit-2", new Gson().toJson(offDuty));
        saveConfig();
    }
}
