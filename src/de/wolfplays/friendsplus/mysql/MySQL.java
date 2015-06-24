package de.wolfplays.friendsplus.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by WolfPlaysDE
 * On: 24.06.2015
 * At: 20:49:49
 * Project: FriendsPlus
 */
public class MySQL {

	private static String username;
	private static String passwort;
	private static String host;
	private static String port;
	private static String database;
	
	public static String TABEL_BANNEDPLAYERS = "BannedPlayers";
	
	private static Connection con;
	
	private static ExecutorService executor;
	
	static {
		executor = Executors.newCachedThreadPool();
	}
	
	public static void connect() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, passwort);
			if(isConnected()) {
				setup();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void diconnect() {
		try {
			if(isConnected()) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isConnected() {
		try {
			return con !=null && con.isValid(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private static void setup() {
		if(isConnected()) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						{
							String qry = "CREATE TABLE IF NOT EXISTS "+ MySQL.TABEL_BANNEDPLAYERS +" (id INT auto_increment, playername TEXT, uuid TEXT, end TEXT, reason TEXT, PRIMARY KEY(id))";
							PreparedStatement stmt;
							stmt = con.prepareStatement(qry);
							stmt.executeUpdate();
							stmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		MySQL.username = username;
	}

	public static String getPasswort() {
		return passwort;
	}

	public static void setPasswort(String passwort) {
		MySQL.passwort = passwort;
	}

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		MySQL.host = host;
	}

	public static String getPort() {
		return port;
	}

	public static void setPort(String port) {
		MySQL.port = port;
	}

	public static String getDatabase() {
		return database;
	}

	public static void setDatabase(String database) {
		MySQL.database = database;
	}

	public static Connection getConnection() {
		return con;
	}
	
}
