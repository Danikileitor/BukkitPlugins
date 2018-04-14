package com.danikileitor.pescalocke;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
    @Override
    public void onEnable() {
    	Bukkit.getServer().getPluginManager().registerEvents(this, this);    
    }
    
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    }
    

    public ItemStack PescaPRO(){
    	ItemStack itemstack = new ItemStack(Material.FISHING_ROD, 1);
    	ItemMeta propied = itemstack.getItemMeta();
    			propied.addEnchant(Enchantment.LUCK, Enchantment.LUCK.getMaxLevel(), true);
    			propied.addEnchant(Enchantment.LURE, 6, true);
    			propied.addEnchant(Enchantment.MENDING, Enchantment.MENDING.getMaxLevel(), true);
    			propied.addEnchant(Enchantment.DURABILITY, Enchantment.DURABILITY.getMaxLevel(), true);
    			propied.setDisplayName("§1Caña UltraPro\u2122");
    			itemstack.setItemMeta(propied);
    	return itemstack;
    		}
    
    @EventHandler
	public void onPlayerJoin(PlayerJoinEvent entrar) {
    	
    Player p = entrar.getPlayer();
    
    if (p.hasPlayedBefore() == false) {
    	p.sendMessage("§1¡Bienvenido al Pescalocke!");
    	p.getInventory().addItem(PescaPRO());
    }
    
    if (p.hasPlayedBefore() == true) {
    	p.sendMessage("§1Otro día más en el Pescalocke...");
    }
    
    }
    
    @EventHandler
    public void onPlayerFish(PlayerFishEvent pesca) {
    
    if (!pesca.getState().equals(State.CAUGHT_FISH)){return;}	
    	
    Player p = pesca.getPlayer();
    Entity pescao = pesca.getCaught();
    int exprandom = ThreadLocalRandom.current().nextInt(0, 100 + 1);
    EntityType[] entidadrandom = EntityType.values();
    int random = new Random().nextInt(entidadrandom.length);
    final Location loc = p.getLocation();
    pescao.setGravity(true);
    Entity lopescao = p.getWorld().spawnEntity(pescao.getLocation(), entidadrandom[random]);
    
    pesca.setExpToDrop(exprandom);
            		
    new Thread(new Runnable() {
       @Override
       public void run() {
    	   try {
        	Thread.sleep(1000);
        	} catch (InterruptedException error1) {}
        	lopescao.teleport(loc);
        	}}).start();
    pescao.remove();}

}
  
