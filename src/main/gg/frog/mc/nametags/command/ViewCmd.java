package gg.frog.mc.nametags.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import gg.frog.mc.base.utils.StrUtil;
import gg.frog.mc.nametags.PluginMain;
import gg.frog.mc.nametags.config.LangCfg;
import gg.frog.mc.nametags.config.PluginCfg;
import gg.frog.mc.nametags.config.TagNameCfg;
import gg.frog.mc.nametags.config.TagNameCfg.TagType;
import gg.frog.mc.nametags.gui.PlayerTagShow;

public class ViewCmd implements Runnable {

	private PluginMain pm;
	private String[] args;
	private CommandSender sender;
	private boolean isPlayer;
	private TagType type = null;

	public ViewCmd(PluginMain pm, CommandSender sender, boolean isPlayer, String[] args) {
		this.pm = pm;
		this.sender = sender;
		this.isPlayer = isPlayer;
		this.args = args;
	}

	@Override
	public void run() {
		if (isPlayer) {
			if (args.length == 1) {
				sender.sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + LangCfg.MSG_PROCESSING));
				List<ItemStack> itemList = new ArrayList<ItemStack>();
				List<ItemStack> disItemList = new ArrayList<ItemStack>();

				Map<String, List<ItemStack>> p_i_map;
				if ("c".equals(args[0])) {
					type = TagNameCfg.TagType.NAMECOLOR_TYPE;
					p_i_map = TagNameCfg.NAMECOLOR_ITEMS;
				} else if ("p".equals(args[0])) {
					type = TagNameCfg.TagType.PREFIX_TYPE;
					p_i_map = TagNameCfg.PREFIX_ITEMS;
				} else if ("s".equals(args[0])) {
					type = TagNameCfg.TagType.SUFFIX_TYPE;
					p_i_map = TagNameCfg.SUFFIX_ITEMS;
				} else {
					sender.sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + LangCfg.MSG_PARAMETER_MISMATCH));
					sender.sendMessage(StrUtil.messageFormat(LangCfg.CMD_VIEW, pm.PLUGIN_NAME_LOWER_CASE));
					return;
				}
				for (String p : p_i_map.keySet()) {
					if ("".equals(p) || sender.hasPermission(p)) {
						itemList.addAll(p_i_map.get(p));
					} else {
						disItemList.addAll(p_i_map.get(p));
					}
				}
				PlayerTagShow.show(pm, (Player) sender, type, itemList, disItemList);
			} else {
				sender.sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + LangCfg.MSG_PARAMETER_MISMATCH));
				sender.sendMessage(StrUtil.messageFormat(LangCfg.CMD_VIEW, pm.PLUGIN_NAME_LOWER_CASE));
			}
		} else {
			sender.sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§4Only player can use this command."));
		}
	}
}
