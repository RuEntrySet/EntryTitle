package ru.entryset.title.item;

import org.bukkit.inventory.ItemStack;

public class Controller {

	public static ItemStack getItem(String info) {
		return new Item(info).getItem();
	}

}
