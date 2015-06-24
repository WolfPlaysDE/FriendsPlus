package de.wolfplays.friendsplus.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class RegisterManager<P extends Plugin> {

	private static String VERSION;
	
	static {
		String path = Bukkit.getServer().getClass().getPackage().getName();
		VERSION = path.substring(path.lastIndexOf(".") +1, path.length());
	}
	
	private P plugin;
	
	public RegisterManager(P plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Register events of the Bukkit/Spigot API 
	 * 
	 * @param classes of the Listener
	 */
	public void registerEvents(Class<?>... classes) {
		for(Class<?> clazz : classes) {
			boolean isConstructor = false;
			try {
				clazz.getConstructor(new Class[] { plugin.getClass() });
				isConstructor = true;
			}catch(NoSuchMethodException nsmex) {
				isConstructor = false;
			}
			try {
				Listener listener = null;
				if(isConstructor) {
					Constructor<?> cww = clazz.getConstructor(new Class[] { plugin.getClass() });
					listener = (Listener) cww.newInstance(new Object[] { plugin });
				} else {
					listener = (Listener) clazz.newInstance();
				}
				Bukkit.getPluginManager().registerEvents(listener, plugin);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Register a new Command of the Bukkit/Spigot API
	 * 
	 * @param name the name of the command
	 * @param description the description of the command
	 * @param commandClass the command Class
	 * @param aliases for the command
	 */
	public void registerCommand(String name, String description, CommandExecutor commandClass, String... aliases) {
		try {
			DynCommand dynCmd = new DynCommand(name, description, commandClass, aliases);
			Class<?> craftServerClass = Class.forName("org.bukkit.craftbukkit." + VERSION + ".CraftServer");
			Field field = craftServerClass.getDeclaredField("commandMap");
			field.setAccessible(true);
			CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());
			commandMap.register(plugin.getName(), dynCmd);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private class DynCommand extends Command {

		private CommandExecutor exec;
		
		protected DynCommand(String name, String description, CommandExecutor exec, String... aliases) {
			super(name);
			this.exec = exec;
			super.setDescription(description);
			super.setAliases(Arrays.asList(aliases));
		}

		@Override
		public boolean execute(CommandSender cs, String label, String[] args) {
			exec.onCommand(cs, this, label, args);
			return false;
		}
		
	}
	
}