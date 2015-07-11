package FugoCraft.SpongePlugin.commandExecutors;

import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import FugoCraft.SpongePlugin.FugoCraft_Main;

public class commandExeRelConf implements CommandExecutor {

	public static FugoCraft_Main get() {
		return FugoCraft_Main.getInstance();
	}


	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		if (get().relConf()) {
			get().getLogger().info("Reloading of configuration was successful");
			return CommandResult.success();
		} else {
			return CommandResult.empty();
		}
	}
	
}
