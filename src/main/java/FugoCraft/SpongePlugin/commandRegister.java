package FugoCraft.SpongePlugin;

import org.spongepowered.api.text.Texts;
import FugoCraft.SpongePlugin.commandExecutors.*;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;

public class commandRegister {

	private static FugoCraft_Main MClass;

	public static void set(FugoCraft_Main singleton) {
		MClass = singleton;
	}

	public static FugoCraft_Main get() {
		return MClass;
	}
	
	public static void registerCommands(){
		// TODO when registering commands, put the method call here
		commandHealReg();
		commandFeedReg();
		
	}
	
	public static void commandHealReg() {

		CommandSpec healCommandSpec = CommandSpec
				.builder()
				.description(Texts.of("Heals a target player."))
				.extendedDescription(
						Texts.of(" If no target player is specified it will heal the commmand excecutor."))
				.executor(new commandExeHeal())
				.permission("fugocraft.command.heal")
				.arguments(
						GenericArguments.onlyOne(GenericArguments
								.playerOrSource(Texts.of("target"), get().getGame())))
				.build();

		get().getGame().getCommandDispatcher()
				.register(
						get().getPluginContainer().getInstance(), healCommandSpec, "heal");
	}
	
	public static void commandFeedReg() {

		CommandSpec feedCommandSpec = CommandSpec
				.builder()
				.description(Texts.of("Feeds a target player."))
				.extendedDescription(
						Texts.of(" If no target player is specified it will feed the commmand excecutor."))
				.executor(new commandExeFeed())
				.permission("fugocraft.command.feed")
				.arguments(
						GenericArguments.onlyOne(GenericArguments
								.playerOrSource(Texts.of("target"), get().getGame())))
				.build();

		get().getGame().getCommandDispatcher()
				.register(
						get().getPluginContainer().getInstance(), feedCommandSpec, "feed");
	}
}