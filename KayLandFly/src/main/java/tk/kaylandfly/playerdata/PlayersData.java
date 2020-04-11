package tk.kaylandfly.playerdata;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayersData {
	
	private Map<UUID, PlayerData> playersData;
	
	public PlayersData() {
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
}
