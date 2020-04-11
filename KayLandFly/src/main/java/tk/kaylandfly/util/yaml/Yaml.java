package tk.kaylandfly.util.yaml;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class Yaml {

    private final JavaPlugin plugin;
    private final String name;
    private File file;
    private FileConfiguration fileConfiguration;

    public Yaml(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name+".yml";
    }

    /**
     * Obtener el archivo de configuracion
     * @return fileConfiguration
     */
    public FileConfiguration get() {
        if (fileConfiguration == null) {
            reload();
        }
        return fileConfiguration;
    }
    
    /**
     * Recarga el archivo
     */
    public void reload() {
        if (fileConfiguration == null) {
            file = new File(plugin.getDataFolder(), name);
        }
        try {
            fileConfiguration = YamlConfiguration.loadConfiguration(file);
            Reader defConfigStream = new InputStreamReader(plugin.getResource(name), "UTF8");
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            fileConfiguration.setDefaults(defConfig);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Guarda el archivo
     */
    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registra el archivo
     */
    public void register() {
        file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) {
            this.get().options().copyDefaults(true);
            save();
        }
    }
}

