package com.mraof.minestuck.particles;

import net.minecraft.particles.*;
import net.minecraft.util.registry.Registry;

public class ParticleTypes {
    public static final BasicParticleType DENIZEN = register("denizen", true);

    private static BasicParticleType register(String key, boolean alwaysShow) {
        return (BasicParticleType) Registry.<ParticleType<? extends IParticleData>>register(Registry.PARTICLE_TYPE, key, new BasicParticleType(alwaysShow));
    }

    private static <T extends IParticleData> ParticleType<T> register(String key, IParticleData.IDeserializer<T> deserializer) {
        return Registry.register(Registry.PARTICLE_TYPE, key, new ParticleType<>(false, deserializer));
    }
}
