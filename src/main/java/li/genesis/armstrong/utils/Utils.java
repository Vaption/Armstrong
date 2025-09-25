package li.genesis.armstrong.utils;

import li.genesis.armstrong.Armstrong;

import com.lunarclient.apollo.Apollo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static String parsePapi(Player player, String text) {
        if (text == null) return "";
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, text);
        }
        return text;
    }

    public static boolean isOnLunar(Player player) {
        return Apollo.getPlayerManager().hasSupport(player.getUniqueId());
    }

    public static String lunarStatus(Player player) {
        String path = isOnLunar(player) ? "OTHER.ENABLED" : "OTHER.DISABLED";
        return translate(Armstrong.getInstance().getConfig().getString(path, ""));
    }

    public static String translate(String text) {
        if (text == null) return "";
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> translate(List<String> text) {
        return text.stream().map(Utils::translate).collect(Collectors.toList());
    }
}