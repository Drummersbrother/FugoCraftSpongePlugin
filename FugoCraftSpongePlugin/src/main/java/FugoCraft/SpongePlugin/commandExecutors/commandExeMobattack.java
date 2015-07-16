package FugoCraft.SpongePlugin.commandExecutors;

import com.google.common.base.Optional;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.entity.SkeletonData;
import org.spongepowered.api.data.type.SkeletonTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.monster.Skeleton;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.TeleportHelper;
import org.spongepowered.api.world.World;

import FugoCraft.SpongePlugin.FugoCraft_Main;

/**
 * Class for the /mobattack command executor and it's help-methods
 *
 *
 */
public class commandExeMobattack implements CommandExecutor {

    public static FugoCraft_Main get() {
        return FugoCraft_Main.getInstance();
    }

    public CommandResult execute(CommandSource src, CommandContext args)
            throws CommandException {

        // Store the command target
        Player target = args.<Player>getOne("target").get();

        // Store how many skeletons to spawn at each location
        int spawnAmount = (Integer) args.getOne("spawnAmount").get();

        // Store the target's current world
        World targetWorld = (World) get().getGame().getServer()
                .loadWorld(target.getWorld().getProperties());

        // Create a skeleton to spawn
        Skeleton skeletonToSpawn;
        try {
            skeletonToSpawn = Skeleton.class.newInstance();

            // Set the skeleton to have appropriate data
            SkeletonData skeletonData = skeletonToSpawn.getSkeletonData();

            // Putting appropriate data in the SkeletonData object
            skeletonData.setValue(SkeletonTypes.NORMAL);

            // Offering the SKeletonData object to the Skeleton entity
            skeletonToSpawn.offer(skeletonData);

            if (spawnEntitiesAroundPlayer(target, skeletonToSpawn.toContainer(), targetWorld, spawnAmount, 2, 2)) {

                target.sendMessage(Texts.of(TextStyles.NONE, TextColors.GREEN, "You have been attacked!"));

                return CommandResult.success();
            } else {
                return CommandResult.empty();
            }
        } catch (InstantiationException e) {
            get().getLogger().warn("Could not instantiate a skeleton entity.");
            e.printStackTrace();
            return CommandResult.empty();
        } catch (IllegalAccessException e) {
            get().getLogger().warn("Could not access the skeleton entity.");
            e.printStackTrace();
            return CommandResult.empty();
        }

    }

    /**
     * Tries to spawn the provided entity around the provided player in the
     * provided world. Also messages the target "You have been attacked!"
     * 
     * @param target {@link org.spongepowered.api.entity.player#Player} to spawn
     *        entities around
     * @param entityContainer {@link org.spongepowered.api.entity#Entity} to be
     *        spawned
     * @param targetWorld Target {@link org.spongepowered.api.world#World} to
     *        spawn the entities in
     * @param spawnAmount How many of the entity to spawn at every location to
     *        spawn at around the target Player
     * @param SafeHeight How high above every location to spawn at around the
     *        player it will search for valid points to spawn the entity(-ies)
     *        at
     * @param SafeWidth How long from every location to spawn at around the
     *        player it will search for valid points to spawn the entity(-ies)
     *        at
     * @return Boolean - If it is equal to {@code true} the spawning has not
     *         thrown an error if it is equal to {@code false} then the spawning
     *         has thrown an error.
     */
    private boolean spawnEntitiesAroundPlayer(Player target,
            DataContainer entityContainer, World targetWorld, int spawnAmount,
            int SafeHeight, int SafeWidth) {

        // Storing the target's location
        Location spawnLoc = target.getLocation();

        // Creating a Location to store the current spawn location
        Location curLoc = spawnLoc;

        // Creating a TeleportHelper object so we can check to see if the
        // location is safe to spawn an entity in
        TeleportHelper checkLoc = get().getGame().getTeleportHelper();

        try {
            checkLoc = TeleportHelper.class.newInstance();

            // TODO add position change and checking if something is a safe
            // position, if it is not, do not spawn entity

            curLoc = spawnLoc.add(2, 0, 2);

            if (((Object) checkLoc.getSafeLocation(curLoc, SafeHeight,
                    SafeWidth)).equals(Optional.absent())) {
                // If this throws a NullPointerException it is not my fault, it
                // is google's fault! (we have checked if there is a valid
                // position,
                // so if it throws a NPE)

                for (int i = 0; i < spawnAmount; i++) {
                    targetWorld.createEntity(entityContainer, checkLoc
                            .getSafeLocation(curLoc, 2, 2).get().getPosition());
                    targetWorld.spawnEntity((Entity) entityContainer);
                }

            }

            curLoc = spawnLoc.add(2, 0, -2);

            if (((Object) checkLoc.getSafeLocation(curLoc, SafeHeight,
                    SafeWidth)).equals(Optional.absent())) {
                // If this throws a NullPointerException it is not my fault, it
                // is google's fault! (we have checked if there is a valid
                // position,
                // so if it throws a NPE)

                for (int i = 0; i < spawnAmount; i++) {
                    targetWorld.createEntity(entityContainer, checkLoc
                            .getSafeLocation(curLoc, 2, 2).get().getPosition());
                    targetWorld.spawnEntity((Entity) entityContainer);
                }

            }

            curLoc = spawnLoc.add(-2, 0, 2);

            if (((Object) checkLoc.getSafeLocation(curLoc, SafeHeight,
                    SafeWidth)).equals(Optional.absent())) {
                // If this throws a NullPointerException it is not my fault, it
                // is google's fault! (we have checked if there is a valid
                // position,
                // so if it throws a NPE)

                for (int i = 0; i < spawnAmount; i++) {
                    targetWorld.createEntity(entityContainer, checkLoc
                            .getSafeLocation(curLoc, 2, 2).get().getPosition());
                    targetWorld.spawnEntity((Entity) entityContainer);
                }

            }

            curLoc = spawnLoc.add(-2, 0, -2);

            if (((Object) checkLoc.getSafeLocation(curLoc, SafeHeight,
                    SafeWidth)).equals(Optional.absent())) {
                // If this throws a NullPointerException it is not my fault, it
                // is google's fault! (we have checked if there is a valid
                // position,
                // so if it throws a NPE)

                for (int i = 0; i < spawnAmount; i++) {
                    targetWorld.createEntity(entityContainer, checkLoc
                            .getSafeLocation(curLoc, 2, 2).get().getPosition());
                    targetWorld.spawnEntity((Entity) entityContainer);
                }

            }

            // If everything went well it will send back true
            return true;

        } catch (InstantiationException e) {
            get().getLogger().warn("Could not instantiate a TeleportHelper.");
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            get().getLogger().warn("Could not access the TeleportHelper.");
            e.printStackTrace();
            return false;
        }

    }
}
