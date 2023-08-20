package Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import Classes.Server;
import Enums.Status;
import Main.Main;
import Utils.Utils;

public class PlayerBreakBlocks implements Listener {

	public Main plugin;

	public PlayerBreakBlocks(Main plugin) {
		super();
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerBreakBlocks(BlockBreakEvent event) {
		if (Server.getStatus() == Status.STOP)
			return;
		if(Server.freezedPlayers.contains(event.getPlayer())) event.setCancelled(true);
		
		Block block = event.getBlock();

		if(block.getType() != Material.SNOW_BLOCK) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(Utils.chat("&cYou can't break that block."));
			return;
		}
		else {
			Server.breakBlocks.add(event.getBlock().getLocation());
		}

		
	}
}
