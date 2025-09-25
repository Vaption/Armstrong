package li.genesis.armstrong.listeners;

import li.genesis.armstrong.Locale;
import li.genesis.armstrong.Armstrong;
import li.genesis.armstrong.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LunarListener implements Listener {
    private final Armstrong plugin;

    public LunarListener() {
        this.plugin = Armstrong.getInstance();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (plugin.getConfig().getBoolean("REQUIRE-LUNAR.ENABLED")) {
                if (!Utils.isOnLunar(player)) {
                    player.kickPlayer(Locale.LUNAR_KICK_MESSAGE.messageFormat().replace("<space>", "\n"));
                }
            }
        }, 20L);
    }
}