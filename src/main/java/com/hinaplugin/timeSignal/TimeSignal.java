package com.hinaplugin.timeSignal;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public final class TimeSignal extends JavaPlugin {
    public static TimeSignal plugin;
    public static Logger logger;
    public static FileConfiguration config;
    public static SignalTimer signalTimer = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            plugin = this;
            logger = this.getLogger();

            final File configFile = new File(this.getDataFolder(), "config.yml");
            if (!configFile.exists()){
                this.saveDefaultConfig();
            }

            config = this.getConfig();

            signalTimer = new SignalTimer();
            signalTimer.runTaskTimerAsynchronously(this, 0L, 20L);

            final PluginCommand command = this.getCommand("timesignal");
            if (command != null){
                command.setExecutor(new Commands());
            }

            logger.info("TimeSignal is Enabled!");
        }catch (Exception exception){
            exception.printStackTrace(new PrintWriter(new StringWriter()));
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            if (signalTimer != null){
                signalTimer.cancel();
            }

            logger.info("TimeSignal is Disabled!");
        }catch (Exception exception){
            exception.printStackTrace(new PrintWriter(new StringWriter()));
        }
    }
}
