# NameTags
### 基于权限的GUI称号系统.
### 支持PAPI变量及称号切换.
### 兼容大部分计分板插件. 如：喵式计分板

##### NameTags插件下载地址:[https://ci.frog.gg/job/NameTags/](https://ci.frog.gg/job/NameTags/)

##### [插件使用说明](https://github.com/geekfrog/NameTags/wiki/NameTags-%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E)

### 前置插件
####FrogsPluginLib(宅宅蛙的插件前置库)
下载地址:[https://ci.frog.gg/job/FrogsPluginLib/](https://ci.frog.gg/job/FrogsPluginLib/)

### 可选前置插件：
##### 前置插件PlaceholderAPI下载地址:[https://www.spigotmc.org/resources/placeholderapi.6245/](https://www.spigotmc.org/resources/placeholderapi.6245/)
##### 前置插件HolographicDisplays下载地址:[https://dev.bukkit.org/projects/holographic-displays](https://dev.bukkit.org/projects/holographic-displays)

使用时必须使用FrogsPluginLib前置插件.

经测试支持大部分1.7.10-1.12的服务器. 其他版本未做测试.

#### 插件功能: 
- 基于权限的称号插件
- 可随时切换要显示的称号
- GUI同时显示可用称号及不可用称号.
- 兼容大部分计分板插件(如：喵式计分板)
- 支持PlaceholderApi变量
- 支持使用HolographicDisplays显示称号(解决称号长度限制问题)
- 可以设置默认称号、是否修改displayname、是否使用HolographicDisplays、以及称号刷新时间(使用Placeholder变量需要)
- 自带Placeholder变量: %nametags_fullname% %nametags_prefix% %nametags_suffix% 来显示displayname、prefix+color、suffix
- 支持UUID
- 等等...

#### 效果展示: 
![image](http://i.imgur.com/9YwMopS.jpg)
![image](http://i.imgur.com//E3SNYNj.jpg)

![image](http://i.imgur.com/zRAOpFf.jpg)
![image](http://i.imgur.com/tP66jxF.jpg)

![image](http://i.imgur.com/FGxa96D.jpg)
![image](http://i.imgur.com/ovCir3l.jpg)
![image](http://i.imgur.com/1oel29B.jpg)

#### 常见问题(作者提醒)：
- 如果插件不能正常工作,如1.7.10版本配置文件需要改成ANSI编码.
- 如果头顶显示了称号,但是聊天和TAB列表不显示,你需要设置Essentials的配置项"change-displayname"为"false"
- 如果你安装了计分板插件, 而导致称号无法显示, 请尝试设置配置文件compatibilityMode为true. 并调整计分板与称号的刷新时间达到最优效果!
- 如果不使用HolographicDisplays,那么前后缀的最大长度为16个字符(包括文字颜色). 注意: 前缀为prefix+"§r"+namecolor, 前后缀长度超出16个字符会被省略.
- 使用HolographicDisplays请同时配合ProtocolLib使用，ProtocolLib使HD有一些很好的特性.
- 1.7.10下使用HD可能会有一些问题, 并且除了HD显示的称号及名字外, 还会额外显示玩家的名字.
- 就算是OP可能也需要有权限才能使用相应的称号。

#### 插件进度：

加粗项已完成

- **插件基本功能**
- 自定义称号?

#### 使用统计：
[https://bstats.org/plugin/bukkit/NameTags](https://bstats.org/plugin/bukkit/NameTags)
