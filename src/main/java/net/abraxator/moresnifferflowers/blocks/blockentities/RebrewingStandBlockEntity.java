package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.client.gui.menu.RebrewingStandMenu;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.http.conn.MultihomePlainSocketFactory;

import java.util.Arrays;
import java.util.function.Predicate;

public class RebrewingStandBlockEntity extends BaseContainerBlockEntity {
    public static final double MAX_FUEL = 16;
    public static final int DATA_PROGRESS = 0;
    public static final int DATA_FUEL = 1;
    public static final int MAX_PROGRESS = 40;
    private NonNullList<ItemStack> inv = NonNullList.withSize(6, ItemStack.EMPTY);
    int brewProgress;
    int fuel;
    public final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> brewProgress;
                case 1 -> fuel;
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0:
                    brewProgress = pValue;
                    break;
                case 1:
                    fuel = pValue;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };
    
    public RebrewingStandBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REBREWING_STAND.get(), pPos, pBlockState);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatableWithFallback("container.more_sniffer_flowers.rebrewing", "Rebrewing Stand");
    }

    @Override
    public boolean isEmpty() {
        return inv.stream().allMatch(Predicate.not(ItemStack::isEmpty));
    }
    
    public void tick(Level level) {
        //FUEL 0; OG-POTION 1; INGREDIENT 2; POTION 3 - 5;
        var fuelStack = inv.get(0);
        var ogPotionStack = inv.get(1);
        var ingredientStack = inv.get(2);
        ItemStack[] potionStack = {inv.get(3), inv.get(4), inv.get(5)};
        if(fuel <= 0 && fuel < MAX_FUEL && fuelStack.is(ModItems.CROPRESSED_NETHERWART.get())) {
            fuel++;
            fuelStack.shrink(1);
            setChanged();
        }
        
        if(canBrew()) {
            brewProgress++;
            
            if(brewProgress >= MAX_PROGRESS) {
                var ogPotion = PotionUtils.getPotion(ogPotionStack);
                ogPotion.getEffects().stream().map(instance -> {
                    var dur = instance.getDuration();
                    var amp = instance.getAmplifier();
                    if(ingredientStack.is(Items.REDSTONE)) {
                        dur = 6000;
                        amp += 2;
                    }
                    if(ingredientStack.is(Items.GLOWSTONE_DUST)) {
                        dur = 12000;
                        amp += 1;
                    }
                    
                    return new MobEffectInstance(instance.getEffect(), dur, amp);
                });
                
                Arrays.stream(potionStack).filter(itemStack -> itemStack.is(Items.GLASS_BOTTLE));
                Arrays.stream(potionStack).map(itemStack -> PotionUtils.setPotion(itemStack, ogPotion));
                fuel -= 4;
                brewProgress = 0;
            }
        }
    }
    
    private boolean canBrew() {
        boolean ret = false;
        
        for(int i = 3; i <= 5; i++) {
            if(!inv.get(i).isEmpty()) {
                ret = true;
            }
        }
        
        return ret && !inv.get(1).isEmpty() && fuel >= 1;
    }
    
    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new RebrewingStandMenu(pContainerId, pInventory, this, this.containerData);
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return pSlot >= 0 && pSlot < this.inv.size() ? this.inv.get(pSlot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return ContainerHelper.removeItem(this.inv, pSlot, pAmount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return ContainerHelper.takeItem(inv, pSlot);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        if (pSlot >= 0 && pSlot < this.inv.size()) {
            this.inv.set(pSlot, pStack);
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        this.inv.clear();
    }
}