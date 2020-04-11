package tk.kaylandfly;

import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import tk.kaylandfly.commands.CMD_Fly;
import tk.kaylandfly.commands.CMD_FlyBuy;
import tk.kaylandfly.commands.CMD_KayLandFly;
import tk.kaylandfly.events.onConnectEvent;
import tk.kaylandfly.events.onDisconnectEvent;
import tk.kaylandfly.events.onFlyingEvents;
import tk.kaylandfly.files.Files;
import tk.kaylandfly.flycontroller.FlyController;
import tk.kaylandfly.playerdata.PlayerData;
import tk.kaylandfly.playerdata.PlayersData;
import tk.kaylandfly.tasks.ConsumeSeconds;
import tk.kaylandfly.util.vault.EconomyController;

public class KayLandFlyPlugin extends JavaPlugin {

	private Files files;
	private EconomyController economyController;
	private PlayersData playersData;
	private FlyController flyController;

	@Override
	public void onEnable() {
		playersData = new PlayersData(this);
		flyController = new FlyController();
		economyController = new EconomyController(this);
		files = new Files(this);
		registerCommands();
		registerEvents();
		for(Player player:getServer().getOnlinePlayers()) {
			playersData.loadPlayerData(player.getUniqueId());
			PlayerData playerData = playersData.getPlayerData(player.getUniqueId());
			FileConfiguration messages = files.getMessages();
			String prefix = messages.getString("prefix");
			if (playerData.getSeconds() > 0) {
				if (playerData.hasQuitWithFly()) {	
					player.setAllowFlight(true);
					player.setFlying(true);
					if (!player.hasPermission("kaylandfly.use.unlimited")) {
						ConsumeSeconds consumeSeconds = new ConsumeSeconds(this, player);
						consumeSeconds.startScheduler();
					}
					String reaciveComplete = messages.getString("command.fly.reaciveComplete");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', reaciveComplete));
				}
			} else {
				String reactiveFail = messages.getString("command.fly.reactiveFail");
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+reactiveFail));
			}
		}
	}
	
	@Override
	public void onDisable() {
		for(Player player:getServer().getOnlinePlayers()) {
			if(flyController.containPlayer(player.getUniqueId())) {
				playersData.getPlayerData(player.getUniqueId()).setQuitWithFly(true);
			}
			playersData.savePlayerData(player.getUniqueId());
		}
	}

	private void registerEvents() {
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(new onConnectEvent(this), this);
		pluginManager.registerEvents(new onDisconnectEvent(this), this);
		pluginManager.registerEvents(new onFlyingEvents(this), this);
	}

	private void registerCommands() {
		FileConfiguration messages = files.getMessages();
		String prefix = messages.getString("prefix");
		String noPermission = messages.getString("noPermission");
		// KayLandFly
		PluginCommand kaylandfly = getCommand("kaylandfly");
		kaylandfly.setPermissionMessage(ChatColor.translateAlternateColorCodes('&', prefix + noPermission));
		kaylandfly.setExecutor(new CMD_KayLandFly(this));
		// Fly
		PluginCommand fly = getCommand("kaylandflyfly");
		fly.setPermissionMessage(ChatColor.translateAlternateColorCodes('&', prefix + noPermission));
		fly.setExecutor(new CMD_Fly(this));
		// FlyBuy
		PluginCommand flybuy = getCommand("kaylandflyflybuy");
		flybuy.setPermissionMessage(ChatColor.translateAlternateColorCodes('&', prefix + noPermission));
		flybuy.setExecutor(new CMD_FlyBuy(this));
	}

	public void reload() {
		files.reload();
		FileConfiguration messages = files.getMessages();
		String prefix = messages.getString("prefix");
		String noPermission = messages.getString("noPermission");
		// KayLandFly
		PluginCommand kaylandfly = getCommand("kaylandfly");
		kaylandfly.setPermissionMessage(ChatColor.translateAlternateColorCodes('&', prefix + noPermission));
		// Fly
		PluginCommand fly = getCommand("kaylandflyfly");
		fly.setPermissionMessage(ChatColor.translateAlternateColorCodes('&', prefix + noPermission));
		// FlyBuy
		PluginCommand flybuy = getCommand("kaylandflyflybuy");
		flybuy.setPermissionMessage(ChatColor.translateAlternateColorCodes('&', prefix + noPermission));
		for(Player player:getServer().getOnlinePlayers()) {
			playersData.savePlayerData(player.getUniqueId());
		}
	}
	/**
	 * Obtener PlayersData
	 * 
	 * @return PlayersData
	 */
	public PlayersData getPlayersData() {
		return playersData;
	}

	/**
	 * Obtener los archivos del complemento
	 * 
	 * @return Files
	 */
	public Files getFiles() {
		return files;
	}
	
	/**
	 * Obtener FlyController
	 * @return
	 */
	public FlyController getFlyController() {
		return flyController;
	}

	/**
	 * Obtener el controlador de economia
	 * 
	 * @return EconomyController
	 */
	public EconomyController getEconomyController() {
		return economyController;
	}
}
