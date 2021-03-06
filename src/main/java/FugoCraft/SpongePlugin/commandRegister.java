package FugoCraft.SpongePlugin;

import FugoCraft.SpongePlugin.commandExecutors.*;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;

public class commandRegister {

    public static FugoCraft_Main get() {
        return FugoCraft_Main.getInstance();
    }

    public static void registerCommands(boolean enableDB) {
        // TODO when registering commands, put the method call here
    	// If a command interacts with a database, please don't register it if enableDB = false
        commandHealReg();
        commandFeedReg();
        commandMobattackReg();
        commandInvEditReg();
        commandInvSubmitReg();
        commandInvSeeReg();
        commandPingReg();
        commandReloadConfReg();
        
        if (enableDB) {
        	commandDBInsertTestReg();
        	commandDBReadTestReg();
        }

    }

    public static void commandPingReg() {

        CommandSpec pingCommandSpec = CommandSpec
                .builder()
                .description(
                        Texts.of("Tells you the target player's ping (in milliseconds)."))
                .extendedDescription(
                        Texts.of(" If no target player is specified it will tell you your ping."))
                .executor(new commandExePing())
                .permission("fugocraft.command.ping")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments
                                .playerOrSource(Texts.of("target"), get()
                                        .getGame())))
                .build();

        get().getGame()
                .getCommandDispatcher()
                .register(get().getPluginContainer().getInstance(),
                        pingCommandSpec, "pong", "ping");
    }

    public static void commandReloadConfReg() {

        CommandSpec reloadConfSpec = CommandSpec.builder()
                .description(Texts.of("Reloads the configuration file"))
                .executor(new commandExeRelConf())
                .permission("fugocraft.command.admin.reloadconf")
                .arguments(GenericArguments.none()).build();

        get().getGame()
                .getCommandDispatcher()
                .register(get().getPluginContainer().getInstance(),
                        reloadConfSpec, "reloadconfig", "relconfig", "reloadconf", "relconf");
    }

    public static void commandInvSeeReg() {

        CommandSpec invSeeCommandSpec = CommandSpec
                .builder()
                .description(
                        Texts.of("Opens the target's inventory for you to edit."))
                .extendedDescription(Texts.of(""))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(
                                Texts.of("target"), get().getGame())))
                .executor(new commandExeInvSee())
                .permission("fugocraft.command.invsee").build();

        get().getGame()
                .getCommandDispatcher()
                .register(get().getPluginContainer().getInstance(),
                        invSeeCommandSpec, "invsee", "inventorysee");

    }

    public static void commandInvSubmitReg() {

        CommandSpec invSubmitCommandSpec = CommandSpec
                .builder()
                .description(
                        Texts.of(
                                "Gives the target from /invedit the inventory you have, also gives back the inventory you had before using /invedit to you"))
                .extendedDescription(
                        Texts.of(" If you don't have another player's inventory it will not do anything."))
                .executor(new commandExeInvSubmit())
                .permission("fugocraft.command.invedit.submit").build();

        get().getGame()
                .getCommandDispatcher()
                .register(get().getPluginContainer().getInstance(),
                        invSubmitCommandSpec, "invsubmit", "inventorysubmit");

    }

    public static void commandInvEditReg() {

        CommandSpec invSubmitCommandSpec = CommandSpec
                .builder()
                .description(
                        Texts.of(
                                "Gives you a copy of a target player's inventory, when you have changed it as desired use /invsubmit to give the target that inventory and getting back your own inventory."))
                .extendedDescription(
                        Texts.of(" If no target player is specified it will not do anything."))
                .executor(new commandExeInvEdit())
                .permission("fugocraft.command.invedit.edit")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.player(
                                Texts.of("target"), get().getGame())))
                .build();

        get().getGame()
                .getCommandDispatcher()
                .register(get().getPluginContainer().getInstance(),
                        invSubmitCommandSpec, "invedit", "inventoryedit");

    }

    public static void commandHealReg() {

        CommandSpec healCommandSpec = CommandSpec
                .builder()
                .description(Texts.of("Heals a target player."))
                .extendedDescription(
                        Texts.of(" If no target player is specified it will heal the commmand excecutor."))
                .executor(new commandExeHeal())
                .permission("fugocraft.command.heal")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments
                                .playerOrSource(Texts.of("target"), get()
                                        .getGame())))
                .build();

        get().getGame()
                .getCommandDispatcher()
                .register(get().getPluginContainer().getInstance(),
                        healCommandSpec, "heal");

    }

    public static void commandFeedReg() {

        CommandSpec feedCommandSpec = CommandSpec
                .builder()
                .description(Texts.of("Feeds a target player."))
                .extendedDescription(
                        Texts.of(" If no target player is specified it will feed the commmand excecutor."))
                .executor(new commandExeFeed())
                .permission("fugocraft.command.feed")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments
                                .playerOrSource(Texts.of("target"), get()
                                        .getGame())))
                .build();

        get().getGame()
                .getCommandDispatcher()
                .register(get().getPluginContainer().getInstance(),
                        feedCommandSpec, "feed");

    }

    public static void commandMobattackReg() {

        CommandSpec mobattackCommandSpec = CommandSpec
                .builder()
                .description(
                        Texts.of("Spooks a target player with scary skeletmans."))
                .extendedDescription(
                        Texts.of(" If no target player is specified it will not spook anyone."))
                .executor(new commandExeMobattack())
                .permission("fugocraft.command.mobattack")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments
                                .playerOrSource(Texts.of("target"), get()
                                        .getGame())),
                        GenericArguments.integer(Texts.of("spawnAmount")))
                .build();

        get().getGame()
                .getCommandDispatcher()
                .register(get().getPluginContainer().getInstance(),
                        mobattackCommandSpec, "mobattack", "moba");

    }
    
    public static void commandDBInsertTestReg() {
    	
        CommandSpec DBInsertTestCommandSpec = CommandSpec
                .builder()
                .description(
                        Texts.of("Inserts the player's name into a table in the database"))
                .executor(new commandExeDBInsertTest())
                .permission("fugocraft.command.dbtest")
                .build();

        get().getGame()
                .getCommandDispatcher()
                .register(get().getPluginContainer().getInstance(),
                        DBInsertTestCommandSpec, "dbinsert", "dbi");
        
    }
    
    public static void commandDBReadTestReg() {
    	
        CommandSpec DBReadTestCommandSpec = CommandSpec
                .builder()
                .description(
                        Texts.of("Reads and outputs the table that was edited by /dbinsert"))
                .executor(new commandExeDBReadTest())
                .permission("fugocraft.command.dbtest")
                .build();

        get().getGame()
                .getCommandDispatcher()
                .register(get().getPluginContainer().getInstance(),
                        DBReadTestCommandSpec, "dbread", "dbr");
        
    }
}
