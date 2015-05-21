package FugoCraft.SpongePlugin.commandExecutors;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import FugoCraft.SpongePlugin.FugoCraft_Main;

public class commandExeMobattack implements CommandExecutor {
	
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
		
		
		
		return CommandResult.success();
	}
	
}
