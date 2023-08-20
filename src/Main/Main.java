package Main;

import java.text.DecimalFormat;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import Classes.Server;
import Commands.Join;
import Commands.SetPlayer;
import Commands.SetWaterLevel;
import Commands.Start;
import Listeners.DisableAnimals;
import Listeners.PlayerBreakBlocks;
import Listeners.PlayerHunger;
import Listeners.PlayerMove;
import Listeners.PlayerQuit;

public class Main extends JavaPlugin{

	
	private static Plugin instance;
	
	@Override
	public void onEnable() {
		instance = this;
	
		if(!getConfig().contains("World"))
			getConfig().set("World", "world");
		
		getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
		getServer().getPluginManager().registerEvents(new PlayerBreakBlocks(this), this);
		//getServer().getPluginManager().registerEvents(new PlayerHit(), this);
		getServer().getPluginManager().registerEvents(new PlayerHunger(), this);
		getServer().getPluginManager().registerEvents(new PlayerMove(this), this);
		getServer().getPluginManager().registerEvents(new DisableAnimals(), this);
		
		getCommand("spleefjoin").setExecutor(new Join(this));
		getCommand("spleefstart").setExecutor(new Start(this));
		getCommand("spleefsetplayer").setExecutor(new SetPlayer(this));
		getCommand("spleefsetwaterlevel").setExecutor(new SetWaterLevel(this));
		
		saveConfig();
	}
	
	@Override
	public void onDisable() {
		Server.stopSpleef();
	}
	
	public static Plugin getInstance() {
		return instance;
	}
	
	public void setLocationInConfig(String label, double x, double y, double z, double yaw, double pitch) {
	    double[] nums = {x, y, z, yaw, pitch};

	    for (int i = 0; i < nums.length; i++) {
	        if (nums[i] < 0) {
	            nums[i] = -nums[i];  // Make negative numbers positive
	            nums[i] = Double.valueOf(new DecimalFormat("#.###").format(nums[i])); // Format positive numbers
	            nums[i] = -nums[i];  // Make positive numbers negative again
	        } else {
	            nums[i] = Double.valueOf(new DecimalFormat("#.###").format(nums[i])); // Format non-negative numbers
	        }
	    }

	    getConfig().set(label + ".x", nums[0]);
	    getConfig().set(label + ".y", nums[1]);
	    getConfig().set(label + ".z", nums[2]);
	    getConfig().set(label + ".yaw", nums[3]);
	    getConfig().set(label + ".pitch", nums[4]);

	    saveConfig();
	}
	
	public void setLocationInConfig(String label, double x, double y, double z) {
	    double[] nums = {x, y, z};

	    for (int i = 0; i < nums.length; i++) {
	        if (nums[i] < 0) {
	            nums[i] = -nums[i];  // Make negative numbers positive
	            nums[i] = Double.valueOf(new DecimalFormat("#.###").format(nums[i])); // Format positive numbers
	            nums[i] = -nums[i];  // Make positive numbers negative again
	        } else {
	            nums[i] = Double.valueOf(new DecimalFormat("#.###").format(nums[i])); // Format non-negative numbers
	        }
	    }

	    getConfig().set(label + ".x", nums[0]);
	    getConfig().set(label + ".y", nums[1]);
	    getConfig().set(label + ".z", nums[2]);

	    saveConfig();
	}
}
