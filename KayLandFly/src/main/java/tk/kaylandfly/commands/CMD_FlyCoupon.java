package tk.kaylandfly.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import tk.kaylandfly.KayLandFlyPlugin;

public class CMD_FlyCoupon implements CommandExecutor{

	private KayLandFlyPlugin plugin;
	
	public CMD_FlyCoupon(KayLandFlyPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			player(sender, args);
		} else {
			console(sender, args);
		}
		return false;
	}
	
	private void player(CommandSender sender, String[] args) {
		
	}
	
	private void console(CommandSender sender, String[] args) {
		FileConfiguration messages = plugin.getFiles().getMessages();
		String prefix = messages.getString("prefix");
		if (args.length > 0) {
			if (args.length > 1) {
				Player player = plugin.getServer().getPlayerExact(args[0]);
				if (player != null) {
					if (player.getInventory().firstEmpty() != -1) {
						try {
							int amount = Integer.valueOf(args[1]);
							// CreateCoupon 
							// Add player inventory
							String couponAdded = messages.getString("command.flycoupon.couponAdded");
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+couponAdded));
							String couponRecived = messages.getString("command.flycoupon.couponRecived");
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+couponRecived));
						} catch (Exception e) {
							String invalidAmount = messages.getString("command.flycoupon.invalidAmount");
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+invalidAmount));
						}
					} else {
						String playerHasNoSpace = messages.getString("command.flycoupon.playerHasNoSpace");
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+playerHasNoSpace.replaceAll("%player%", args[0])));
					}
				} else {
					String playerNoFound = messages.getString("command.flycoupon.playerNoFound");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+playerNoFound.replaceAll("%player%", args[0])));
				}
			} else {
				String amountEmpty = messages.getString("command.flycoupon.amountEmpty");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+amountEmpty));
			}
		} else {
			String playerEmpty = messages.getString("command.flycoupon.playerEmpty");
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+playerEmpty));
		}
	}

}
