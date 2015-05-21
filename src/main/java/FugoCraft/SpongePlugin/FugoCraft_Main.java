package FugoCraft.SpongePlugin;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.plugin.PluginManager;

import com.google.inject.Inject;

@Plugin(id = "fugocraftserver", name = "FugoCraft Serverside Plugin", version = "1.0")
public class FugoCraft_Main {
	// Variable defining for class-scope variables (also used in other classes to interact with ANYTHINg)
	@Inject
	private Logger logger;
	@Inject
	private Game game;
	@Inject
	private PluginManager pluginManager;
	
	public String PluginID = "fugocraftserver";
	
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
	
	@Subscribe
	public void onInit(PreInitializationEvent event) {
		
		// Giving the class instance setter a singleton of this class
		pluginInstanceSetter.set(this);
		
		// Telling the commandRegister class to register all the commands
		commandRegister.registerCommands();
	}

	@Subscribe
	public void OnStart(ServerStartedEvent event) {
		logger.info("FugoCraft Sponge serverside plugin loading...");

		logger.info("FugoCraft Sponge serverside plugin done loading!");
	}

	@Subscribe
	public void onStop(ServerStoppingEvent event) {
		logger.info("FugoCraft Sponge serverside plugin stopping...");

		logger.info("FugoCraft Sponge serverside plugin now stopped!");
	}
}