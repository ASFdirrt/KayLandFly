package tk.kaylandfly.events;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tk.kaylandfly.KayLandFlyPlugin;
import tk.kaylandfly.playerdata.PlayerData;
import tk.kaylandfly.tasks.ConsumeSeconds;

public class onConnectEvent implements Listener {

	private KayLandFlyPlugin plugin;

	public onConnectEvent(KayLandFlyPlugin plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onConnect(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		loadPlayerData(uuid);
		enableFlyOnJoin(player);
	}
	
	private void enableFlyOnJoin(Player player) {
		PlayerData playerData = plugin.getPlayersData().getPlayerData(player.getUniqueId());
		if (playerData.hasQuitWithFly()) {
			if (playerData.getSeconds() > 0) {
				plugin.getFlyController().addPlayer(player.getUniqueId());
				player.setAllowFlight(true);
				player.setFlying(true);
				ConsumeSeconds consumeSeconds = new ConsumeSeconds(plugin, player);
				consumeSeconds.startScheduler();
			}
		}
	}
	
	private void loadPlayerData(UUID uuid) {
		FileConfiguration players = plugin.getFiles().getPlayers();
		if (players.contains(uuid.toString())) {
			int seconds = players.getInt(uuid + ".seconds");
			boolean quitWithFly = players.getBoolean(uuid + ".quitWithFly");
			PlayerData playerData = new PlayerData(seconds, quitWithFly);
			plugin.getPlayersData().addPlayerData(uuid, playerData);
		} else {
			PlayerData playerData = new PlayerData(0, false);
			plugin.getPlayersData().addPlayerData(uuid, playerData);
		}
	}
}
