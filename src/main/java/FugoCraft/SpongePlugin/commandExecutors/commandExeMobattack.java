package FugoCraft.SpongePlugin.commandExecutors;

import org.spongepowered.api.data.manipulator.entity.SkeletonData;
import org.spongepowered.api.data.type.SkeletonType;
import org.spongepowered.api.data.type.SkeletonTypes;
import org.spongepowered.api.entity.living.monster.Skeleton;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.World;

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
		
		// Store the target's current world
		World targetWorld = (World) get().getGame().getServer().loadWorld(target.getWorld().getProperties());
		
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
			
			// TODO Add a check if this entity can be spawned at the location. This can be done by using setLocationSafely(Location) and checking if it returns true or not
			
			// Trying to spawn a skeleton at a location close to the target player
			// If it fails (returns and Optional<Entity>.absent) it will log to console 
			if (targetWorld.createEntity(skeletonToSpawn.toContainer()).equals(false)) {
				// TODO Log the skeleton spawning error
			}
			
			return CommandResult.success();
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
}
