package com.coco.mc.data;

import com.coco.mc.LiteMCCrystalAntiCheat;
import org.bukkit.Bukkit;

import java.util.UUID;

public class PlayerData {

    private String uuid;
    private String name;
    private long crystalSpeed;
    private long totemSpeed;
    private long anchorSpeed;

    public PlayerData(String uuid, String name, long crystalSpeed, long totemSpeed, long anchorSpeed) {
        this.uuid = uuid;
        this.name = name;
        this.crystalSpeed = crystalSpeed;
        this.totemSpeed = totemSpeed;
        this.anchorSpeed = anchorSpeed;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public long getCrystalSpeed() {
        return crystalSpeed;
    }

    public long getAnchorSpeed() {
        return anchorSpeed;
    }

    public long getTotemSpeed() {
        return totemSpeed;
    }

    public void setAnchorSpeed(long anchorSpeed) {
        this.anchorSpeed = anchorSpeed;
    }

    public void setCrystalSpeed(long crystalSpeed) {
        this.crystalSpeed = crystalSpeed;
    }

    public void setTotemSpeed(long totemSpeed) {
        this.totemSpeed = totemSpeed;
    }

    public static PlayerData get(UUID uuid){
        for (PlayerData playerDatum : LiteMCCrystalAntiCheat.instance.playerData) {
            if(playerDatum.getUuid().equalsIgnoreCase(uuid.toString())){
                return playerDatum;
            }
        }
        PlayerData playerData = new PlayerData(uuid.toString(), Bukkit.getOfflinePlayer(uuid).getName(), 0, 0, 0);
        LiteMCCrystalAntiCheat.instance.playerData.add(playerData);
        return playerData;
    }
}
