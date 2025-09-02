package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TorchflowerBlockEntity extends ModBlockEntity{
    public TorchflowerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TORCHFLOWER.get(), pos, state);
    }

    @Override
    public void tick(Level level) {
        if (getBlockState().getValue(ModStateProperties.AGE_2) != 1) return;
        AABB area = new AABB(getBlockPos().below().east().getCenter(), getBlockPos().above(4).south().getCenter());
        for (Entity entity : level.getEntities(null, area)) {
            int distance = getBlockPos().getY() + 4 - entity.getBlockY();
            entity.igniteForSeconds(distance*2);
        }
    }
}
