package iie;

import java.util.UUID;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import net.minecraft.server.v1_10_R1.IInventory;


public class WorldChangeListener implements Listener {
	
	PerWorldInventoryPlugin plugin;
	public WorldChangeListener(PerWorldInventoryPlugin plugin){
		this.plugin = plugin;
	}	
		
	@EventHandler(priority = EventPriority.MONITOR)
	public void onWorldChangeListener(PlayerChangedWorldEvent event){
		
		
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		String playerPath = ".players.(" + player.getName() + ")" + uuid.toString() + ".";
		
		World worldTo = player.getWorld();
		World worldFrom = event.getFrom();
		String worldToName = worldTo.getName();
		String worldFromName = worldFrom.getName();
		
		String pathTo = worldToName + playerPath;
		String pathFrom = worldFromName + playerPath;
		
		FileConfiguration config = plugin.getConfig();


		//----------------------------------------------------------------------instantiations			
		

		
		//----------------------------------------------------------------------INVENTORY


		final IInventory inventory = ((CraftInventory)player.getInventory()).getInventory();
		
		config.set(pathFrom + "inventory", SerializerDebug.serializeInventory(inventory, player));
		
		plugin.saveConfig();
		
		SerializerDebug.setInventoryFromSerialized(inventory, (String) config.get(pathTo + "inventory"), player);
		
		//player.getInventory().clear();
		//player.getInventory().setContents(
		//		SerializerDebug.InventoryFromString(invString, invInventory.getType())
		//		.getContents()
		//		);
		
		//----------------------------------------------------------------------ENDERCHEST
		
		
		final IInventory enderchest = ((CraftInventory)player.getEnderChest()).getInventory();
		
		config.set(pathFrom + "enderchest", SerializerDebug.serializeInventory(enderchest, player));
		
		plugin.saveConfig();
				
		SerializerDebug.setInventoryFromSerialized(enderchest, (String) config.get(pathTo + "enderchest"), player);
		
		//player.getEnderChest().clear();
		//player.getEnderChest().setContents(
		//		SerializerDebug.InventoryFromString(invString, invInventory.getType())
		//		.getContents()
		//		);
		
		//----------------------------------------------------------------------PLAYER DATA
		
		
		config.set(pathFrom + "status", PlayerData.Position.StringFromLocation(player));
		
		
		
		
		plugin.saveConfig();
	}
}
