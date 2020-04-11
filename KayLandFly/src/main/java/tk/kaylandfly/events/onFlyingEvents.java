package tk.kaylandfly.events;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.md_5.bungee.api.ChatColor;
import tk.kaylandfly.KayLandFlyPlugin;

public class onFlyingEvents implements Listener{

	
	private KayLandFlyPlugin plugin;
	
	public onFlyingEvents(KayLandFlyPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onFlying(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		if(player.isFlying()) {
			if(!player.getGameMode().equals(GameMode.CREATIVE)) {
				if(!plugin.getFlyController().containPlayer(uuid)) {
					player.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&cNo pueds volar en este servidor"));
				}
			}
		}
	}
}
