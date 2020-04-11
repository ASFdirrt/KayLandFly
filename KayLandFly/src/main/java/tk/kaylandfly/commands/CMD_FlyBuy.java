package tk.kaylandfly.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import tk.kaylandfly.KayLandFlyPlugin;
import tk.kaylandfly.playerdata.PlayerData;

public class CMD_FlyBuy implements CommandExecutor{

	private KayLandFlyPlugin plugin;
	
	public CMD_FlyBuy(KayLandFlyPlugin plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		FileConfiguration settings = plugin.getFiles().getSettings();
		FileConfiguration messages = plugin.getFiles().getMessages();
		String prefix = messages.getString("prefix");
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (plugin.getEconomyController().isEnable()) {
				if (args.length > 0) {
					try {
						double secondCost = settings.getDouble("secondCost");
						int amount = Integer.valueOf(args[1]);
						if (plugin.getEconomyController().getEconomy().getBalance(player) >= secondCost*amount) {
							plugin.getEconomyController().getEconomy().withdrawPlayer(player, secondCost*amount);
							PlayerData playerData = plugin.getPlayersData().getPlayerData(player.getUniqueId());
							playerData.addSeconds(amount);
							String amountAdded = messages.getString("command.flybuy.amountAdded");
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+amountAdded.replaceAll("%amount%", amount+"")));
						} else {
							String insufficientBalance = messages.getString("command.flybuy.insufficientBalance");
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+insufficientBalance));
						}
					} catch (Exception e) {
						String invalidAmount = messages.getString("command.flybuy.invalidAmount");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+invalidAmount));
					}
				} else {
					String amountEmpty = messages.getString("command.flybuy.amountEmpty");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+amountEmpty));
				}
			} else {
				String economyDisable = messages.getString("command.flybuy.economyDisable");
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+economyDisable));
			}
		} else {
			if (args.length > 0) {
				if (args.length > 1) {
					Player player = plugin.getServer().getPlayerExact(args[0]);
					if (player != null) {
						try {
							int amount = Integer.valueOf(args[1]);
							PlayerData playerData = plugin.getPlayersData().getPlayerData(player.getUniqueId());
							playerData.addSeconds(amount);
							String amountAddedOther = messages.getString("command.flybuy.amountAddedOther");
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+amountAddedOther.replaceAll("%amount%", amount+"").replaceAll("%player%", player.getName())));
						} catch (Exception e) {
							String invalidAmount = messages.getString("command.flybuy.invalidAmount");
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+invalidAmount));
						}
					} else {
						String playerNoFound = messages.getString("command.flybuy.playerNoFound");
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+playerNoFound.replaceAll("%player%", args[0])));						
					}
				} else {
					String amountEmpty = messages.getString("command.flybuy.amountEmpty");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+amountEmpty));
				}
			} else {
				String playerEmpty = messages.getString("command.flybuy.playerEmpty");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+playerEmpty));
			}
		}
		return false;
	}
	
}
