package me.grom.joins;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinsMain extends JavaPlugin implements Listener {
	
	private FileConfiguration msgs;
	static JoinsMain instance;
	boolean isJoin = false;
	boolean isLeave = false;
	
	@Override
	public void onEnable() {	
		this.getLogger().info("Enabled");
		this.getCommand("change").setExecutor(new CommandChange());
		
		instance = this;
		getServer().getPluginManager().registerEvents(this, (Plugin)this);

        File msgsfile = new File(getDataFolder() + File.separator  + "msgs.yml");

        if(!(msgsfile.exists())){
        	try {
				msgsfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }

        msgs = YamlConfiguration.loadConfiguration(msgsfile);
		
	}
	
	public void onDisable() {}
	
	String getMsgs(String PlayerName, String type){
	    try {
            return msgs.getString("messages." + type + "." + PlayerName);
        }
	    catch (NullPointerException e) {
	    	if (type.equalsIgnoreCase("join")) {setMsgs(PlayerName, "join" , "Вошел на сервер.");}
	    	else if (type.equalsIgnoreCase("leave")) {setMsgs(PlayerName, "leave" , "Покинул сервер.");}
	        return msgs.getString("messages." + type + "." + PlayerName);
        }
    }

    void setMsgs(String PlayerName, String type, String msg) {
	    msgs.set("messages." + PlayerName + "." + type, msg);
    }
	
    @EventHandler
	public void onJoin(PlayerJoinEvent e) {
		String pn = e.getPlayer().getName();
		String pjm = getMsgs(pn, "join");
		Bukkit.broadcastMessage(ChatColor.GREEN + ">>>" + ChatColor.WHITE + pn + ChatColor.GOLD + pjm);
	}
	
    @EventHandler
	public void onLeave(PlayerQuitEvent e) {
		String pn = e.getPlayer().getName();
		String plm = getMsgs(pn, "leave");
		Bukkit.broadcastMessage(ChatColor.RED + ">>>" + ChatColor.WHITE + pn + ChatColor.GOLD + plm);
	}
    
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
    	Player p = e.getPlayer();
    	if (isJoin) {
    		setMsgs(p.getName(), "join", "%2$s");
    		p.sendMessage("Ваше сообщение о входе теперь: %2$s");
    		isJoin = false;
    		e.setCancelled(true);
    	}
    	if (isLeave) {
    		setMsgs(p.getName(), "leave", "%2$s");
    		p.sendMessage("Ваше сообщение о выходе теперь: %2$s");
    		isLeave = false;
    		e.setCancelled(true);
    	}
    }
	
}
