package koral.bandageplugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public final class Bandageplugin extends JavaPlugin implements CommandExecutor, Listener {

    private bandageCommands commandexecutor;
    private bandagePlayerListener playerListener;

    @Override
    public void onEnable() {
        File file = new File(getDataFolder() + File.separator + "config.yml"); //This will get the config file
        if (!file.exists()) { //This will check if the file exist
        saveDefaultConfig();;
        } else {
            //situation B, Config does exist
            saveDefaultConfig(); //saves the config
            reloadConfig();   //reloads the config
        }

        this.commandexecutor = new bandageCommands(this);
        this.playerListener = new bandagePlayerListener(this);
        getServer().getPluginManager().registerEvents(this.playerListener, this);
      this.getCommand("dajbandaz").setExecutor(this.commandexecutor);
        this.getCommand("bandagereload").setExecutor(this.commandexecutor);
    }


    @Override
    public void onDisable() {
    }


    public ItemStack getMineShard(int amount) {
        ItemStack bandage = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = bandage.getItemMeta();

        itemMeta.setDisplayName(ChatColor.RED + getConfig().getString("bandagename"));

        ArrayList<String> lore = new ArrayList<String>();
        lore.add(getConfig().getString("bandagelore"));
        itemMeta.setLore(lore);
        //set the meta
        bandage.setItemMeta(itemMeta);

        return bandage;
    }


}
