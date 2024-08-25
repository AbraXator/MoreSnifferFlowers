package net.abraxator.moresnifferflowers.items;

import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.level.NoteBlockEvent;

import java.util.List;

public class CreativeTabItem extends Item {
    public CreativeTabItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        var list = BuiltInRegistries.MOB_EFFECT.stream().toList();
        var effect = Util.getRandom(list, pLevel.random);
        var stew = new ItemStack(Items.SUSPICIOUS_STEW);
        var stewComponent = new SuspiciousStewEffects(List.of(new SuspiciousStewEffects.Entry(Holder.direct(effect), Integer.MAX_VALUE)));
        
        if(pEntity instanceof Player player) {
            stew.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, stewComponent);
            player.addItem(stew);
        }
    }
}
