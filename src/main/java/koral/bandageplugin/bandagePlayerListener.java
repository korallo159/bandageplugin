package koral.bandageplugin;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class bandagePlayerListener implements Listener {
    HashMap<Player, Long> cooldown = new HashMap<>();

    Bandageplugin plugin;

    public bandagePlayerListener(final Bandageplugin plugin)
    {
        this.plugin = plugin;
    }



    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        Entity entity = event.getEntity();
        if(entity instanceof Player) {
            boolean disableRegeneration = plugin.getConfig().getBoolean("disableregeneration");
            if (disableRegeneration) { // if it is true

                if (!entity.hasPermission("bandageplugin.regeneration"))
                    event.setCancelled(true);
            } else
                return;
        }
        else
            return;
    }

void wyleczmax(Player player)
{
    player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());

}


    @EventHandler
    public void onPlayerClicks(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getItem() != null && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains(plugin.getConfig().getString("bandagelore"))) {
                if((cooldown.get(player) == null)){
                    player.playSound(player.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
                    if (player.getHealth() + plugin.getConfig().getInt("healamount") > player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue())
                        wyleczmax(player);
                    else
                        player.setHealth(player.getHealth() + plugin.getConfig().getInt("healamount"));

                    player.getInventory().removeItem(plugin.getMineShard(-1));
                    cooldown.put(player, (System.currentTimeMillis() / 1000));

                }
                else if((cooldown.get(player) + plugin.getConfig().getInt("cooldown")) <= (System.currentTimeMillis() / 1000))  {
                    player.playSound(player.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
                    if (player.getHealth() + plugin.getConfig().getInt("healamount") > 20)
                        player.setHealth(20);
                    else
                        player.setHealth(player.getHealth() + plugin.getConfig().getInt("healamount"));

                    player.getInventory().removeItem(plugin.getMineShard(-1));
                    cooldown.put(player, (System.currentTimeMillis() / 1000));
                }
                else
                    player.sendMessage(ChatColor.GRAY + "Musisz odczekać jeszcze" + ChatColor.RED + " " + (cooldown.get(player) + plugin.getConfig().getInt("cooldown") - System.currentTimeMillis() / 1000) + "s" );
                return;
            }

        }
    }

}
