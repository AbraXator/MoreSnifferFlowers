package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blocks.blockentities.CropressorBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CropressorBlock extends Block implements ModEntityBlock {
    public static final EnumProperty<Part> PART = EnumProperty.of("part", Part.class);

    public CropressorBlock(Settings pProperties) {
        super(pProperties);
    }

    @Override
    public ActionResult onUse(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockHitResult pHit) {
        if(!pLevel.isClient && pLevel.getBlockEntity(pPos) instanceof CropressorBlockEntity entity) {
            if(entity.isHasFinished()) {
                pPlayer.giveItemStack(entity.getInventory().get(0));
            } else {
                entity.addItem(pPlayer.getMainHandStack());
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return tickerHelper(pLevel);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pPos, BlockState pState) {
        return new CropressorBlockEntity(pPos, pState);
    }

    public static enum Part implements StringIdentifiable {
        SHLONGADOODLE("shlongadoodle"),
        SCRUNCLO("scrunclo");

        private String name;

        Part(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String asString() {
            return name;
        }
    }
}
