package FugoCraft.SpongePlugin.commandExecutors;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.data.manipulator.entity.*;

import FugoCraft.SpongePlugin.FugoCraft_Main;

public class commandExeFeed implements CommandExecutor {

	private static FugoCraft_Main MClass;

	public static void set(FugoCraft_Main singleton) {
		MClass = singleton;
	}

	public static FugoCraft_Main get() {
		return MClass;
	}

	@SuppressWarnings("null")
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {

		// Store the command target
		Player target = args.<Player> getOne("target").get();
		
		// Creating and initializing a FoodData object TODO figure out how to do this without getting a NPE
	    FoodData foodData = null;
	    
		foodData.setExhaustion(0);
		foodData.setFoodLevel(10);
		foodData.setSaturation(5);

		// Looping through all of the player's available datamanipulators
		for (int i = 0; i < target.getManipulators().size(); i++) {
			if (target.getManipulators().toArray()[i] instanceof FoodData) {
				foodData = (FoodData) target.getManipulators().toArray()[i];
			}
		}

		// Stores the target's food and saturation levels before they are fed
		double HungerBefore = foodData.getFoodLevel();
		double SaturationBefore = foodData.getSaturation();

		// Heal the target
		// This is done by getting the FoodData object (done ^), modifying it
		// and then using the offer(FoodData) method to insert it back into the
		// player object. this will be mostly error-safe
		foodData.setFoodLevel(20).setSaturation(20).setExhaustion(0);
		target.offer(foodData);

		// Store the target's food and saturation levels after they have been
		// fed
		double HungerAfter = foodData.getFoodLevel();
		double SaturationAfter = foodData.getSaturation();

		if (src instanceof Player) {
			// Store the source as a player
			Player source = (Player) src;

			// Tells the target that they have been fed
			target.sendMessage(Texts
					.of(TextColors.GREEN, "You have been fed by ")
					.builder()
					.append(Texts.of(TextColors.GOLD, source
							.getDisplayNameData().getDisplayName()))
					.append(Texts.of(TextColors.GREEN, " from "))
					.append(Texts.of(TextColors.RED, HungerBefore))
					.append(Texts.of(TextColors.GREEN, "hunger to "))
					.append(Texts.of(TextColors.RED, HungerAfter))
					.append(Texts.of(TextColors.GREEN, "hunger!")).build());

			// Tells the player that they have fed the target
			source.sendMessage(Texts
					.of(TextColors.GREEN, "You have fed ")
					.builder()
					.append(Texts.of(TextColors.GOLD, target
							.getDisplayNameData().getDisplayName()))
					.append(Texts.of(TextColors.GREEN, " from "))
					.append(Texts.of(TextColors.RED, HungerBefore))
					.append(Texts.of(TextColors.GREEN, "hunger to "))
					.append(Texts.of(TextColors.RED, HungerAfter))
					.append(Texts.of(TextColors.GREEN, "hunger!")).build());

		} else {
			// Tells the target that they have been fed by
			// "an unknown source of great power"
			target.sendMessage(Texts.of(TextColors.GREEN,
					"You have been fed by an unknown source of great power!"));

			src.sendMessage(Texts.of("You have fed "
					+ target.getDisplayNameData().getDisplayName() + " from "
					+ HungerBefore + "hunger and " + SaturationBefore
					+ " saturation to " + HungerAfter + "hunger and "
					+ SaturationAfter + " saturation."));

			if (!(src instanceof ConsoleSource)) {
				get().logger.info("A non-player object has fed "
						+ target.getName() + " from " + HungerBefore
						+ "hunger and " + SaturationBefore + " saturation to "
						+ HungerAfter + "hunger and " + SaturationAfter
						+ " saturation.");
			}
		}
		return CommandResult.success();
	}
}
