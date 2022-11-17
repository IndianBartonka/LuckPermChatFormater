package me.indian.pl;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.plugin.service.RegisteredServiceProvider;
import cn.nukkit.utils.Config;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.model.user.User;

public class Formater extends PluginBase implements Listener {


    // IndianPL
    //Chat formater for Nukkit
    //https://github.com/IndianBartonka/LuckPermChatFormater
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
        Config conf = this.getConfig();



        String cenzura = this.getConfig().getString("censorship");

        for (String czarnalista : this.getConfig().getStringList("BlackWords")) {
            if (e.getMessage().toLowerCase().contains(czarnalista.toLowerCase())) {
                if (e.getMessage().toLowerCase().contains("Huje22".toLowerCase())) {
                    return;
                }
            }
            if(!(p.isOp())) {
                msg = e.getMessage().toLowerCase().replace(czarnalista.toLowerCase(), cenzura);
                e.setMessage(msg);
            }


        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getPlayerAdapter(Player.class).getUser(p);

        String prefix = Formater.getPrefix(user);
        String suffix = Formater.getSuffix(user);

        e.setFormat(this.getConfig().getString("message.format")
                        .replace("<player>", p.getName())
                        .replace("<prefix>", prefix)
                        .replace("<suffix>", suffix)
                        .replace("<msg>", msg)
                        .replace("<device>", getDevice(p, this))
                        .replace("<health>", p.getHealth() + "")
                        .replace("<model>", p.getLoginChainData().getDeviceModel() + "")
                        .replace("<version>", p.getLoginChainData().getGameVersion())
                        .replace("<language>", p.getLoginChainData().getLanguageCode())
                        .replace("<ping>", getPing(p))
                //message.format: "<prefix> <player> <suffix> >> <msg>
        );




        }
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


    public static String getDevice(Player p , Formater plugin){
        Config conf = plugin.getConfig();
        String windows = conf.getString("Windows");
        String android = conf.getString("Android");
        String ios = conf.getString("Ios");
        String mac = conf.getString("Mac");
        String fire = conf.getString("Fire");
        String gearvr = conf.getString("Gearvr");
        String hololens = conf.getString("Hololens");
        String dedicated = conf.getString("Hedicated");
        String tvos = conf.getString("TvOs");
        String playstation = conf.getString("PlayStation");
        String nx = conf.getString("Nintendo");
        String xbox = conf.getString("Xbox");
        String unknow = conf.getString("Unknow");


        String dev = "" + p.getLoginChainData().getDeviceOS();


        String device = dev
                .replace("1", android)
                .replace("2", ios)
                .replace("3", mac)
                .replace("4", fire)
                .replace("5", gearvr)
                .replace("6", hololens)
                .replace("7", windows)
                .replace("8", windows)
                .replace("9", dedicated)
                .replace("10", tvos)
                .replace("11", playstation)
                .replace("12", nx)
                .replace("13", xbox);

        return device;
    }

    public static String getPing(Player p ){

        String ping = "";
        if (p.getPing() >= 1) {
            ping = "§a" + p.getPing();
        }
        if (p.getPing() >= 75) {
            ping = "§e" + p.getPing();
        }
        if (p.getPing() >= 100) {
            ping = "§c" + p.getPing();
        }
        return ping;
    }



}