package tk.kaylandfly.flycontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlyController {

	private List<UUID> players;
	
	public FlyController() {
		players = new ArrayList<UUID>();
	}
	
	/**
	 * Agrega un jugador
	 * @param uuid
	 */
	public void addPlayer(UUID uuid) {
		players.add(uuid);
	}
	
	/**
	 * Remueve un jugador
	 * @param uuid
	 */
	public void removePlayer(UUID uuid) {
		players.remove(uuid);
	}
	
	/**
	 * Verifica si contiene un jugador
	 * @param uuid
	 * @return
	 */
	public boolean containPlayer(UUID uuid) {
		return players.contains(uuid);
	}
}
