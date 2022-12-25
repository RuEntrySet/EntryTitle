package ru.entryset.title.hook;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Shards {
    private Player player;

    public Shards(Player player) {
        setPlayer(player);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean has(Integer amont) {
        if (Bukkit.getPluginManager().getPlugin("PlayerPoints") == null)
            return true;
        PlayerPointsAPI ppAPI = PlayerPoints.getInstance().getAPI();
        return ppAPI.look(getPlayer().getUniqueId()) >= amont;
    }

    public void give(Integer amont) {
        if (Bukkit.getPluginManager().getPlugin("PlayerPoints") == null)
            return;
        PlayerPointsAPI ppAPI = PlayerPoints.getInstance().getAPI();
        ppAPI.give(getPlayer().getUniqueId(), amont);
    }

    public void take(Integer amont) {
        if (Bukkit.getPluginManager().getPlugin("PlayerPoints") == null)
            return;
        PlayerPointsAPI ppAPI = PlayerPoints.getInstance().getAPI();
        ppAPI.take(getPlayer().getUniqueId(), amont);
    }
}

