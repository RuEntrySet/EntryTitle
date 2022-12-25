package ru.entryset.title.mysql;

import ru.entryset.title.main.Main;
import ru.entryset.title.user.Title;
import ru.entryset.title.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySQLExecutor {

    public static void createTableProducts() {
        Main.base.execute(Main.getInstance().getResource("deploy.sql"));
    }

    public static void loadTitles(User user){
        Main.base.select("SELECT * FROM `kaiftitles` WHERE `user`= ?", rs -> {
            List<Title> titles = new ArrayList<>();
            while (rs.next()){
                String id = rs.getString(1);
                String title = rs.getString(3);
                int active = rs.getInt(4);
                Title t = new Title(id, title);
                titles.add(t);
                if(active == 1){
                    user.setActive(t);
                }
            }
            user.setTitles(titles);
            user.setLoad(false);
        } , user.getPlayer().getName());
    }

    public static void addTitle(User user, String title) {
        String id = UUID.randomUUID().toString();
        Main.base.update("INSERT INTO `kaiftitles` (`id`, `user`, `title`, `state`) VALUES (?, ?, ?, ?)",id, user.getPlayer().getName()
                , title, 0);
        Title title1 = new Title(id, title);
        user.getTitles().add(title1);
    }

    public static void activation(User user, Title title){
        user.setActive(title);
        Main.base.update("UPDATE `kaiftitles` SET `state` = 1 WHERE `id` = ?", title.getId());
    }

    public static void deactivation(User user, Title title){
        user.setActive(null);
        Main.base.update("UPDATE `kaiftitles` SET `state` = 0 WHERE `id` = ?", title.getId());
    }
}
