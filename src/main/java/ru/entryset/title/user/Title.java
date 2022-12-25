package ru.entryset.title.user;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.entryset.title.main.Main;
import ru.entryset.title.tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class Title {

    String id;

    String text;

    public Title(String id, String text){
        setText(text);
        setId(id);
    }

    public ItemStack getItem(){
        ItemStack stack = new ItemStack(Material.valueOf(Main.config().getString("settings.material_title_select").toUpperCase()), 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(Utils.color(getText()));
        List<String> lore = new ArrayList<>();
        for(String srt : Main.config().getStringList("settings.lore")){
            lore.add(Utils.color(srt));
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack getActiveItem(){
        ItemStack stack = new ItemStack(Material.valueOf(Main.config().getString("settings.material_title").toUpperCase()), 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(Utils.color(getText()));
        List<String> lore = new ArrayList<>();
        for(String srt : Main.config().getStringList("settings.lore_select")){
            lore.add(Utils.color(srt));
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
