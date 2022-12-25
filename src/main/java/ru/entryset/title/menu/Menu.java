package ru.entryset.title.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.entryset.api.tools.Messager;
import ru.entryset.title.item.Controller;
import ru.entryset.title.main.Main;
import ru.entryset.title.user.Title;
import ru.entryset.title.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menu {

    private Player player;

    private Inventory inventory;

    private int maxPage;

    private int page;

    private HashMap<Integer, List<Title>> map = new HashMap<>();

    public Menu(Player player){
        setPlayer(player);
        setPage(1);
        setMaxPage(1);
        load();
        update();
    }

    private void load(){
        User user = Main.map.get(getPlayer());

        int pages = 1;
        int inner = 0;
        HashMap<Integer, List<Title>> map2 = new HashMap<>();
        for(int x = 0; x < user.getTitles().size(); x++){
            if(inner == 14) {
                inner = 0;
                pages++;
            } else {
                inner++;
            }
            if(!map2.containsKey(pages)){
                map2.put(pages, new ArrayList<>());
            }
            map2.get(pages).add(user.getTitles().get(x));
        }
        setMap(map2);
        setMaxPage(pages);
        if(getPage() > getMaxPage()){
            setPage(getMaxPage());
        }
    }

    public void update(){
        setInventory(Bukkit.createInventory(getPlayer(), 9*5, Messager.color(Main.config.getString("settings.menu_title"))
                .replace("<page>", getPage() + "").replace("<max>", getMaxPage() + "")));
        for(int inner2 = 0; inner2 <= 13; inner2++){
            if(getMap().containsKey(getPage())){
                if(getMap().get(getPage()).size() > inner2){
                    int slot;
                    if(inner2 < 7){
                        slot = (inner2 + 10);
                    } else {
                        slot = (inner2 + 12);
                    }
                    Title title = getMap().get(getPage()).get(inner2);
                    if(getUser().getActive() == null){
                        getInventory().setItem(slot, title.getItem());
                    } else if(getUser().getActive().getId().equalsIgnoreCase(title.getId())){
                        getInventory().setItem(slot, title.getActiveItem());
                    } else {
                        getInventory().setItem(slot, title.getItem());
                    }
                } else {
                    break;
                }
            }
        }
        if(getPage() > 1){
            getInventory().setItem(39, Controller.getItem("last"));
        }
        if(getPage() < getMaxPage()){
            getInventory().setItem(41, Controller.getItem("next"));
        }
        getInventory().setItem(36, Controller.getItem("back"));
        getInventory().setItem(40, Controller.getItem("bay"));
        getPlayer().openInventory(getInventory());
        Main.menus.put(getPlayer(), this);
    }

    public HashMap<Integer, List<Title>> getMap() {
        return map;
    }

    public void setMap(HashMap<Integer, List<Title>> map) {
        this.map = map;
    }

    public User getUser(){
        return Main.map.get(getPlayer());
    }

    public int getPage() {
        return page;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
