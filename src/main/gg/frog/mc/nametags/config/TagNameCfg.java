package gg.frog.mc.nametags.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;

import gg.frog.mc.base.utils.StrUtil;
import gg.frog.mc.base.utils.config.PluginConfig;
import gg.frog.mc.base.utils.nms.ItemUtil;
import gg.frog.mc.nametags.PluginMain;
import gg.frog.mc.nametags.model.PlayerTagBean;
import gg.frog.mc.nametags.model.cfg.TagPackageBean;

public class TagNameCfg extends PluginConfig {

	public static String DEFAULT_NAMECOLOR = null;
	public static String DEFAULT_PREFIX = null;
	public static String DEFAULT_SUFFIX = null;
	public static boolean CHANGE_DISPLAYNAME = true;
	public static boolean COMPATIBILITY_MODE = false;
	public static boolean USE_HD_PLUGIN = false;
	public static boolean ONE_LINE_DISPLAY = false;
	public static Integer REFRESH_TAG_TIME = null;
	public static Map<String, TagPackageBean> PACKAGES = new ConcurrentHashMap<>();
	public static List<String> TEMPLATE_LORE = null;

	public static Map<String, List<ItemStack>> NAMECOLOR_ITEMS = new ConcurrentHashMap<>();
	public static Map<String, List<ItemStack>> PREFIX_ITEMS = new ConcurrentHashMap<>();
	public static Map<String, List<ItemStack>> SUFFIX_ITEMS = new ConcurrentHashMap<>();

	public static Map<String, List<String>> NAMECOLOR_PERMISSIONS = new ConcurrentHashMap<>();
	public static Map<String, List<String>> PREFIX_PERMISSIONS = new ConcurrentHashMap<>();
	public static Map<String, List<String>> SUFFIX_PERMISSIONS = new ConcurrentHashMap<>();

	public static Map<String, PlayerTagBean> PLAYER_TAG = new ConcurrentHashMap<>();

	public static Scoreboard scoreboard = null;

	private BukkitTask task = null;

	public enum TagType {
		NAMECOLOR_TYPE, PREFIX_TYPE, SUFFIX_TYPE
	}

	public TagNameCfg(String fileName, PluginMain pm) {
		super(fileName, pm);
	}

	@Override
	protected void init() {
	}

	@Override
	protected void loadToDo(CommandSender sender) {
		DEFAULT_NAMECOLOR = setGetDefault("defaultNamecolor", "");
		DEFAULT_PREFIX = setGetDefault("defaultPrefix", "");
		DEFAULT_SUFFIX = setGetDefault("defaultSuffix", "");
		CHANGE_DISPLAYNAME = setGetDefault("changeDisplayname", true);
		COMPATIBILITY_MODE = setGetDefault("compatibilityMode", false);
		USE_HD_PLUGIN = setGetDefault("useHdPlugin", false);
		if (USE_HD_PLUGIN && !PluginMain.enabledHdPlugin) {
			USE_HD_PLUGIN = false;
			if (sender != null) {
				sender.sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§eHolographicDisplays is not installed or not enabled. "));
				sender.sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§eBut you enabled some func need HolographicDisplays."));
			} else {
				pb.getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§eHolographicDisplays is not installed or not enabled. "));
				pb.getServer().getConsoleSender().sendMessage(StrUtil.messageFormat(PluginCfg.PLUGIN_PREFIX + "§eBut you enabled some func need HolographicDisplays."));
			}
		}
		ONE_LINE_DISPLAY = setGetDefault("oneLineDisplay", true);
		REFRESH_TAG_TIME = setGetDefault("refreshTagTime", -1);
		PACKAGES = getObjMap("packages", TagPackageBean.class);

		TagPackageBean defaultTagPackage = new TagPackageBean();
		defaultTagPackage.setDescription(LangCfg.DEFAULT_TAG_DESCRIPTION);
		defaultTagPackage.getNamecolor().add(DEFAULT_NAMECOLOR);
		defaultTagPackage.getPrefix().add(DEFAULT_PREFIX);
		defaultTagPackage.getSuffix().add(DEFAULT_SUFFIX);
		defaultTagPackage.setPermissions("");
		PACKAGES.put("defaultTagPackage", defaultTagPackage);

		TEMPLATE_LORE = getConfig().getStringList("template.lore");
		getConfig().set("template.lore", TEMPLATE_LORE);
		setObj("packages", PACKAGES);
		if (PluginCfg.IS_DEBUG) {
			System.out.println("defaultNamecolor:" + DEFAULT_NAMECOLOR);
			System.out.println("defaultPrefix:" + DEFAULT_PREFIX);
			System.out.println("defaultSuffix:" + DEFAULT_SUFFIX);
			System.out.println("useHdPlugin:" + USE_HD_PLUGIN);
			for (Entry<String, TagPackageBean> p : PACKAGES.entrySet()) {
				System.out.println(p.getKey() + ":" + p.getValue());
			}
			for (String lore : TEMPLATE_LORE) {
				System.out.println(lore);
			}
		}
		NAMECOLOR_ITEMS.clear();
		PREFIX_ITEMS.clear();
		SUFFIX_ITEMS.clear();

		NAMECOLOR_PERMISSIONS.clear();
		PREFIX_PERMISSIONS.clear();
		SUFFIX_PERMISSIONS.clear();

		for (Entry<String, TagPackageBean> e : PACKAGES.entrySet()) {
			List<ItemStack> items = getTagItem(TagType.NAMECOLOR_TYPE, e.getValue());
			if (NAMECOLOR_ITEMS.containsKey(e.getValue().getPermissions())) {
				NAMECOLOR_ITEMS.get(e.getValue().getPermissions()).addAll(items);
			} else {
				NAMECOLOR_ITEMS.put(e.getValue().getPermissions(), items);
			}
			items = getTagItem(TagType.PREFIX_TYPE, e.getValue());
			if (PREFIX_ITEMS.containsKey(e.getValue().getPermissions())) {
				PREFIX_ITEMS.get(e.getValue().getPermissions()).addAll(items);
			} else {
				PREFIX_ITEMS.put(e.getValue().getPermissions(), items);
			}
			items = getTagItem(TagType.SUFFIX_TYPE, e.getValue());
			if (SUFFIX_ITEMS.containsKey(e.getValue().getPermissions())) {
				SUFFIX_ITEMS.get(e.getValue().getPermissions()).addAll(items);
			} else {
				SUFFIX_ITEMS.put(e.getValue().getPermissions(), items);
			}
		}

		if (task != null) {
			task.cancel();
		}
		refreshTagTask();
	}

