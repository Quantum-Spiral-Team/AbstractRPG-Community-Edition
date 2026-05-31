package com.vivern.arpg.events;

import com.vivern.arpg.arpgamemodes.SurvivorGameStyleWatcher;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class CommandGameStyles extends CommandBase {

    public static final String NAME = "gamestyles";
    public static final String USAGE = "/gamestyles style <start|stage|...>";

    @Override
    public String getName() {
        return "gamestyles";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/gamestyles style <start|stage|...>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, new String[]{"survivor"});
        } else {
            return args.length == 2 ? getListOfStringsMatchingLastWord(args, new String[]{"start", "level", "stage"}) : Collections.emptyList();
        }
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException, NumberFormatException {
        if (args.length <= 0) {
            throw new WrongUsageException("/gamestyles style <start|stage|...>", new Object[0]);
        } else {
            if (args.length == 2 && args[0].equals("survivor") && args[1].equals("start")) {
                SurvivorGameStyleWatcher.startSurvivor(server);
            } else if (args.length == 3 && args[0].equals("survivor") && args[1].equals("level")) {
                if (SurvivorGameStyleWatcher.currentWatcher == null) {
                    SurvivorGameStyleWatcher.currentWatcher.LEVEL = Integer.parseInt(args[2]);
                }
            } else {
                if (args.length != 3 || !args[0].equals("survivor") || !args[1].equals("stage")) {
                    throw new WrongUsageException("/gamestyles style <start|stage|...>", new Object[0]);
                }

                if (SurvivorGameStyleWatcher.currentWatcher == null) {
                    SurvivorGameStyleWatcher.currentWatcher.STAGE = Integer.parseInt(args[2]);
                }
            }
        }
    }

}
