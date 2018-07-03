package gg.frog.mc.nametags.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import gg.frog.mc.base.utils.StrUtil;
import gg.frog.mc.base.utils.data.PlayerData;
import gg.frog.mc.base.utils.nms.ItemUtil;
import gg.frog.mc.nametags.PluginMain;
import gg.frog.mc.nametags.config.LangCfg;
import gg.frog.mc.nametags.config.PluginCfg;
import gg.frog.mc.nametags.config.TagNameCfg;
import gg.frog.mc.nametags.config.TagNameCfg.TagType;
import gg.frog.mc.nametags.model.PlayerTagBean;

public class PlayerTagShow {

	public static void show(PluginMain pm, Player p, TagType tagType, List<ItemStack> itemList, List<ItemStack> disItemList) {
		Inventory inventory = null;
		int size = 0;
		if (itemList.size() > 0) {
			inventory = Bukkit.createInventory(null, ((itemList.size() + disItemList.size()) % 9 == 0 ? (itemList.size() + disItemList.size()) : ((itemList.size() + disItemList.size()) / 9 + 1) * 9), StrUtil.messageFormat(LangCfg.TAG_INVENTORY_NAME + "§r§5§9§2§0§2§r"));
			String uuid = PlayerData.getPlayerUUIDByName(p);
			PlayerTagBean playerTag = null;
			if (uuid != null && TagNameCfg.PLAYER_TAG.containsKey(uuid)) {
				playerTag = TagNameCfg.PLAYER_TAG.get(uuid);
				if (playerTag != null) {
					for (ItemStack item : itemList) {
						playerTag = playerTag.clone();
						ItemStack tItem = item.clone();
						ItemMeta meta = tItem.getItemMeta();
						List<String> lores = meta.getLore();
						String tag = lores.get(lores.size() - 1);
						tag = tag.length() > 2 ? tag.substring(2) : "";
						if (tagType == TagType.NAMECOLOR_TYPE) {
							meta.setDisplayName(meta.getDisplayName() + p.getName());
							playerTag.setNamecolor(tag);
						} else if (tagType == TagType.PREFIX_TYPE) {
							playerTag.setPrefix(tag);
						} else if (tagType == TagType.SUFFIX_TYPE) {
							playerTag.setSuffix(tag);
						}
						for (int i = 0; i < lores.size(); i++) {
							if (lores.get(i).contains("%displayname%")) {
								lores.set(i, lores.get(i).replaceAll("%displayname%", playerTag.getDisplayNameStr(p)));// 效果
							}
						}
						meta.setLore(lores);
						tItem.setItemMeta(meta);
						inventory.addItem(tItem);
						size++;
					}
					for (ItemStack item : disItemList) {
						playerTag = playerTag.clone();
						ItemStack tItem = item.clone();
						ItemMeta meta = tItem.getItemMeta();
						List<String> lores = meta.getLore();
						String tag = lores.get(lores.size() - 1);
						tag = tag.length() > 2 ? tag.substring(2) : "";
						if (tagType == TagType.NAMECOLOR_TYPE) {
							meta.setDisplayName("§6§l§m" + meta.getDisplayName().substring(4) + p.getName());
							playerTag.setNamecolor(tag);
						} else if (tagType == TagType.PREFIX_TYPE) {
							meta.setDisplayName("§6§l§m" + meta.getDisplayName().substring(4));
							playerTag.setPrefix(tag);
						} else if (tagType == TagType.SUFFIX_TYPE) {
							meta.setDisplayName("§6§l§m" + meta.getDisplayName().substring(4));
							playerTag.setSuffix(tag);
						}
						for (int i = 0; i < lores.size(); i++) {
							if (lores.get(i).contains("%displayname%")) {
								lores.set(i, lores.get(i).replaceAll("%displayname%", playerTag.getDisplayNameStr(p)));// 效果
							}
						}
						meta.setLore(lores);
						tItem.setItemMeta(meta);
						tItem = ItemUtil.addEnchantLight(tItem);
						inventory.addItem(tItem);
						size++;
					}
				}
			}
		}
		if (inventory != null && size > 0) {
			try {
				p.openInventory(inventory);
			} catch (Exception e) {
				pm.getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§4Catch Exception: " + e.getMessage()));
			}
		} else {
			p.sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + LangCfg.MSG_NO_TAG_DATA));
		}
	}
}
