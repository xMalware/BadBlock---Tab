package fr.badblock.bukkit.tab.general.permissions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class AbstractPermissions {
	private static AbstractPermissions permissions = null;

	public static void init(){
		if(permissions != null) return;
		
		if(Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null){
			try {
				permissions = new PermissionsExManager();
			} catch(Exception e){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {}
				Bukkit.shutdown();
			}
		} else permissions = new NullPermissions();
	}
	public static AbstractPermissions getPermissions(){
		if(permissions == null) init();
		return permissions;
	}
	public abstract String getGroup(Player base);
	public abstract String getPrefix(Player base);
	public abstract String getSuffix(Player base);
	public abstract String[] getGroups(Player base);
	public abstract boolean	isInGroup(Player base, String groupName);
	public abstract void removeGroup(Player base, String groupName);
	
	public abstract boolean hasPermission(Player base, String node);
}
