# PermissionsTime
### 支持跨服的权限限时插件(+称号功能)

##### [插件使用说明](https://github.com/geekfrog/PermissionsTime/wiki/PermissionsTime-%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E)
##### 构建地址:[http://ci.frog.gg/](http://ci.frog.gg/)
##### 前置插件Vault下载地址:[https://dev.bukkit.org/projects/vault/](https://dev.bukkit.org/projects/vault/)
##### 前置插件SQLibrary下载地址:[https://dev.bukkit.org/projects/sqlibrary/](https://dev.bukkit.org/projects/sqlibrary/)

本插件并非权限插件, 而是权限扩展插件. 可以限制权限和权限组的使用时间.

使用时必须同时使用Vault及SQLibrary两个前置插件.

使用跨服权限, 要在配置文件里开启MySQl功能.

经测试支持大部分1.7.10-1.12的服务器. 其他版本未做测试.

本插件增加了称号管理功能，默认情况下为关闭状态.(2018-06-15)

#### 插件截图: 
![image](http://i.imgur.com/cnnd5hb.jpg)
![image](http://i.imgur.com/bqvIVvz.jpg)
![image](http://i.imgur.com/NFK3WKa.jpg)

#### 称号功能: 
<details>
<summary>查看详细</summary></br>
1.支持Placeholder变量、HolographicDisplays显示称号(解决称号长度限制问题)</br>
2.可以设置称号功能开关、默认称号、是否 修改displayname、是否使用HolographicDisplays、以及称号刷新时间(使用Placeholder变量的话)</br>
3.自带Placeholder变量: %pttag_fullname% %pttag_prefix% %pttag_suffix% 来显示displayname、prefix+color、suffix</br>

![image](http://i.imgur.com/o7tZTQp.jpg)
![image](http://i.imgur.com//E3SNYNj.jpg)

![image](http://i.imgur.com/zRAOpFf.jpg)
![image](http://i.imgur.com/tP66jxF.jpg)

![image](http://i.imgur.com/FGxa96D.jpg)
![image](http://i.imgur.com/ovCir3l.jpg)
![image](http://i.imgur.com/1oel29B.jpg)

![image](http://i.imgur.com/w702i9I.jpg)

</details>

#### 已知问题(作者提醒):

- 如果出现问题一般退出服务器重进就能解决。一次不行再来一次。
- 插件重载不会重置在线玩家的权限, 如有必要请重启服务器。
- 玩家每次登陆会重置权限包涉及的权限。请不要单独给予玩家涉及的权限。
- 服务器关闭时, 插件被禁用的顺序不一, 会导致玩家有权限留存。 如果配置的权限包删掉原来设置的权限(或修改世界), 会导致插件无法清理原有的权限及权限组。(你可以配置一个不使用的权限包, 权限包内含有你想清理的权限及权限组即可。)

#### 插件进度：

加粗项已完成

- **在配置文件中编辑权限包(可含有多个权限组和多个权限)**
- **时间可以累加**
- **支持重载**
- **支持UUID**
- **命令支持给玩家添加、设置、移除、查询自身权限包时间**
- **添加、设置、移除-命令执行失败记录**
- **支持多世界权限(需要权限插件支持)**
- **调用vault API 设置玩家权限 -- 登录时(添加 移除)、游戏中(命令添加/移除 延迟移除)、退出时移除**
- **mysql保存数据**
- **支持跨服**
- **gui显示自己的权限包**
- **语言支持整理**
- **支持自定义哪个玩家的头颅**
- **支持物品发光(附魔效果)**
- **玩家进入游戏时赋予权限更早**
- **管理员查看玩家到期时间**
- **检测是否有新版本**
- **提示权限包到期**
- **玩家登录时删除过期的或无效数据**
- **提示剩余时间**
- **权限包到期可执行自定义命令**
- **增加称号管理功能**
- 取消前置插件


- 支持分页?
- 数据迁移?

#### 使用统计：
[https://bstats.org/plugin/bukkit/PermissionsTime](https://bstats.org/plugin/bukkit/PermissionsTime)
