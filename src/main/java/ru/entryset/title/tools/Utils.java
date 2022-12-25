package ru.entryset.title.tools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class Utils {

	static public final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

	public static String color(String text){

		String[] texts = text.split(String.format(WITH_DELIMITER, "&"));

		StringBuilder finalText = new StringBuilder();

		try {
			for (int i = 0; i < texts.length; i++){
				if (texts[i].equalsIgnoreCase("&")){
					//get the next string
					i++;
					if (texts[i].charAt(0) == '#'){
						try {
							finalText.append(net.md_5.bungee.api.ChatColor.of(texts[i].substring(0, 7))).append(texts[i].substring(7));
						} catch (Exception e){
							finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));
						}
					}else{
						finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));
					}
				}else{
					finalText.append(texts[i]);
				}
			}
		} catch (Exception e){
			return ChatColor.translateAlternateColorCodes('&', text);
		}
		return finalText.toString();
	}
	
	public static boolean hasPermission(CommandSender p, String permission) {
		if(p.hasPermission(permission)) {
			return true;
		}
		Utils.sendMessage(p, Configuration.getMessage("no_permission").replace("<permission>", permission), true);
		return false;
		
	}

	public static String formApi(String text, Player player){
		String s = text;
		if(player != null && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			s = PlaceholderAPI.setPlaceholders(player, s);
		}
		return s;
	}

	public static void sendTitle(Player player, String text){
		player.sendTitle(Utils.color(text), "");
	}

	public static void sendAction(Player player, String text){
		player.sendTitle("", text);
	}

	public static void sendMessage(CommandSender player, String text, Boolean with_prefix) {
		for (String line : text.split(";")) {
			line = color(line.replace("%player%", player.getName()));
			if(player instanceof Player && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
				line = PlaceholderAPI.setPlaceholders((Player) player, line);
			}
			if (line.startsWith("title:")) {
				if (player instanceof Player)
					sendTitle((Player) player, line.split("title:")[1]);
			} else if (line.startsWith("actionbar:")) {
				if (player instanceof Player) {
					sendAction((Player) player, line.split("actionbar:")[1]);
				}
			} else {
				if(with_prefix){
					player.sendMessage(color(Configuration.getMessage("prefix") + line));
					return;
				}
				player.sendMessage(color(line));
			}
		}
	}

	public static int getVersion() {
		int result = 7;
		for(int inner = 8; inner <= 20; inner++){
			if(Bukkit.getVersion().contains("1." + String.valueOf(inner))){
				result = inner;
				break;
			}
		}
		return result;
	}

}
