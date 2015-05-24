package FugoCraft.SpongePlugin.commandExecutors;

import FugoCraft.SpongePlugin.FugoCraft_Main;
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
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class commandExeInvSubmit implements CommandExecutor {

	private static FugoCraft_Main MClass;

	public static void set(FugoCraft_Main singleton) {
		MClass = singleton;
	}

	public static FugoCraft_Main get() {
		return MClass;
	}

	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {

		if (src instanceof Player) {
			// Storing the CommandSource as a Player object
			Player source = (Player) src;

			// Getting the invEdit data
			InvEditData commandData = commandExeInvEdit.getCommandData();

			if (commandData.haveUUIDCalled(source.getUniqueId())) {
				// Storing the target player
				Player target = get()
						.getGame()
						.getServer()
						.getPlayer(
								commandData.getTargetUUID(source.getUniqueId()))
						.get();

				// Putting the changed inventory from the source into the target
				// player from /invedit
				PlayerHelpers.setPlayerInventory(source.getInventory(), target);

				// Storing the source's previous Inventory
				Inventory prevInventory = commandData.getPrevInventory(source
						.getUniqueId());

				// Putting the original Inventory from the source back into the
				// source
				PlayerHelpers.setPlayerInventory(prevInventory, source);

				// Telling the source that they have successfully given back the
				// changed Inventory
				source.sendMessage(Texts
						.of(TextColors.GREEN, "You have successfully changed ")
						.builder()
						.append(Texts.of(TextColors.GOLD, target.getName()))
						.append(Texts.of(TextColors.GREEN, "'s inventory!"))
						.build());
				
				// Removing the player from all hashmaps
				commandData.removeUUID(source.getUniqueId());
				
				// Giving back the edited commandData object
				commandExeInvEdit.setCommandData(commandData);

				return CommandResult.success();
			} else {
				source.sendMessage(Texts
						.of(TextColors.RED,
								"You can not use this command because you do not carry another player's inventory!"));
				get().getLogger()
						.info(source.getName()
								+ " tried to use /invsubmit but didn't carry another player's inventory.");
				return CommandResult.empty();
			}
		} else {
			src.sendMessage(Texts
					.of("You can not call this command because you are not a player"));
			if (!(src instanceof ConsoleSource)) {
				get().getLogger().info(
						"A non-player object has tried to use /invsubmit!");
			}
			return CommandResult.empty();
		}
	}

}
