package Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class DisableAnimals implements Listener{
	
	@EventHandler
	public void onDisableAnimals(EntitySpawnEvent event) {
		if(event.getEntity() instanceof Player) event.getEntity().remove();
	}

}
