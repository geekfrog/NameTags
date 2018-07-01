package gg.frog.mc.nametags.config;

import org.bukkit.command.CommandSender;

import gg.frog.mc.base.utils.PluginBase;
import gg.frog.mc.base.utils.config.PluginConfig;

/**
 * 插件默认配置
 * 
 * @author QiaoPengyu
 *
 */
public class PluginCfg extends PluginConfig {

    public static String PLUGIN_PREFIX;
    public static boolean IS_METRICS = true;
    public static boolean IS_DEBUG = false;
    public static String LANG;

    public PluginCfg(PluginBase pb) {
        super(pb);
    }

    @Override
    protected void init() {}

    @Override
    protected void loadToDo(CommandSender sender) {
        PLUGIN_PREFIX = setGetDefault("pluginPrefix", "§4[§b" + pb.PLUGIN_NAME + "§4] ") + "§r";
        IS_DEBUG = setGetDefault("debug", false);
        IS_METRICS = setGetDefault("metrics", true);
        LANG = setGetDefault("lang", "en");
    }

}
