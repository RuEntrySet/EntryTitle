package ru.entryset.title.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.entryset.title.main.Main;
import ru.entryset.title.mysql.MySQLExecutor;
import ru.entryset.title.user.User;

public class AddCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!Main.messager.hasPermission(sender, Main.config.getPermission("add"))){
            return false;
        }

        if(args.length > 1){
            if(Bukkit.getPlayer(args[0]) != null){
                Player player = Bukkit.getPlayer(args[0]);
                User user = Main.map.get(player);
                StringBuilder builder = new StringBuilder();
                for(int x = 1; x < args.length; x++){
                    if(x != 1){
                        builder.append(" ");
                    }
                    builder.append(args[x]);
                }
                String title = builder.toString();
                MySQLExecutor.addTitle(user, title);
                Main.messager.sendMessage(sender, Main.config.getMessage("add_title")
                        .replace("<title>", title).replace("<player>", args[0]));
                return true;
            }
            Main.messager.sendMessage(sender, Main.config.getMessage("offline"));
            return false;
        }
        Main.messager.sendMessage(sender, Main.config.getMessage("add_use"));
        return false;
    }
}
