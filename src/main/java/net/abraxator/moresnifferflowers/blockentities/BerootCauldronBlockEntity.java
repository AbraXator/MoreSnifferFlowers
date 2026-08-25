package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.blocks.BerootCauldronBlock;
import net.abraxator.moresnifferflowers.client.ModColorHandler;
import net.abraxator.moresnifferflowers.components.BetterNonNullList;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.networking.toClient.BerootCauldronSuckPacket;
import net.abraxator.moresnifferflowers.networking.ModPacketHandler;
import net.abraxator.moresnifferflowers.nutrition.Nutrition;
import net.abraxator.moresnifferflowers.nutrition.NutritionEntry;
import net.abraxator.moresnifferflowers.nutrition.NutritionType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.nikdo53.tinymultiblocklib.block.AbstractMultiBlock;
import net.nikdo53.tinymultiblocklib.block.IMultiBlock;
import net.nikdo53.tinymultiblocklib.blockentities.AbstractMultiBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class BerootCauldronBlockEntity extends AbstractMultiBlockEntity implements IModBlockEntity {
    public int beetroots = 0;
    private final int foodLimit = 8;
    public BetterNonNullList<ItemStack> ingredients = BetterNonNullList.withSize(foodLimit, ItemStack.EMPTY);
    public int itemRot = 0;
    public ItemStack soup = ItemStack.EMPTY;
    public int soupCount = 0;
    public boolean isCrafted = false;
    public final int MAX_SOUP_COUNT = 6;
    private static final int BEETROOT_LIMIT = 4;
    int spoonRotation = 0;
    public boolean redSoup = true;
    int craftingTicks = -1;

    public BerootCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BEROOT_CAULDRON.get(), pos, state);
    }

    public InteractionResult addItem(ItemStack itemStack, Player player) {
        if(itemStack.is(ModItems.CROPRESSED_BEETROOT.get()) && this.beetroots < BEETROOT_LIMIT && !this.isCrafted) {
            addBeetroot(itemStack, player);
            this.redSoup = true;
        } else if (!itemStack.isEmpty() && !ingredients.isFull() && !Nutrition.getNutritionForItem(itemStack.getItem()).isEmpty() && !this.isCrafted && this.beetroots > 0) {
            addIngredient(itemStack, player);
            this.redSoup = false;
        } else if(itemStack.is(Items.BOWL) && isCrafted) {
           return giveSoup(itemStack, player);
        } else return InteractionResult.PASS;

        setChanged();
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
    
    public void craft() {
        //initialize all variables for soup creation
        if(ingredients.isFullyDefault()) {
            return;
        }

        this.isCrafted = true;

        Map<NutritionType, Integer> map = new HashMap<>();
        ItemStack soup = ModItems.ROOTED_SOUP.get().getDefaultInstance();
        CompoundTag tag = new CompoundTag();
        int neutral = 0;
        this.ingredients.validStream().forEach(stack -> {
                    Nutrition nutrition = Nutrition.getNutritionForItem(stack.getItem());
                    nutrition.getNutritionEntries().forEach(entry -> 
                            map.merge(entry.nutrition(), entry.weight(), Integer::sum));
                }
        );
        
        List<NutritionEntry> entryList = new ArrayList<>(map.entrySet()
                .stream()
                .map((Map.Entry<NutritionType, Integer> entry) -> new NutritionEntry(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(NutritionEntry::weight))
                .toList());
        int sat = ingredients.validStream()
                .filter(itemStack -> itemStack.getFoodProperties(null) != null)
                .mapToInt(value -> (int) value.getFoodProperties(null).getSaturationModifier()).sum();
        int food = ingredients.validStream()
                .filter(itemStack -> itemStack.getFoodProperties(null) != null)
                .mapToInt(value -> value.getFoodProperties(null).getNutrition()).sum();
        int ingredients = this.ingredients.getValidSize();
        this.soupCount = this.beetroots + (ingredients / 4);
        int soupFood = 6 + (food / ingredients);
        float soupSat = 7 + ((float) sat / ingredients);
        soupSat = (soupSat / soupFood) / 2f; // because mojang made it relative
        
        //calculate neutral factor
        for(NutritionEntry nutritionEntry : entryList) {
            if(nutritionEntry.nutrition().equals(NutritionType.NEUTRAL)) {
                neutral += nutritionEntry.weight();
            }
        }

        int maxSoupUses = 6;
        int soupUses = Mth.clamp(Math.round(food / 3f) + (ingredients - foodLimit / 2) / 2, 1, maxSoupUses);


        //For Cookbook unlocking
        ListTag ingredientListTag = new ListTag();
        for (ItemStack stack : this.ingredients.validStream().toList() ){
            CompoundTag ingredientTag = new CompoundTag();
            stack.save(ingredientTag);
            ingredientListTag.add(ingredientTag);
        }
        tag.put("ingredients", ingredientListTag);

        //values into tag
        tag.putInt("soupFood", soupFood);
        tag.putFloat("soupSat", soupSat);
        tag.putInt("soupCount", soupUses);
        tag.putInt("soupCountMax", soupUses);
        tag.putInt("color", ModColorHandler.RGBtoInt(color()));

        float positiveThreshold = 0.5f;
        float negativeThreshold = 0.75f;
        float perfectMix = (positiveThreshold + negativeThreshold) / 2f;
        int maxAmp = 4;
        float ampThresholds = (perfectMix - positiveThreshold) / maxAmp;

        int minDuration = 60*20; // A minute
        int duration = minDuration + neutral / 100 * 60*20 ; // max 9 minutes

        int totalFlavour = 0; // for neutral effect calculations
        int blandThreshold = 120;
        int minFlavour = 50;

        //effect init
        ListTag effectTag = new ListTag();
        for (NutritionEntry nutritionEntry : entryList) {
            if (!nutritionEntry.nutrition().equals(NutritionType.NEUTRAL)) {
                totalFlavour += nutritionEntry.weight();
                float ratio = nutritionEntry.weight() / (neutral + 1f);
                int amplifier = 1;
                Boolean positive = null;


                if (ratio > negativeThreshold) {
                    amplifier = Math.round((ratio - negativeThreshold) / ampThresholds);
                    positive = false;
                    duration /= 2;
                } else if (ratio > positiveThreshold) {
                    positive = true;
                    amplifier = maxAmp;
                    float inaccuracy = Mth.abs(ratio - perfectMix);
                    amplifier -= Math.round(inaccuracy / ampThresholds);
                }

                amplifier = Math.max(Math.min(amplifier, maxAmp), 1);

                if (positive != null && nutritionEntry.weight() >= minFlavour) {
                    CompoundTag compoundTag = new CompoundTag();
                    compoundTag.putInt("nutritionType", nutritionEntry.nutrition().ordinal());
                    compoundTag.putBoolean("positive", positive);
                    compoundTag.putInt("dur", duration);
                    compoundTag.putInt("amp", amplifier);
                    effectTag.add(compoundTag);
                }
            }
        }

        //Neutral effects
        if (effectTag.isEmpty()){
            if (totalFlavour < blandThreshold){
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putInt("nutritionType", NutritionType.NEUTRAL.ordinal());
                compoundTag.putBoolean("positive", false);
                compoundTag.putInt("dur", duration);
                compoundTag.putInt("amp", 1);
                effectTag.add(compoundTag);

            }else {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putInt("nutritionType", NutritionType.NEUTRAL.ordinal());
                compoundTag.putBoolean("positive", true);
                compoundTag.putInt("dur", duration);
                compoundTag.putInt("amp", Math.min(Mth.floor((float) (totalFlavour - blandThreshold) / 100 + 1), 3));
                effectTag.add(compoundTag);

            }

        }

        tag.put("effects", effectTag);

        soup.setTag(tag);
        this.soup = soup;
        setChanged();
    }

    @Override
    public void tick(Level level){
        if (!isCenter()) return;

        suckInItems(level, this.getCenter());

        if (craftingTicks > 0) {
            craftingTicks--;
            spoonRotation += 10;

            if (craftingTicks == 0) {
                if(this.spoonRotation >= this.soupCount * 180) {
                    craft();
                    this.isCrafted = true;
                    this.spoonRotation = this.spoonRotation % 360;

                    Vec3 center = getMiddle();
                    for (int i = 0; i < 360; i++) {
                        if (i % 20 == 0) {
                            this.level.addParticle(
                                    ParticleTypes.LAVA,
                                    center.x, center.y, center.z,
                                    Mth.cos(i), 0.5F, Mth.sin(i));
                        }
                    }
                }
            }
        }
    }

    public InteractionResult useWithoutItem(Level level) {
        if (!isCenter()) return InteractionResult.FAIL;

        if (!ingredients.isFullyDefault() && !this.isCrafted && craftingTicks <= 0) {
            this.craftingTicks = 9;
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return InteractionResult.PASS;
    }

    @Override
    public void clientTick(Level level) {
        if (!isCenter()) return;

        if (craftingTicks > 0){
            itemRot += 10;
        } else {
            itemRot++;
        }

        tick(level);
    }

    private boolean valuesClose(List<NutritionEntry> entryList, int tolerance) {
        List<Integer> weights = entryList.stream().map(NutritionEntry::weight).toList();
        int min = Collections.min(weights);
        int max = Collections.max(weights);
        
        return (max - min) <= tolerance;
    }
    
    public boolean hasSoup() {
        return this.soupCount > 0 && !soup.equals(ItemStack.EMPTY);
    }


    private InteractionResult giveSoup(ItemStack itemStack, Player player) {
        boolean b = !hasSoup();
        boolean b1 = !this.isCrafted;
        boolean isServer = !level.isClientSide;

        if (b || b1){
            return InteractionResult.FAIL;
        }
        itemStack.shrink(1);

        ItemStack soup1 = this.soup.copy();
        player.setItemInHand(InteractionHand.MAIN_HAND, ItemUtils.createFilledResult(player.getItemInHand(InteractionHand.MAIN_HAND), player, soup1, false));
        this.soupCount -= 1;
        if (this.soupCount <= 0){
            this.soup = ItemStack.EMPTY;
            clearIngredients();
        }

        setChanged();
        return InteractionResult.SUCCESS;
    }

    private void addIngredient(ItemStack itemStack, Player player) {
        this.ingredients.set(ingredients.getFirstEmptySlot() ,new ItemStack(itemStack.getItem(), 1));
        int ingredients = this.ingredients.getValidSize();
        this.soupCount = this.beetroots + (ingredients / 4);

        if (level.isClientSide){
            Vec3 center = getMiddle();
            for (int i = 0; i < 360; i++) {
                if(i % 20 == 0) {
                    this.level.addParticle(
                            new DustParticleOptions(color(itemStack.getItem()).scale(1/255D).toVector3f(), 1.0F),
                            center.x, center.y, center.z,
                            Mth.cos(i), 0.5F, Mth.sin(i));
                }
            }
        }

        itemStack.shrink(1);
        setChanged();
    }
    
    private void addBeetroot(ItemStack itemStack, Player player) {
        this.beetroots++;
        int ingredients = this.ingredients.getValidSize();
        this.soupCount = this.beetroots + (ingredients / 4);

        itemStack.shrink(1);

        if(!this.level.isClientSide) {
            return;
        }

        Vec3 center = getMiddle();
        for (int i = 0; i < 360; i++) {
            if(i % 20 == 0) {
                this.level.addParticle(
                        new DustParticleOptions(Vec3.fromRGB24(0x0f44336).toVector3f(), 1.0F),
                        center.x, center.y, center.z,
                        Mth.cos(i), 0.5F, Mth.sin(i));
            }
        }
        setChanged();
    }


    public void suckInItems(Level level, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        switch (level.getBlockState(pos).getValue(HorizontalDirectionalBlock.FACING)){
            case EAST -> x +=1;
            case NORTH -> {
                x += 1;
                z -= 1;
            }
            case WEST -> z -= 1;
        }

        for(ItemEntity itementity : getItemsAtAndAbove(level, new BlockPos(x,y,z))) {
            ItemStack itemStack = itementity.getItem().copy();

            if (addItem(itemStack, null).consumesAction()) {
                itementity.setItem(itemStack);
            }
        }
    }

    public static List<ItemEntity> getItemsAtAndAbove(Level level, BlockPos pos) {
        return BerootCauldronBlock.SHAPE_INSIDE.toAabbs().stream().flatMap((p_155558_) -> level.getEntitiesOfClass(ItemEntity.class, p_155558_.move(pos.getX(), pos.getY(), pos.getZ() + 1.125), EntitySelector.ENTITY_STILL_ALIVE).stream()).collect(Collectors.toList());
    }
    
    public float getItemsRotation(float partialTick) {
        if (this.craftingTicks > 0) partialTick *= 10;
        return (this.itemRot + partialTick) * 2;
    }
    
    public float getSpoonRotation(float partialTick) {
        if(this.craftingTicks > 0) {
           if (this.spoonRotation % 9 != 0) return (this.spoonRotation + partialTick);
        }

        return this.spoonRotation;
    }
    
    private Vec3 getMiddle() {
        float x = -0.5F;
        float y = 1.5F;
        float z = 0.5F;

        switch (this.getBlockState().getValue(HorizontalDirectionalBlock.FACING)){
            case EAST -> x +=1;
            case NORTH -> {
                x += 1;
                z -= 1;
            }
            case WEST -> z -= 1;
        }

        return this.getCenter().getCenter().add(x, y, z);
    }

    public void clearIngredients() {
        this.ingredients.clear();
        this.beetroots = 0;
        this.isCrafted = false;
    }


    
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!isCenter()) return;

        tag.putInt("beetroots", this.beetroots);
        ListTag items = new ListTag();
        for (ItemStack stack : ingredients) {
            CompoundTag itemTag = new CompoundTag();
            stack.save(itemTag);
            items.add(itemTag);
        }
        tag.put("ingredients", items);
        tag.putInt("soupCount", this.soupCount);
        tag.putInt("crafting", this.craftingTicks);
        tag.putBoolean("isCrafted", this.isCrafted);
        tag.putBoolean("redSoup", this.redSoup);
        tag.putInt("spoonRotation", this.spoonRotation);

        if(!this.soup.isEmpty()) {
            CompoundTag soupTag = new CompoundTag();
            this.soup.save(soupTag);
            tag.put("soup", soupTag);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (!isCenter()) return;

        this.ingredients.clear();
        this.beetroots = tag.getInt("beetroots");

        ListTag ingredientsTag = tag.getList("ingredients", 10);
        for (int i = 0; i < this.ingredients.size(); i++) {
            if (i < ingredientsTag.size()) {
                CompoundTag itemTag = ingredientsTag.getCompound(i);
                ItemStack stack = ItemStack.of(itemTag);
                this.ingredients.set(i, stack);
            } else {
                this.ingredients.set(i, ItemStack.EMPTY);
            }
        }

        this.soupCount = tag.getInt("soupCount");
        this.craftingTicks = tag.getInt("crafting");
        this.isCrafted = tag.getBoolean("isCrafted");
        this.redSoup = tag.getBoolean("redSoup");
        this.spoonRotation = tag.getInt("spoonRotation");


        boolean soup1 = tag.contains("soup");

        if(soup1) {
            this.soup = ItemStack.of(tag.getCompound("soup"));
        } else {
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

/*    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }*/


    public Vec3 color() {
        return color(Items.AIR);
    }

    public Vec3 color(Item item) {
        int r = 0;
        int g = 0;
        int b = 0;

        Item latestIngredient = Items.AIR;
        if (!ingredients.isFullyDefault()){
            latestIngredient = ingredients.getLastValid().getItem();
        }

        if (!item.equals(Items.AIR))
            latestIngredient = item;

        int loop = this.isCrafted ? this.ingredients.size() : 1;

        if (!latestIngredient.equals(Items.AIR)) {
            for (int i  = 0; i < loop; i++) {
                if (this.isCrafted) latestIngredient = this.ingredients.get(i).getItem();

                if (!latestIngredient.equals(Items.AIR)) {
                    NutritionType nutritionType = Nutrition.getLargestNutrition(latestIngredient);
                    switch (nutritionType) {
                        case SOUR -> {
                            r += 255;
                            g += 205;
                            b += 0;
                        }
                        case SALTY -> {
                            r += 190;
                            g += 233;
                            b += 233;
                        }
                        case SPICY -> {
                            r += 187;
                            g += 67;
                            b += 48;
                        }
                        case SWEET -> {
                            r += 230;
                            g += 120;
                            b += 150;
                        }
                        case NEUTRAL -> {
                            r += 140;
                            g += 102;
                            b += 30;
                        }
                    }
                }
            }
        }

        if (this.isCrafted && !ingredients.isFullyDefault()){
            r = r/ingredients.getValidSize();
            g = g/ingredients.getValidSize();
            b = b/ingredients.getValidSize();
        }

        if (this.redSoup && item.equals(Items.AIR) && !this.isCrafted) {
            r = 164;
            g = 39;
            b = 44;
        }

        return new Vec3(r,g,b);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return AABB.ofSize(this.getCenter().getCenter(), 4, 4, 4);
    }

}
