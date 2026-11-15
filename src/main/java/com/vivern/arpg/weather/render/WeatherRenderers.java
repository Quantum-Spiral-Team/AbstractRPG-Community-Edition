package com.vivern.arpg.weather.render;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.weather.*;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

@SideOnly(Side.CLIENT)
public final class WeatherRenderers {

    public static final IRenderHandler EMPTY_CLOUDS = new IRenderHandler() {
        @Override
        public void render(float partialTicks, WorldClient world, Minecraft mc) {}
    };

    private static final Reference2ObjectMap<Class<? extends WorldEvent>, BiFunction<WorldEvent, AbstractWorldProvider, WorldEventRenderer<?>>> FACTORIES = new Reference2ObjectOpenHashMap<>();

    static {
        register(Snowfall.class, SnowfallRenderer::new);
        register(Hail.class, HailRenderer::new);
        register(Aurora.class, AuroraRenderer::new);
        register(Rainfall.class, RainfallRenderer::new);
        register(PoisonRain.class, PoisonRainRenderer::new);
        register(Storm.class, StormRenderer::new);
        register(Rainstorm.class, RainstormRenderer::new);
    }

    private WeatherRenderers() {}

    private static <E extends WorldEvent> void register(Class<E> type, BiFunction<E, AbstractWorldProvider, WorldEventRenderer<?>> factory) {
        FACTORIES.put(type, (event, provider) -> factory.apply(type.cast(event), provider));
    }

    @Nullable
    public static WorldEventRenderer<?> create(WorldEvent event, AbstractWorldProvider provider) {
        BiFunction<WorldEvent, AbstractWorldProvider, WorldEventRenderer<?>> factory = FACTORIES.get(event.getClass());
        return factory == null ? null : factory.apply(event, provider);
    }

}
