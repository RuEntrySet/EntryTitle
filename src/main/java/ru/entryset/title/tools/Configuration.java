package ru.entryset.title.tools;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.entryset.title.main.Main;

import java.io.File;
import java.util.HashMap;

public class Configuration {

    private static JavaPlugin instance;

    // Файлы конфигурации, добовлять строго по форме.
    public static YamlConfiguration config;

    public static YamlConfiguration items;
    //

    //Для подключения главного класса
    public static JavaPlugin getInstance() {
        return instance;
    }

    public static void setInstance(JavaPlugin instance) {
        Configuration.instance = instance;
    }
    //

    public static void reloadConfig() {
        config = Configuration.getFile("config.yml");
        items = Configuration.getFile("items.yml");
    }

    public static HashMap<String, YamlConfiguration> ymlconfigcache = new HashMap<>();

    public static YamlConfiguration getFile(String fileName) {
        YamlConfiguration configuration = ymlconfigcache.get(fileName);
        if(configuration == null){
            File file = new File(Configuration.getInstance().getDataFolder(), fileName);
            if (!file.exists()) {
                Configuration.getInstance().saveResource(fileName, false);
            }
            configuration = YamlConfiguration.loadConfiguration(file);
            ymlconfigcache.put(fileName, configuration);
        }
        return configuration;
    }

    public static String getMySQL(String str){
        return Main.config().getString("mysql." + str);
    }

    public static int getMySQLPort(){
        return Main.config().getInt("mysql.port");
    }

    public static String getMessage(String str){
        return Main.config().getString("messages." + str);
    }

    public static String getPermission(String str){
        return Main.config().getString("permissions." + str);
    }


}
