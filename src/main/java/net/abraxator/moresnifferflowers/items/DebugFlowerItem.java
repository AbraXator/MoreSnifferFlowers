package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.blockentities.BerootCauldronBlockEntity;
import net.abraxator.moresnifferflowers.capability.CorruptionCapability;
import net.abraxator.moresnifferflowers.client.renderer.custom.GhostBlockEntityRenderer;
import net.abraxator.moresnifferflowers.client.renderer.custom.GhostBlockRenderer;
import net.abraxator.moresnifferflowers.client.shaders.ShaderTagAttachment;
import net.abraxator.moresnifferflowers.client.shaders.ShaderTagRegistry;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModDataAttachments;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.nikdo53.tinymultiblocklib.block.IMultiBlock;
import net.nikdo53.tinymultiblocklib.blockentities.IMultiBlockEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class DebugFlowerItem extends Item {
    public DebugFlowerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        Player player = context.getPlayer();

        if (level.isClientSide()) System.out.println("Below this is CLIENT:");
        if (!level.isClientSide()) System.out.println("Below this is SERVER:");

        if (blockState.is(ModBlocks.CORRUPTED_GRASS_BLOCK.get())){
            CorruptionCapability.printDebug(level.getChunkAt(pos));
        }

        if (IMultiBlock.isMultiblock(blockState) && level.getBlockEntity(pos) instanceof IMultiBlockEntity entity) {
            System.out.println("entity.getCenter() = " + entity.getCenter());
            System.out.println("entity.isPlaced() = " + entity.isPlaced());
        }

/*        new GhostModelRenderer(pos, 60, SimpleModels.simpleCube().bakeRoot(), new Material(TextureAtlas.LOCATION_BLOCKS, MoreSnifferFlowers.loc("block/amber_block")))
                .enableFadeOut(60)
                .addToRenderList();*/

/*
        new GhostBlockRenderer(pos, 20*5, Blocks.DIAMOND_BLOCK.defaultBlockState())
                .setARGB(1, 0.5f, 0.5f, 0.5f)
                .addToRenderList();
*/

        if (player != null && player.isShiftKeyDown()) {
            AABB area = new AABB(pos).inflate(50);

            Map<ChunkPos, Set<BlockPos>> posMap = new HashMap<>();

            BlockPos.betweenClosedStream(area).map(BlockPos::immutable).forEach(pos1 -> {
                if (level.getBlockState(pos1).is(ShaderTagRegistry.CUSTOM_RENDER)){
                    posMap.computeIfAbsent(new ChunkPos(pos1), chunkPos -> new HashSet<>()).add(pos1);
                }
            });

            for (Map.Entry<ChunkPos, Set<BlockPos>> entry : posMap.entrySet()) {
                LevelChunk chunk = level.getChunkAt(entry.getKey().getWorldPosition());

                HashSet<BlockPos> positions = chunk.getData(ModDataAttachments.SHADER_BLOCKS).positions();
                positions.addAll(entry.getValue());
                chunk.setData(ModDataAttachments.SHADER_BLOCKS, new ShaderTagAttachment(positions));

            }


        }

        new GhostBlockRenderer(pos.above(), 100, Blocks.DIAMOND_BLOCK.defaultBlockState()).addToRenderList();


        return super.useOn(context);
    }
}
