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

	public static void commandHealReg() {

		CommandSpec healCommandSpec = CommandSpec
				.builder()
				.setDescription(Texts.of("Heals a target player."))
				.setExtendedDescription(
						Texts.of(" If no target player is specified it will heal the commmand excecutor."))
				.setExecutor(new commandExeHeal())
				.setArguments(
						GenericArguments.onlyOne(GenericArguments
								.playerOrSource(Texts.of("target"), get().game)))
				.build();

		get().game.getCommandDispatcher()
				.register(
						get().getPluginContainer().getInstance(), healCommandSpec, "heal");
	}
	
	public static void commandFeedReg() {

		CommandSpec feedCommandSpec = CommandSpec
				.builder()
				.setDescription(Texts.of("Feeds a target player."))
				.setExtendedDescription(
						Texts.of(" If no target player is specified it will feed the commmand excecutor."))
				.setExecutor(new commandExeFeed())
				.setArguments(
						GenericArguments.onlyOne(GenericArguments
								.playerOrSource(Texts.of("target"), get().game)))
				.build();

		get().game.getCommandDispatcher()
				.register(
						get().getPluginContainer().getInstance(), feedCommandSpec, "heal");
	}
}