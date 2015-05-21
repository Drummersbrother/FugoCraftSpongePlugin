package FugoCraft.SpongePlugin;

import FugoCraft.SpongePlugin.commandExecutors.*;
public class pluginInstanceSetter {
	
	private static FugoCraft_Main MSingleton;
	
	public static void set(FugoCraft_Main singleton) {
		MSingleton = singleton;
	}
	
	public static void passSingleton() {
		commandRegister.set(MSingleton);
		commandExeFeed.set(MSingleton);
		commandExeHeal.set(MSingleton);
		commandExeMobattack.set(MSingleton);
	}
	
}
