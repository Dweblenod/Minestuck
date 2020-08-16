package com.mraof.minestuck.particles;

import com.mraof.minestuck.block.MSBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class DenizenParticle extends SpriteTexturedParticle {
    private DenizenParticle(World p_i48192_1_, double p_i48192_2_, double p_i48192_4_, double p_i48192_6_, IItemProvider p_i48192_8_) {
        super(p_i48192_1_, p_i48192_2_, p_i48192_4_, p_i48192_6_);
        this.setSprite(Minecraft.getInstance().getItemRenderer().getItemModelMesher().getParticleIcon(p_i48192_8_));
        this.particleGravity = 0.0F;
        this.maxAge = 900;
        this.canCollide = false;
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.TERRAIN_SHEET;
    }

    public float getScale(float p_217561_1_) {
        return 0.5F;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            return new DenizenParticle(worldIn, x, y, z, MSBlocks.DENIZEN.asItem());
            //return new BarrierParticle(worldIn, x, y, z, Blocks.BARRIER.asItem());
        }
    }

}