package ru.entryset.title.tools;

import org.bukkit.Bukkit;
import ru.entryset.title.main.Main;

public class Logger {

	public static void info(String text) {
		Bukkit.getConsoleSender().sendMessage(Utils.color("&a(" + Main.getInstance().getName() + "/INFO) " + text));
	}

	public static void warn(String text) {
		Bukkit.getConsoleSender().sendMessage(Utils.color("&6(" + Main.getInstance().getName() + "/WARN) " + text));
	}

	public static void error(String text) {
		Bukkit.getConsoleSender().sendMessage(Utils.color("&c(" + Main.getInstance().getName() + "/ERROR) " + text));
	}


	public static void enable(String text) {
		Bukkit.getConsoleSender().sendMessage(Utils.color("&a[&f" + Main.getInstance().getName() + "&a] " + text + "&r"));
	}

} 
