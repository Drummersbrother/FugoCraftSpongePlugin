package FugoCraft.SpongePlugin.commandExecutors;

import FugoCraft.SpongePlugin.FugoCraft_Main;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class commandExeDBInsertTest implements CommandExecutor {

	public static FugoCraft_Main get() {
		return FugoCraft_Main.getInstance();
	}

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (src instanceof Player) {

			try {
				// Getting the connection to the database
				Connection con = get().getDBConn();
				con.setAutoCommit(true);
				
				PreparedStatement prepStmt = null;
				Statement stmt = null;
				String query;
				
				// Preparing a query to get all previous usernames who have used this command
				query = "SELECT * FROM cmdtestdb;";
				
				stmt = con.createStatement();
				ResultSet prevUsers = stmt.executeQuery(query);
				
				boolean hasPlayerUsed = false;
				String playerName = src.getName();
				while (prevUsers.next()) {
					if (prevUsers.getString(1).equals(playerName)) {
						hasPlayerUsed = true;
						break;
					}
				}
				
				if (!hasPlayerUsed) {
					query = "INSERT INTO cmdtestdb (name) VALUES ('?');";
					prepStmt = con.prepareStatement(query);
					prepStmt.setString(1, playerName);
					prepStmt.executeUpdate();
					src.getMessageSink().sendMessage(Texts.of(TextColors.GREEN, "Your usage has been recorded in the database."));
				} else  {
					src.getMessageSink().sendMessage(Texts.of(TextColors.RED, "You already have an entry in the database and will not get another one"));
				}
				
				stmt.close();
				con.close();

			} catch (SQLException e) {
				src.getMessageSink().sendMessage(Texts.of(TextColors.RED, "Got error when trying to communicate with database!"));
				get().getLogger().error(
						"Was not able to connect to DB, please check it's status as it was working when the plugin loaded");
				e.printStackTrace();
				return CommandResult.empty();
			}
		} else {
			src.getMessageSink().sendMessage(Texts.of("You are not a player and can therefore not use this command"));
		}
		return CommandResult.success();
	}

}
