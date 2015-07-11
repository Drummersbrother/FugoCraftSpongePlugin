package FugoCraft.SpongePlugin;

import java.util.UUID;

import org.spongepowered.api.event.entity.player.PlayerQuitEvent;

public class playerLogoutEvent implements Runnable {

	private static PlayerQuitEvent event;
	
	private UUID taskUUID; 

	public playerLogoutEvent(UUID uniqueId) {
		taskUUID = uniqueId;
	}

	public static FugoCraft_Main get() {
		return FugoCraft_Main.getInstance();
	}
	
	// This is the method to use in the task builder
	public void run() {
		get().getLogoutTimes().remove(taskUUID);
	}
	
	// This is called when the corresponding event is called
	public static void eventCalled(PlayerQuitEvent eventObject) {
		event = eventObject;
		
		if (get().getConfig().getNode("Use relog cooldown?").getBoolean()) {
		LogoutTimeHandler();
		}
	}

	public static void LogoutTimeHandler() {
		get().getLogoutTimes().put(event.getEntity().getUniqueId(), System.currentTimeMillis());
		
		// Schedules the removal of the player's logout time after X seconds (Time limit specified in the configuration file) (Asynchronously)
		get().getGame().getScheduler().getTaskBuilder().async().delay((long) (get().getRelogTimeLimit() * 1000)).execute(new playerLogoutEvent(event.getEntity().getUniqueId()));
	}
}
