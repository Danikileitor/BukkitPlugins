package com.danikileitor.pescalocke;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

	public ItemStack getPalo(){
		ItemStack item = new ItemStack(Material.FISHING_ROD, 1);

		ItemMeta propied = item.getItemMeta();
		ArrayList<String> lores = new ArrayList<>();
		lores.add("Caña buena");
		lores.add("Generada por PescaLocke, por CristichiEX");
		propied.setLore(lores);
		propied.addEnchant(Enchantment.LUCK, Enchantment.LUCK.getMaxLevel(), true);
		propied.addEnchant(Enchantment.LURE, Enchantment.LURE.getMaxLevel(), true);
		propied.addEnchant(Enchantment.MENDING, Enchantment.MENDING.getMaxLevel(), true);
		propied.addEnchant(Enchantment.DURABILITY, Enchantment.DURABILITY.getMaxLevel(), true);
		propied.setDisplayName("§4§KCaña de Pescar §6RANDOM");
		item.setItemMeta(propied);

		return item;
	}

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("§2Plugin cargado correctamente");
		super.onEnable();
	}

	@Override
	public void onLoad() {
		getServer().broadcastMessage("§2[PescaLocke]§A Plugin cargado correctamente");
		super.onLoad();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (!player.hasPlayedBefore()){
			PlayerInventory inventory = player.getInventory();
			ItemStack itemstack = getPalo();

			inventory.addItem(itemstack);
			player.sendMessage("§2[PescaLocke]§A Tu caña, a pescar que es gerundio.");
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		PlayerInventory inventory = player.getInventory();
		ItemStack itemstack = getPalo();
		inventory.addItem(itemstack);
		player.sendMessage("§2[PescaLocke]§A Tu caña, a pescar que es gerundio.");
	}

	@EventHandler
	public void onPlayerFish(PlayerFishEvent pesca) {
		Entity pescao = pesca.getCaught();
		if (pescao==null){
			return;
		}
		Player p = pesca.getPlayer();

		pescao.isGlowing();
		boolean saleMob = false;
		if (Math.random()>0.80 || pesca.getState().equals(State.CAUGHT_ENTITY)){
			saleMob=true;
		}

		final Location loc = p.getLocation();
		if (saleMob){
			EntityType[] mobs = EntityType.values();
			int rng = 0;

			Entity aparecida= null;
			boolean bien= false;
			while(!bien){
				try{
					rng = new Random().nextInt(mobs.length);
					aparecida = p.getWorld().spawnEntity(pescao.getLocation(), mobs[rng]);
					aparecida.setCustomName("§4Random "+mobs[rng].getEntityClass().getSimpleName());
					aparecida.setCustomNameVisible(true);
					bien=true;
				}catch(Exception e){
					//getLogger().info("§A Error al hacer spawn a "+mobs[rng].getEntityClass().getTypeName());
				}

			}

			if (pescao instanceof Player){
				pescao.sendMessage("§2[PescaLocke]§A Te ha pescado "+p.getName()+" y te ha cambiado por "+aparecida.getType().getEntityClass().getSimpleName());
				p.sendMessage("§2[PescaLocke]§A Has pescado a "+pescao.getName()+" y lo has cambiado por "+aparecida.getType().getEntityClass().getSimpleName());
			}else{
				p.sendMessage("§2[PescaLocke]§A Has pescado un "+mobs[rng].getEntityClass().getSimpleName());
			}
			final Entity finalAp= aparecida;
			new Thread(new Runnable() {
				@Override
				public void run() {
					finalAp.teleport(loc);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
					finalAp.teleport(loc);
				}
			}).start();

		}else{
			PlayerInventory inventory = p.getInventory();
			Material[] items = Material.values();
			ArrayList<Material> comidas = new ArrayList<>();
			for (int i = 0; i < items.length; i++) {
				if (items[i].isEdible()){
					comidas.add(items[i]);
				}
			}
			int rng = new Random().nextInt(comidas.size());

			ItemStack item = new ItemStack(comidas.get(rng));
			ItemMeta propied = item.getItemMeta();
			if (item.getType().equals(Material.BOOK)){
				Enchantment[] encs = Enchantment.values();
				int numRandom = new Random().nextInt(40);
				for (int i = 0; i < numRandom; i++) {
					int rng2 = new Random().nextInt(encs.length);
					propied.addEnchant(encs[rng2], encs[rng2].getMaxLevel(), true);
				}
			}
			item.setItemMeta(propied);

			inventory.addItem(item);
			p.sendMessage("§2[PescaLocke]§A Has pescado un "+items[rng].name().toLowerCase().replace("_", " "));
		}
		if (pescao instanceof Player){
			pescao.teleport(loc);
		}else
			pescao.remove();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("darPalo")){
			try{
				if (args.length!=1){
					args= new String[]{((Player)sender).getName()};
				}

				@SuppressWarnings("deprecation")
				Player player = Bukkit.getPlayer(args[0]);
				PlayerInventory inventory = player.getInventory();
				ItemStack itemstack = getPalo();

				inventory.addItem(itemstack);

			}catch(Exception e){
				if (args.length!=1){
					return false;
				}
				try{
					Player tio = (Player)sender;
					tio.sendMessage("El jugador "+args[0]+" no existe.");
				}catch(Exception e1){
					Bukkit.getConsoleSender().sendMessage("El jugador "+args[0]+" no existe.");
				}
			}

			return true;
		}
		return super.onCommand(sender, command, label, args);
	}
}
