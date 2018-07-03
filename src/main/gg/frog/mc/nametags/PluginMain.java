package gg.frog.mc.nametags;

import java.util.Locale;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;

import gg.frog.mc.base.utils.StrUtil;
import gg.frog.mc.base.utils.UpdateCheck;
import gg.frog.mc.base.utils.PluginBase;
import gg.frog.mc.nametags.command.NtCommand;
import gg.frog.mc.nametags.config.ConfigManager;
import gg.frog.mc.nametags.config.PluginCfg;
import gg.frog.mc.nametags.config.TagNameCfg;
import gg.frog.mc.nametags.listener.TagsListener;
import gg.frog.mc.nametags.placeholder.TagPlaceholder;

public class PluginMain extends PluginBase {

	public static final String DEPEND_PLUGIN = "FrogsPluginLib";

	private ConfigManager cm = null;
	private PluginMain pm = null;
	public static boolean enabledHdPlugin;

	public PluginMain() {
		PLUGIN_NAME = getDescription().getName();
		PLUGIN_VERSION = getDescription().getVersion();
		PLUGIN_NAME_LOWER_CASE = PLUGIN_NAME.toLowerCase(Locale.ENGLISH);
	}

	@Override
	public void onEnable() {
		pm = this;
		cm = new ConfigManager(pm);
		getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "==============================="));
		getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX));
		getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "    " + PLUGIN_NAME + " v" + PLUGIN_VERSION));
		getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "    author：GeekFrog QQ：324747460"));
		getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "    https://github.com/geekfrog/NameTags/ "));
		getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX));
		getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "==============================="));
		if (!checkPluginDepends()) {
			getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§4Startup failure!"));
			getServer().getPluginManager().disablePlugin(pm);
		} else {
			cm.initConfig();
			registerListeners();
			registerCommands();
			getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§2Startup successful!"));
			TagNameCfg.scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
			getServer().getScheduler().runTask(pm, new Runnable() {
				public void run() {
					if (PluginCfg.IS_METRICS) {
						try {
							new Metrics(pm);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					getServer().getScheduler().runTaskAsynchronously(pm, new UpdateCheck(pm, "https://raw.githubusercontent.com/geekfrog/NameTags/master/src/resources/plugin.yml"));
				}
			});
		}
	}

	/**
	 * 注册监听器 <br/>
	 * 这里可以注册多个
	 */
	private void registerListeners() {
		pm.getServer().getPluginManager().registerEvents(new TagsListener(pm), pm);
	}

	/**
	 * 注册命令 <br/>
	 * 这里可以注册多个，一般注册一个就够用
	 */
	private void registerCommands() {
		NtCommand tagCmd = new NtCommand(pm);
		if (getDescription().getCommands().containsKey("nametags")) {
			getCommand("nametags").setExecutor(tagCmd);
		}
		if (getDescription().getCommands().containsKey("nt")) {
			getCommand("nt").setExecutor(tagCmd);
		}
	}

	public ConfigManager getConfigManager() {
		return cm;
	}

	private boolean checkPluginDepends() {
		boolean needDepend = false;
		if (DEPEND_PLUGIN.length() > 0) {
			for (String name : DEPEND_PLUGIN.split(",")) {
				if (!getServer().getPluginManager().isPluginEnabled(name)) {
					getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§4Need depend plugins : " + name + "."));
					needDepend = true;
				}
			}
		}
		boolean enabledPlaceholder = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
		if (!enabledPlaceholder) {
			getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§ePlaceholder is not installed or not enabled."));
			getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§eSome func will be disabled."));
		} else {
			boolean placeholdersHook = false;
			try {
				placeholdersHook = new TagPlaceholder(pm).register();
				placeholdersHook = true;
			} catch (Exception e) {

			}
			if (!placeholdersHook) {
				getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§4Cann''t hook placeholders"));
				getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§4The placeholders '%nametags_xxxxx%' Cann''t use."));
			}
		}
		enabledHdPlugin = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
		if (!enabledHdPlugin) {
			getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§eHolographicDisplays is not installed or not enabled."));
			getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§eSome func will be disabled."));
		}
		if (needDepend) {
			return false;
		}
		return true;
	}

	@Override
	public void onDisable() {
		getServer().getServicesManager().unregisterAll(pm);
		Bukkit.getScheduler().cancelTasks(pm);
	}
}
