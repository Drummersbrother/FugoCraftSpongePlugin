package FugoCraft.SpongePlugin;

import com.google.inject.Inject;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.entity.player.PlayerQuitEvent;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.plugin.PluginManager;
import org.spongepowered.api.service.config.DefaultConfig;
import org.spongepowered.api.service.sql.SqlService;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

//@Plugin(id = "fugocraftserver", name = "FugoCraft Serverside Plugin", version = "1.0")
public class FugoCraft_Main {

	@Inject
	private Logger logger;
	@Inject
	private Game game;
	@Inject
	private PluginManager pluginManager;
	@Inject
	@DefaultConfig(sharedRoot = true)
	private File defaultConfig;
	@Inject
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> configManager;

	private CommentedConfigurationNode config;

	private String PluginID = "fugocraftserver";

	private HashMap<UUID, Long> LogoutTime = new HashMap<UUID, Long>();

	// This part is for default config values
	private double defRelogCooldown = 5;
	private String defRelogCooldownComment = "Relog cooldown in seconds";
	private boolean defUseRelogCooldown = true;
	private String defUseRelogCooldownComment = "Use relog cooldown? (Prevents logging out and then logging in within a number of seconds)";
	private boolean defUseDB = false;
	private String defUseDBComment = "Use an sql database? (If false , or if the provided URL is invalid this might affect behaviour of various commands)";
	private String defDBURL = "";
	private String defDBURLComment = "URL to the sql database that is used, format is: jdbc:<engine>://[<username>[:<password>]@]<host>";
	private String defDBDB = "";
	private String defDBDBComment = "The database in the sql database to use (remember to give the user specified in the database URL full access to this database). This is where FugoCraft_Sponge_ServerSide_Plugin will put all tables and data";

	private static FugoCraft_Main instance;

	// Some getters and useful stuff like that
	public String getPluginID() {
		return PluginID;
	}

	public Game getGame() {
		return game;
	}

	public Logger getLogger() {
		return logger;
	}

	public PluginManager getPluginManager() {
		return pluginManager;
	}

	public PluginContainer getPluginContainer() {
		return pluginManager.getPlugin(PluginID).orNull();
	}

	public HashMap<UUID, Long> getLogoutTimes() {
		return LogoutTime;
	}

	public ConfigurationNode getConfig() {
		return config;
	}

	public ConfigurationLoader<CommentedConfigurationNode> getConfManager() {
		return configManager;
	}

	@Subscribe
	public void onPluginInit(InitializationEvent evt) {
		instance = this;
	}

	public static FugoCraft_Main getInstance() {
		return instance;
	}

	@Subscribe
	public void onInit(InitializationEvent event) {
		// Logging that we have started loading
		logger.info("FugoCraft Sponge serverside plugin loading...");

		// Logging that we are setting up the configuration file
		logger.info("Now loading configuration files...");

		// Setting up the config file
		try {
			// Checking if the config exists
			if (!defaultConfig.exists()) {
				defaultConfig.createNewFile();

				config = configManager.load();

				// Setting up default values for the config
				fixConfig(config);

				// Saving the values
				configManager.save(config);

			}

			// If the config exists then it just loads it
			config = configManager.load();

			// Making sure that all values exist
			fixConfig(config);

		} catch (IOException e) {
			logger.error("CONFIG FILE CREATION FAILED! THIS MAY CAUSE INSTABILITY WITH SOME FEATURES AND COMMANDS!");
			e.printStackTrace();
		}
		// Logging that we are done loading configuration files
		logger.info("Done loading the configuration files.");

		// Telling the commandRegister class to register all the commands
		if (getUseDB()) {
			try {
				Connection conn = getDBConn();
				fixDB(conn);
				commandRegister.registerCommands(true);
				logger.info("Registered commands with: UseDB=true");
				conn.close();
			} catch (SQLException e) {
				commandRegister.registerCommands(false);
				logger.error("Registered commands with: UseDB=false, because the DB URL did not return a connection or we got an error when trying to check the table validity.");
			}
		} else {
			commandRegister.registerCommands(false);
			logger.warn("Registered command with: UseDB=false, this may cause some command to be disabled or limited.");
		}

		// Logging that we have finished loading
		logger.info("FugoCraft Sponge serverside plugin done loading!");
	}
	
