package bnana.embercore;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    private final EmberCorePlugin plugin;

    public SetSpawnCommand(EmberCorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("embercore.setspawn")) {
            sender.sendMessage("§cYou don't have permission!");
            return true;
        }
        Player player = (Player) sender;
        Location loc = player.getLocation();
        plugin.getConfig().set("spawn", loc);
        plugin.saveConfig();
        player.sendMessage("§aSpawn point set!");
        return true;
    }
}