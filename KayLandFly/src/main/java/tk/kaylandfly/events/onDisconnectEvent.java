package tk.kaylandfly.events;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import tk.kaylandfly.KayLandFlyPlugin;
import tk.kaylandfly.playerdata.PlayerData;

public class onDisconnectEvent implements Listener{

	private KayLandFlyPlugin plugin;
	
	public onDisconnectEvent(KayLandFlyPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent event) {
		setQuitWithFly(event.getPlayer().getUniqueId());
		savePlayerData(event.getPlayer().getUniqueId());
	}
	
	private void setQuitWithFly(UUID uuid) {
		PlayerData playerData = plugin.getPlayersData().getPlayerData(uuid);
		if (plugin.getFlyController().containPlayer(uuid)) {
			playerData.setQuitWithFly(true);
		} else {
			playerData.setQuitWithFly(false);
		}
	}
	
	private void savePlayerData(UUID uuid) {
		FileConfiguration players = plugin.getFiles().getPlayers();
		PlayerData playerData = plugin.getPlayersData().getPlayerData(uuid);
		players.set(uuid + "seconds", playerData.getSeconds());
		players.set(uuid + ".quitWithFly", playerData.hasQuitWithFly());
		plugin.getFiles().savePlayers();
	}
	
}
