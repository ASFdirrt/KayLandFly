package tk.kaylandfly.util.chat;

public class Time {

    public boolean isFormattedStringTime(String string){
        boolean result = false;
        boolean a = true;
        for(int i = 0;i< string.length();i++){
            char value = string.charAt(i);
            if(a){
                if(value == 's'){
                    a = true;
                }

            }
        }
        return result;
    }

    public static int getSecondsOfFormattedStringTime(String string){
        int seconds = 0;
        String numbers = "";
        for(int i = 0;i< string.length();i++){
            char value = string.charAt(i);
            if(value != ' '){
                if(value == 'd'){
                    seconds = seconds + (Integer.valueOf(numbers) * 86400);
                    numbers = "";
                }else if(value == 'h'){
                    seconds = seconds + (Integer.valueOf(numbers) * 3600);
                    numbers = "";
                }else if(value == 'm'){
                    seconds = seconds + (Integer.valueOf(numbers) * 60);
                    numbers = "";
                }else if(value == 's'){
                    seconds = seconds + Integer.valueOf(numbers);
                    numbers = "";
                }else{
                    numbers = numbers + value;
                }
            }
        }
        return seconds;
    }

    public static String getFormattedStringTimeOfSeconds(int time) {
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = time;
        if (seconds > 86400){
            days = seconds / 86400;
            seconds = seconds % 86400;
        }
        if (seconds  > 3600){
            hours = seconds / 3600;
            seconds  = seconds % 3600;
        }
        if (seconds > 60){
            minutes = seconds / 60;
            seconds = seconds % 60;
        }
        // Declaro una cadena de texto que representara el formato
        String format = ""; // Formato %days% days %hours% hours %minutes% minutes %seconds% seconds / 1 days 4 hours 30 minutes 2 seconds
        // Damos formato al texto
        if (days > 0) { // Si days es mayor que 0
            format = days + "d ";
        }
        if (hours > 0) { // Si hours es mayor que 0
            format = format + hours + "h ";
        }
        if (minutes > 0) { // Si minutes es mayor que 0
            format = format + minutes + "m ";
        }
        if (seconds != 0 && format != "") { // Si seconds es distinto que 0
            format = format + seconds + "s";
        }else{
            return "0s";
        }
        return format;
    }
}
