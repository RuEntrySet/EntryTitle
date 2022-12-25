package ru.entryset.title.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.entryset.title.main.Main;
import ru.entryset.title.menu.Menu;

public class TagCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;
        if(!Main.messager.hasPermission(player, Main.config.getPermission("tag"))){
            return false;
        }
        new Menu(player);
        return true;
    }
}
