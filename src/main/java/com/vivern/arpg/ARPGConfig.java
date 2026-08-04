package com.vivern.arpg;


import com.cleanroommc.configanytime.ConfigAnytime;
import net.minecraftforge.common.config.Config;

@Config(modid = Reference.MOD_ID, name = Reference.MOD_ID)
public class ARPGConfig {

    static {
        ConfigAnytime.register(ARPGConfig.class);
    }

}
