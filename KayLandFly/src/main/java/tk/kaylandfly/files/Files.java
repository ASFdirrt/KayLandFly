package tk.kaylandfly.files;

import org.bukkit.configuration.file.FileConfiguration;

import tk.kaylandfly.KayLandFlyPlugin;
import tk.kaylandfly.util.yaml.Yaml;

public class Files {

	private Yaml settingsYaml;
	private Yaml messagesYaml;
	private Yaml playersYaml;
	
	public Files(KayLandFlyPlugin plugin) {
		settingsYaml = new Yaml(plugin, "settings");
		messagesYaml = new Yaml(plugin, "messages");
		playersYaml = new Yaml(plugin, "players");
		settingsYaml.register();
		messagesYaml.register();
		playersYaml.register();
	}
	
	public FileConfiguration getSettings() { return settingsYaml.get(); }
	
	public FileConfiguration getMessages() { return messagesYaml.get(); }
	
	public FileConfiguration getPlayers() { return playersYaml.get(); }
	
	public void saveSettings() { settingsYaml.save(); }
	
	public void saveMessages() { messagesYaml.save(); }
	
	public void savePlayers() { playersYaml.save(); }
	
	public void reload() {
		settingsYaml.reload();
		messagesYaml.reload();
		playersYaml.reload();
	}
}
