package FugoCraft.SpongePlugin.Helpers;

import com.google.common.base.Optional;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.HumanInventory;

public class PlayerHelpers {

    /**
     * Made by drummersbrother.
     * 
     * This method gives a passed Player the passed Inventory. Note that this
     * method will not be compatible with the 1.9 new Inventory slots
     * 
     * @param Inventory inventory. This inventory must be compatible with all
     *        slots in a Player, I think this means that it has to be a
     *        HumanInventory
     * @param Player target.
     * @return The target but with the given inventory. If
     *         inventory.query(HumanInventory.class).isEmpty() == true then it
     *         will return the original player
     */
    public static Player setPlayerInventory(Inventory inventory, Player target) {
        // Don't look at the ugly formatting ;P

        // Checking if the passed inventory is a HumanInventory (or something, I
        // don't really understand it all)
        if (inventory.query(HumanInventory.class).isEmpty() != true) {

            // Clear the inventory to prepare for a new inventory
            target.getInventory().clear();

            // This should work, if it throws a NPE it is not my fault (I think)
            // as I have already checked that
            for (Optional<ItemStack> curItemStack = inventory.poll(); curItemStack.isPresent(); curItemStack = inventory.poll()) {

                target.getInventory().set(curItemStack.get());

            }
        }

        return target;
    }
}
