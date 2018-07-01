package gg.frog.mc.nametags.model.cfg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import gg.frog.mc.base.utils.config.IConfigBean;

/**
 * 标签包实体类
 * 
 * @author QiaoPengyu
 *
 */
public class TagPackageBean implements IConfigBean {

	//所需权限
	private String permissions;
	//名称颜色
	private List<String> namecolor = new ArrayList<>();
	//前缀
	private List<String> prefix = new ArrayList<>();
	//后缀
	private List<String> suffix = new ArrayList<>();
	//说明
	private String description;
	
	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public List<String> getNamecolor() {
		return namecolor;
	}

	public void setNamecolor(List<String> namecolor) {
		this.namecolor = namecolor;
	}

	public List<String> getPrefix() {
		return prefix;
	}

	public void setPrefix(List<String> prefix) {
		this.prefix = prefix;
	}

	public List<String> getSuffix() {
		return suffix;
	}

	public void setSuffix(List<String> suffix) {
		this.suffix = suffix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public YamlConfiguration toConfig() {
		YamlConfiguration config = new YamlConfiguration();
		config.set("permissions", permissions);
		config.set("namecolor", namecolor);
		config.set("prefix", prefix);
		config.set("suffix", suffix);
		config.set("description", description);
		return config;
	}

	@Override
	public void toConfigBean(MemorySection config) {
		permissions = config.getString("permissions");
		namecolor = config.getStringList("namecolor");
		prefix = config.getStringList("prefix");
		suffix = config.getStringList("suffix");
		description = config.getString("description");
	}

	@Override
	public String toString() {
		return "PermissionPackageBean [permissions=" + permissions + ", namecolor=" + namecolor + ", prefix=" + prefix + ", suffix="
				+ suffix + ", description=" + description + "]";
	}
}
