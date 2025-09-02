package net.abraxator.moresnifferflowers.items;

import com.google.common.collect.Maps;
import net.abraxator.moresnifferflowers.blockentities.DyespriaPlantBlockEntity;
import net.abraxator.moresnifferflowers.capability.BlockPatternCapability;
import net.abraxator.moresnifferflowers.client.ModColorHandler;
import net.abraxator.moresnifferflowers.components.Colorable;
import net.abraxator.moresnifferflowers.components.Dye;
import net.abraxator.moresnifferflowers.components.DyespriaMode;
import net.abraxator.moresnifferflowers.components.EntityDistanceComparator;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModDataComponents;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.abraxator.moresnifferflowers.networking.toClient.DyespriaDisplayModeChangePacket;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class DyespriaItem extends BlockItem implements Colorable {
    public DyespriaItem(Properties properties) {
        super(ModBlocks.DYESPRIA_PLANT.get(), properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        ItemStack stack = context.getItemInHand();
        Dye dye = Dye.getDyeFromDyespria(stack);

        if (context.getHand() != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if (checkDyedBlock(blockState) || blockState.getBlock() instanceof Colorable && !dye.isEmpty() || (BlockPatternCapability.hasPattern(blockPos, level) && !player.isCrouching())) {
            DyespriaMode dyespriaMode = stack.getOrDefault(ModDataComponents.DYESPRIA_MODE, DyespriaMode.SINGLE);
            AtomicBoolean canContinueDyeing = new AtomicBoolean(true);
            DyespriaMode.DyespriaSelector dyespriaSelector = new DyespriaMode.DyespriaSelector(blockPos, blockState, getMatchTag(blockState), level, context.getClickedFace(), player.isCrouching());
            Set<BlockPos> set = dyespriaMode.getSelector().apply(dyespriaSelector);
            set.stream().sorted(new EntityDistanceComparator(blockPos)).takeWhile(t -> canContinueDyeing.get()).forEach(blockPos1 -> {
                var state = level.getBlockState(blockPos1);

                if(!Dye.getDyeFromDyespria(stack).isEmpty()) {
                    colorOne(stack, level, blockPos1, state, context.getClickedFace(), player, BlockPatternCapability.hasPattern(blockPos, level));
                } else canContinueDyeing.set(false);
            });

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return handlePlacement(blockPos, level, player, context.getHand(), stack);
    }

    private @Nullable TagKey<Block> getMatchTag(BlockState blockState) {
        return blockState instanceof Colorable colorable ? colorable.matchTag() : null;
    }
    
    private InteractionResult handlePlacement(BlockPos blockPos, Level level, Player player, InteractionHand hand, ItemStack stack) {
        ItemStack oldStack = stack.copy();
        var posForDyespria = blockPos.above();
        var blockHitResult = new BlockHitResult(posForDyespria.below().getCenter(), Direction.UP, posForDyespria.below(), false);
        var useOnCtx = new UseOnContext(level, player, hand, stack, blockHitResult);
        var result = super.useOn(useOnCtx);

        if (level.getBlockEntity(blockPos.above()) instanceof DyespriaPlantBlockEntity entity) {
            entity.dye = Dye.getDyeFromDyespria(oldStack);
            if (getDyespriaUses(oldStack) < 4) entity.dye = new Dye(entity.dye.color(), entity.dye.amount() - 1);
            entity.setChanged();
        }

        return result;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        var state = super.getPlacementState(context);
        return state == null ? null : state.setValue(ModStateProperties.AGE_3, 3);
    }

    public void colorOne(ItemStack stack, Level level, BlockPos blockPos, BlockState blockState, Direction face, Player player, boolean clickedPattern) {
        Dye dye = Dye.getDyeFromDyespria(stack);
        
        if (!canDye(blockState, dye)) {
            return;
        }

        if (BlockPatternCapability.hasPattern(blockPos, level) && !player.isCrouching() && clickedPattern){
            colorPattern(dye, level, blockPos);
            finishColoring(dye, level, stack, blockPos, face);
            return;
        }

        if(blockState.getBlock() instanceof Colorable colorable) {
            if(colorable.canBeColored(blockState, dye)) {
                colorable.colorBlock(level, blockPos, blockState, dye);
                finishColoring(dye, level, stack, blockPos, face);

            }
        } else {
            dyeNonColorableBlock(blockState, blockPos, dye.color(), level);
            finishColoring(dye, level, stack, blockPos, face);

        }

    }

    public void colorPattern(Dye dye, Level level, BlockPos pos){
       int patternId = BlockPatternCapability.getPattern(pos, level).patternId();

       int originalColor = BlockPatternCapability.getPattern(pos, level).color();
       int dyeColor = dye.color().getFireworkColor();
       int[] originalHSB = ModColorHandler.hexToRGBLarge(originalColor);
       int[] dyeHSB = ModColorHandler.hexToRGBLarge(dyeColor);

       int r =  (originalHSB[0]*5 + dyeHSB[0]) / 6;
       int g =  (originalHSB[1]*5 + dyeHSB[1]) / 6;
       int b =  (originalHSB[2]*5 + dyeHSB[2]) / 6;

       int finalColor = ((r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);

        BlockPatternCapability.recolor(level, pos, finalColor);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return !Dye.getDyeFromDyespria(stack).isEmpty();
    }

    @Override
    public int getBarColor(ItemStack stack) {
        int input = getDyespriaUses(stack)-1;
        int maxInput= 4;

        return ModColorHandler.barColorHelper(input, maxInput);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(getDyespriaUses(stack) * 13.0F / 4);
    }

    public void finishColoring(Dye dye, Level level, ItemStack dyespria, BlockPos blockPos, Direction face) {
        int uses = getDyespriaUses(dyespria) - 1;
        int dyeCount;
        
        if(uses <= 0) {
            dyeCount = dye.amount() - 1;
            setDyespriaUses(dyespria, 4);
        } else {
            dyeCount = dye.amount();
            setDyespriaUses(dyespria, uses);
        }
        
        ItemStack itemStack = Dye.stackFromDye(new Dye(dye.color(), dyeCount));
        Dye.setDyeToDyeHolderStack(dyespria, itemStack, itemStack.getCount(), getDyespriaUses(dyespria));
        if (level.isClientSide) {
            particles(level.getRandom(), level, dye, blockPos, face);
        }
    }
    
    public static int getDyespriaUses(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.COLOR, 4);
    }
    
    public static void setDyespriaUses(ItemStack stack, int uses) {
        stack.set(ModDataComponents.COLOR, uses);
    }
    
    private boolean canDye(BlockState blockState, Dye dye) {
        return (blockState.hasProperty(ModStateProperties.COLOR) && !blockState.getValue(ModStateProperties.COLOR).equals(dye.color())) || !dye.isEmpty();
    }

    public static boolean checkDyedBlock(BlockState blockState) {
        return blockState.is(ModTags.ModBlockTags.DYED);
    }

    private void dyeNonColorableBlock(BlockState blockState, BlockPos blockPos, DyeColor newColor, Level level) {
        if(!checkDyedBlock(blockState)) {
            return;
        }

        ResourceLocation location = BuiltInRegistries.BLOCK.getKey(blockState.getBlock());
        String modId = location.getNamespace();
        String blockId = location.getPath();

        if(blockId.equals("candle") || blockId.equals("shulker_box") || blockId.equals("terracotta")) {
            blockId = "white_" + blockId;
        }
        if (blockId.equals("glass") || blockId.equals("glass_pane") ){
            blockId = "white_stained_" + blockId;
        }

        String validColorName = "white|light_gray|gray|black|brown|red|orange|yellow|lime|green|cyan|light_blue|blue|purple|magenta|pink";
        String finalBlockName = blockId.replaceFirst(validColorName, newColor.getName());
        Block finalBlock = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(modId, finalBlockName));
        BlockState finalBlockState = finalBlock.defaultBlockState();

        BlockEntity originalShulker = level.getBlockEntity(blockPos);
        CompoundTag shulkerData = null;

        if(originalShulker instanceof ShulkerBoxBlockEntity entity) {
            shulkerData = entity.saveWithoutMetadata(level.registryAccess());
        }

        if (finalBlock != Blocks.AIR) level.setBlockAndUpdate(blockPos, copyAllBlockStateProperties(blockState, finalBlockState));

        if (shulkerData != null && level.getBlockEntity(blockPos) instanceof ShulkerBoxBlockEntity newShulkerBox) {
            newShulkerBox.loadFromTag(shulkerData, level.registryAccess());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> BlockState copyAllBlockStateProperties(BlockState sourceState, BlockState targetState) {
        for (Property<?> property : sourceState.getProperties()) {
            if (targetState.hasProperty(property)) {
                T value = (T) sourceState.getValue(property);
                targetState = targetState.setValue((Property<T>) property, value);
            }
        }
        return targetState;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if(action == ClickAction.SECONDARY && slot.allowModification(player)) {
            if(other.isEmpty()) {
                access.set(remove(stack));
                playRemoveOneSound(player);
            } else {
                ItemStack itemStack = add(stack, Dye.getDyeFromDyespria(stack), other);
                access.set(itemStack);
                if(itemStack.isEmpty()) {
                    this.playInsertSound(player);
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void onAddDye(@Nullable ItemStack destinationStack, ItemStack dye, int amount) {
        Dye.setDyeToDyeHolderStack(destinationStack, dye, amount);
    }

    private ItemStack remove(ItemStack stack) {
        var dye = Dye.getDyeFromDyespria(stack);
        int uses = getDyespriaUses(stack);
        
        if(!dye.isEmpty()) {
            Dye.setDyeColorToStack(stack, DyeColor.WHITE, 0);
            ItemStack returnStack = Dye.stackFromDye(new Dye(dye.color(), dye.amount() - (uses == 4 ? 0 : 1)));
            setDyespriaUses(stack, 4);
            return returnStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, components, tooltipFlag);
        Dye dye = Dye.getDyeFromDyespria(stack);
        Component usage = Component.translatableWithFallback("tooltip.dyespria.usage", "Right click with dye to insert \nRight click caulorflower to repaint \nSneak to apply to the whole column \n").withStyle(ChatFormatting.GOLD);
        var usageComponents = Arrays.stream(usage.getString().split("\n", -1))
                .filter(s -> !s.isEmpty())
                .map(String::trim);

        usageComponents.forEach(s -> components.add(Component.literal(s).withStyle(ChatFormatting.GOLD)));
        components.add(Component.empty());
        components.add(getCurrentModeComponent(getCurrentMode(stack)));
        components.add(Component.empty());
        
        if(!dye.isEmpty()) {
            var name = Component
                    .literal(dye.amount() + " - " + WordUtils.capitalizeFully(dye.color()
                            .getName()
                            .toLowerCase()
                            .replaceAll("[^a-z_]", "")
                            .replaceAll("_", " ")))
                    .withStyle(Style.EMPTY
                            .withColor(Dye.colorForDye(this, dye.color())));
            components.add(name);
        } else {
            components.add(Component.translatableWithFallback("tooltip.dyespria.empty", "Empty").withStyle(ChatFormatting.GRAY));
        }
    }

    private void playRemoveOneSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    @Override
    public Map<DyeColor, Integer> colorValues() {
        return Util.make(Maps.newLinkedHashMap(), dyeColorHexFormatMap -> {
            dyeColorHexFormatMap.put(DyeColor.WHITE, 0xFFFFFFFF);
            dyeColorHexFormatMap.put(DyeColor.LIGHT_GRAY, 0xFF9d979b);
            dyeColorHexFormatMap.put(DyeColor.GRAY, 0xFF474f52);
            dyeColorHexFormatMap.put(DyeColor.BLACK, 0xFF1d1d21);
            dyeColorHexFormatMap.put(DyeColor.BROWN, 0xFF835432);
            dyeColorHexFormatMap.put(DyeColor.RED, 0xFFce5849);
            dyeColorHexFormatMap.put(DyeColor.ORANGE, 0xFFf89635);
            dyeColorHexFormatMap.put(DyeColor.YELLOW, 0xFFffee53);
            dyeColorHexFormatMap.put(DyeColor.LIME, 0xFF80c71f);
            dyeColorHexFormatMap.put(DyeColor.GREEN, 0xFF5e7c16);
            dyeColorHexFormatMap.put(DyeColor.CYAN, 0xFF36a98c);
            dyeColorHexFormatMap.put(DyeColor.LIGHT_BLUE, 0xFF70d9e4);
            dyeColorHexFormatMap.put(DyeColor.BLUE, 0xFF4753ac);
            dyeColorHexFormatMap.put(DyeColor.PURPLE, 0xFFb15fc2);
            dyeColorHexFormatMap.put(DyeColor.MAGENTA, 0xFFd276b9);
            dyeColorHexFormatMap.put(DyeColor.PINK, 0xFFf8b0c4);
        });
    }

    public static DyespriaMode getCurrentMode(ItemStack itemStack) {
        return itemStack.getOrDefault(ModDataComponents.DYESPRIA_MODE.get(), DyespriaMode.SINGLE);
    }

    public static Component getCurrentModeComponent(DyespriaMode dyespriaMode) {
        var baseText = Component.translatable("message.more_sniffer_flowers.dyespria_mode").append(": ").withStyle(ChatFormatting.GOLD);
        var modeText = Component.literal(dyespriaMode.getSerializedName()).withStyle(dyespriaMode.getTextColor());
        return baseText.append(modeText);
    }
    
    public void changeMode(ServerPlayer player, ItemStack stack, int amount) {
        var currentMode = stack.getOrDefault(ModDataComponents.DYESPRIA_MODE, DyespriaMode.SINGLE);
        var newMode = DyespriaMode.shift(currentMode, amount);
        stack.set(ModDataComponents.DYESPRIA_MODE, newMode);
        player.displayClientMessage(DyespriaItem.getCurrentModeComponent(DyespriaMode.byIndex(newMode.ordinal())), true);
    }
}
