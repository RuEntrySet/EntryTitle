package ru.entryset.title.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.entryset.title.main.Main;

import java.util.Objects;
import java.util.logging.Logger;

public class SendCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!Main.messager.hasPermission(sender, Main.config.getPermission("send"))){
            return false;
        }
        if(args.length > 0){
            if(Bukkit.getPlayer(args[0]) != null){
                Player player = Bukkit.getPlayer(args[0]);
                Main.getInstance().sendTitle(player);
                Main.messager.sendMessage(sender, Main.config.getMessage("send_title")
                        .replace("<player>", Objects.requireNonNull(player).getName()));
                return true;
            }
            Main.messager.sendMessage(sender, Main.config.getMessage("offline"));
            return false;
        }
        Main.messager.sendMessage(sender, Main.config.getMessage("send_use"));
        return false;
    }
}
