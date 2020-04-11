package tk.kaylandfly.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import tk.kaylandfly.KayLandFlyPlugin;

public class CMD_KayLandFly implements CommandExecutor {

	private KayLandFlyPlugin plugin;

	public CMD_KayLandFly(KayLandFlyPlugin plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		FileConfiguration messages = plugin.getFiles().getMessages();
		String prefix = messages.getString("prefix");
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("help")) {
				List<String> helpList = messages.getStringList("command.kaylandfly.help");
				for (String helpLine:helpList) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', helpLine));
				}
			} else if (args[0].equalsIgnoreCase("reload")) {
				//Reload
				String reloadComplete = messages.getString("command.kaylandfly.reloadComplete");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+reloadComplete));
			} else {
				String invalidArgument = messages.getString("command.kaylandfly.invalidArgument");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix+invalidArgument));
			}
		} else {
			String noArguments = messages.getString("command.kaylandfly.noArguments");
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noArguments));
		}
		return false;
	}

}
