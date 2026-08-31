package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.init.MSFBlockEntities;
import net.abraxator.moresnifferflowers.init.MSFStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TorchflowerBlockEntity extends BlockEntity implements IMSFBlockEntity {
    public TorchflowerBlockEntity(BlockPos pos, BlockState state) {
        super(MSFBlockEntities.TORCHFLOWER.get(), pos, state);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (state.getValue(MSFStateProperties.AGE_2) != 1) return;
        AABB area = new AABB(pos.below().east().getCenter(), pos.above(4).south().getCenter());
        for (Entity entity : level.getEntities(null, area)) {
            int distance = pos.getY() + 4 - entity.getBlockY();
            entity.igniteForSeconds(distance*2);
        }
    }
}
