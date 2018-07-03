package gg.frog.mc.nametags.listener;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryView;

import gg.frog.mc.base.utils.StrUtil;
import gg.frog.mc.base.utils.data.PlayerData;
import gg.frog.mc.nametags.PluginMain;
import gg.frog.mc.nametags.config.LangCfg;
import gg.frog.mc.nametags.config.PluginCfg;
import gg.frog.mc.nametags.config.TagNameCfg;
import gg.frog.mc.nametags.model.PlayerTagBean;

public class TagsListener implements Listener {

	private PluginMain pm;

	public TagsListener(PluginMain pm) {
		this.pm = pm;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent event) {
		pm.getServer().getScheduler().runTaskLaterAsynchronously(pm, new Runnable() {
			@Override
			public void run() {
				PlayerTagBean.initPlayerTag(event.getPlayer(), pm);
				PlayerTagBean.addAllTeamToSelfScoreboard(event.getPlayer());
			}
		}, 1 * 20);
	}

	@EventHandler
	public void onRespawn(PlayerChangedWorldEvent event) {
		PlayerTagBean.initPlayerTag(event.getPlayer(), pm);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (TagNameCfg.USE_HD_PLUGIN) {
			String uuid = PlayerData.getPlayerUUIDByName(event.getPlayer());
			PlayerTagBean playerTag = TagNameCfg.PLAYER_TAG.get(uuid);
			playerTag.delHologramsName();
		}
	}

	@EventHandler
	public void onKick(PlayerKickEvent event) {
		if (TagNameCfg.USE_HD_PLUGIN) {
			String uuid = PlayerData.getPlayerUUIDByName(event.getPlayer());
			PlayerTagBean playerTag = TagNameCfg.PLAYER_TAG.get(uuid);
			playerTag.delHologramsName();
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (TagNameCfg.USE_HD_PLUGIN) {
			String uuid = PlayerData.getPlayerUUIDByName(event.getPlayer());
			PlayerTagBean playerTag = TagNameCfg.PLAYER_TAG.get(uuid);
			if (playerTag != null) {
				playerTag.moveHologramsName(event.getTo());
			}
		}
	}

	@EventHandler
	public void onPlayerClick(InventoryClickEvent event) {
		if (StrUtil.messageFormat(LangCfg.TAG_INVENTORY_NAME + "§r§5§9§2§0§2§r").equals(event.getInventory().getName())) {
			event.setCancelled(true);
			try {
				if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().hasLore()) {
					List<String> lores = event.getCurrentItem().getItemMeta().getLore();
					if (lores.size() > 1) {
						String permissions = lores.get(lores.size() - 2);
						permissions = permissions.startsWith("§8§k") ? permissions.substring(4) : "noPermissions";
						String uuid = PlayerData.getPlayerUUIDByName((Player) event.getWhoClicked());
						if (permissions.length() == 0 || event.getWhoClicked().hasPermission(permissions)) {
							if (TagNameCfg.PLAYER_TAG.containsKey(uuid)) {
								String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
								PlayerTagBean playerTag = TagNameCfg.PLAYER_TAG.get(uuid);
								if (itemName.startsWith(StrUtil.messageFormat(LangCfg.TAG_COLOR_ITEM_NAME + "§1§r "))) {
									playerTag.setNamecolor(lores.get(lores.size() - 1).substring(2));
								} else if (itemName.startsWith(StrUtil.messageFormat(LangCfg.TAG_PREFIX_ITEM_NAME + "§2§r "))) {
									playerTag.setPrefix(lores.get(lores.size() - 1).substring(2));
								} else if (itemName.startsWith(StrUtil.messageFormat(LangCfg.TAG_SUFFIX_ITEM_NAME + "§3§r "))) {
									playerTag.setSuffix(lores.get(lores.size() - 1).substring(2));
								} else {
									((Player) event.getWhoClicked()).sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + LangCfg.MSG_NO_PERMISSION));
									return;
								}
								playerTag.setPlayerDisplayName((Player) event.getWhoClicked(), true);
								playerTag.saveConfig();
								((Player) event.getWhoClicked()).sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + LangCfg.MSG_TAG_SET_SUCCESS));
								InventoryView inventory = event.getView();
								inventory.close();
								return;
							}
						}
						((Player) event.getWhoClicked()).sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + LangCfg.MSG_NO_PERMISSION));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
