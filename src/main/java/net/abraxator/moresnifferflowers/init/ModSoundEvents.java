package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = 
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MoreSnifferFlowers.MOD_ID);
    
    public static final DeferredHolder<SoundEvent, SoundEvent> CROPRESSOR_BELT = variableRange("block.cropressor.belt");
    public static final DeferredHolder<SoundEvent, SoundEvent> DYESPRIA_PAINT = variableRange("item.dyespria.paint");
    
    private static DeferredHolder<SoundEvent, SoundEvent> variableRange(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(MoreSnifferFlowers.loc(name)));
    }
}
