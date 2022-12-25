package ru.entryset.title.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.entryset.title.menu.Menu;
import ru.entryset.title.tools.Configuration;
import ru.entryset.title.tools.Utils;

public class TagCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;
        if(!Utils.hasPermission(player, Configuration.getPermission("tag"))){
            return false;
        }
        new Menu(player);
        return true;
    }
}
