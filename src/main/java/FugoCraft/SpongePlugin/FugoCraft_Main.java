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
	public Logger logger;
	@Inject
	public Game game;
	@Inject
	public PluginManager pluginManager;
	
	public String PluginID = "fugocraftserver";

	public PluginManager getPluginManager() {
		return pluginManager;
	}
	
	public PluginContainer getPluginContainer() {
		return pluginManager.getPlugin(PluginID).orNull();
	}
	@Subscribe
	public void onInit(PreInitializationEvent event) {
		// TODO -> start plugin: load config, assign variables
		commandRegister.set(this);
		commandExeHeal.set(this);
		commandExeFeed.set(this);
		commandRegister.commandHealReg();
	}

	@Subscribe
	public void OnStart(ServerStartedEvent event) {
		logger.info("FugoCraft Sponge serverside plugin loading...");

		logger.info("FugoCraft Sponge serverside plugin done loading!");
	}

	@Subscribe
	public void onStop(ServerStoppingEvent event) {
		// TODO -> stop plugin: save config (if changed), clean up
		logger.info("FugoCraft Sponge serverside plugin stopping...");

		logger.info("FugoCraft Sponge serverside plugin now stopped!");
	}
}