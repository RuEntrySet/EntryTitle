package ru.entryset.title.main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import ru.entryset.api.database.Database;
import ru.entryset.api.database.mysql.MysqlDatabase;
import ru.entryset.title.commands.AddCommand;
import ru.entryset.title.commands.SendCommand;
import ru.entryset.title.commands.TagCommand;
import ru.entryset.title.menu.Menu;
import ru.entryset.title.mysql.MySQLExecutor;
import ru.entryset.title.placeholderapi.Placeholders;
import ru.entryset.title.events.Events;
import ru.entryset.title.tools.Configuration;
import ru.entryset.title.tools.Utils;
import ru.entryset.title.user.User;

import java.util.HashMap;
import java.util.Objects;

public class Main extends JavaPlugin {

    private static Main instance;

    public static Database base;

    public static HashMap<Player, User> map = new HashMap<>();

    public static HashMap<Player, Menu> menus = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        registerCommands();
        registerEvents();
        loadconfig();
        database();
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders().register();
        }
    }

    @Override
    public void onDisable(){
        base.close();
    }

    public void loadconfig(){
        Configuration.setInstance(this);
        Configuration.reloadConfig();
    }

    public void database(){
        base = new MysqlDatabase(Configuration.getMySQL("host"), Configuration.getMySQLPort(), Configuration.getMySQL("user")
                , Configuration.getMySQL("password"), Configuration.getMySQL("database"));
        base.start();
        MySQLExecutor.createTableProducts();
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
        Utils.sendTitle(player, Configuration.getMessage("title"));
        Utils.sendMessage(player, Configuration.getMessage("select_msg"), true);
        Utils.sendMessage(player, Configuration.getMessage("select_msg"), true);
        Utils.sendMessage(player, Configuration.getMessage("select_msg"), true);
        if(player.hasMetadata("kaiftitle")){
            return;
        }
        player.setMetadata("kaiftitle", new FixedMetadataValue(Main.getInstance(), Bukkit.getConsoleSender()));
    }

    public static Main getInstance() {
        return Main.instance;
    }

    //Берем конфиги
    public static YamlConfiguration config() {
        if(Configuration.config != null) {
            return Configuration.config;
        }
        return Configuration.config = Configuration.getFile("config.yml");
    }

    public static YamlConfiguration items() {
        if(Configuration.items != null) {
            return Configuration.items;
        }
        return Configuration.items = Configuration.getFile("items.yml");
    }

}
