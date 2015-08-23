package FugoCraft.SpongePlugin.commandExecutors;

import FugoCraft.SpongePlugin.FugoCraft_Main;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class commandExeFeed implements CommandExecutor {

    public static FugoCraft_Main get() {
        return FugoCraft_Main.getInstance();
    }

    public CommandResult execute(CommandSource src, CommandContext args)
            throws CommandException {

        // Store the command target
        Player target = args.<Player>getOne("target").get();

        // Creating and initializing a FoodData object
        FoodData foodData = target.getOrCreate(FoodData.class).get();
        	

            // Stores the target's food and saturation levels before they are fed
            int HungerBefore = foodData.foodLevel().get();
            double SaturationBefore = foodData.saturation().get();
            
            // Modifying the foodData object so it's has full foodLevel and good saturation and exhaustion
            foodData.foodLevel().set(20);
            foodData.saturation().set(foodData.saturation().getMaxValue());
            foodData.exhaustion().set(foodData.exhaustion().getMaxValue());
            
            // Giving the target a full FoodData DataContainer
            target.offer(foodData);

            // Store the target's food and saturation levels after they have been fed
            int HungerAfter = foodData.foodLevel().get();
            double SaturationAfter = foodData.saturation().get();

            if (src instanceof Player) {
                // Store the source as a player
                Player source = (Player) src;

                // Tells the target that they have been fed
                target.sendMessage(Texts
                        .of(TextColors.GREEN, "You have been fed by ")
                        .builder()
                        .append(Texts.of(TextColors.GOLD, source
                                .getName()))
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
                                .getName()))
                        .append(Texts.of(TextColors.GREEN, " from "))
                        .append(Texts.of(TextColors.RED, HungerBefore))
                        .append(Texts.of(TextColors.GREEN, "hunger to "))
                        .append(Texts.of(TextColors.RED, HungerAfter))
                        .append(Texts.of(TextColors.GREEN, "hunger!")).build());

            } else {
                // Tells the target that they have been fed by
                // "an unknown source of great power"
                target.sendMessage(Texts
                        .of(TextColors.GREEN,
                                "You have been fed by an unknown source of great power!"));

                src.sendMessage(Texts.of("You have fed "
                        + target.getDisplayNameData().displayName()
                        + " from " + HungerBefore + "hunger and "
                        + SaturationBefore + " saturation to " + HungerAfter
                        + "hunger and " + SaturationAfter + " saturation."));

                if (!(src instanceof ConsoleSource)) {
                    get().getLogger().info(
                            "A non-player object has fed " + target.getName()
                                    + " from " + HungerBefore + "hunger and "
                                    + SaturationBefore + " saturation to "
                                    + HungerAfter + "hunger and "
                                    + SaturationAfter + " saturation.");
               }
           }
           return CommandResult.success();
        }
    }
