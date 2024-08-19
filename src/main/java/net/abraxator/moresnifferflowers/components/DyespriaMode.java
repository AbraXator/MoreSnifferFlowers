package net.abraxator.moresnifferflowers.components;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.networking.DyespriaModePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public enum DyespriaMode implements StringRepresentable {
    SINGLE("single", DyespriaSelector::single),
    COLUMN("column", DyespriaSelector::column),
    SHAPE("shape", DyespriaSelector::shape);

    public static final Codec<DyespriaMode> CODEC = StringRepresentable.fromValues(DyespriaMode::values);
    public static final IntFunction<DyespriaMode> BY_ID = ByIdMap.continuous(DyespriaMode::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    public static final StreamCodec<ByteBuf, DyespriaMode> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Enum::ordinal); 

    private final String name;
    private final Function<DyespriaSelector, Set<BlockPos>> selector;

    DyespriaMode(String name, Function<DyespriaSelector, Set<BlockPos>> selector) {
        this.name = name;
        this.selector = selector;
    }
    
    public static DyespriaMode byIndex(int index) {
        return BY_ID.apply(index);
    }

    public static DyespriaMode shift(DyespriaMode current, int amount) {
        int size = values().length;
        int currentIndex = current.ordinal();
        int newIndex = (currentIndex + amount) % size;

        return byIndex(newIndex);
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    public Function<DyespriaSelector, Set<BlockPos>> getSelector() {
        return selector;
    }

    public static record DyespriaSelector(BlockPos originalPos, BlockState blockState, @Nullable TagKey<Block> tag, Level level) {
        public Set<BlockPos> single() {
            return Set.of(originalPos());
        }
        
        public Set<BlockPos> column() {
            Set<BlockPos> ret = new HashSet<>();
            ret.add(originalPos);
            var posUp = originalPos.above().mutable();
            var posDown = originalPos.below().mutable();
            while (matchBlock(posUp)) {
                ret.add(posUp);
                posUp.move(Direction.UP);
            }
            
            while(matchBlock(posDown)) {
                ret.add(posDown);
                posDown.move(Direction.DOWN);
            }
            
            return ret;
        }
        
        public Set<BlockPos> shape() {
             return BlockPos.withinManhattanStream(originalPos, 4, 4, 4)
                     .filter(this::matchBlock)
                     .collect(Collectors.toCollection(HashSet::new));
        }
        
        private boolean matchBlock(BlockPos pos) {
            return level.getBlockState(pos).is(blockState.getBlock()) || (tag != null && level.getBlockState(pos).is(tag));
        }
    }
}
