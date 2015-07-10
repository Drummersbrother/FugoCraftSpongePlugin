package FugoCraft.SpongePlugin.commandExecutors;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import FugoCraft.SpongePlugin.FugoCraft_Main;

public class commandExePing implements CommandExecutor {

	private static FugoCraft_Main MClass;

	public static void set(FugoCraft_Main singleton) {
		MClass = singleton;
	}

	public static FugoCraft_Main get() {
		return MClass;
	}

	// Gives the player information about another player's ping
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {

		// Storing the target
		Player target = args.<Player> getOne("target").get();

		if (src instanceof Player) {

			// Storing the source
			Player source = (Player) src;

			// Storing the target's ping
			int targetPing = target.getConnection().getPing();

			// Telling the console about the command execution
			get().getLogger().info(
					source.getName() + " has used /ping on " + target.getName()
							+ ", the ping was " + targetPing);

			// Giving the source the info
			source.sendMessage(Texts.of(TextColors.GOLD, target.getName())
					.builder()
					.append(Texts.of(TextColors.GREEN, "'s ping is "))
					.append(Texts.of(TextColors.RED, targetPing))
					.append(Texts.of(TextColors.GREEN, " ms.")).build());

		} else {

			// Tell the console that it obviously has 0 ping, because it is the server

			get().getLogger()
					.info("You, the console, obviously have 0 ping, because YOU ARE the server. SO why even use the /ping command?");

		}

		return CommandResult.success();

	}
}
