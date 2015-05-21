package FugoCraft.SpongePlugin.commandExecutors;

import FugoCraft.SpongePlugin.FugoCraft_Main;

public class commandExeMobattack {
	
	private static FugoCraft_Main MClass;
	
	public static void set(FugoCraft_Main singleton) {
		MClass = singleton;
	}
	
	public static FugoCraft_Main get() {
		return MClass;
	}
	
}
