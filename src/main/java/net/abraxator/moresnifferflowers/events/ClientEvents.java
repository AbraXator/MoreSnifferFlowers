package net.abraxator.moresnifferflowers.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.capability.GluedCapability;
import net.abraxator.moresnifferflowers.capability.SlipperyCapability;
import net.abraxator.moresnifferflowers.client.renderer.custom.BlockPatternRenderer;
import net.abraxator.moresnifferflowers.entities.GluingGumEntity;
import net.abraxator.moresnifferflowers.init.*;
import net.abraxator.moresnifferflowers.networking.toServer.DyespriaModePacket;
import net.abraxator.moresnifferflowers.networking.toServer.PatternspriaModePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Set;

@EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onInputMouseScrolling(InputEvent.MouseScrollingEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player.isCrouching() && player.getMainHandItem().is(MSFItems.DYESPRIA.get())) {
            event.setCanceled(true);
            PacketDistributor.sendToServer(new DyespriaModePacket((int) event.getScrollDeltaY()));
        }
        if(player.isCrouching() && player.getMainHandItem().is(MSFItems.PATTERNSPRIA.get())) {
            event.setCanceled(true);
            PacketDistributor.sendToServer(new PatternspriaModePacket((int) event.getScrollDeltaY()));
        }
    }

    @SubscribeEvent
    public static void addSectionGeometry(AddSectionGeometryEvent event) {
        Set<BlockPatternRenderer.BlockPatternQuad> cache = BlockPatternRenderer.cache(event.getLevel(), event.getSectionOrigin());
        if (!cache.isEmpty()) {
            event.addRenderer(ctx -> BlockPatternRenderer.renderAll(ctx, cache));
        }
    }


    @SubscribeEvent
    public static void renderLiving(RenderLivingEvent.Post<?, ?> event) {
        LivingEntity entity = event.getEntity();

        GluedCapability cap = entity.getData(MSFDataAttachments.GLUED);
        if (cap.isGlued) {
            Vec3 pos = entity.position();
            Minecraft minecraft = Minecraft.getInstance();
            PoseStack poseStack = event.getPoseStack();

            GluingGumEntity gum = new GluingGumEntity(entity.level());
            gum.setPos(pos.x, pos.y, pos.z);

            poseStack.pushPose();
            float yOff = 0;
            if (entity instanceof Player player && player.isCrouching()) {
                yOff += 0.13f;
            }
            minecraft.getEntityRenderDispatcher().render(gum, 0, yOff, 0, entity.yBodyRot, event.getPartialTick(), poseStack, event.getMultiBufferSource(), event.getPackedLight());
            poseStack.popPose();
        }
    }

    @SubscribeEvent
    public static void renderPlayer(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        PoseStack pose = event.getPoseStack();

        if (player.hasEffect(MSFEffects.SLIPPERY)){
            SlipperyCapability cap = player.getData(MSFDataAttachments.SLIPPERY);

            if (cap.isFallen){
                pose.mulPose(Axis.ZP.rotationDegrees(180.0F));
                pose.translate(0.0D, -0.5D, 0.0D);
            }

        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.getDeltaMovement() != Vec3.ZERO && player.level().getGameTime() % 10 == 0){
         //   ClientRegistration.getBlockPatternRenderer().markDirty();
        }
    }

}
