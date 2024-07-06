package net.abraxator.moresnifferflowers.mixins;

import io.github.jamalam360.rightclickharvest.RightClickHarvestModInit;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RightClickHarvestModInit.class)
public class RightClickHarvestModInitMixin {
    @Inject(method = "onBlockUse", at = @At("TAIL"), cancellable = true)
    public void moresnifferflowers$onBlockUse(Player player, Level world, InteractionHand hand, BlockHitResult hitResult, boolean initialCall, CallbackInfoReturnable<InteractionResult> cir) {
        if(player.getItemInHand(hand).is(ModItems.JAR_OF_BONMEEL.get())) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}
