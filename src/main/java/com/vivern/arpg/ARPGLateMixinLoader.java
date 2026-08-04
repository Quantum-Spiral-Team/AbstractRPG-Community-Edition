package com.vivern.arpg;

import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;

public class ARPGLateMixinLoader implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins.arpg.mods.ender_io.json");
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        if (mixinConfig.equals("mixins.arpg.mods.ender_io.json")) {
            AbstractRPG.LOGGER.info("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
            return Loader.isModLoaded("enderio");
        }
        return false;
    }

}
