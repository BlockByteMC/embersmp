package bnana.embercore;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LiveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("embercore.live")) {
            sender.sendMessage("§cYou don't have permission!");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("§cUsage: /live <url>");
            return true;
        }
        Player player = (Player) sender;
        String url = args[0];

        // Name in blue, "is live!" in white
        TextComponent firstLine = new TextComponent("§9" + player.getName() + " §fis live!");
        // URL in white, clickable
        TextComponent urlLine = new TextComponent(url);
        urlLine.setColor(net.md_5.bungee.api.ChatColor.WHITE);
        urlLine.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));

        // Send to all players
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(firstLine);
            p.spigot().sendMessage(urlLine);
        }
        return true;
    }
}