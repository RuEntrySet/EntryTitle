package ru.entryset.title.user;

import org.bukkit.entity.Player;
import ru.entryset.title.mysql.MySQLExecutor;

import java.util.ArrayList;
import java.util.List;

public class User {

    private Player player;

    private List<Title> titles;

    private Title active;

    private boolean load;

    public User(Player player){
        setLoad(true);
        setPlayer(player);
        setTitles(new ArrayList<>());
        setActive(null);
        MySQLExecutor.loadTitles(this);
    }

    public boolean isLoad() {
        return load;
    }

    public void setLoad(boolean load) {
        this.load = load;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public void setActive(Title active) {
        this.active = active;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public Title getActive() {
        return active;
    }
}
