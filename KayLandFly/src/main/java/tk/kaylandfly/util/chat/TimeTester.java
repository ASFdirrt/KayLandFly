package tk.kaylandfly.util.chat;

public class TimeTester {

	public static void main(String[] args) {
		String tiempo = "m20h";		
		if (Time.verifyString(tiempo)) {
			System.out.println(tiempo + " en cantidad de segundos es " + Time.getSecondsOfFormattedStringTime(tiempo));			
		} else {
			System.out.println(tiempo + "Este tiempo es un formato invalido");
		}
	}
}
