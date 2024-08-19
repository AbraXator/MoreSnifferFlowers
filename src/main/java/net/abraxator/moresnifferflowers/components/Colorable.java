package net.abraxator.moresnifferflowers.components;

import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface Colorable {
    Map<DyeColor, Integer> colorValues();

    default TagKey<Block> matchTag() {
        return null;
    }
    
    default ItemStack add(@Nullable ItemStack dyespria, Dye dyeInside, ItemStack dyeToInsert) {
        if(dyeToInsert.getItem() instanceof DyeItem) {
            if(!dyeInside.isEmpty()) {
                int amountInside = dyeInside.amount();
                int freeSpace = 64 - amountInside;
                int totalDye = Math.min(amountInside + dyeToInsert.getCount(), 64); //AMOUNT TO INSERT INTO DYESPRIA
                if (!Dye.dyeCheck(dyeInside, dyeToInsert)) {
                    //DYESPRIA HAS DIFFERENT DYE AND YOU INSERT ANOTHER 😝
                    onAddDye(dyespria, dyeToInsert, dyeToInsert.getCount());
                    dyeToInsert.shrink(totalDye);
                    return Dye.stackFromDye(dyeInside);
                } else if(freeSpace <= 0) {
                    //DYESPRIA HAS NO SPACE 😇
                    return dyeToInsert;
                }

                //DYESPRIA HAS DYE AND YOU INSERT SAME DYE 🥳
                onAddDye(dyespria, dyeToInsert, totalDye);
                dyeToInsert.shrink(totalDye);
                return dyeToInsert;
            } else {
                //DYESPRIA HAS NO DYE 🥸
                onAddDye(dyespria, dyeToInsert, dyeToInsert.getCount());
                return ItemStack.EMPTY;
            }
        }
        return dyeToInsert;
    }
    
    default void onAddDye(@Nullable ItemStack destinationStack, ItemStack dye, int amount) {}

    default void particles(RandomSource randomSource, ServerLevel level, Dye dye, BlockPos blockPos) {
        for(int i = 0; i <= randomSource.nextIntBetweenInclusive(5, 10); i++) {
            level.sendParticles(
                    new DustParticleOptions(dye.isEmpty() ? Vec3.fromRGB24(14013909).toVector3f() : Vec3.fromRGB24(Dye.colorForDye(this, dye.color())).toVector3f(), 1.0F),
                    blockPos.getX() + randomSource.nextDouble(),
                    blockPos.getY() + randomSource.nextDouble(),
                    blockPos.getZ() + randomSource.nextDouble(),
                    1, 0, 0, 0, 0.3D);
        }
    }
    
}
