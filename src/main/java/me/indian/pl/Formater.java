package me.indian.pl;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.plugin.service.RegisteredServiceProvider;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.model.user.User;

public class Formater extends PluginBase implements Listener {

    @Override
    public void onEnable(){

        if(getServer().getPluginManager().getPlugin("LuckPerms")== null){
            getLogger().warning("You dont have lucky perms");
            getServer().getPluginManager().disablePlugin(this);
        }
        saveDefaultConfig();


        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this , this);
    }

    @EventHandler
    public void playerChat(PlayerChatEvent e) {
        Player p = (Player) e.getPlayer();
        String msg = e.getMessage();

        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getPlayerAdapter(Player.class).getUser(p);

        String prefix = Formater.getPrefix(user);
        String suffix = Formater.getSuffix(user);

        e.setFormat(this.getConfig().getString("message.format")
                .replace("<player>" , p.getName())
                .replace("<prefix>" , prefix)
                .replace("<suffix>" , suffix)
                .replace("<msg>" , msg)
                //message.format: "<prefix> <player> <suffix> >> <msg>
        );


    }

    public static String getPrefix(User user){
        String prefix = "";
        if(user.getCachedData().getMetaData().getPrefix() != null) {
            prefix = user.getCachedData().getMetaData().getPrefix();
        } else {
            prefix = "";
        }
        return prefix;
    }

    public static String getSuffix(User user){
        String suffix = "";
        if(user.getCachedData().getMetaData().getSuffix() != null) {
            suffix = user.getCachedData().getMetaData().getSuffix();
        } else{
            suffix = "";
        }
        return suffix;
    }

}