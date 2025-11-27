package bnana.embercore;

import org.bukkit.plugin.java.JavaPlugin;

public class EmberCorePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // Register commands
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("hide").setExecutor(new HideCommand());
        getCommand("live").setExecutor(new LiveCommand());
        // Listeners if needed
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}