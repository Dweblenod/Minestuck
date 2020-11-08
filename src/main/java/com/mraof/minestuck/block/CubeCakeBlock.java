package com.mraof.minestuck.block;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class CubeCakeBlock extends Block
{
	private static final List<String> MESSAGES = ImmutableList.of("happy birthday Kirderf!!!!");
	
	protected CubeCakeBlock(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
	{
		if (!worldIn.isRemote)
		{
			String key = MESSAGES.get(player.getRNG().nextInt(MESSAGES.size()));
			ITextComponent message = new TranslationTextComponent(getTranslationKey()+".message."+key);
			message.getStyle().setColor(TextFormatting.YELLOW);
			player.sendMessage(message);
			double d0 = (double)pos.getX();
			double d1 = (double)pos.getY();
			double d2 = (double)pos.getZ();
			player.addPotionEffect(new EffectInstance(Effects.SATURATION, 100, 2));
			worldIn.addParticle(ParticleTypes.FIREWORK, d0, d1, d2, 0.0D, 1.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.FIREWORK, d0, d1, d2, 0.0D, 1.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.FIREWORK, d0, d1, d2, 0.0D, 1.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.FIREWORK, d0, d1, d2, 0.0D, 1.0D, 0.0D);
			worldIn.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_VILLAGER_CELEBRATE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			worldIn.removeBlock(pos, false);
			return true;
		}
		else
		{
			return false;
		}
	}
}