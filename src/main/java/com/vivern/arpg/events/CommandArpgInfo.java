package com.vivern.arpg.events;

import com.vivern.arpg.Reference;
import com.vivern.arpg.container.GuiHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class CommandArpgInfo extends CommandBase {

    public static final String NAME = "arpg";
    public static final String USAGE = "/arpg info";

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        return getListOfStringsMatchingLastWord(args, "info");
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return USAGE;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws NumberFormatException {
        if (sender instanceof EntityPlayer && args.length == 1 && "info".equals(args[0])) {
            EntityPlayer player = (EntityPlayer) sender;
            player.openGui(Reference.MOD_ID, GuiHandler.ARPG_HELP_GUI_ID, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }

}
