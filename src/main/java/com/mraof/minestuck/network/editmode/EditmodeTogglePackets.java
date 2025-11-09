package com.mraof.minestuck.network.editmode;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.computer.editmode.EditData;
import com.mraof.minestuck.network.MSPacket;
import com.mraof.minestuck.world.storage.MSExtraData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class EditmodeTogglePackets
{
	public record ToggleNoClip() implements MSPacket.PlayToServer
	{
		public static final String EDITMODE_NOCLIP_TOGGLE_MESSAGE = "minestuck.editmode.noclip_toggle";
		
		public static final Type<ToggleNoClip> ID = new Type<>(Minestuck.id("editmode_toggle/no_clip"));
		public static final StreamCodec<FriendlyByteBuf, ToggleNoClip> STREAM_CODEC = StreamCodec.unit(new ToggleNoClip());
		
		@Override
		public Type<? extends CustomPacketPayload> type()
		{
			return ID;
		}
		
		@Override
		public void execute(IPayloadContext context, ServerPlayer player)
		{
			EditData editData = MSExtraData.get(player.serverLevel()).findEditData(data -> data.getEditor() == player);
			
			if(editData == null)
				return;
			
			editData.toggleNoclip();
			
			player.displayClientMessage(Component.translatable(EDITMODE_NOCLIP_TOGGLE_MESSAGE, editData.noclip()), true);
		}
	}
}
