package bnana.embercore;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HideCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("embercore.hide.use")) {
            sender.sendMessage("§cYou don't have permission!");
            return true;
        }
        Player player = (Player) sender;
        // This only changes display name - true nickname hiding needs a protocol hack or extra plugin
        player.setDisplayName("HiddenPlayer");
        Bukkit.broadcastMessage(player.getName() + " is now hidden!");
        return true;
    }
}