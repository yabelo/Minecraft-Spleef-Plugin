package Classes;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import Enums.Status;
import Items.IronShovel;
import Main.Main;
import Utils.Utils;

public class Server {

	private static ArrayList<Player> players = new ArrayList<Player>(2);
	public static ArrayList<Player> freezedPlayers = new ArrayList<Player>(2);
	public static ArrayList<Location> breakBlocks = new ArrayList<Location>();
	private static Status status = Status.STOP;

	public static void addPlayer(Player player) {

		players.add(player);

		checkIfCanStart();
	}

	public static void removePlayer(Player player) {
		players.remove(player);
		freezedPlayers.remove(player);

		if (status == Status.STOP) {
			return;
		}

		switch (status) {
		case START:
			players.get(0).sendMessage(Utils.chat("&cSpleef disabled."));
			resetSpleef();
			break;
		case RUNNING:
			setWinner(players.get(0));
			break;
		default:
		}
	}

	private static void resetSpleef() {
		status = Status.STOP;
		freezedPlayers.clear();

		Player p = players.get(0);

		removePlayer(p);
		addPlayer(p);

	}

	public static ArrayList<Player> getPlayers() {
		return players;
	}

	public static void setStatus(Status status) {
		Server.status = status;
	}

	public static Status getStatus() {
		return status;
	}

	private static void checkIfCanStart() {

		if (players.size() == 2) {
			startSpleef();
		} else {
			new BukkitRunnable() {

				@Override
				public void run() {

					if (players.size() == 2) {
						startSpleef();
					}
					players.get(0).sendMessage(Utils.chat("&dWaiting for players..."));

				}
			}.runTaskLater(Main.getInstance(), 20L);
		}
	}

	public static void startSpleef() {
		setStatus(Status.START);
		Var.world.setPVP(false);

		makeChanges();
		sendStartMessage();

		new BukkitRunnable() {

			@Override
			public void run() {
				if (status == Status.STOP)
					return;
				setStatus(Status.RUNNING);
			}
		}.runTaskLater(Main.getInstance(), 20L * 6);

	}

	public static void stopSpleef() {
		
		status = Status.STOP;
		
		for(Location l : breakBlocks) {
			l.getBlock().setType(Material.SNOW_BLOCK);
		}
		
		for(Entity e : Var.world.getEntities()) {
			if(e.getType().equals(EntityType.FALLING_BLOCK) || e.getType().equals(EntityType.SNOWBALL)) {
				e.remove();
			}
		}
		
		teleportPlayersToLocation();
		
		for(Player p : players) p.getInventory().clear();
		
		if (!players.isEmpty())
			players.clear();
		if (!freezedPlayers.isEmpty())
			freezedPlayers.clear();

	}

	public static void setWinner(Player player) {

		for (Player p : getPlayers()) {
			p.sendMessage(Utils.chat("&6&l" + player.getName() + " won the game!"));
		}
		
		stopSpleef();

	}

	private static void sendStartMessage() {

		for (Player player : getPlayers()) {

			new BukkitRunnable() {
				int counter = 6;

				@Override
				public void run() {
					counter--;

					if (status == Status.STOP)
						return;

					switch (counter) {
					case 5:
						teleportPlayersToLocation();
						freezePlayers(player);
						player.getInventory().setItem(0, IronShovel.item);
						player.sendMessage(Utils.chat("Starting in &e5"));
						break;
					case 4:
						player.sendMessage(Utils.chat("Starting in &34"));
						break;
					case 3:
						player.sendMessage(Utils.chat("Starting in &c3"));
						break;
					case 2:
						player.sendMessage(Utils.chat("Starting in &b2"));
						break;
					case 1:
						player.sendMessage(Utils.chat("Starting in &a1"));

						freezePlayers(player);
						Var.world.setPVP(true);
						return;
					}
				}
			}.runTaskTimer(Main.getInstance(), 20L, 20L);
		}

	}

	private static void teleportPlayersToLocation() {
		int i = 0;
		for (Player player : getPlayers()) {
			player.teleport(Var.playerLocations[i]);
			i++;
		}
	}

	private static void freezePlayers(Player player) {

		if (freezedPlayers.contains(player))
			freezedPlayers.remove(player);
		else
			freezedPlayers.add(player);

	}

	private static void makeChanges() {

		for (Player player : getPlayers()) {
			player.setGameMode(GameMode.SURVIVAL);
			player.setHealth(20);
			player.setFoodLevel(20);
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
		}
	}
}