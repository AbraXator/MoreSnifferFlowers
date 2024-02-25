package net.abraxator.moresnifferflowers.items;

import com.google.common.collect.Maps;
import net.abraxator.moresnifferflowers.blocks.CaulorflowerBlock;
import net.abraxator.moresnifferflowers.init.ModAdvancementCritters;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.item.*;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;

public class DyespriaItem extends Item {
    public static final Map<DyeColor, Integer> COLORS = Util.make(Maps.newLinkedHashMap(), dyeColorHexFormatMap -> {
        dyeColorHexFormatMap.put(DyeColor.WHITE, 0xFFD9D9D9);
        dyeColorHexFormatMap.put(DyeColor.LIGHT_GRAY, 0xFF696969);
        dyeColorHexFormatMap.put(DyeColor.GRAY, 0xFF4C4C4C);
        dyeColorHexFormatMap.put(DyeColor.BLACK, 0xFF1E1E1E);
        dyeColorHexFormatMap.put(DyeColor.BROWN, 0xFF724727);
        dyeColorHexFormatMap.put(DyeColor.RED, 0xFFD0021B);
        dyeColorHexFormatMap.put(DyeColor.ORANGE, 0xFFFF8300);
        dyeColorHexFormatMap.put(DyeColor.YELLOW, 0xFFFFE100);
        dyeColorHexFormatMap.put(DyeColor.LIME, 0xFF41CD34);
        dyeColorHexFormatMap.put(DyeColor.GREEN, 0xFF287C23);
        dyeColorHexFormatMap.put(DyeColor.CYAN, 0xFF00AACC);
        dyeColorHexFormatMap.put(DyeColor.LIGHT_BLUE, 0xFF4F3FE0);
        dyeColorHexFormatMap.put(DyeColor.BLUE, 0xFF2E388D);
        dyeColorHexFormatMap.put(DyeColor.PURPLE, 0xFF963EC9);
        dyeColorHexFormatMap.put(DyeColor.MAGENTA, 0xFFD73EDA);
        dyeColorHexFormatMap.put(DyeColor.PINK, 0xFFF38BAA);
    });

    public DyespriaItem(Settings pProperties) {
        super(pProperties);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext pContext) {
        PlayerEntity player = pContext.getPlayer();
        World level = pContext.getWorld();
        BlockPos blockPos = pContext.getBlockPos();
        BlockState blockState = level.getBlockState(blockPos);
        ItemStack stack = pContext.getStack();

        if (pContext.getHand() != Hand.MAIN_HAND) {
            return ActionResult.PASS;
        }

        if(blockState.isIn(ModBlocks.CAULORFLOWER.get()) && player instanceof ServerPlayerEntity serverPlayer && level instanceof ServerWorld serverLevel) {
            if (!player.isSneaking()) {
                colorOne(stack, serverLevel, blockPos, blockState);
            } else {
                colorColumn(stack, serverLevel, blockPos);
            }
            level.playSound(player, blockPos, SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS);
            ModAdvancementCritters.USED_DYESPRIA.get().trigger(serverPlayer);
            return ActionResult.success(level.isClient);
        }
        return ActionResult.PASS;
    }

    public void colorOne(ItemStack stack, ServerWorld level, BlockPos blockPos, BlockState blockState) {
        Dye dye = getDye(stack);
        Random randomSource = level.random;

        if(blockState.get(CaulorflowerBlock.COLOR).equals(dye.color)) {
            return;
        }

        if(!dye.isEmpty()) {
            level.setBlockState(blockPos, blockState.with(CaulorflowerBlock.COLOR, dye.color).with(CaulorflowerBlock.HAS_COLOR, true), 3);
            setDye(stack, stackFromDye(new Dye(dye.color, dye.amount - 1)));
        } else {
            level.setBlockState(blockPos, blockState.with(CaulorflowerBlock.HAS_COLOR, false), 3);
        }

        particles(randomSource, level, dye, blockPos);

        level.updateListeners(blockPos, blockState, blockState, 1);
    }

    private void colorColumn(ItemStack stack, ServerWorld level, BlockPos blockPos) {
        BlockPos posUp = blockPos.up().mutableCopy();
        BlockPos posDown = blockPos.mutableCopy();
        while (level.getBlockState(posUp).isIn(ModBlocks.CAULORFLOWER.get())) {
            colorOne(stack, level, posUp, level.getBlockState(posUp));
            posUp = posUp.up();
        }

        while (level.getBlockState(posDown).isIn(ModBlocks.CAULORFLOWER.get())) {
            colorOne(stack, level, posDown, level.getBlockState(posDown));
            posDown = posDown.down();
        }
    }

    @Override
    public boolean onClicked(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickType pAction, PlayerEntity pPlayer, StackReference pAccess) {
        if(pAction == ClickType.RIGHT && pSlot.canTakePartial(pPlayer)) {
            if(pOther.isEmpty()) {
                pAccess.set(remove(pStack));
                playRemoveOneSound(pPlayer);
            } else {
                ItemStack itemStack = add(pStack, pOther);
                pAccess.set(itemStack);
                if(itemStack.isEmpty()) {
                    this.playInsertSound(pPlayer);
                }
            }
            return true;
        }
        return false;
    }

