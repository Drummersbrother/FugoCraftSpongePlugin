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

public class commandExeInvSee implements CommandExecutor {

	public static FugoCraft_Main get() {
		return FugoCraft_Main.getInstance();
	}
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {

		Player target = args.<Player> getOne("target").get();

		if (src instanceof Player) {
			Player source = (Player) src;
			
			if (target != source) {

			source.openInventory(target.getInventory());

			source.sendMessage(Texts.of(TextColors.GREEN, "You have opened ")
					.builder()
					.append(Texts.of(TextColors.RED, target.getName()))
					.append(Texts.of(TextColors.GREEN, "'s inventory."))
					.build());
			
			get().getLogger().info(source.getName() + " has opened " + target.getName() + "'s inventory.");
			
			return CommandResult.success();
			} else {
				source.sendMessage(Texts.of(TextColors.RED, "You can't use /invsee on yourself"));
				return CommandResult.empty();
			}
		} else {
			
			get().getLogger().info("A non-player object has tried to use /invsee on " + target.getName() + ".");
			return CommandResult.empty();
		}
	}

}
