package bnana.embercore;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

public class SpawnCommand implements CommandExecutor {
    private final EmberCorePlugin plugin;
    private final HashSet<UUID> cooldowns = new HashSet<>();

    public SpawnCommand(EmberCorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("embercore.spawn")) {
            sender.sendMessage("§cYou can't use this command!");
            return true;
        }
        Player player = (Player) sender;
        if (cooldowns.contains(player.getUniqueId())) {
            player.sendMessage("§cYou're on cooldown!");
            return true;
        }
        Location startBlock = player.getLocation().getBlock().getLocation().clone();

        // Multi-line display like image 2
        cooldowns.add(player.getUniqueId());
        new BukkitRunnable() {
            int timer = 5;
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cooldowns.remove(player.getUniqueId());
                    cancel();
                    return;
                }
                Location currentBlock = player.getLocation().getBlock().getLocation();
                if (!currentBlock.equals(startBlock)) {
                    player.sendMessage("§5EMBERSMP §8» §cTeleport cancelled because you moved!");
                    cooldowns.remove(player.getUniqueId());
                    cancel();
                    return;
                }
                if (timer > 0) {
                    player.sendMessage("§5EMBERSMP §8» §fYou will be teleported in §9" + timer + " seconds§f!");
                } else {
                    Location spawn = (Location) plugin.getConfig().get("spawn");
                    if (spawn != null) {
                        player.teleport(spawn);

                        // "Teleported to Spawn!" with "Spawn" as blue clickable
                        TextComponent prefix = new TextComponent("§5EMBERSMP §8» §fTeleported to ");
                        TextComponent spawnComponent = new TextComponent("§9Spawn");
                        spawnComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/spawn"));

                        prefix.addExtra(spawnComponent);

                        player.spigot().sendMessage(prefix);
                    } else {
                        player.sendMessage("§5EMBERSMP §8» §cSpawn is not set!");
                    }
                    cooldowns.remove(player.getUniqueId());
                    cancel();
                }
                timer--;
            }
        }.runTaskTimer(plugin, 0, 20);
        return true;
    }
}