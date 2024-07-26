package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.entities.CorruptedSlimeBallProjectile;
import net.abraxator.moresnifferflowers.entities.DragonflyProjectile;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModParticles;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.io.output.ThresholdingOutputStream;
import org.checkerframework.common.returnsreceiver.qual.This;

import java.util.HashSet;
import java.util.Set;

public class CorruptedSludgeBlockEntity extends ModBlockEntity implements GameEventListener.Provider<CorruptedSludgeBlockEntity.CorruptedSludgeListener> {
    public CorruptedSludgeListener corruptedSludgeListener;
    
    public CorruptedSludgeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CORRUPTED_SLUDGE.get(), pPos, pBlockState);
        this.corruptedSludgeListener = new CorruptedSludgeListener(new BlockPositionSource(pPos));
    }

    @Override
    public CorruptedSludgeListener getListener() {
        return corruptedSludgeListener;
    }

    public static class CorruptedSludgeListener implements GameEventListener {
        private PositionSource positionSource;

        public CorruptedSludgeListener(PositionSource positionSource) {
            this.positionSource = positionSource;
        }

        @Override
        public PositionSource getListenerSource() {
            return this.positionSource;
        }

        @Override
        public int getListenerRadius() {
            return GameEvent.BLOCK_DESTROY.value().notificationRadius();
        }

        @Override
        public boolean handleGameEvent(ServerLevel pLevel, Holder<GameEvent> pGameEvent, GameEvent.Context pContext, Vec3 pPos) {
            if(pGameEvent.is(GameEvent.BLOCK_DESTROY) && pContext.affectedState().is(ModTags.ModBlockTags.CORRUPTED_BLOCKS) && !pPos.equals(this.positionSource.getPosition(pLevel).get())) {
                var xo = this.getListenerSource().getPosition(pLevel).get().x;
                var yo = this.getListenerSource().getPosition(pLevel).get().y;
                var zo = this.getListenerSource().getPosition(pLevel).get().z;
                var r = 2.5;
                var checkR = 1.5;
                Set<Vec3> set = new HashSet<>();

                for (double theta = 0; theta <= Mth.TWO_PI * 3; theta += Mth.TWO_PI / 8) {
                    generateMob(set, xo, yo, zo, r, theta + pLevel.random.nextDouble(), checkR, pLevel);
                }
            }

            return false;
        }

        private void generateMob(Set<Vec3> set, double xo, double yo, double zo, double r, double theta, double checkR, ServerLevel pLevel) {
            var x = xo + r * Mth.cos((float) theta);
            var yx = yo + r * Mth.sin((float) theta);
            var yz = yo + r * Mth.cos((float) theta);
            var z = zo + r * Mth.sin((float) theta);

            createAndAddProjectile(set, checkR, new Vec3(x, yo, z), pLevel);
            createAndAddProjectile(set, checkR, new Vec3(x, yx, zo), pLevel);
            createAndAddProjectile(set, checkR, new Vec3(xo, yz, z), pLevel);
        }
        
        private void createAndAddProjectile(Set<Vec3> set, double checkR, Vec3 vec3, ServerLevel level) {
            AABB aabb = AABB.ofSize(vec3, checkR, checkR, checkR);
            if (set.stream().noneMatch(aabb::contains) && level.getBlockState(BlockPos.containing(vec3)).canBeReplaced()) {
                var pos = this.positionSource.getPosition(level).get();
                var random = level.random;
                var x = random.nextDouble() * 0.5;
                var y = random.nextDouble() * 0.5;
                var z = random.nextDouble() * 0.5;
                CorruptedSlimeBallProjectile projectile = new CorruptedSlimeBallProjectile(level);
                projectile.setPos(vec3);
                Vec3 dir = new Vec3(projectile.getX() - pos.x, projectile.getY() - pos.y, projectile.getZ() - pos.z).normalize().multiply(x, y, z);
                projectile.setDeltaMovement(dir);
                level.addFreshEntity(projectile);
                
                //level.sendParticles(ModParticles.CARROT.get(), vec3.x, vec3.y, vec3.z, 1, 0D, 0D, 0D, 0D);
                set.add(vec3);
            }
        }
    }
}
