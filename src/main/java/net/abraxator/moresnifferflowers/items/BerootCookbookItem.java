package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.capability.NutritionCapability;
import net.abraxator.moresnifferflowers.init.MSFDataAttachments;
import net.abraxator.moresnifferflowers.networking.toClient.BerootCookbookScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class BerootCookbookItem extends Item {
    public BerootCookbookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if(player instanceof ServerPlayer serverPlayer) {
            NutritionCapability cap = serverPlayer.getData(MSFDataAttachments.NUTRITION);
            cap.sync(player);
            PacketDistributor.sendToPlayer( serverPlayer, new BerootCookbookScreenPacket());
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
