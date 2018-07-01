package gg.frog.mc.nametags.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import gg.frog.mc.base.utils.StrUtil;
import gg.frog.mc.nametags.PluginMain;
import gg.frog.mc.nametags.command.ViewCmd;
import gg.frog.mc.nametags.config.LangCfg;
import gg.frog.mc.nametags.config.PluginCfg;

public class NtCommand implements CommandExecutor, TabCompleter {

	private PluginMain pm;

	public NtCommand(PluginMain pm) {
		this.pm = pm;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("nametags") || commandLabel.equalsIgnoreCase("nt")) {
			boolean isPlayer = false;
			if (sender instanceof Player) {
				isPlayer = true;
			}
			if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
				getHelp(sender, isPlayer);
				return true;
			} else {
				if (args[0].equalsIgnoreCase("reload")) {
					sender.sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + LangCfg.MSG_PROCESSING));
					if (isPlayer) {
						Player player = (Player) sender;
						if (player.hasPermission("nametags.reload")) {
							for (Player p : pm.getServer().getOnlinePlayers()) {
								InventoryView inventory = p.getOpenInventory();
								if (inventory != null && (StrUtil.messageFormat(LangCfg.TAG_INVENTORY_NAME + "§r§5§9§2§0§2§r").equals(inventory.getTitle()))) {
									inventory.close();
								}
							}
							pm.getConfigManager().reloadConfig(sender);
							sender.sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + LangCfg.MSG_CONFIG_RELOADED));
							pm.getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + LangCfg.MSG_CONFIG_RELOADED));
						} else {
							sender.sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + LangCfg.MSG_NO_PERMISSION));
						}
					} else {
						for (Player p : pm.getServer().getOnlinePlayers()) {
							InventoryView inventory = p.getOpenInventory();
							if (inventory != null && (StrUtil.messageFormat(LangCfg.TAG_INVENTORY_NAME + "§r§5§9§2§0§2§r").equals(inventory.getTitle()))) {
								inventory.close();
							}
						}
						pm.getConfigManager().reloadConfig(sender);
						sender.sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + LangCfg.MSG_CONFIG_RELOADED));
					}
					return true;
				} else if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("p") || args[0].equalsIgnoreCase("s")) {
					if (hasPermission(sender, isPlayer, "nametags.view")) {
						ViewCmd tagCmd = new ViewCmd(pm, sender, isPlayer, args);
						new Thread(tagCmd).start();
					}
				} else {
					sender.sendMessage(StrUtil.messageFormat(LangCfg.CMD_HELP, pm.PLUGIN_NAME_LOWER_CASE));
				}
				return true;
			}
		}
		return false;
	}

	private void getHelp(CommandSender sender, boolean isPlayer) {
		sender.sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "\n§a===== " + pm.PLUGIN_NAME + " Version:" + pm.PLUGIN_VERSION + (pm.getDescription().getCommands().containsKey("nt") ? " Aliases:/nt" : "") + " ====="));
		if (isPlayer && (sender.hasPermission(pm.PLUGIN_NAME_LOWER_CASE + ".view"))) {
			sender.sendMessage(StrUtil.messageFormat(LangCfg.CMD_VIEW, pm.PLUGIN_NAME_LOWER_CASE));
		}
		if (!isPlayer || sender.hasPermission(pm.PLUGIN_NAME_LOWER_CASE + ".reload")) {
			sender.sendMessage(StrUtil.messageFormat(LangCfg.CMD_RELOAD, pm.PLUGIN_NAME_LOWER_CASE));
		}
	}

	private boolean hasPermission(CommandSender sender, boolean isPlayer, String permissionPath) {
		if (isPlayer) {
			Player player = (Player) sender;
			if (player.hasPermission(permissionPath)) {
			} else {
				sender.sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + LangCfg.MSG_NO_PERMISSION));
				return false;
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> tipList = new ArrayList<String>();
		boolean isPlayer = false;
		if (sender instanceof Player) {
			isPlayer = true;
		}
		if (args.length == 1) {
			args[0] = args[0].toLowerCase(Locale.ENGLISH);
			if ("tag".startsWith(args[0]) && (!isPlayer || sender.hasPermission(pm.PLUGIN_NAME_LOWER_CASE + ".view"))) {
				tipList.add("c");
				tipList.add("p");
				tipList.add("s");
			}
			if ("reload".startsWith(args[0]) && (!isPlayer || sender.hasPermission(pm.PLUGIN_NAME_LOWER_CASE + ".reload"))) {
				tipList.add("reload");
			}
		}
		return tipList;
	}
}
