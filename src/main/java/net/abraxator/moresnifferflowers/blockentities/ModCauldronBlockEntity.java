package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.init.MSFBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ModCauldronBlockEntity extends BlockEntity {
    public ModCauldronBlockEntity(BlockPos pos, BlockState blockState) {
        super(MSFBlockEntities.MOD_CAULDRON.get(), pos, blockState);
    }

    public BlockState originalCauldron = Blocks.CAULDRON.defaultBlockState();

    public ItemStack getItemstack() {
        return Item.BY_BLOCK.get(originalCauldron.getBlock()).getDefaultInstance();
    }

    public static ItemStack getItemstack(BlockGetter level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof ModCauldronBlockEntity blockEntity) {
            return blockEntity.getItemstack();
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("cauldron", BuiltInRegistries.BLOCK.getKey(originalCauldron.getBlock()).toString());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        originalCauldron = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(tag.getString("cauldron"))).defaultBlockState();

        if (originalCauldron.isAir()) {
            originalCauldron = Blocks.CAULDRON.defaultBlockState();
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
