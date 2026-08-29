package net.abraxator.moresnifferflowers.entities.boat;

import net.abraxator.moresnifferflowers.init.MSFBlocks;
import net.abraxator.moresnifferflowers.init.MSFEntityTypes;
import net.abraxator.moresnifferflowers.init.MSFItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.function.IntFunction;

public class ModBoatEntity extends Boat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(ModBoatEntity.class, EntityDataSerializers.INT);

    public ModBoatEntity(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    public ModBoatEntity(Level level, double pX, double pY, double pZ) {
        this(MSFEntityTypes.MOD_CORRUPTED_BOAT.get(), level);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    @Override
    public Item getDropItem() { 
        return switch (getModVariant()) {
            case CORRUPTED -> MSFItems.CORRUPTED_BOAT.get();
            case VIVICUS -> MSFItems.VIVICUS_BOAT.get();
        };
    }


    public void setVariant(Type pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    public Type getModVariant() {
        return Type.byId(this.entityData.get(DATA_ID_TYPE));
    }


    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE, Type.CORRUPTED.ordinal());
    }

    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putString("Type", this.getModVariant().getSerializedName());
    }

    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Type", 8)) {
            this.setVariant(Type.byName(tag.getString("Type")));
        }
    }

    public enum Type implements StringRepresentable {
        CORRUPTED(MSFBlocks.CORRUPTED_PLANKS.get(), "corrupted"),
        VIVICUS(MSFBlocks.VIVICUS_PLANKS.get(),"vivicus" );

        private final String name;
        private final Block planks;
        @SuppressWarnings("deprecation")
        public static final EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
        private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

        Type(Block planks, String name) {
            this.name = name;
            this.planks = planks;
        }

        public String getSerializedName() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }

        public Block getPlanks() {
            return this.planks;
        }

        public String toString() {
            return this.name;
        }

        /**
         * Get a boat type by its enum ordinal
         */
        public static Type byId(int id) {
            return BY_ID.apply(id);
        }

        public static Type byName(String name) {
            return CODEC.byName(name, CORRUPTED);
        }
    }
}
