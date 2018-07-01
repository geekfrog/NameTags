package gg.frog.mc.nametags.config;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;

import gg.frog.mc.base.utils.FileUtil;
import gg.frog.mc.base.utils.FileUtil.FindFilesDo;
import gg.frog.mc.base.utils.config.PluginConfig;
import gg.frog.mc.nametags.PluginMain;
import gg.frog.mc.nametags.config.LangCfg;
import gg.frog.mc.nametags.config.PluginCfg;

/**
 * 配置文件管理
 * 
 * @author QiaoPengyu
 *
 */
public class ConfigManager {

	private PluginMain pm;
	private Map<String, PluginConfig> cfgMap = new LinkedHashMap<>();

	public ConfigManager(PluginMain pm) {
		this.pm = pm;
		copyLangFilesFromJar();
		// 添加到配置列表
		cfgMap.put("plugin", new PluginCfg(pm));
	}

	public void initConfig() {
		cfgMap.put("lang", new LangCfg("lang/" + PluginCfg.LANG + ".yml", pm));
		cfgMap.put("tagNames", new TagNameCfg("tagNames.yml", pm));
	}

	public void reloadConfig(CommandSender sender) {
		for (Entry<String, PluginConfig> entry : cfgMap.entrySet()) {
			if ("lang".equals(entry.getKey())) {
				entry.setValue(new LangCfg("lang/" + PluginCfg.LANG + ".yml", pm));
			}
			entry.getValue().reloadConfig(sender);
		}
	}

	public Map<String, PluginConfig> getCfgMap() {
		return cfgMap;
	}

	private void copyLangFilesFromJar() {
		FileUtil.findFilesFromJar(new FindFilesDo() {

			@Override
			public void process(String fileName, InputStream is) {
				File f = new File(pm.getDataFolder(), fileName);
				FileUtil.writeFile(f, is);
			}

			@Override
			public boolean isProcess(String fileName) {
				if (fileName.matches("lang/.+\\.yml") || "config.yml".equals(fileName) || "tagNames.yml".equals(fileName)) {
					File f = new File(pm.getDataFolder(), fileName);
					if (!f.exists()) {
						return true;
					}
				}
				return false;
			}
		}, this.getClass());
	}
}
