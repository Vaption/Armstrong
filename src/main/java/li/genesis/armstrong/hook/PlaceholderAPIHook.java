package li.genesis.armstrong.hook;

import li.genesis.armstrong.utils.Utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "armstrong";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return "Vaption";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        // %armstrong_status%
        if (identifier.equalsIgnoreCase("status")) {
            return Utils.lunarStatus(player);
        }

        return null;
    }
}