    private void particles(Random randomSource, ServerWorld level, Dye dye, BlockPos blockPos) {
        for(int i = 0; i <= randomSource.nextBetween(5, 10); i++) {
            level.spawnParticles(
                    new DustParticleEffect(dye.isEmpty() ? Vec3d.unpackRgb(14013909).toVector3f() : Vec3d.unpackRgb(colorForDye(dye.color)).toVector3f(), 1.0F),
                    blockPos.getX() + randomSource.nextDouble(),
                    blockPos.getY() + randomSource.nextDouble(),
                    blockPos.getZ() + randomSource.nextDouble(),
                    1, 0, 0, 0, 0.3D);
        }
    }

    private ItemStack add(ItemStack dyespria, ItemStack dyeToInsert) {
        Dye dye = getDye(dyespria);
        if(dyeToInsert.getItem() instanceof DyeItem) {
            if(!dye.isEmpty()) {
                int amountInside = dye.amount;
                int freeSpace = 64 - amountInside;
                int totalDye = Math.min(amountInside + dyeToInsert.getCount(), 64); //AMOUNT TO INSERT INTO DYESPRIA
                if (!dyeCheck(dye, dyeToInsert)) {
                    //DYESPRIA HAS DIFFERENT DYE AND YOU INSERT ANOTHER 😝
                    setDye(dyespria, dyeToInsert);
                    dyeToInsert.decrement(totalDye);
                    return stackFromDye(dye);
                } else if(freeSpace <= 0) {
                    //DYESPRIA HAS NO SPACE 😇
                    return dyeToInsert;
                }

                //DYESPRIA HAS DYE AND YOU INSERT SAME DYE 🥳
                setDye(dyespria, dye.color, totalDye);
                dyeToInsert.decrement(totalDye);
                return dyeToInsert;
            } else {
                //DYESPRIA HAS NO DYE 🥸
                setDye(dyespria, dyeToInsert);
                return ItemStack.EMPTY;
            }
        }
        return dyeToInsert;
    }

    private boolean dyeCheck(Dye dye, ItemStack dyeToInsert) {
        DyeItem dyeToInsertItem = ((DyeItem) dyeToInsert.getItem());

        return dye.color.equals(dyeToInsertItem.getColor());
    }

    private ItemStack remove(ItemStack pStack) {
        Dye dye = getDye(pStack);
        if(!dye.isEmpty()) {
            setDye(pStack, DyeColor.WHITE, 0);
            return stackFromDye(dye);
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void appendTooltip(ItemStack pStack, @Nullable World pLevel, List<Text> pTooltipComponents, TooltipContext pIsAdvanced) {
        Dye dye = getDye(pStack);
        Text usage = Text.translatableWithFallback("tooltip.dyespria.usage", "Right click with dye to insert \nRight click caulorflower to repaint \nSneak to apply to the whole column \n").formatted(Formatting.GOLD);

        if(!dye.isEmpty()) {
            Text name = Text
                    .literal(dye.amount + " - " + WordUtils.capitalizeFully(dye.color
                                    .getName()
                                    .toLowerCase()
                                    .replaceAll("[^a-z_]", "")
                                    .replaceAll("_", " ")))
                    .fillStyle(Style.EMPTY
                            .withColor(colorForDye(dye.color)));

            pTooltipComponents.add(usage);
            pTooltipComponents.add(name);
        } else {
            pTooltipComponents.add(usage);
            pTooltipComponents.add(Text.translatableWithFallback("tooltip.dyespria.empty", "Empty").formatted(Formatting.GRAY));
        }
    }

    public static void setDye(ItemStack stack, ItemStack dye) {
        setDye(stack, ((DyeItem) dye.getItem()).getColor(), dye.getCount());
    }

    public static void setDye(ItemStack stack, DyeColor dyeColor, int amount) {
        NbtCompound tag = stack.getOrCreateNbt();
        tag.putInt("color", dyeColor.getId());
        tag.putInt("amount", amount);
        stack.setNbt(tag);
    }

    public static Dye getDye(ItemStack itemStack) {
        NbtCompound tag = itemStack.getOrCreateNbt();
        int colorId = tag.getInt("color");
        int amount = tag.getInt("amount");

        return new Dye(DyeColor.byId(colorId), amount);
    }

    public static ItemStack stackFromDye(Dye dye) {
        return new ItemStack(DyeItem.byColor(dye.color), dye.amount);
    }

    public static int colorForDye(DyeColor dyeColor) {
        return COLORS.getOrDefault(dyeColor, -1);
    }

    private void playRemoveOneSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE, 0.8F, 0.8F + pEntity.method_48926().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F, 0.8F + pEntity.method_48926().getRandom().nextFloat() * 0.4F);
    }

    public record Dye(DyeColor color, int amount) {
        public boolean isEmpty() {
            return Dye.this.amount <= 0;
        }
    }
}
