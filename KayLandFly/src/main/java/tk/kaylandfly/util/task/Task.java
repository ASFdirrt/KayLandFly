package tk.kaylandfly.util.task;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public abstract class Task{

    private JavaPlugin plugin;
    private BukkitTask task;
    private long ticks;

    public Task(JavaPlugin plugin, long ticks){
        this.plugin = plugin;
        this.ticks = ticks;
    }

    public void startScheduler() {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        task = scheduler.runTaskTimer(plugin, new Runnable() {
            public void run() {
                actions();
            }
        },0L,ticks);
    }

    public void stopScheduler(){
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.cancelTask(task.getTaskId());
    }

    public abstract void actions();
}
