package tk.kaylandfly.coupon;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tk.kaylandfly.KayLandFlyPlugin;
import tk.kaylandfly.util.chat.Time;

import java.util.List;

public class Coupon {
	
	private KayLandFlyPlugin plugin;
	
	public Coupon(KayLandFlyPlugin plugin) {
		this.plugin = plugin;
	}
	
	
	/**
	 * Genera un cupon
	 * @param seconds
	 * @return ItemStack
	 */
    public ItemStack create(int seconds){
    	FileConfiguration settings = plugin.getFiles().getSettings();
    	String itemName = settings.getString("coupon.name");
    	List<String> itemLore = settings.getStringList("coupon.lore");
    	for(int i=0;i<itemLore.size();i++) {
    		if(itemLore.get(i).contains("%time%")) {
    			itemLore.set(i, ChatColor.translateAlternateColorCodes('&', itemLore.get(i).replaceAll("%time%", Time.getFormattedStringTimeOfSeconds(seconds))));    			    			
    		} else {
    			itemLore.set(i, ChatColor.translateAlternateColorCodes('&', itemLore.get(i)));    			
    		}
    	}
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemName));
        meta.setLore(itemLore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY,1);
        return new ItemStack(item);
    }
    
    /**
     * Verificar un objeto true si es un cupon, false si no es un cupon 
     * @param ItemStack
     * @return boolean
     */
    public boolean verifyItem(ItemStack item){
    	FileConfiguration settings = plugin.getFiles().getSettings();
    	String itemName = settings.getString("coupon.name"); 
        ItemMeta meta = item.getItemMeta();
        if (item.containsEnchantment(Enchantment.DURABILITY)){
            if (meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)){
            	if (meta.hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS)){
            		if (meta.hasDisplayName()){
                		if (meta.getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', itemName))) {
                			return true;                		
                		}
                	}
            	}
            }
        }
        return false;
    }

    /**
     * Obtener segundos de un cupon
     * @param ItemStack
     * @return int
     */
    public int getSeconds(ItemStack item){
    	FileConfiguration settings = plugin.getFiles().getSettings();
    	int secondsLine = settings.getInt("coupon.secondsLine");
        int seconds = 0;
        ItemMeta meta = item.getItemMeta();
        List<String> loreList = meta.getLore();
        String timeText = loreList.get(secondsLine);
        seconds = Time.getSecondsOfFormattedStringTime(timeText);
        return seconds;
    }
}
