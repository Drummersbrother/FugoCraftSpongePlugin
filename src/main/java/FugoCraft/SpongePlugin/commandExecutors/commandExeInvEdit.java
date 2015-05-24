package FugoCraft.SpongePlugin.commandExecutors;

import FugoCraft.SpongePlugin.DataObject.InvEditData;
import FugoCraft.SpongePlugin.Helpers.PlayerHelpers;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import FugoCraft.SpongePlugin.FugoCraft_Main;

public class commandExeInvEdit implements CommandExecutor {

	private static FugoCraft_Main MClass;

	private static InvEditData commandData = new InvEditData();

	public static void set(FugoCraft_Main singleton) {
		MClass = singleton;
	}

	public static FugoCraft_Main get() {
		return MClass;
	}

	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		// Checking if the source is a Player or not
		// TODO Add check to see if src has already used this command
		if (src instanceof Player) {

			// Storing the source as a Player
			Player source = (Player) src;
			// Storing the target as a Player
			Player target = args.<Player> getOne("target").get();

			// Checking if the player has already called this command and has
			// submitted the inventory to the previous target
			if (commandData.haveUUIDCalled(source.getUniqueId())) {

				source.sendMessage(Texts
						.of(TextColors.RED,
								"You already have another player's inventory! Use /invsubmit to be able to use /invedit again."));
				return CommandResult.empty();
			} else {

				// Storing all the data that needs to be stored
				commandData.UUIDCalled(source.getUniqueId());
				commandData.setPrevInventory(source.getUniqueId(),
						(Inventory) source.getInventory());
				commandData.setTargetUUID(source.getUniqueId(),
						target.getUniqueId());
				
				// Giving the target's Inventory to the source
				PlayerHelpers.setPlayerInventory(target.getInventory(), source);
				
				return CommandResult.success();
			}
		} else {
			src.sendMessage(Texts
					.of("A non-player object can not use this command"));
			return CommandResult.empty();
		}

	}

	public static InvEditData getCommandData() {
		return commandData;
	}

	public static void setCommandData(InvEditData newCommandData) {
		commandData = newCommandData;
	}

}
