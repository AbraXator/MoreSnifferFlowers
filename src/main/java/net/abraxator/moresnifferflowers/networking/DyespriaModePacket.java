package net.abraxator.moresnifferflowers.networking;

import io.netty.buffer.ByteBuf;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.components.DyespriaMode;
import net.abraxator.moresnifferflowers.init.ModDataComponents;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DyespriaModePacket(int amount) implements IMSFPacket {
    public static final Type<DyespriaModePacket> TYPE = new Type<>(MoreSnifferFlowers.loc("dyespria_mode"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DyespriaModePacket> STREAM_CODEC = CustomPacketPayload.codec(DyespriaModePacket::write, DyespriaModePacket::new);

    public DyespriaModePacket(FriendlyByteBuf buf) {
        this(buf.readVarInt());
    }
    
    public void write(FriendlyByteBuf buf) {
        buf.writeVarInt(this.amount);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            var stack = context.player().getMainHandItem();
            if(stack.is(ModItems.DYESPRIA)) {
                var currentMode = stack.getOrDefault(ModDataComponents.DYESPRIA_MODE, DyespriaMode.SINGLE);
                stack.set(ModDataComponents.DYESPRIA_MODE, DyespriaMode.shift(currentMode, amount));
            }
        });
    }
}
