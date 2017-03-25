package fr.badblock.bukkit.tab.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.badblock.bukkit.tab.general.commands.BaillonCommand;
import fr.badblock.bukkit.tab.general.commands.SetAFKCommand;
import fr.badblock.bukkit.tab.general.commands.SlowmodeCommand;
import fr.badblock.bukkit.tab.general.commands.TabBlockCommand;
import fr.badblock.bukkit.tab.general.listeners.AsyncChatListener;
import fr.badblock.bukkit.tab.general.listeners.PermissionEntityListener;
import fr.badblock.bukkit.tab.general.listeners.PlayerDisconnectionListener;
import fr.badblock.bukkit.tab.general.listeners.PlayerJoinListener;
import fr.badblock.bukkit.tab.general.objects.TabPlayer;
import fr.badblock.bukkit.tab.general.permissions.PermissionsExManager;

public class BadBlockTab extends JavaPlugin {

	private static BadBlockTab		 instance;

	public boolean				 baillon		= false;
	public long					 slowmodeTime   = 0L;
	public long					 slowmodeMax    = 0L;
	public String				 slowmodePlayer;
	
	public Map<String,  String>  teamsPrefix    = new HashMap<>();
	public Map<String,  String>  teamsGroup     = new HashMap<>();
	public PermissionsExManager  permissionsExManager;
	
	public boolean				 chat;
	public List<String>			 afk 			= new ArrayList<>();
	
	@Override
	public void onEnable() {
		instance = this;
		permissionsExManager = new PermissionsExManager();
		this.saveDefaultConfig();
		this.reloadConfig();
		PluginManager pluginManager = this.getServer().getPluginManager();
		pluginManager.registerEvents(new PlayerDisconnectionListener(), this);
		pluginManager.registerEvents(new PermissionEntityListener(), this);
		pluginManager.registerEvents(new PlayerJoinListener(), this);
		pluginManager.registerEvents(new AsyncChatListener(), this);
		getCommand("tabblock").setExecutor(new TabBlockCommand());
		getCommand("baillon").setExecutor(new BaillonCommand());
		getCommand("slowmode").setExecutor(new SlowmodeCommand());
		getCommand("setafk").setExecutor(new SetAFKCommand());
	}
	
	@Override
	public void reloadConfig() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			TabPlayer tabPlayer = TabPlayer.getPlayer(player);
			for (String string : teamsGroup.keySet()) {
				tabPlayer.scoreboard.getTeam(string).unregister();
			}
		}
		super.reloadConfig();
		FileConfiguration config = this.getConfig();
		for (String string : config.getStringList("groupsOrder")) {
			String[] splitter = string.split(":");
			if (splitter[1].equalsIgnoreCase("CTO")) splitter[0] = "B0";
			teamsGroup.put(splitter[0], splitter[1]);
			teamsPrefix.put(splitter[0], splitter[2]);
		}
		if (!config.contains("chat")) {
			config.set("chat", true);
			this.saveConfig();
			chat = true;
		}else chat = config.getBoolean("chat");
		TabPlayer.players.clear();
		for (Player player : this.getServer().getOnlinePlayers())
			TabPlayer.getPlayer(player);
	}
	
	public static BadBlockTab getInstance() {
		return instance;
	}
	
}
