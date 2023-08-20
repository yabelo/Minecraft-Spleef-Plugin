package Items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Utils.Utils;

public class IronShovel {

	private static int amount = 1;
	private static String name = "The Fastest Shovel";
	public static ItemStack item = getItemStack(new ItemStack(Material.IRON_SPADE, amount), name);

	private static ItemStack getItemStack(ItemStack item, String name) {

		ItemMeta meta = item.getItemMeta();
		meta.spigot().setUnbreakable(true);
		meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.setDisplayName(Utils.chat("&r&d" + name));

		item.setItemMeta(meta);

		return item;
	}
}
