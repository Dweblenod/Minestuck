package com.mraof.minestuck.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.world.storage.loot.MSLootTables;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Function;

public class EndDungeonTierOneFeature extends Feature<NoFeatureConfig>
{
    private static final ResourceLocation DUNGEON_BURNING_LIBRARY = new ResourceLocation(Minestuck.MOD_ID, "dungeon_burning_library");
    private static final ResourceLocation DUNGEON_HALL = new ResourceLocation(Minestuck.MOD_ID, "dungeon_hall");

    public EndDungeonTierOneFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn)
    {
        super(configFactoryIn);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        Rotation rotation = Rotation.randomRotation(rand);
        ResourceLocation library = rand.nextInt(50) == 0 ? DUNGEON_BURNING_LIBRARY : DUNGEON_HALL;
        TemplateManager templates = ((ServerWorld) worldIn.getWorld()).getSaveHandler().getStructureTemplateManager();
        Template template = templates.getTemplateDefaulted(library);

        PlacementSettings settings = new PlacementSettings().setChunk(new ChunkPos(pos)).setRandom(rand).addProcessor(StructureBlockRegistryProcessor.INSTANCE);

        BlockPos size = template.transformedSize(rotation);
        int xOffset = rand.nextInt(16 - size.getX() - 2) + 1, zOffset = rand.nextInt(16 - size.getZ() - 2) + 1;

        int y = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.getX() + xOffset + size.getX()/2, pos.getZ() + zOffset + size.getZ()/2) - 2;

        BlockPos center = pos.add(xOffset + size.getX()/2, y - pos.getY(), zOffset + size.getZ()/2);

        settings.setRotation(rotation);
        BlockPos structurePos = template.getZeroPositionWithTransform(new BlockPos(pos.getX() + xOffset, y, pos.getZ() + zOffset), Mirror.NONE, rotation);
        template.addBlocksToWorld(worldIn, structurePos, settings);

        for(Template.BlockInfo blockInfo : template.func_215381_a(structurePos, settings, Blocks.STRUCTURE_BLOCK))
        {
            if(blockInfo.nbt != null)
            {
                StructureMode structuremode = StructureMode.valueOf(blockInfo.nbt.getString("mode"));
                if (structuremode == StructureMode.DATA)
                {
                    String data = blockInfo.nbt.getString("metadata");
                    if(data.equals("basic_chest"))
                    {
                        worldIn.setBlockState(blockInfo.pos, Blocks.AIR.getDefaultState(), 3);
                        TileEntity tileentity = worldIn.getTileEntity(blockInfo.pos.down());
                        if (tileentity instanceof ChestTileEntity)
                        {
                            ((ChestTileEntity) tileentity).setLootTable(MSLootTables.BASIC_MEDIUM_CHEST, rand.nextLong());
                        }
                    }
                }
            }
        }

        return true;
    }
}

