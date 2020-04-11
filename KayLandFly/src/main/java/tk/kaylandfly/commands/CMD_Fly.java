package tk.kaylandfly.commands;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import tk.kaylandfly.KayLandFlyPlugin;
import tk.kaylandfly.flycontroller.FlyController;
import tk.kaylandfly.playerdata.PlayerData;
import tk.kaylandfly.tasks.ConsumeSeconds;

public class CMD_Fly implements CommandExecutor {

	private KayLandFlyPlugin plugin;

	public CMD_Fly(KayLandFlyPlugin plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {		
		if (sender instanceof Player) {
			player(sender, args);
		} else {
			console(sender, args);
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	private void player(CommandSender sender, String[] args) {
		FileConfiguration messages = plugin.getFiles().getMessages();
		String prefix = messages.getString("prefix");
		Player player = (Player) sender;
		UUID uuid = player.getUniqueId();
		if (args.length > 0) {
			String targetName = args[0];
			Player target = plugin.getServer().getPlayerExact(targetName);
			UUID targetUUID = target.getUniqueId();
			if (target != null) {
				if (player.equals(target)) {
					FlyController flyController = plugin.getFlyController();
					if (flyController.containPlayer(uuid)) {
						flyController.removePlayer(uuid);
						player.setFlying(false);
						player.setAllowFlight(false);
						String disableOwn = messages.getString("command.fly.disableOwn");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + disableOwn));
					} else {
						PlayerData playerData = plugin.getPlayersData().getPlayerData(uuid);
						if (playerData.getSeconds() > 0) {
							flyController.addPlayer(uuid);
							player.setAllowFlight(true);
							player.setFlying(true);
							ConsumeSeconds consumeSeconds = new ConsumeSeconds(plugin, player);
							consumeSeconds.startScheduler();
							String enableOwn = messages.getString("command.fly.enableOwn");
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + enableOwn));
						} else {
							String insufficientSeconds = messages.getString("command.fly.insufficientSeconds");
							player.sendMessage(
									ChatColor.translateAlternateColorCodes('&', prefix + insufficientSeconds));
						}
					}
				} else {
					if (player.hasPermission("kaylandfly.use.other")) {
						FlyController flyController = plugin.getFlyController();
						if (flyController.containPlayer(targetUUID)) {
							flyController.removePlayer(targetUUID);
							target.setFlying(false);
							target.setAllowFlight(false);
							String disableOwn = messages.getString("command.fly.disableOwn");
							String disableOther = messages.getString("command.fly.disableOther");
							target.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + disableOwn));
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + disableOther));
						} else {
							if(target.hasPermission("kaylandfly.use.unlimited")) {
								flyController.addPlayer(targetUUID);
								target.setAllowFlight(true);
								target.setFlying(true);
								String enableOwn = messages.getString("command.fly.enableOwn");
								String enableOther = messages.getString("command.fly.enableOther");
								target.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + enableOwn));
								player.sendMessage(
										ChatColor.translateAlternateColorCodes('&', prefix + enableOther));
							} else {
								PlayerData targetData = plugin.getPlayersData().getPlayerData(targetUUID);
								if (targetData.getSeconds() > 0) {
									flyController.addPlayer(targetUUID);
									target.setAllowFlight(true);
									target.setFlying(true);
									ConsumeSeconds consumeSeconds = new ConsumeSeconds(plugin, target);
									consumeSeconds.startScheduler();
									String enableOwn = messages.getString("command.fly.enableOwn");
									String enableOther = messages.getString("command.fly.enableOther");
									target.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + enableOwn));
									player.sendMessage(
											ChatColor.translateAlternateColorCodes('&', prefix + enableOther));
								} else {
									String insufficientSecondsOther = messages
											.getString("command.fly.insufficientSecondsOther");
									player.sendMessage(ChatColor.translateAlternateColorCodes('&',
											prefix + insufficientSecondsOther));
								}
							}
						}
					} else {
						String noPermissionOther = messages.getString("command.fly.noPermissionOther");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noPermissionOther));
					}
				}
			} else {
				String playerNotFound = messages.getString("command.fly.playerNotFound");
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + playerNotFound));
			}
		} else {
			FlyController flyController = plugin.getFlyController();
			if (flyController.containPlayer(uuid)) {
				flyController.removePlayer(uuid);
				player.setFlying(false);
				player.setAllowFlight(false);
				String disableOwn = messages.getString("command.fly.disableOwn");
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + disableOwn));
			} else {
				if (player.hasPermission("kaylandfly.use.unlimited")) {
					flyController.addPlayer(uuid);
					player.setAllowFlight(true);
					player.setFlying(true);
					String enableOwn = messages.getString("command.fly.enableOwn");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + enableOwn));
				} else {
					PlayerData playerData = plugin.getPlayersData().getPlayerData(uuid);
					if (playerData.getSeconds() > 0) {
						flyController.addPlayer(uuid);
						player.setAllowFlight(true);
						player.setFlying(true);
						ConsumeSeconds consumeSeconds = new ConsumeSeconds(plugin, player);
						consumeSeconds.startScheduler();
						String enableOwn = messages.getString("command.fly.enableOwn");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + enableOwn));
					} else {
						String insufficientSeconds = messages.getString("command.fly.insufficientSeconds");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + insufficientSeconds));
					}
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private void console(CommandSender sender, String[] args) {
		FileConfiguration messages = plugin.getFiles().getMessages();
		String prefix = messages.getString("prefix");
		if (args.length > 0) {
			String targetName = args[0];
			Player target = plugin.getServer().getPlayer(targetName);
			UUID targetUUID = target.getUniqueId();
			if (target != null) {
				FlyController flyController = plugin.getFlyController();
				if (flyController.containPlayer(targetUUID)) {
					flyController.removePlayer(targetUUID);
					target.setFlying(false);
					target.setAllowFlight(false);
					String disableOwn = messages.getString("command.fly.disableOwn");
					target.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + disableOwn));
				} else {
					PlayerData playerData = plugin.getPlayersData().getPlayerData(targetUUID);
					if (playerData.getSeconds() > 0) {
						flyController.addPlayer(targetUUID);
						target.setAllowFlight(true);
						target.setFlying(true);
						ConsumeSeconds consumeSeconds = new ConsumeSeconds(plugin, target);
						consumeSeconds.startScheduler();
						String enableOwn = messages.getString("command.fly.enableOwn");
						target.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + enableOwn));
					} else {
						String insufficientSeconds = messages.getString("command.fly.insufficientSeconds");
						target.sendMessage(
								ChatColor.translateAlternateColorCodes('&', prefix + insufficientSeconds));
					}
				}
			} else {
				String playerNotFound = messages.getString("command.fly.playerNotFound");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + playerNotFound));
			}
		}
	}
}
