package com.mraof.minestuck.item;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.TrimPattern;

import java.util.Optional;

public class MSTrimPatterns
{
   public static final ResourceKey<TrimPattern> LORD = registryKey("lord");
   public static final ResourceKey<TrimPattern> MUSE = registryKey("muse");

   public static void bootstrap(BootstapContext<TrimPattern> pContext) {
      register(pContext, MSItems.LORD_ARMOR_TRIM_SMITHING_TEMPLATE.get(), LORD);
      register(pContext, MSItems.LORD_ARMOR_TRIM_SMITHING_TEMPLATE.get(), MUSE);
   }

   public static Optional<Holder.Reference<TrimPattern>> getFromTemplate(RegistryAccess pRegistry, ItemStack pTemplate) {
      return pRegistry.registryOrThrow(Registries.TRIM_PATTERN).holders().filter((p_266833_) -> pTemplate.is(p_266833_.value().templateItem())).findFirst();
   }

   private static void register(BootstapContext<TrimPattern> pContext, Item pTemplateItem, ResourceKey<TrimPattern> pTrimPatternKey) {
      TrimPattern trimpattern = new TrimPattern(pTrimPatternKey.location(), BuiltInRegistries.ITEM.wrapAsHolder(pTemplateItem), Component.translatable(Util.makeDescriptionId("trim_pattern", pTrimPatternKey.location())));
      pContext.register(pTrimPatternKey, trimpattern);
   }

   private static ResourceKey<TrimPattern> registryKey(String pKey) {
      return ResourceKey.create(Registries.TRIM_PATTERN, new ResourceLocation(pKey));
   }
}