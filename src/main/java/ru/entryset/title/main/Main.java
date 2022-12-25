package ru.entryset.title.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import ru.entryset.api.configuration.Config;
import ru.entryset.api.configuration.Configuration;
import ru.entryset.api.database.Database;
import ru.entryset.api.sync.redis.Redis;
import ru.entryset.api.tools.Messager;
import ru.entryset.title.commands.AddCommand;
import ru.entryset.title.commands.SendCommand;
import ru.entryset.title.commands.TagCommand;
import ru.entryset.title.menu.Menu;
import ru.entryset.title.mysql.MySQLExecutor;
import ru.entryset.title.placeholderapi.Placeholders;
import ru.entryset.title.events.Events;
import ru.entryset.title.user.User;

import java.util.HashMap;
import java.util.Objects;

public class Main extends JavaPlugin {

    private static Main instance;

    public static Database base;

    public static Messager messager;

    public static Config config;

    public static Configuration items;

    public static HashMap<Player, User> map = new HashMap<>();

    public static HashMap<Player, Menu> menus = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        config = new Config(getInstance(), "config.yml");
        items = new Configuration(getInstance(), "items.yml");
        messager = new Messager(config);
        base = config.getMysqlDatabase("mysql");
        base.start();
        MySQLExecutor.createTableProducts();

        registerCommands();
        registerEvents();

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders().register();
        }
    }

    @Override
    public void onDisable(){
        base.close();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new Events(), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("tags")).setExecutor(new TagCommand());
        Objects.requireNonNull(getCommand("sendtag")).setExecutor(new SendCommand());
        Objects.requireNonNull(getCommand("addtag")).setExecutor(new AddCommand());
    }

    public void sendTitle(Player player){
        messager.sendTitle(player, config.getMessage("title"));
        for(int inner = 0; inner < 3; inner++){
            messager.sendMessage(player, config.getMessage("select_msg"));
        }
        if(player.hasMetadata("entrytitle")){
            return;
        }
        player.setMetadata("entrytitle", new FixedMetadataValue(Main.getInstance(), Bukkit.getConsoleSender()));
    }

    public static Main getInstance() {
        return Main.instance;
    }
}
