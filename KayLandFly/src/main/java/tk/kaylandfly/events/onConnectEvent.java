package tk.kaylandfly.events;

import java.util.UUID;

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
		plugin.getPlayersData().loadPlayerData(uuid);
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
}
