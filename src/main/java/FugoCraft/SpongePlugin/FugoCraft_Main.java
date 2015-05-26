package FugoCraft.SpongePlugin;

import java.util.HashMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.entity.player.PlayerQuitEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.plugin.PluginManager;

import FugoCraft.SpongePlugin.commandExecutors.commandExeFeed;
import FugoCraft.SpongePlugin.commandExecutors.commandExeHeal;
import FugoCraft.SpongePlugin.commandExecutors.commandExeInvEdit;
import FugoCraft.SpongePlugin.commandExecutors.commandExeInvSee;
import FugoCraft.SpongePlugin.commandExecutors.commandExeInvSubmit;
import FugoCraft.SpongePlugin.commandExecutors.commandExeMobattack;

import com.google.inject.Inject;

@Plugin(id = "fugocraftserver", name = "FugoCraft Serverside Plugin", version = "1.0")
public class FugoCraft_Main {
	
	@Inject
	private Logger logger;
	@Inject
	private Game game;
	@Inject
	private PluginManager pluginManager;
	
	private String PluginID = "fugocraftserver";
	
	private HashMap<UUID, Long> LogoutTime= new HashMap<UUID, Long>();
	
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
	
	@Subscribe
	public void onInit(PreInitializationEvent event) {
		// Logging that we have started loading
		logger.info("FugoCraft Sponge serverside plugin loading...");
		
		// Giving the classes a singleton of this class
		commandRegister.set(this);
		commandExeFeed.set(this);
		commandExeHeal.set(this);
		commandExeMobattack.set(this);
		commandExeInvSubmit.set(this);
		commandExeInvEdit.set(this);
		commandExeInvSee.set(this);
		playerJoinEvent.set(this);
		playerLogoutEvent.set(this);
		
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
	
	@Subscribe
	public void onPlayerJoin(PlayerJoinEvent event) {
		playerJoinEvent.eventCalled(event);
	}
	
	@Subscribe
	public void onPlayerQuit(PlayerQuitEvent event) {
		playerLogoutEvent.eventCalled(event);
	}
}