package com.danikileitor.pescalocke;

import java.util.ArrayList;
import java.util.List;
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

	private Ajuste[] ajustes = new Ajuste[]{
			new Ajuste("giveAllOnRespawn", false),
			new Ajuste("giveAllOnFirstJoin", true)
	};

	private final String NOMBRE_PL= "Random Fishing";
	private final String MSG= "§2["+NOMBRE_PL+"]§A";

	public ItemStack getPaloRandom(){
		ItemStack item = new ItemStack(Material.FISHING_ROD, 1);

		ItemMeta propied = item.getItemMeta();
		ArrayList<String> lores = new ArrayList<>();
		lores.add("Aleatoriza tu mundo");
		lores.add("Generada por PescaLocke");
		propied.setLore(lores);
		propied.addEnchant(Enchantment.LUCK, Enchantment.LUCK.getMaxLevel(), true);
		propied.addEnchant(Enchantment.LURE, Enchantment.LURE.getMaxLevel(), true);
		propied.addEnchant(Enchantment.MENDING, Enchantment.MENDING.getMaxLevel(), true);
		propied.addEnchant(Enchantment.DURABILITY, Enchantment.DURABILITY.getMaxLevel(), true);
		propied.setDisplayName("§4§KCaña de Pescar 1§6§O RANDOM");
		item.setItemMeta(propied);

		return item;
	}

	public ItemStack getPaloNombres(){
		ItemStack item = new ItemStack(Material.FISHING_ROD, 1);

		ItemMeta propied = item.getItemMeta();
		ArrayList<String> lores = new ArrayList<>();
		lores.add("Pon tu nombre en todo");
		lores.add("Generada por PescaLocke");
		propied.setLore(lores);
		propied.addEnchant(Enchantment.LUCK, Enchantment.LUCK.getMaxLevel(), true);
		propied.addEnchant(Enchantment.LURE, Enchantment.LURE.getMaxLevel(), true);
		propied.addEnchant(Enchantment.MENDING, Enchantment.MENDING.getMaxLevel(), true);
		propied.addEnchant(Enchantment.DURABILITY, Enchantment.DURABILITY.getMaxLevel(), true);
		propied.setDisplayName("§4§KCaña de Pescar 2§6§O NOMBRES");
		item.setItemMeta(propied);

		return item;
	}

	public ItemStack getPaloMontame(){
		ItemStack item = new ItemStack(Material.FISHING_ROD, 1);

		ItemMeta propied = item.getItemMeta();
		ArrayList<String> lores = new ArrayList<>();
		lores.add("Pon tu nombre en todo");
		lores.add("Generada por PescaLocke");
		propied.setLore(lores);
		propied.addEnchant(Enchantment.LUCK, Enchantment.LUCK.getMaxLevel(), true);
		propied.addEnchant(Enchantment.LURE, Enchantment.LURE.getMaxLevel(), true);
		propied.addEnchant(Enchantment.MENDING, Enchantment.MENDING.getMaxLevel(), true);
		propied.addEnchant(Enchantment.DURABILITY, Enchantment.DURABILITY.getMaxLevel(), true);
		propied.setDisplayName("§4§KCaña de Pescar 3§6§O MONTAME");
		item.setItemMeta(propied);

		return item;
	}

	public ItemStack getPaloMontate(){
		ItemStack item = new ItemStack(Material.FISHING_ROD, 1);

		ItemMeta propied = item.getItemMeta();
		ArrayList<String> lores = new ArrayList<>();
		lores.add("Pon tu nombre en todo");
		lores.add("Generada por PescaLocke");
		propied.setLore(lores);
		propied.addEnchant(Enchantment.LUCK, Enchantment.LUCK.getMaxLevel(), true);
		propied.addEnchant(Enchantment.LURE, Enchantment.LURE.getMaxLevel(), true);
		propied.addEnchant(Enchantment.MENDING, Enchantment.MENDING.getMaxLevel(), true);
		propied.addEnchant(Enchantment.DURABILITY, Enchantment.DURABILITY.getMaxLevel(), true);
		propied.setDisplayName("§4§KCaña de Pescar 3§6§O MONTATE");
		item.setItemMeta(propied);

		return item;
	}

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		super.onEnable();
	}

	@Override
	public void onLoad() {
		for (int i = 0; i < ajustes.length; i++) {
			if (!getConfig().isSet(ajustes[i].clave)){
				getConfig().set(ajustes[i].clave, ajustes[i].porDefecto);
				getServer().broadcastMessage(MSG+" Ajustado "+ajustes[i].clave+" al valor por defecto ("+ajustes[i].toString()+")");
			}else{
				getConfig().set(ajustes[i].clave, getConfig().getBoolean(ajustes[i].clave));
			}
		}
		saveConfig();
		getServer().broadcastMessage(MSG+" Plugin cargado correctamente");
		super.onLoad();
	}

	@Override
	public void onDisable() {
		saveConfig();
		super.onDisable();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (!player.hasPlayedBefore()){
			PlayerInventory inventory = player.getInventory();
			ItemStack itemstack = getPaloRandom();
			inventory.addItem(itemstack);
			itemstack = getPaloNombres();
			inventory.addItem(itemstack);
			itemstack = getPaloMontate();
			inventory.addItem(itemstack);
			itemstack = getPaloMontame();
			inventory.addItem(itemstack);
			player.sendMessage(MSG+" Has recibido las cañas del amor.");
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		reloadConfig();
		Boolean configDarTodoRespawn = getConfig().getBoolean(ajustes[0].clave);

		if (configDarTodoRespawn){
			Player player = e.getPlayer();
			PlayerInventory inventory = player.getInventory();
			ItemStack itemstack = getPaloRandom();
			inventory.addItem(itemstack);
			itemstack = getPaloNombres();
			inventory.addItem(itemstack);
			itemstack = getPaloMontate();
			inventory.addItem(itemstack);
			itemstack = getPaloMontame();
			inventory.addItem(itemstack);
		}
	}

	/*
	@EventHandler
	public void onEntityHit(EntityDamageByEntityEvent e){
		if (e.getDamager() instanceof Player){
			getServer().broadcastMessage(e.getDamager().getName()+" > "+e.getEntity().getName());
			Player p = (Player) e.getDamager();
			@SuppressWarnings("deprecation")
			ItemStack mano = p.getItemInHand();
			ItemStack palo = getPaloRandom();
			if (mismoItem(mano, palo)){

			}
		}
	}
	 */

	public boolean mismoItem(ItemStack a, ItemStack b){
		String n1, n2;
		if (a.hasItemMeta())
			n1= a.getItemMeta().getDisplayName();
		else
			n1 = a.getType().name();
		if (b.hasItemMeta())
			n2=b.getItemMeta().getDisplayName();
		else
			n2 = b.getType().name();
		return n1.equals(n2);
	}


	public boolean tieneEseNombre(ItemStack a, String b){
		String n1;
		if (a.hasItemMeta())
			n1= a.getItemMeta().getDisplayName();
		else
			n1 = a.getType().name();
		return n1.equals(b);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerFish(PlayerFishEvent evento) {
		Entity pescao = evento.getCaught();
		if (pescao==null){
			return;
		}
		Player jugador = evento.getPlayer();

		pescao.isGlowing();
		ItemStack mano = jugador.getItemInHand();


		if (mismoItem(mano, getPaloNombres())){
			pescao.setCustomName("§6"+jugador.getDisplayName());
			pescao.setCustomNameVisible(true);

		}else if (mismoItem(mano, getPaloMontame())){
			if (pescao instanceof Player){
				Player pescado = (Player) pescao;
				if (pescado.getPlayerListName().equals(jugador.getPlayerListName())){
					jugador.sendMessage(MSG+" No puedes subirte sobre ti mismo");
					return;
				}
			}
			List<Entity> montados = jugador.getPassengers();
			for (int i = 0; i < montados.size(); i++) {
				pescao.addPassenger(montados.get(i));
			}
			pescao.addPassenger(jugador);

		}else if (mismoItem(mano, getPaloMontate())){
			if (pescao instanceof Player){
				Player pescado = (Player) pescao;
				if (pescado.getPlayerListName().equals(jugador.getPlayerListName())){
					jugador.sendMessage(MSG+" No puedes subirte sobre ti mismo");
					return;
				}
			}
			List<Entity> pasajeros = jugador.getPassengers();
			if (pasajeros.isEmpty())
				jugador.addPassenger(pescao);
			else
				pasajeros.get(pasajeros.size()-1).addPassenger(pescao);
		}else if (mismoItem(mano, getPaloRandom())){
			boolean saleMob = false;
			if (Math.random()>0.80 || evento.getState().equals(State.CAUGHT_ENTITY)){
				saleMob=true;
			}

			final Location loc = jugador.getLocation();
			if (saleMob){
				EntityType[] mobs = EntityType.values();
				int rng = 0;

				Entity aparecida= null;
				boolean bien= false;
				while(!bien){
					try{
						rng = new Random().nextInt(mobs.length);
						aparecida = jugador.getWorld().spawnEntity(pescao.getLocation(), mobs[rng]);
						aparecida.setCustomName("§4Random "+mobs[rng].getEntityClass().getSimpleName());
						aparecida.setCustomNameVisible(true);
						aparecida.setGlowing(true);
						bien=true;
					}catch(Exception e){}
				}

				if (pescao instanceof Player){
					pescao.sendMessage(MSG+" Te ha pescado "+jugador.getName()+" y te ha cambiado por "+aparecida.getType().getEntityClass().getSimpleName());
					jugador.sendMessage(MSG+" Has pescado a "+pescao.getName()+" y lo has cambiado por "+aparecida.getType().getEntityClass().getSimpleName());
				}else{
					jugador.sendMessage(MSG+" Has transformado un "+pescao.getType().getEntityClass().getSimpleName()+" en un "+mobs[rng].getEntityClass().getSimpleName());
				}

				final Entity finalAp= aparecida;
				new Thread(new Runnable() {
					@Override
					public void run() {
						String name = finalAp.getCustomName();
						int ticks=10;
						for (int i = ticks; i > 0; i--) {
							finalAp.setCustomName(name+"§5 "+i);
							try {
								Thread.sleep(100);
							}catch(InterruptedException e) {}
						}
						finalAp.teleport(loc);
						finalAp.setCustomName(name);
						finalAp.setGlowing(false);
					}
				}).start();

			}else{
				PlayerInventory inventory = jugador.getInventory();
				Material[] items = Material.values();
				ArrayList<Material> comidas = new ArrayList<>();
				for (int i = 0; i < items.length; i++) {
					if (items[i].isEdible()){
						comidas.add(items[i]);
					}
				}
				int rng=0;
				ItemStack item;
				if (Math.random()>0.1){
					rng = new Random().nextInt(comidas.size());
					item = new ItemStack(comidas.get(rng));
					jugador.sendMessage(MSG+" Has pescado un "+comidas.get(rng).name().toLowerCase().replace("_", " "));
				}else{
					rng = new Random().nextInt(items.length);
					item = new ItemStack(items[rng]);
					jugador.sendMessage(MSG+" Has pescado un "+items[rng].name().toLowerCase().replace("_", " "));
				}

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
			}
			if (pescao instanceof Player){
				pescao.teleport(loc);
			}else
				pescao.remove();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("darPaloRandom")){
			try{
				if (args.length!=1){
					args= new String[]{((Player)sender).getName()};
				}

				@SuppressWarnings("deprecation")
				Player player = Bukkit.getPlayer(args[0]);
				PlayerInventory inventory = player.getInventory();
				ItemStack itemstack = getPaloRandom();

				inventory.addItem(itemstack);

			}catch(Exception e){
				if (args.length!=1){
					return false;
				}
				try{
					Player tio = (Player)sender;
					tio.sendMessage("El jugador "+args[0]+" no existe.");
				}catch(Exception e1){
					getServer().broadcastMessage(MSG+" plugin cargado correctamente");
				}
			}

			return true;
		}

		else if (command.getName().equalsIgnoreCase("darPaloNombres")){
			try{
				if (args.length!=1){
					args= new String[]{((Player)sender).getName()};
				}

				@SuppressWarnings("deprecation")
				Player player = Bukkit.getPlayer(args[0]);
				PlayerInventory inventory = player.getInventory();
				ItemStack itemstack = getPaloNombres();

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

		else if (command.getName().equalsIgnoreCase("darPaloMonturas")){
			try{
				if (args.length!=1){
					args= new String[]{((Player)sender).getName()};
				}

				@SuppressWarnings("deprecation")
				Player player = Bukkit.getPlayer(args[0]);
				PlayerInventory inventory = player.getInventory();
				ItemStack itemstack = getPaloMontame();
				inventory.addItem(itemstack);
				itemstack = getPaloMontate();
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
