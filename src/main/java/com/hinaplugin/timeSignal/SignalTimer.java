package com.hinaplugin.timeSignal;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Calendar;
import java.util.Locale;

public class SignalTimer extends BukkitRunnable {
    private final Calendar calendar = Calendar.getInstance(Locale.JAPAN);
    private final MiniMessage miniMessage = MiniMessage.miniMessage();


    @Override
    public void run() {
        final int hour = calendar.get(Calendar.HOUR);
        final int min = calendar.get(Calendar.MINUTE);
        final int sec = calendar.get(Calendar.SECOND);

        if (min == 0 && sec == 0){
            final String message = TimeSignal.config.getString("time." + hour, "");
            if (message.isEmpty()){
                return;
            }

            final Component component = miniMessage.deserialize(message);

            for (final Player player : TimeSignal.plugin.getServer().getOnlinePlayers()){
                if (player.hasPermission("timesignal.notice")){
                    player.sendMessage(component);
                }
            }
        }
    }
}
