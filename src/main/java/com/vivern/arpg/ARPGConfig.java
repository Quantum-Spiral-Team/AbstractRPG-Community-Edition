package com.vivern.arpg;


import com.cleanroommc.configanytime.ConfigAnytime;
import net.minecraftforge.common.config.Config;

@SuppressWarnings("unused")
@Config(modid = Reference.MOD_ID, name = Reference.MOD_ID + "/general")
public class ARPGConfig {

    @Config.Name("general")
    @Config.Comment("General settings")
    public static GeneralCategory general = new GeneralCategory();

    public static class GeneralCategory {

        @Config.Comment("Disable Realms")
        public boolean disableRealms = true;

    }

    static {
        ConfigAnytime.register(ARPGConfig.class);
    }

}
