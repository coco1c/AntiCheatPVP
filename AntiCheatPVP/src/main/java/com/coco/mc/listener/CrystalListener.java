package com.coco.mc.listener;

import com.hakan.core.HCore;
import com.coco.mc.data.PlayerData;
import com.coco.mc.util.ConfigUtil;
import com.coco.mc.util.HackerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CrystalListener implements Listener {

    @EventHandler
    public void onCrystal(PlayerInteractEvent event){
        if(event.isCancelled()) return;
        ItemStack itemStack = event.getItem();
        if(itemStack != null && itemStack.getType() == Material.END_CRYSTAL && event.getAction() == Action.RIGHT_CLICK_BLOCK){
            long currentTime = System.currentTimeMillis();
            HCore.registerEvent(EntityDamageByEntityEvent.class).filter(e -> (e.getDamager() == event.getPlayer() && e.getEntity() instanceof EnderCrystal)).limit(1).consume((e) -> {
                long damageTime = System.currentTimeMillis();
                long timeElapsed = damageTime - currentTime;
                if(timeElapsed > 60000){
                    timeElapsed = 60000;
                }
                PlayerData.get(event.getPlayer().getUniqueId()).setCrystalSpeed(timeElapsed);
                if(timeElapsed <= ConfigUtil.getSusSpeed("crystal")){
                    HackerUtil.hacker(event.getPlayer(), "crystal", timeElapsed);
                }
            });
        }
    }

    @EventHandler
    public void onAnchorPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;

        if (event.getBlockPlaced().getType() == Material.RESPAWN_ANCHOR) {
            Block block = event.getBlockPlaced();
            long currentTime = System.currentTimeMillis();
            HCore.registerEvent(BlockExplodeEvent.class)
                    .filter(e -> e.getBlock().getLocation().getX() == block.getLocation().getX() && e.getBlock().getLocation().getZ() == block.getLocation().getZ() && e.getBlock().getLocation().getY() == block.getLocation().getY())
                    .limit(1)
                    .consume((e) -> {
                        long placeTime = System.currentTimeMillis();
                        long timeElapsed = placeTime - currentTime;
                        if (timeElapsed > 60000) {
                            timeElapsed = 60000;
                        }
                        PlayerData.get(event.getPlayer().getUniqueId()).setAnchorSpeed(timeElapsed);
                        if(timeElapsed <= ConfigUtil.getSusSpeed("anchor")){
                            HackerUtil.hacker(event.getPlayer(), "anchor", timeElapsed);
                        }
                    });
        }
    }

    @EventHandler
    public void onTotem(EntityResurrectEvent event){
        if(event.isCancelled()) return;
      if(event.getEntity() instanceof Player player){
          EquipmentSlot equipmentSlot = event.getHand();
          if (equipmentSlot == null) return;
          AtomicBoolean isDone = new AtomicBoolean(false);
          long currentTime = System.currentTimeMillis();
          HCore.syncScheduler().every(1L).freezeIf(e -> isDone.get()).run(() -> {
             if(player.getInventory().getItem(equipmentSlot) != null && player.getInventory().getItem(equipmentSlot).getType() == Material.TOTEM_OF_UNDYING){
                 isDone.set(true);
                 long totemTime = System.currentTimeMillis() - currentTime;
                 if(totemTime > 60000){
                     totemTime = 60000;
                 }
                 PlayerData.get(player.getUniqueId()).setTotemSpeed(totemTime);
                 if(totemTime <= ConfigUtil.getSusSpeed("totem")){
                     HackerUtil.hacker(player, "totem", totemTime);
                 }
             }
          });
      }
    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent event){
        List<String> players = new ArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers()){
            players.add(player.getName());
        }
        if(event.getBuffer().contains("pop") || event.getBuffer().contains("totemspeed")
                || event.getBuffer().contains("anchorspeed") || event.getBuffer().contains("cspeed")
                || event.getBuffer().contains("crystalspeed") || event.getBuffer().contains("tspeed")
                || event.getBuffer().contains("aspeed")){
            event.setCompletions(players);
        }
    }

}
