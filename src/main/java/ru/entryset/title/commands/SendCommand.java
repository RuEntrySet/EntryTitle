package ru.entryset.title.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.entryset.title.main.Main;
import ru.entryset.title.tools.Configuration;
import ru.entryset.title.tools.Utils;

import java.util.Objects;

public class SendCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!Utils.hasPermission(sender, Configuration.getPermission("send"))){
            return false;
        }
        if(args.length > 0){
            if(Bukkit.getPlayer(args[0]) != null){
                Player player = Bukkit.getPlayer(args[0]);
                Main.getInstance().sendTitle(player);
                Utils.sendMessage(sender, Configuration.getMessage("send_title")
                        .replace("<player>", Objects.requireNonNull(player).getName()), true);
                return true;
            }
            Utils.sendMessage(sender, Configuration.getMessage("offline"), true);
            return false;
        }
        Utils.sendMessage(sender, Configuration.getMessage("send_use"), true);
        return false;
    }
}