	/** Creates all tables that should exist for the DB to be used properly in this plugin. This will NOT close passed connections
	 * @throws SQLException **/
	public void fixDB(Connection conn) throws SQLException {
		// This method checks that all tables that should exist, exist.
		Statement stmt = null;
		String query;
		
		
		String[][] tablesToCheck = {{"cmdtestdb"}, {"name VARCHAR(16) NOT NULL, PRIMARY KEY (name)"}};
		
		query = "SHOW TABLES;";
		stmt = conn.createStatement();
		
		ResultSet tables = stmt.executeQuery(query);
		
		if (!tables.first()) {
			Statement stmt2 = conn.createStatement();
			ResultSet setFirstDB = stmt2.executeQuery("CREATE TABLE " + tablesToCheck[0][0] + "(" + tablesToCheck[1][0] + ");");
		}
		
		for (int i = 0; i < tablesToCheck[0].length; i++) {
			tables.beforeFirst();
			boolean hasFound = false; 
			while (tables.next()) {
				if (tables.getString(1).equals(tablesToCheck[0][i])) {
					hasFound = true;
					break;
				}
			}
			
			if (!hasFound) {
				Statement stmt2 = conn.createStatement();
				ResultSet setFirstDB = stmt2.executeQuery("CREATE TABLE " + tablesToCheck[0][i] + "(" + tablesToCheck[1][i] + ");");
			}
			
		}
	}

	private SqlService sql;

	public Connection getDBConn() throws SQLException {
		if (sql == null) {
			sql = game.getServiceManager().provide(SqlService.class).get();
		}
		return sql.getDataSource(getDBURL()).getConnection();
	}

	@Subscribe
	public void OnStart(ServerStartedEvent event) {
	}

	@Subscribe
	public void onStop(ServerStoppingEvent event) {
		logger.info("FugoCraft Sponge serverside plugin stopping...");

		logger.info("FugoCraft Sponge serverside plugin now stopped!");
	}

	@Subscribe
	public void onPlayerJoin(PlayerJoinEvent event) {
		playerJoinEvent.eventCalled(event);
	}

	@Subscribe
	public void onPlayerQuit(PlayerQuitEvent event) {
		playerLogoutEvent.eventCalled(event);
	}

	public double getRelogTimeLimit() {
		return config.getNode("Relog cooldown").getDouble();
	}

	public boolean getUseDB() {
		return config.getNode("Database Options:", "Use Database?").getBoolean();
	}

	/**
	 * This method return the JDBC formatted SQL database url that is specified
	 * in the config file. This URL may be invalid. Supported SQL database types
	 * are: MySQL and H2
	 **/
	public String getDBURL() {
		return config.getNode("Database Options:", "Database URL").getString() + "/" + config.getNode("Database Options:", "Database Database Name").getString();
	}

	// This is used to check if all config values that should exist exists and
	// if not then it sets them to default
	public void fixConfig(CommentedConfigurationNode Config) {

		// Checking and fixing the "Use relog cooldown?" entry
		if ((Object) Config.getNode("Use relog cooldown?").getBoolean() == null
				|| Config.getNode("Use relog cooldown?").isVirtual()) {
			Config.getNode("Use relog cooldown?").setValue(defUseRelogCooldown);
			Config.getNode("Use relog cooldown?").setComment(defUseRelogCooldownComment);
		}

		// Checking and fixing the "Relog cooldown" entry
		if ((Object) Config.getNode("Relog cooldown").getDouble() == null
				|| Config.getNode("Relog cooldown").isVirtual()) {
			Config.getNode("Relog cooldown").setValue(defRelogCooldown);
			Config.getNode("Relog cooldown").setComment(defRelogCooldownComment);
		}

		// Checking and fixing the "Use database?" entry
		if ((Object) Config.getNode("Database Options:", "Use Database?").getBoolean() == null
				|| Config.getNode("Database Options:", "Use Database?").isVirtual()) {
			Config.getNode("Database Options:", "Use Database?").setValue(defUseDB);
			Config.getNode("Database Options:", "Use Database?").setComment(defUseDBComment);

		}

		// Checking and fixing the "Database URL" entry
		if (Config.getNode("Database Options:", "Database URL").isVirtual()) {
			Config.getNode("Database Options:", "Database URL").setValue(defDBURL);
			Config.getNode("Database Options:", "Database URL").setComment(defDBURLComment);
		}
		
		// Checking and fixing the "Database Database Name" entry
		if (Config.getNode("Database Options:", "Database Database Name").isVirtual()) {
			Config.getNode("Database Options:", "Database Database Name").setValue(defDBDB);
			Config.getNode("Database Options:", "Database Database Name").setComment(defDBDBComment);
		}

		try {
			configManager.save(Config);
		} catch (IOException e) {
			logger.error("Got error when trying to save config file! See stacktrace for more info");
			e.printStackTrace();
		}
	}

	public boolean relConf() {
		try {
			config = configManager.load();
			fixConfig(config);

			return true;
		} catch (IOException e) {
			logger.error("Failed to reload config, check stacktrace for more info!");
			e.printStackTrace();
			return false;
		}
	}
}
