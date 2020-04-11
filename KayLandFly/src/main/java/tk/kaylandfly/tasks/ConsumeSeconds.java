package tk.kaylandfly.tasks;

import org.bukkit.entity.Player;

import tk.kaylandfly.KayLandFlyPlugin;
import tk.kaylandfly.playerdata.PlayerData;
import tk.kaylandfly.util.task.Task;

public class ConsumeSeconds extends Task{

	private KayLandFlyPlugin plugin;
	private Player player;
	private PlayerData playerData;
	
	public ConsumeSeconds(KayLandFlyPlugin plugin, Player player) {
		super(plugin, 20L);
		this.plugin = plugin;
		this.player = player;
		this.playerData = plugin.getPlayersData().getPlayerData(player.getUniqueId());
	}

	@Override
	public void actions() {	
		if (player.isOnline()) {
			if (plugin.getFlyController().containPlayer(player.getUniqueId())) {
				if (playerData.getSeconds() > 0) {
					if (player.isFlying()) {
						playerData.takeSeconds(1);
					}
				} else {
					plugin.getFlyController().removePlayer(player.getUniqueId());
					player.setFlying(false);
					player.setAllowFlight(false);
					stopScheduler();
				}
			} else {
				stopScheduler();
			}
		} else {
			stopScheduler();
		}
	}
	
}
