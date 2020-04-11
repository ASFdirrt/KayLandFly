package tk.kaylandfly.playerdata;

public class PlayerData {
	
	private int seconds;
	private boolean quitWithFly;
	
	public PlayerData(int seconds, boolean quitWithFly) {
		this.seconds = seconds;
		this.quitWithFly = quitWithFly;
	}
	
	/**
	 * Agregar una cantidad de tiempo
	 * @param amount
	 */
	public void addSeconds(int amount) { seconds = seconds + amount; }
	
	/**
	 * Remueve una catidad de tiempo
	 * @param amount
	 */
	public void takeSeconds(int amount) { seconds = seconds - amount; }

	/**
	 * Obten los segundos
	 * @return
	 */
	public int getSeconds() { return seconds; }
	
	/**
	 * Establece si el jugador se desconecto con el vuelo activado
	 * @param boolean
	 */
	public void setQuitWithFly(boolean value) { quitWithFly = value; }
	
	/**
	 * Verifica si el jugador se desconecto con el vuelo activado
	 * @return boolean
	 */
	public boolean hasQuitWithFly() { return quitWithFly; }
}
