package li.genesis.armstrong.listeners;

import li.genesis.armstrong.Armstrong;
import li.genesis.armstrong.utils.Utils;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.nametag.Nametag;
import com.lunarclient.apollo.module.nametag.NametagModule;
import com.lunarclient.apollo.player.ApolloPlayer;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NametagTask implements Runnable {
    private final Armstrong plugin;
    private final NametagModule nametagModule;
    private static final String[] LINE_NAMES = {"FIRST", "SECOND", "THIRD", "FOURTH"};

    public NametagTask(Armstrong plugin) {
        this.plugin = plugin;
        this.nametagModule = Apollo.getModuleManager().getModule(NametagModule.class);
    }

    @Override
    public void run() {
        if (nametagModule == null || !plugin.getConfig().getBoolean("NAMETAG.ENABLE", true)) return;

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target.isOnline()) {
                updateNametagFor(target);
            }
        }
    }

    public void updateNametagFor(Player target) {
        List<Component> nametagLines = buildNametagLines(target);
        if (nametagLines.isEmpty()) return;

        Nametag nametag = Nametag.builder()
                .lines(nametagLines)
                .build();

        for (Player viewer : Bukkit.getOnlinePlayers()) {
            if (viewer.isOnline()) {
                Optional<ApolloPlayer> apolloViewerOpt = Apollo.getPlayerManager().getPlayer(viewer.getUniqueId());
                apolloViewerOpt.ifPresent(apolloViewer ->
                        nametagModule.overrideNametag(apolloViewer, target.getUniqueId(), nametag)
                );
            }
        }
    }

    private List<Component> buildNametagLines(Player target) {
        List<Component> lines = new ArrayList<>();

        String lunarStatus = Utils.isOnLunar(target)
                ? Utils.translate(plugin.getConfig().getString("OTHER.ENABLED", "&aON&r"))
                : Utils.translate(plugin.getConfig().getString("OTHER.DISABLED", "&cOFF&r"));

        for (String lineName : LINE_NAMES) {
            ConfigurationSection lineSection = plugin.getConfig().getConfigurationSection("NAMETAG." + lineName);
            if (lineSection == null || !lineSection.getBoolean("ENABLE", true)) continue;

            String lineContent = processLine(lineSection, target, lunarStatus);
            if (!lineContent.isEmpty()) {
                lines.add(LegacyComponentSerializer.legacyAmpersand().deserialize(lineContent));
            }
        }

        return lines;
    }

    private String processLine(ConfigurationSection lineSection, Player target, String lunarStatus) {
        ConfigurationSection tiersSection = lineSection.getConfigurationSection("TIERS");
        if (tiersSection == null) return "";

        return processTieredLine(tiersSection, target, lunarStatus);
    }

    private String processTieredLine(ConfigurationSection tiersSection, Player target, String lunarStatus) {
        String chosenTemplate = "";
        int highestWeight = Integer.MIN_VALUE;

        for (String key : tiersSection.getKeys(false)) {
            ConfigurationSection tier = tiersSection.getConfigurationSection(key);
            if (tier == null) continue;

            String perm = tier.getString("permission", "");
            int weight = tier.getInt("weight", 0);
            String format = tier.getString("format", "");

            if ((perm.isEmpty() || target.hasPermission(perm)) && weight > highestWeight) {
                highestWeight = weight;
                chosenTemplate = format;
            }
        }

        return chosenTemplate.isEmpty() ? "" : formatLine(chosenTemplate, target, lunarStatus);
    }

    private String formatLine(String format, Player target, String lunarStatus) {
        String processed = Utils.parsePapi(target, format);
        return Utils.translate(processed)
                .replace("<player_displayname>", target.getDisplayName())
                .replace("<player_name>", target.getName())
                .replace("<status>", lunarStatus);
    }
}
