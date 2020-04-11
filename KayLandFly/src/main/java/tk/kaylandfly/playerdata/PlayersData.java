package tk.kaylandfly.playerdata;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import tk.kaylandfly.KayLandFlyPlugin;

public class PlayersData {
	
	private KayLandFlyPlugin plugin;
	private Map<UUID, PlayerData> playersData;
	
	public PlayersData(KayLandFlyPlugin plugin) {
		this.plugin = plugin;
		playersData = new HashMap<UUID, PlayerData>();
	}
	
	/**
	 * Agrega PlayerData a los datos cargados
	 * @param uuid
	 * @param playerData
	 */
	public void addPlayerData(UUID uuid, PlayerData playerData) { playersData.put(uuid, playerData); }
	
	/**
	 * Remueve un PlayerData de los datos cargados
	 * @param uuid
	 */
	public void removePlayerData(UUID uuid) { playersData.remove(uuid); }
	
	/**
	 * Verifica si un jugador posee un PlayerData cargado
	 * @param uuid
	 * @return
	 */
	public boolean containPlayerData(UUID uuid) { return playersData.containsKey(uuid); }
	
	/**
	 * Obtener PlayerData de un jugador
	 * @param uuid
	 * @return PlayerData
	 */
	public PlayerData getPlayerData(UUID uuid) { return playersData.get(uuid); }

	/**
	 * Carga los datos de vuelo de un jugador
	 * @param uuid
	 */
	public void loadPlayerData(UUID uuid) {
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

	/**
	 * Guarda los datos de un jugador
	 * @param uuid
	 */
	public void savePlayerData(UUID uuid) {
		if (plugin.getPlayersData().containPlayerData(uuid)) {
			FileConfiguration players = plugin.getFiles().getPlayers();
			PlayerData playerData = plugin.getPlayersData().getPlayerData(uuid);
			players.set(uuid + ".seconds", playerData.getSeconds());
			players.set(uuid + ".quitWithFly", playerData.hasQuitWithFly());
			plugin.getFiles().savePlayers();
		}
	}
}
