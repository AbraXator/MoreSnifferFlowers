package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blockentities.GiantCropBlockEntity;
import net.abraxator.moresnifferflowers.client.particle.GiantCropParticle;
import net.abraxator.moresnifferflowers.init.ModParticles;
import net.abraxator.moresnifferflowers.items.JarOfBonmeelItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
import net.minecraft.world.ticks.ScheduledTick;
import org.antlr.runtime.tree.TreeRuleReturnScope;
import org.jetbrains.annotations.Nullable;

public class GiantCropBlock extends Block implements ModEntityBlock {
    public static final EnumProperty<ModelPos> MODEL_POSITION = EnumProperty.create("model_pos", ModelPos.class);

    public GiantCropBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(MODEL_POSITION, ModelPos.NONE));
    }

    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if(pLevel.getBlockEntity(pPos) instanceof GiantCropBlockEntity entity) {
            entity.canGrow = true;
        }
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if(!pState.getValue(MODEL_POSITION).equals(ModelPos.NONE)) {
            pLevel.getBlockTicks().schedule(new ScheduledTick<>(this, pPos, pLevel.getGameTime() + 7, pLevel.nextSubTickCount()));
            if(pState.getValue(MODEL_POSITION).equals(ModelPos.NED) && pLevel instanceof ServerLevel level) {
                level.sendParticles(ModParticles.GIANT_CROP.get(), pPos.getCenter().x - 1, pPos.getCenter().y - 1, pPos.getCenter().z + 1, 1, 0, 0, 0, 0);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(MODEL_POSITION);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GiantCropBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return tickerHelper(pLevel);
    }
    
    public static enum ModelPos implements StringRepresentable {
        NED("ned"),
        NEU("neu"),
        NWD("nwd"),
        NWU("nwu"),
        SED("sed"),
        SEU("seu"),
        SWD("swd"),
        SWU("swu"),
        NONE("none");

        public final String name;
        public final double x;
        public final double y;
        public final double z;

        ModelPos(String name, double x, double y, double z) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        ModelPos(String name) {
            this(name, 0, 0, 0);
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
