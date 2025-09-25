package li.genesis.armstrong;

import li.genesis.armstrong.commands.LunarCommand;
import li.genesis.armstrong.commands.LunarStaffCommand;
import li.genesis.armstrong.hook.PlaceholderAPIHook;
import li.genesis.armstrong.listeners.LunarListener;
import li.genesis.armstrong.listeners.NametagTask;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.nametag.NametagModule;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import co.aikar.commands.PaperCommandManager;
import org.bstats.bukkit.Metrics;

@Getter
public class Armstrong extends JavaPlugin implements Listener {

    @Getter
    private static Armstrong instance;

    private PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("Apollo-Bukkit") == null || !getServer().getPluginManager().getPlugin("Apollo-Bukkit").isEnabled()) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Armstrong] Apollo-Bukkit is missing!");
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Armstrong] Get it from https://github.com/LunarClient/Apollo/releases");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        instance = this;

        this.commandManager = new PaperCommandManager(this);

        this.commandManager.registerCommand(new LunarCommand(this));
        this.commandManager.registerCommand(new LunarStaffCommand());

        this.saveDefaultConfig();
        this.registerApolloModules();

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new LunarListener(), this);

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Armstrong] The plugin has been enabled!");

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIHook().register();
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Armstrong] Placeholder API expansion successfully registered.");
        }

        new Metrics(this, 27367);
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Armstrong] The plugin has been disabled!");
    }

    private void registerApolloModules() {
        if (getConfig().getBoolean("NAMETAG.ENABLE")) {
            this.reloadConfig();
            Apollo.getModuleManager().getModule(NametagModule.class);
            this.getServer().getScheduler().runTaskTimerAsynchronously(this,
                new NametagTask(this), 0, Long.parseLong(Locale.LUNAR_TAG_TICKS.messageFormat()));
        }
    }
}