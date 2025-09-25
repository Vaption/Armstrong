package li.genesis.armstrong.commands;

import li.genesis.armstrong.Locale;
import li.genesis.armstrong.Armstrong;
import li.genesis.armstrong.utils.Utils;

import com.lunarclient.apollo.Apollo;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

@CommandAlias(value = "armstrong|lc")
public class LunarCommand extends BaseCommand {

    @Dependency
    private final Armstrong plugin;

    public LunarCommand(Armstrong plugin) {
        this.plugin = plugin;
    }

    @Default
    @Syntax(value = "[Player]")
    @CommandCompletion(value = "@players")
    @CommandPermission("armstrong.players")
    @Description(value = "View a player's LunarClient status.")
    public void lunarCommand(Player player, @Optional OnlinePlayer target) {
        if (target == null) {
            player.sendMessage(Utils.parsePapi(player, Locale.LUNAR_COMMAND_PLAYER.messageFormat())
                .replace("<player>", player.getDisplayName())
                .replace("<status>", Utils.lunarStatus(player)));
        } else {
            player.sendMessage(Utils.parsePapi(player, Locale.LUNAR_COMMAND_TARGET.messageFormat())
                .replace("<target>", target.getPlayer().getName())
                .replace("<status>", Utils.lunarStatus(target.getPlayer())));
        }
    }

    @Subcommand(value = "users|players|list")
    @CommandPermission("armstrong.players")
    @Description(value = "View a list of players using LunarClient.")
    public void userCommand(CommandSender sender) {
        long totalUsers = Bukkit.getOnlinePlayers().stream()
            .filter(p -> Apollo.getPlayerManager().hasSupport(p.getUniqueId()))
            .count();

        if (totalUsers == 0) {
            sender.sendMessage(ChatColor.RED + "There are no players online using LunarClient!");
            return;
        }

        String playerLists = Bukkit.getOnlinePlayers().stream()
            .filter(p -> Apollo.getPlayerManager().hasSupport(p.getUniqueId()))
            .map(Player::getName)
            .collect(Collectors.joining(Locale.LUNAR_USERS_SEPERATOR.messageFormat()));

        Locale.LUNAR_USERS_MESSAGE.messageFormatList().stream()
            .map(message -> message
                .replace("<totalUsers>", String.valueOf(totalUsers))
                .replace("<playerList>", playerLists))
            .forEach(sender::sendMessage);
    }

    @Subcommand(value = "reload")
    @CommandPermission(value = "armstrong.reload")
    @Description(value = "Reload the Armstrong plugin configuration.")
    public void reloadCommand(CommandSender sender) {
        try {
            plugin.reloadConfig();
            sender.sendMessage(Locale.LUNAR_COMMAND_RELOAD.messageFormat());
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "An error occurred!");
        }
    }
}