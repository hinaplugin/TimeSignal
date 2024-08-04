package com.hinaplugin.timeSignal;

import com.google.common.collect.Lists;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.hasPermission("timesignal.command")){
            commandSender.sendMessage(Component.text("You do not have permission perform this commands!").color(TextColor.color(255, 75, 75)));
            return true;
        }

        if (TimeSignal.signalTimer != null){
            TimeSignal.signalTimer.cancel();
        }

        TimeSignal.plugin.reloadConfig();
        TimeSignal.config = TimeSignal.plugin.getConfig();
        TimeSignal.signalTimer = new SignalTimer();
        TimeSignal.signalTimer.runTaskTimerAsynchronously(TimeSignal.plugin, 0L, 20L);

        commandSender.sendMessage(Component.text("config.yml is reloaded!").color(TextColor.color(75, 255, 75)));

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        final List<String> complete = Lists.newArrayList();
        if (!commandSender.hasPermission("timesignal.command")){
            return null;
        }

        if (strings.length == 0 || strings.length == 1){
            complete.add("reload");
        }
        return complete;
    }
}
