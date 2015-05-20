package FugoCraft.SpongePlugin;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class commandExeHeal implements CommandExecutor {

	private static FugoCraft_Main MClass;

	public static void set(FugoCraft_Main singleton) {
		MClass = singleton;
	}

	public static FugoCraft_Main get() {
		return MClass;
	}

	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {

		// Store the command target
		Player target = args.<Player> getOne("target").get();

		// Store the target's hp before they are healed
		double HealthBefore = target.getHealthData().getHealth();

		// Heal the target
		target.getHealthData().setHealth(target.getHealthData().getMaxHealth());

		// Store the target's hp after they are healed
		double HealthAfter = target.getHealthData().getHealth();

		if (src instanceof Player) {
			// Store the source as a player
			Player source = (Player) src;

			// Tells the target that they have been healed
			target.sendMessage(Texts
					.of(TextColors.GREEN, "You have been healed by ")
					.builder()
					.append(Texts.of(TextColors.GOLD, source
							.getDisplayNameData().getDisplayName()))
					.append(Texts.of(TextColors.GREEN, " from "))
					.append(Texts.of(TextColors.RED, HealthBefore))
					.append(Texts.of(TextColors.GREEN, "hp to "))
					.append(Texts.of(TextColors.RED, HealthAfter))
					.append(Texts.of(TextColors.GREEN, "hp!")).build());

			// Tells the player that they have healed the target
			source.sendMessage(Texts
					.of(TextColors.GREEN, "You have healed ")
					.builder()
					.append(Texts.of(TextColors.GOLD, target
							.getDisplayNameData().getDisplayName()))
					.append(Texts.of(TextColors.GREEN, " from "))
					.append(Texts.of(TextColors.RED, HealthBefore))
					.append(Texts.of(TextColors.GREEN, "hp to "))
					.append(Texts.of(TextColors.RED, HealthAfter))
					.append(Texts.of(TextColors.GREEN, "hp!")).build());

		} else {
			// Tells the target that they have been healed by
			// "an unknown source of great power"
			target.sendMessage(Texts
					.of(TextColors.GREEN,
							"You have been healed by an unknown source of great power!"));

			src.sendMessage(Texts.of("You have healed "
					+ target.getDisplayNameData().getDisplayName() + " from "
					+ HealthBefore + "hp to " + HealthAfter + "hp."));
			if (!(src instanceof ConsoleSource)) {
				get().logger.info(
						"A non-player object has healed " + target.getName()
								+ " from " + HealthBefore + "hp to "
								+ HealthAfter + "hp.");
			}
		}
		return CommandResult.success();
	}
}
