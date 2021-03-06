package buttondevteam.perworld.main;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import buttondevteam.lib.TBMCCoreAPI;

public class MainPlugin extends JavaPlugin {
	
	
	public static MainPlugin plugin;
	public static volatile FileConfiguration config;
	
	public static volatile ConfigurationSection worlds;
	public static volatile ConfigurationSection players;
	public static long debugClock;
	
	
	public void onEnable(){
		
		//getServer().getPluginManager().registerEvents(new WorldLoadListener(this), this);
		TBMCCoreAPI.RegisterEventsForExceptions(new WorldChangeListener(this), this);
		TBMCCoreAPI.RegisterEventsForExceptions(new ListenerPlayerWorldChange(this), this);
		
		saveDefaultConfig();
		
		plugin = this;
		config = getConfig();
		worlds = config.getConfigurationSection("worlds");
		players = config.getConfigurationSection("players");
		
		if (!config.contains("worlds")) config.createSection("worlds");
		if (!config.contains("players")) config.createSection("players");
		saveConfig();
		
		new buttondevteam.perworld.cache.world.ShareSettings().initCache();
	}
}