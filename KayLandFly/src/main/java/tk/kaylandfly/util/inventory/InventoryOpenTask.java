package tk.kaylandfly.util.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import tk.kaylandfly.util.task.Task;

public class InventoryOpenTask extends Task {

    private Player player;
    private Inventory inventory;
    private int time;

    public InventoryOpenTask(JavaPlugin plugin,Player player, Inventory inventory, int time) {
        super(plugin, 20L);
        this.player = player;
        this.inventory = inventory;
        this.time = time;
    }

    public void actions() {
        if (time == 0){
            if(player.isOnline()){
                player.openInventory(inventory);
            }
        }
        time--;
    }
}
