package koral.bandageplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;




public class bandageCommands implements CommandExecutor {

    Bandageplugin plugin;

    public bandageCommands(final Bandageplugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("dajbandaz")) {
                {
                    if (args.length == 0) {
                        player.getInventory().addItem(plugin.getMineShard(1));
                        return true;
                    } else {
                        final Player target = Bukkit.getServer().getPlayer(args[0]);
                        if (target == null) {
                            sender.sendMessage(ChatColor.RED + "Taki gracz nie jest online!");
                            return true;
                        } else
                            target.getInventory().addItem(plugin.getMineShard(1));
                        sender.sendMessage(ChatColor.GRAY + "Gracz " + ChatColor.YELLOW + args[0] + ChatColor.GRAY + " dostał od Ciebie bandaz.");
                        target.sendMessage(ChatColor.GRAY + "Otrzymałeś bandaz");
                        return true;
                    }
                }
            }

        } else if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender) {
            if (cmd.getName().equalsIgnoreCase("voucherinfoadmin")) {
                sender.sendMessage("komenda niedostepna dla konsoli");
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("dajbandaz") && args.length > 0) {
                final Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Taki gracz nie jest online!");
                    return true;
                } else
                    target.getInventory().addItem(plugin.getMineShard(1));
                target.sendMessage(ChatColor.GRAY + "Otrzymałeś Bandaz");
                return true;
            } else sender.sendMessage("komenda niedostepna dla sendera");
        }

        if(cmd.getName().equalsIgnoreCase("bandagereload")){
            plugin.saveDefaultConfig();
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Config został przeładowany.");
        }


        return true;
    }
}
