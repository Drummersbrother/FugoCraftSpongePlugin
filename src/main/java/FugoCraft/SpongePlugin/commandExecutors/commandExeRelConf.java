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

public class commandExeRelConf implements CommandExecutor {

    public static FugoCraft_Main get() {
        return FugoCraft_Main.getInstance();
    }

    public CommandResult execute(CommandSource src, CommandContext args)
            throws CommandException {

        if (get().relConf()) {
            get().getLogger().info("Reloading of configuration was successful!");
            if (src instanceof Player) {
            	src.getMessageSink().sendMessage(Texts.of(TextColors.GREEN, "Reloading the configuration was successful!"));
            }
            return CommandResult.success();
        } else {
            if (src instanceof Player) {
            	src.getMessageSink().sendMessage(Texts.of(TextColors.GREEN, "Reloading the configuration failed!"));
            }
            return CommandResult.empty();
        }
    }

}
