//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.Vivern.Arpg.events.CommandArpgInfo;
import com.Vivern.Arpg.events.CommandDebug;
import com.Vivern.Arpg.events.CommandGameStyles;
import com.Vivern.Arpg.events.CommandSwarmPoints;
import com.Vivern.Arpg.events.CommandWorldEvents;
import com.Vivern.Arpg.proxy.CommonProxy;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(
   modid = "arpg",
//   dependencies = "after:thermalfoundation;after:thermalexpansion;after:endercore;after:enderio;after:redstoneflux;after:cofhcore;after:cofhworld;after:codechickenlib;after:ic2",
   dependencies = "after:thermalfoundation;after:thermalexpansion;after:endercore;after:enderio;after:redstoneflux;after:cofhcore;after:cofhworld;after:codechickenlib;after:ic2" +
           ";after:thaumicaugmentation", // Mixin/ASM hell
   acceptedMinecraftVersions = "[1.12.2]"
)
public class Main {
   @Instance
   public static Main instance;
   @SidedProxy(
      clientSide = "com.Vivern.Arpg.proxy.ClientProxy",
      serverSide = "com.Vivern.Arpg.proxy.CommonProxy"
   )
   public static CommonProxy proxy;
   public static final String modid = "arpg";
   public static boolean ENDERIO_installed = false;
   public static final boolean IS_RELEASE = true;

   /**
    * Вылет, если ThaumicAugmentation и ARPG установлены вместе.
    * Причина - TA патчит 'tryCatchFire()', а ARPG заменяет этот метод полностью.
    */
   public static boolean thaumicAugmentationLoaded = false;

   @EventHandler
   public void preInit(FMLPreInitializationEvent event) throws IllegalArgumentException, IllegalAccessException {
      thaumicAugmentationLoaded = Loader.isModLoaded("thaumicaugmentation");
      Sounds.registerSounds();
      proxy.preInit(event);
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      proxy.init(event);
   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
      proxy.postInit(event);
      // Move to ClientProxy#postInit()
//      PlayerAnimations.instance = new PlayerAnimations(Minecraft.getMinecraft());
   }

   @EventHandler
   public void serverStarting(FMLServerStartingEvent event) {
      event.registerServerCommand(new CommandDebug());
      event.registerServerCommand(new CommandSwarmPoints());
      event.registerServerCommand(new CommandWorldEvents());
      event.registerServerCommand(new CommandGameStyles());
      if (FMLCommonHandler.instance().getSide().isClient()) {
         event.registerServerCommand(new CommandArpgInfo());
      }
   }

   static {
      FluidRegistry.enableUniversalBucket();
   }
}
