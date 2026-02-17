package net.abraxator.moresnifferflowers.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.abraxator.moresnifferflowers.capability.BlockPatternCapability;
import net.abraxator.moresnifferflowers.capability.CorruptionCapability;
import net.abraxator.moresnifferflowers.client.shaders.ShaderTagAttachment;
import net.abraxator.moresnifferflowers.client.shaders.ShaderTagRegistry;
import net.abraxator.moresnifferflowers.init.ModDataAttachments;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateMixin {
    @Shadow
    public abstract boolean is(TagKey<Block> tag);

    @Shadow
    public abstract Block getBlock();

    @Shadow
    protected abstract BlockState asState();

    @WrapMethod(method = "getRenderShape")
    public RenderShape getRenderShape(Operation<RenderShape> original) {
        if (this.is(ShaderTagRegistry.NO_RENDERING)){
            return RenderShape.INVISIBLE;
        }
        return original.call();
    }


    @Inject(method = "onRemove", at = @At("HEAD"))
    public void onRemove(Level level, BlockPos pos, BlockState newState, boolean movedByPiston, CallbackInfo ci) {
        BlockState state = this.asState();
        boolean isDifferent = state.getBlock() != newState.getBlock();

        if (isDifferent) {

            if (BlockPatternCapability.hasPattern(pos, level)) {
                BlockPatternCapability.removePattern(pos, level);
            }

            if (state.is(ModTags.ModBlockTags.CORRUPTION_SHIELDING) && !level.isClientSide){
                LevelChunk chunk = level.getChunkAt(pos);
                CorruptionCapability cap = CorruptionCapability.get(chunk);
                cap.flowers.remove(pos);
                if (cap.resistance > 0 && cap.flowers.size() < cap.resistance) cap.resistance--;
            }


            if (state.is(ShaderTagRegistry.CUSTOM_RENDER)){
                HashSet<BlockPos> positions = level.getChunkAt(pos).getData(ModDataAttachments.SHADER_BLOCKS).positions();
                positions.remove(pos);
                level.getChunkAt(pos).setData(ModDataAttachments.SHADER_BLOCKS, new ShaderTagAttachment(positions));
            }

        }
    }


    @Inject(method = "onPlace", at = @At("HEAD"))
    public void onPlace(Level level, BlockPos pos, BlockState newState, boolean movedByPiston, CallbackInfo ci) {
        BlockState state = this.asState();
        boolean isDifferent = state.getBlock() != newState.getBlock();

        if (isDifferent) {
            if (state.is(ModTags.ModBlockTags.CORRUPTION_SHIELDING) && !level.isClientSide){
                LevelChunk chunk = level.getChunkAt(pos);
                CorruptionCapability cap = chunk.getData(ModDataAttachments.CHUNK_CORRUPTION);

                cap.resistance++;
                cap.isSource = false;
                cap.flowers.add(pos);
            }


            if (state.is(ShaderTagRegistry.CUSTOM_RENDER)){
                HashSet<BlockPos> positions = level.getChunkAt(pos).getData(ModDataAttachments.SHADER_BLOCKS).positions();
                positions.add(pos);
                level.getChunkAt(pos).setData(ModDataAttachments.SHADER_BLOCKS, new ShaderTagAttachment(positions));
            }
        }
    }

}
