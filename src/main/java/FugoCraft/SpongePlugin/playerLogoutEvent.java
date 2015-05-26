package FugoCraft.SpongePlugin;

import org.spongepowered.api.event.entity.player.PlayerQuitEvent;

public class playerLogoutEvent implements Runnable {

	private static FugoCraft_Main MClass;

	private static PlayerQuitEvent event;

	public static void set(FugoCraft_Main singleton) {
		MClass = singleton;
	}

	public static FugoCraft_Main get() {
		return MClass;
	}

	public void run() {
		get().getLogoutTimes().remove(event.getEntity().getUniqueId());
	}

	public static void eventCalled(PlayerQuitEvent eventObject) {
		event = eventObject;

		LogoutTimeHandler();
	}

	public static void LogoutTimeHandler() {
		get().getLogoutTimes().put(event.getEntity().getUniqueId(), System.currentTimeMillis());
		
		// Schedules the removal of the player's logout time after 5 second (100 ticks)
		get().getGame().getSyncScheduler().runTaskAfter(get(), new playerLogoutEvent(), 100);
	}
}
