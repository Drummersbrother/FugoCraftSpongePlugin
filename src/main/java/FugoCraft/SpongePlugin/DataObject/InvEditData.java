package FugoCraft.SpongePlugin.DataObject;

import java.util.HashMap;
import java.util.UUID;

import org.spongepowered.api.item.inventory.Inventory;

public class InvEditData {

    private HashMap<UUID, Boolean> hasCalled = new HashMap<UUID, Boolean>();
    private HashMap<UUID, UUID> whatTarget = new HashMap<UUID, UUID>();
    private HashMap<UUID, Inventory> prevInventory = new HashMap<UUID, Inventory>();

    public boolean haveUUIDCalled(UUID uuid) {
        if (hasCalled.get(uuid) == null) {
            return false;
        } else {
            return true;
        }
    }

    public void UUIDCalled(UUID uuid) {
        if (hasCalled.get(uuid) == null) {
            hasCalled.put(uuid, true);
        }
    }

    /**
     * This method should not be called if you have not checked to see if a
     * player has used the command
     */
    public UUID getTargetUUID(UUID uuid) {
        return whatTarget.get(uuid);
    }

    public void setTargetUUID(UUID Source, UUID Target) {
        whatTarget.put(Source, Target);
    }

    /**
     * This method should not be called if you have not checked to see if a
     * player has used the command
     */
    public Inventory getPrevInventory(UUID uuid) {
        return prevInventory.get(uuid);
    }

    public void setPrevInventory(UUID uuid, Inventory inventory) {
        prevInventory.put(uuid, inventory);
    }

    public void removeUUID(UUID uuid) {
        hasCalled.remove(uuid);
        whatTarget.remove(uuid);
        prevInventory.remove(uuid);
    }
}
