package gg.frog.mc.nametags.config;

import org.bukkit.command.CommandSender;

import gg.frog.mc.base.utils.config.PluginConfig;
import gg.frog.mc.nametags.PluginMain;

/**
 * 语言支持
 * 
 * @author QiaoPengyu
 *
 */
public class LangCfg extends PluginConfig {

	public static String TAG_INVENTORY_NAME = null;
	public static String TAG_COLOR_ITEM_NAME = null;
	public static String TAG_PREFIX_ITEM_NAME = null;
	public static String TAG_SUFFIX_ITEM_NAME = null;

	public static String MSG_PARAMETER_MISMATCH = null;
	public static String MSG_PROCESSING = null;
	public static String MSG_NO_TAG_DATA = null;
	public static String MSG_NO_PERMISSION = null;
	public static String MSG_CONFIG_RELOADED = null;
	public static String MSG_TAG_SET_SUCCESS = null;

	public static String CMD_HELP = null;
	public static String CMD_VIEW = null;
	public static String CMD_RELOAD = null;

	public LangCfg(String fileName, PluginMain pm) {
		super(fileName, pm);
	}

	@Override
	protected void init() {
	}

	@Override
	protected void loadToDo(CommandSender sender) {
		TAG_INVENTORY_NAME = getConfig().getString("tagInventoryName", "&4===Tag Packages===");
		TAG_COLOR_ITEM_NAME = getConfig().getString("tagColorItemName", "&6&lName Style");
		TAG_PREFIX_ITEM_NAME = getConfig().getString("tagPrefixItemName", "&6&lTag Prefix");
		TAG_SUFFIX_ITEM_NAME = getConfig().getString("tagSuffixItemName", "&6&lTag Suffix");

		MSG_PARAMETER_MISMATCH = getConfig().getString("msg.parameterMismatch", "&4Parameter mismatch.");
		MSG_PROCESSING = getConfig().getString("msg.processing", "&2Please wait for processing...");
		MSG_NO_TAG_DATA = getConfig().getString("msg.noTagData", "&4No data for tag packages.");
		MSG_NO_PERMISSION = getConfig().getString("msg.nopermission", "&4You do not have permission to do this.");
		MSG_CONFIG_RELOADED = getConfig().getString("msg.configReloaded", "&2Configuration reload is complete.");
		MSG_TAG_SET_SUCCESS = getConfig().getString("msg.tagSetSuccess", "&2Tag Set Success.");

		CMD_HELP = getConfig().getString("cmd.help", "/{0} help \\n&7  - Show commands.");
		CMD_VIEW = getConfig().getString("cmd.view", "&6/{0} tag <c/p/s> \\n&7  - Set your color/prefix/suffix of tags.");
		CMD_RELOAD = getConfig().getString("cmd.reload", "&6/{0} reload \\n&7  - Reloads the config file.");
	}
}
