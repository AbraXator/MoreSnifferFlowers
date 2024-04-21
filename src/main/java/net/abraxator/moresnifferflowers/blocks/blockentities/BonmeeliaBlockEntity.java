package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.blocks.BonmeeliaBlock;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class BonmeeliaBlockEntity extends GrowingCropBlockEntity {
    public boolean hasBottle;
    public boolean hasHint;
    private double hintDuration;
    
    public BonmeeliaBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.BONMEELIA_BLOCK_ENTITY, pPos, pBlockState, 0.01F);
    }

    @Override
    public void tick() {
        hintLogic();
    }
    
    private void hintLogic() {
        if(hintDuration > 0) {
            hintDuration--;
        }
        else {
            hasHint = false;
            BonmeeliaBlock.displayHint(world, pos, getCachedState(), false);
        }
    }
    
    public void displayHint() {
        this.hasHint = true;
        this.hintDuration = 40;
        BonmeeliaBlock.displayHint(world, pos, getCachedState(), true);
    }

    @Override
    public void readNbt(NbtCompound pTag) {
        super.readNbt(pTag);
        hasHint = pTag.getBoolean("hint");
        hasBottle = pTag.getBoolean("bottle");
    }

    @Override
    protected void writeNbt(NbtCompound pTag) {
        super.writeNbt(pTag); 
        pTag.putBoolean("hint", hasHint);
        pTag.putBoolean("bottle", hasBottle);
    }
}