	private void refreshTagTask() {
		for (Player player : pb.getServer().getOnlinePlayers()) {
			PlayerTagBean.initPlayerTag(player, (PluginMain) pb);
		}
		if (REFRESH_TAG_TIME > 0) {
			task = pb.getServer().getScheduler().runTaskLaterAsynchronously(pb, new Runnable() {
				@Override
				public void run() {
					refreshTagTask();
				}
			}, REFRESH_TAG_TIME * 20);
		}
	}

	private List<ItemStack> getTagItem(TagType tagType, TagPackageBean tpb) {
		List<ItemStack> items = new ArrayList<>();
		if (tpb != null) {
			List<String> tags = null;
			String itemDisplayName = "";
			Map<String, List<String>> tagPermissions = null;
			if (tagType == TagType.NAMECOLOR_TYPE) {
				tags = tpb.getNamecolor();
				itemDisplayName = LangCfg.TAG_COLOR_ITEM_NAME + "§1§r ";
				tagPermissions = NAMECOLOR_PERMISSIONS;
			} else if (tagType == TagType.PREFIX_TYPE) {
				tags = tpb.getPrefix();
				itemDisplayName = LangCfg.TAG_PREFIX_ITEM_NAME + "§2§r ";
				tagPermissions = PREFIX_PERMISSIONS;
			} else if (tagType == TagType.SUFFIX_TYPE) {
				tags = tpb.getSuffix();
				itemDisplayName = LangCfg.TAG_SUFFIX_ITEM_NAME + "§3§r ";
				tagPermissions = SUFFIX_PERMISSIONS;
			}
			if (tags != null) {
				for (String tag : tags) {
					String[] args = tag.split(":");
					tag = args[0];
					Material type = null;
					int exid = 0;
					String skullOwner = null;
					if (args.length > 1) {
						type = Material.getMaterial(args[1].toUpperCase(Locale.ENGLISH));
						if (type == null) {
							int id = Integer.parseInt(args[1]);
							type = Material.getMaterial(id);
						}
						if (args.length > 2) {
							try {
								exid = Integer.parseInt(args[2]);
							} catch (NumberFormatException e) {
								if (Material.SKULL_ITEM.equals(type)) {
									exid = 3;
									skullOwner = args[2];
								} else {
									e.printStackTrace();
								}
							}
						}
					} else {
						type = Material.getMaterial("NAME_TAG");
					}
					if (type != null) {
						ItemStack item = new ItemStack(type, 1, (short) 0, (byte) exid);
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName(StrUtil.messageFormat(itemDisplayName + tag));
						List<String> lores = new ArrayList<>(TEMPLATE_LORE);
						for (int i = 0; i < lores.size(); i++) {
							if (lores.get(i).contains("%description%")) {
								lores.set(i, StrUtil.messageFormat(lores.get(i).replaceAll("%description%", tpb.getDescription())));// 描述
							} else {
								lores.set(i, StrUtil.messageFormat(lores.get(i)));
							}
						}
						lores.add("§8§k" + tpb.getPermissions());// 权限
						lores.add("§8" + tag);// 称号
						meta.setLore(lores);
						item.setItemMeta(meta);
						if (skullOwner != null) {
							item = ItemUtil.addSkullOwner(item, skullOwner);
						}
						items.add(item);
					}
					if (!tagPermissions.containsKey(tag)) {
						tagPermissions.put(tag, new ArrayList<>());
					}
					tagPermissions.get(tag).add(tpb.getPermissions());
				}
			}
		}
		return items;
	}
}