package tk.kaylandfly.events;

import java.util.UUID;

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
		UUID uuid = event.getPlayer().getUniqueId();
		setQuitWithFly(uuid);
		plugin.getPlayersData().savePlayerData(uuid);
		plugin.getPlayersData().removePlayerData(uuid);
	}
	
	private void setQuitWithFly(UUID uuid) {
		PlayerData playerData = plugin.getPlayersData().getPlayerData(uuid);
		if (plugin.getFlyController().containPlayer(uuid)) {
			playerData.setQuitWithFly(true);
		} else {
			playerData.setQuitWithFly(false);
		}
	}
	
}
