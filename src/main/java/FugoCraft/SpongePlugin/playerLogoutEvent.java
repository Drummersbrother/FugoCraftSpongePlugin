package FugoCraft.SpongePlugin;

import java.util.UUID;

import org.spongepowered.api.event.entity.player.PlayerQuitEvent;

public class playerLogoutEvent implements Runnable {

	private static FugoCraft_Main MClass;

	private static PlayerQuitEvent event;
	
	private UUID taskUUID; 

	public playerLogoutEvent(UUID uniqueId) {
		taskUUID = uniqueId;
	}

	public static void set(FugoCraft_Main singleton) {
		MClass = singleton;
	}

	public static FugoCraft_Main get() {
		return MClass;
	}
	
	// This is the method to use in the task builder
	public void run() {
		get().getLogoutTimes().remove(taskUUID);
	}
	
	// This is called when the corresponding event is called
	public static void eventCalled(PlayerQuitEvent eventObject) {
		event = eventObject;

		LogoutTimeHandler();
	}

	public static void LogoutTimeHandler() {
		get().getLogoutTimes().put(event.getEntity().getUniqueId(), System.currentTimeMillis());
		
		// Schedules the removal of the player's logout time after 5 seconds (Asynchronously)
		get().getGame().getScheduler().getTaskBuilder().async().delay(5000).execute(new playerLogoutEvent(event.getEntity().getUniqueId()));
	}
}
