package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.abraxator.moresnifferflowers.blocks.blockentities.GiantCropBlockEntity;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModParticles;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.block.BeetrootsBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.*;
import java.util.stream.StreamSupport;

public class JarOfBonmeelItem extends Item {
    public final Map<Block, Pair<Block, Pair<IntProperty, Integer>>> MAP = Map.of(
            Blocks.CARROTS, new Pair<>(ModBlocks.GIANT_CARROT, new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE)),
            Blocks.POTATOES, new Pair<>(ModBlocks.GIANT_POTATO, new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE)),
            Blocks.NETHER_WART, new Pair<>(ModBlocks.GIANT_NETHERWART, new Pair<>(NetherWartBlock.AGE, NetherWartBlock.MAX_AGE)),
            Blocks.BEETROOTS, new Pair<>(ModBlocks.GIANT_BEETROOT, new Pair<>(BeetrootsBlock.AGE, BeetrootsBlock.BEETROOTS_MAX_AGE)),
            Blocks.WHEAT, new Pair<>(ModBlocks.GIANT_WHEAT, new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE))
    );

    public JarOfBonmeelItem(Settings pProperties) {
        super(pProperties);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext pContext) {
        PlayerEntity player = pContext.getPlayer();
        World level = pContext.getWorld();
        BlockPos clickedPos = pContext.getBlockPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        if(!clickedState.isIn(ModTags.ModBlockTags.CROPS_FERTIABLE_BY_FBM)) return ActionResult.PASS;
        Block crop = clickedState.getBlock();
        Block giantVersion = MAP.get(crop).getA();
        Iterable<BlockPos> blockPosList = BlockPos.iterate(
                clickedPos.getX() - 1,
                clickedPos.getY() - 0,
                clickedPos.getZ() - 1,
                clickedPos.getX() + 1,
                clickedPos.getY() + 2,
                clickedPos.getZ() + 1
        );
        boolean flag = StreamSupport.stream(blockPosList.spliterator(), false).allMatch(pos -> {
            BlockState blockState = level.getBlockState(pos);
            int cropY = clickedPos.getY();
            var PROPERTY = MAP.get(crop).getB().getA();
            int MAX_AGE = MAP.get(crop).getB().getB();

            return pos.getY() == cropY ? blockState.isIn(ModTags.ModBlockTags.CROPS_FERTIABLE_BY_FBM) && blockState.get(PROPERTY) == MAX_AGE : blockState.isOf(Blocks.AIR);
        });

        if (pContext.getHand() != Hand.MAIN_HAND) {
            return ActionResult.PASS;
        }

        if (flag && !level.isClient()) {
            return placeLogic(blockPosList, level, giantVersion, clickedPos, player);
        }

        return ActionResult.PASS;
    }

    private ActionResult placeLogic(Iterable<BlockPos> blockPosList, World level, Block giantVersion, BlockPos clickedPos, PlayerEntity player) {
        int i = 0;
        List<BlockPos> list = new ArrayList<>();
        blockPosList.forEach(list::add);

        blockPosList.forEach(pos -> {
            pos = pos.toImmutable();
            level.breakBlock(pos, false);
            level.setBlockState(pos, giantVersion.getDefaultState().with(GiantCropBlock.IS_CENTER, pos.equals(clickedPos)));
            for(int j = 0; j <= 3; j++) {
                ((ServerWorld) level).spawnParticles(new DustParticleEffect(Vec3d.unpackRgb(11162034).toVector3f(), 1.0F), clickedPos.getX() + level.random.nextDouble(), clickedPos.up().getY() + level.random.nextDouble(), clickedPos.getZ() + level.random.nextDouble()   , 1, 0, 0, 0, 0.3D);
            }
            if(level.getBlockEntity(pos) instanceof GiantCropBlockEntity entity) {
                entity.pos1 = clickedPos.mutableCopy().move(1, 2, 1);
                entity.pos2 = clickedPos.mutableCopy().move(-1, 0, -1);
            }
        });

        player.getMainHandStack().decrement(1);
        return ActionResult.success(level.isClient);
    }

    @Override
    public void appendTooltip(ItemStack pStack, @Nullable World pLevel, List<Text> pTooltipComponents, TooltipContext pIsAdvanced) {
        super.appendTooltip(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Text.translatableWithFallback("tooltip.jar_of_bonmeel.usage", "Can be applied to a fully grown 3x3 grid of the following crops: carrot, potato, wheat, beetroot and nether wart").formatted(Formatting.GOLD));
    }
}
