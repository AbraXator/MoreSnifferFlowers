package net.abraxator.moresnifferflowers.entities.behaviour;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.RandomLookAround;

public class SitAndPonderAndLookAround extends RandomLookAround {
    public SitAndPonderAndLookAround(IntProvider pInterval, float pMaxYaw, float pMinPitch, float pMaxPitch) {
        super(pInterval, pMaxYaw, pMinPitch, pMaxPitch);
    }

    @Override
    protected void start(ServerLevel pLevel, Mob pEntity, long pGameTime) {
        super.start(pLevel, pEntity, pGameTime);
    }
}
