package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.client.gui.screen.cookbook.CookbookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BerootCookbookItem extends Item {
    public BerootCookbookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (level.isClientSide()) {
            Client.openCookbookScreen(player);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    public static class Client{
        public static void openCookbookScreen(Player player) {
            Minecraft.getInstance().setScreen(new CookbookScreen());
        }
    }
}
