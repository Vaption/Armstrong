package li.genesis.armstrong.commands;

import li.genesis.armstrong.Locale;
import li.genesis.armstrong.utils.Utils;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.staffmod.StaffMod;
import com.lunarclient.apollo.module.staffmod.StaffModModule;
import com.lunarclient.apollo.player.ApolloPlayer;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Syntax;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;

import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@CommandAlias("lunarstaffmode|lsm")
@CommandPermission("armstrong.staff")
public class LunarStaffCommand extends BaseCommand {

    private final StaffModModule staffModModule;
    private final Map<UUID, Set<StaffMod>> enabledMods = new HashMap<>();

    public LunarStaffCommand() {
        this.staffModModule = Apollo.getModuleManager().getModule(StaffModModule.class);
    }

    @Default
    @Syntax("[Player]")
    @Description("Toggle a player's Lunar Client staff mod(s).")
    public void toggleStaff(Player sender, @co.aikar.commands.annotation.Optional OnlinePlayer onlinePlayer) {
        Player target = (onlinePlayer == null ? sender : onlinePlayer.getPlayer());

        Optional<ApolloPlayer> apolloPlayerOpt = Apollo.getPlayerManager().getPlayer(target.getUniqueId());
        if (!apolloPlayerOpt.isPresent()) {
            sender.sendMessage(Utils.parsePapi(sender, Locale.LUNAR_STAFF_COMMAND_ENABLE_ERROR.messageFormat())
                .replace("<target>", target.getName()));
            return;
        }
        ApolloPlayer apolloPlayer = apolloPlayerOpt.get();

        StaffMod mod = StaffMod.XRAY;

        UUID targetUUID = target.getUniqueId();
        Set<StaffMod> modsForTarget = enabledMods.computeIfAbsent(targetUUID, k -> new HashSet<>());

        if (modsForTarget.contains(mod)) {
            staffModModule.disableStaffMods(apolloPlayer, Collections.singletonList(mod));
            modsForTarget.remove(mod);

            if (onlinePlayer == null) {
                sender.sendMessage(Utils.parsePapi(sender, Locale.LUNAR_STAFF_COMMAND_DISABLE_PLAYER.messageFormat()));
            } else {
                sender.sendMessage(Utils.parsePapi(sender, Locale.LUNAR_STAFF_COMMAND_DISABLE_TARGET.messageFormat())
                    .replace("<target>", target.getDisplayName()));
                target.sendMessage(Utils.parsePapi(target, Locale.LUNAR_STAFF_COMMAND_DISABLE_TO_TARGET.messageFormat())
                    .replace("<player>", sender.getDisplayName()));
            }
        } else {
            staffModModule.enableStaffMods(apolloPlayer, Collections.singletonList(mod));
            modsForTarget.add(mod);

            if (onlinePlayer == null) {
                sender.sendMessage(Utils.parsePapi(sender, Locale.LUNAR_STAFF_COMMAND_ENABLE_PLAYER.messageFormat()));
            } else {
                sender.sendMessage(Utils.parsePapi(sender, Locale.LUNAR_STAFF_COMMAND_ENABLE_TARGET.messageFormat())
                    .replace("<target>", target.getDisplayName()));
                target.sendMessage(Utils.parsePapi(target, Locale.LUNAR_STAFF_COMMAND_ENABLE_TO_TARGET.messageFormat())
                    .replace("<player>", sender.getDisplayName()));
            }
        }
    }
}
