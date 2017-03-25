package fr.badblock.bukkit.tab.skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.badblock.bukkit.tab.skyblock.objects.TabPlayer;

public class PlayerDisconnectionListener implements Listener {

	@EventHandler (priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		TabPlayer.getPlayer(event.getPlayer()).remove();
	}
	
}
