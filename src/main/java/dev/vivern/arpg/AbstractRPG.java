package dev.vivern.arpg;

import dev.vivern.arpg.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = "@{MOD_ID}",
    name = "@{MOD_NAME}",
    version = "@{MOD_VERSION}",
    dependencies = "@{DEPENDENCIES}",
    acceptableRemoteVersions = "*"
)
public final class AbstractRPG {

    @SidedProxy(
        clientSide = "@{ROOT_PACKAGE}.@{MOD_ID}.proxy.ClientProxy",
        serverSide = "@{ROOT_PACKAGE}.@{MOD_ID}.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}
