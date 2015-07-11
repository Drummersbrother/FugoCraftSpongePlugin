package FugoCraft.SpongePlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.entity.player.PlayerQuitEvent;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.plugin.PluginManager;
import org.spongepowered.api.service.config.DefaultConfig;

import com.google.inject.Inject;

@Plugin(id = "fugocraftserver", name = "FugoCraft Serverside Plugin", version = "1.0")
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
	
	private ConfigurationNode config;
	
	private String PluginID = "fugocraftserver";
	
	private HashMap<UUID, Long> LogoutTime= new HashMap<UUID, Long>();
	
	
	// This part is for default config values
	private double defRelogCooldown = 5;
	private boolean defuseRelogCooldown = true;
	
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
	public void onInit(PreInitializationEvent event) {
		// Logging that we have started loading
		logger.info("FugoCraft Sponge serverside plugin loading...");
		
		// Setting up the config file
		try{
			// Checking if the config exists
			if (!defaultConfig.exists()) {
			defaultConfig.createNewFile();
			
			config = configManager.load();
			
			// Setting up default values for the config
			config.getNode("Use relog cooldown?").setValue(defuseRelogCooldown);
			config.getNode("Relog cooldown").setValue(defRelogCooldown);
			
			// Saving the values
			configManager.save(config);
			
			}
			
			// If the config exists then it just loads it
			config = configManager.load();
			
			// Making sure that all values exist
			fixConfig(config);
			
		} catch (IOException e){
			logger.error("CONFIG FILE CREATION FAILED! THIS MAY CAUSE INSTABILITY WITH SOME FEATURES AND COMMANDS!");
			e.printStackTrace();
		}
		
		// Telling the commandRegister class to register all the commands
		commandRegister.registerCommands();
		
		// Logging that we have finished loading
		logger.info("FugoCraft Sponge serverside plugin done loading!");
	}

	@Subscribe
	public void OnStart(ServerStartedEvent event) {
	}

	@Subscribe
	public void onStop(ServerStoppingEvent event) {
		logger.info("FugoCraft Sponge serverside plugin stopping...");

		logger.info("FugoCraft Sponge serverside plugin now stopped!");
	}
	
	private static FugoCraft_Main instance;
	
	@Subscribe
	public void onPluginInit(InitializationEvent evt) {
	     instance = this;
	}

	public static FugoCraft_Main getInstance() {
	    return instance;
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
	
	// This is used to check if all config values that should exist exists and if not then it sets them to default
	private void fixConfig(ConfigurationNode fixConfig) {
		
		// Checking and fixing the "Use relog cooldown?" entry
		if ((Object) config.getNode("Use relog cooldown?").getBoolean() == null || config.getNode("Use relog cooldown?").isVirtual()) {
			config.getNode("Use relog cooldown?").setValue(defuseRelogCooldown);
		}
		
		// Checking and fixing the "Relog cooldown" entry
		if ((Object) config.getNode("Relog cooldown").getDouble() == null || config.getNode("Relog cooldown").isVirtual()) {
			config.getNode("Relog cooldown").setValue(defRelogCooldown);
		}
	}
	
	public boolean relConf() {
		try {
			config = configManager.load();
			
			return true;
		} catch (IOException e) {
			logger.error("Failed to reload config, check stacktrace for more info!");
			e.printStackTrace();
			return false;
		}
	}
}