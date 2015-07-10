package FugoCraft.SpongePlugin;

import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.text.Texts;

public class playerJoinEvent {

	private static FugoCraft_Main MClass;

	private static PlayerJoinEvent event;

	public static void set(FugoCraft_Main singleton) {
		MClass = singleton;
	}

	public static FugoCraft_Main get() {
		return MClass;
	}

	// This method should be called from an event subscriber in the main class
	public static void eventCalled(PlayerJoinEvent EventObject) {
		event = EventObject;

		kickOnJoin();
	}

	public static void kickOnJoin() {
		get().getLogger().info("A player logged in");
		// Checking if there is logout time data for this player
		if (get().getLogoutTimes().containsKey(event.getUser().getUniqueId())) {
			if (System.currentTimeMillis() - get().getLogoutTimes().get(event.getUser().getUniqueId()) < (get().getRelogTimeLimit() * 1000)) {
				event.getEntity()
					.kick(Texts
						.of("You can not log back into the server that quickly! (must at least be " + get().getRelogTimeLimit() + " seconds between log off and log in)"));
				get().getLogger()
						.info("A player has tried to join the server too soon after logging out!");
				get().getLogger().info("DEBUG: " + (System.currentTimeMillis() - get().getLogoutTimes().get(event.getUser().getUniqueId())) + "!");
			} else {
				get().getLogoutTimes().remove(event.getUser().getUniqueId());
			}
		}
	}
}
