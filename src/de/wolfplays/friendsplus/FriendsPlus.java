package de.wolfplays.friendsplus;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by WolfPlaysDE
 * On: 24.06.2015
 * At: 19:55:19
 * Project: FriendsPlus
 */
public class FriendsPlus extends JavaPlugin {

	private static FriendsPlus instance;
	private ConsoleCommandSender console;
	
	@Override
	public void onLoad() {
		instance = this;
		console = getServer().getConsoleSender();
	}
	
	@Override
	public void onEnable() {
		console.sendMessage("");
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static FriendsPlus getInstance() {
		return instance;
	}
	
}
