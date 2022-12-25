package ru.entryset.title.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import ru.entryset.api.tools.Messager;
import ru.entryset.title.main.Main;
import ru.entryset.title.user.User;

public class Placeholders extends PlaceholderExpansion {

    public String onPlaceholderRequest(Player player, String params) {

        User user = Main.map.get(player);

        if(user.isLoad()){
            return Messager.color(" " + Main.config.getString("settings.load"));
        }

        if(user.getActive() != null){
            return Messager.color(" " + user.getActive().getText());
        }

        if(params.equalsIgnoreCase("title_scoreboard")){
            return Messager.color(" " + Main.config.getString("settings.none"));
        }

        if(params.equalsIgnoreCase("title")){
            return "";
        }
        return null;
    }

    public String getIdentifier() {
        return "entrytitle";
    }

    public String getAuthor() {
        return "EntrySet";
    }

    public String getVersion() {
        return "1.0.0";
    }
}