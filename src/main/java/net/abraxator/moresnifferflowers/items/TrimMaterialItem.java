package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.init.ModParticles;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrimMaterialItem extends Item {
    public TrimMaterialItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.translatableWithFallback("tooltip.trim_material_item.usage", "Can be used as an armor trim material").withStyle(ChatFormatting.GOLD));
    }

    /*public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide) {
            var pos = pContext.getClickedPos();
            var xo = pos.getCenter().x;
            var yo = pos.getCenter().y;
            var zo = pos.getCenter().z;
            var r = 5;
            var checkR = 2;
            Set<Vec3> set = new HashSet<>();

            for (double theta = 0; theta <= Mth.TWO_PI * 3; theta += Mth.TWO_PI / 8) {
                generateParticle(pContext, set, xo, yo, zo, r, theta, checkR);
            }

            return InteractionResult.sidedSuccess(pContext.getLevel().isClientSide);
        }

        return super.useOn(pContext);
    }

    private void generateParticle(UseOnContext pContext, Set<Vec3> set, double xo, double yo, double zo, double r, double theta, double checkR) {
        var x = xo + r * Mth.cos((float) theta);
        var yx = yo + r * Mth.sin((float) theta);
        var yz = yo + r * Mth.cos((float) theta);
        var z = zo + r * Mth.sin((float) theta);

        createAndAddParticle(pContext, set, checkR, new Vec3(x, yo, z));
        createAndAddParticle(pContext, set, checkR, new Vec3(x, yx, zo));
        createAndAddParticle(pContext, set, checkR, new Vec3(xo, yz, z));
    }

    private void createAndAddParticle(UseOnContext pContext, Set<Vec3> set, double checkR, Vec3 vec3) {
        AABB aabb = AABB.ofSize(vec3, checkR, checkR, checkR);
        if (set.stream().noneMatch(aabb::contains)) {
            pContext.getLevel().addParticle(ModParticles.CARROT.get(), vec3.x, vec3.y, vec3.z, 0, 0, 0);
            set.add(vec3);
        }
    }*/
}
