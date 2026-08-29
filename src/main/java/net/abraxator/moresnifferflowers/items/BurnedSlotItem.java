package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.init.MSFEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BurnedSlotItem extends Item {
    public BurnedSlotItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player && !player.hasEffect(MSFEffects.PANTS_ON_FIRE)) {
            player.inventoryMenu.getSlot(slotId).set(ItemStack.EMPTY);
        }
    }
}
