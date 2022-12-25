package ru.entryset.title.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.entryset.title.hook.Shards;
import ru.entryset.title.main.Main;
import ru.entryset.title.menu.Menu;
import ru.entryset.title.mysql.MySQLExecutor;
import ru.entryset.title.tools.Configuration;
import ru.entryset.title.tools.Utils;
import ru.entryset.title.user.Title;
import ru.entryset.title.user.User;

public class Events implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Main.map.put(e.getPlayer(), new User(e.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Main.map.remove(e.getPlayer());
        if(e.getPlayer().hasMetadata("kaiftitle")){
            e.getPlayer().removeMetadata("kaiftitle", Main.getInstance());
            Shards shards = new Shards(e.getPlayer());
            shards.give(Main.config().getInt("settings.cost"));
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e){
        Main.map.remove(e.getPlayer());
        if(e.getPlayer().hasMetadata("kaiftitle")){
            e.getPlayer().removeMetadata("kaiftitle", Main.getInstance());
            Shards shards = new Shards(e.getPlayer());
            shards.give(Main.config().getInt("settings.cost"));
        }
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        if(!player.hasMetadata("kaiftitle")){
            return;
        }
        e.setCancelled(true);
        String message = e.getMessage();
        if(message.contains("\\") || message.contains("/") || message.contains("-") || message.contains("&k")){
            Utils.sendMessage(player, Configuration.getMessage("symbol"), true);
            return;
        }
        message = Utils.color(message);
        player.removeMetadata("kaiftitle", Main.getInstance());
        MySQLExecutor.addTitle(Main.map.get(player), message);
        Utils.sendMessage(player, Configuration.getMessage("confirm").replace("<title>", message), true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){

        if(!(e.getPlayer() instanceof Player)){
            return;
        }
        Player player = (Player) e.getPlayer();
        Main.menus.remove(player);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(!(e.getWhoClicked() instanceof Player)){
            return;
        }
        Player player = (Player) e.getWhoClicked();
        if(!Main.menus.containsKey(player)){
            return;
        }
        Menu menu = Main.menus.get(player);
        e.setCancelled(true);
        if(e.getClickedInventory() == null){
            return;
        }
        if(!menu.getInventory().equals(e.getClickedInventory())){
            return;
        }
        if(e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()){
            return;
        }
        if(e.getSlot() > 9 && e.getSlot() < 26 && e.getSlot() != 17 && e.getSlot() != 18){
            User user = Main.map.get(player);
            int x;
            if(e.getSlot() > 9 && e.getSlot() < 17){
                x = e.getSlot() - 10;
            } else {
                x = e.getSlot() - 12;
            }
            Title title = menu.getMap().get(menu.getPage()).get(x);
            if(user.getActive() != null && user.getActive().getId().equalsIgnoreCase(title.getId())){
                MySQLExecutor.deactivation(user, title);
                Utils.sendMessage(player, Configuration.getMessage("disable")
                        .replace("<title>", title.getText())
                        , true);
            } else {
                if(user.getActive() != null){
                    MySQLExecutor.deactivation(user, user.getActive());
                }
                MySQLExecutor.activation(user, title);
                Utils.sendMessage(player, Configuration.getMessage("enable")
                                .replace("<title>", title.getText())
                        , true);
            }
            menu.update();
            return;
        }
        if(e.getSlot() == 36){
            player.chat("/menu");
            Main.menus.remove(player);
            return;
        }
        if(e.getSlot() == 39 && menu.getPage() > 1){
            menu.setPage(menu.getPage() - 1);
            menu.update();
            return;
        }
        if(e.getSlot() == 41 && menu.getPage() < menu.getMaxPage()){
            menu.setPage(menu.getPage() + 1);
            menu.update();
            return;
        }
        if(e.getSlot() == 40){
            Shards shards = new Shards(player);
            int inner = Main.config().getInt("settings.cost");
            if(shards.has(inner)){
                player.closeInventory();
                if(!player.hasMetadata("kaiftitle")){
                    shards.take(inner);
                }
                Main.getInstance().sendTitle(player);
                return;
            }
            Utils.sendMessage(player, Configuration.getMessage("no_money()"), true);
        }
    }

}
