package de.wolfplays.friendsplus;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import de.wolfplays.friendsplus.util.PluginLogger;
import de.wolfplays.friendsplus.util.RegisterManager;

/**
 * Created by WolfPlaysDE
 * On: 24.06.2015
 * At: 19:55:19
 * Project: FriendsPlus
 */
public class FriendsPlus extends JavaPlugin {

	private static FriendsPlus instance;
	private ConsoleCommandSender console;
	
	private static PluginLogger logger;
	private static RegisterManager<FriendsPlus> register;
	
	@Override
	public void onLoad() {
		instance = this;
		console = getServer().getConsoleSender();
		logger = new PluginLogger(getDataFolder(), "log.txt");
		register = new RegisterManager<FriendsPlus>(this);
	}
	
	@Override
	public void onEnable() {
		console.sendMessage("§1=====FrindsPlus======");
		console.sendMessage("Plugin: " + getServer().getPluginManager().isPluginEnabled(this) != null ? "§asuccessfully loaded" : "§cfailed to load");
		console.sendMessage("§1=====================");
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static FriendsPlus getInstance() {
		return instance;
	}
	
	public PluginLogger getPluginLogger() {
		return logger;
	}
	
}
