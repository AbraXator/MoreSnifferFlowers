package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface MSFSounds {
     DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MoreSnifferFlowers.MOD_ID);

     DeferredHolder<SoundEvent, SoundEvent> CROPRESSOR_BELT = variableRange("block.cropressor.belt");
     DeferredHolder<SoundEvent, SoundEvent> DYESPRIA_PAINT = variableRange("item.dyespria.paint");
     DeferredHolder<SoundEvent, SoundEvent> BOBLING_BATTLE_DISC = variableRange("bobling_battle");

    private static DeferredHolder<SoundEvent, SoundEvent> variableRange(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(MoreSnifferFlowers.loc(name)));
    }

    interface MusicDiscs{
        DeferredRegister<JukeboxSong> MUSIC_DISCS = DeferredRegister.create(Registries.JUKEBOX_SONG, MoreSnifferFlowers.MOD_ID);

        DeferredHolder<JukeboxSong, JukeboxSong>  BOBLING_BATTLE =
                MUSIC_DISCS.register("bobling_battle", () -> makeJukeboxSong("music_disc_bobling",  BOBLING_BATTLE_DISC, 75, 1));

        private static JukeboxSong makeJukeboxSong(String name, Holder<SoundEvent> soundEvent, float lengthInSeconds, int comparatorOutput) {
            return new JukeboxSong(soundEvent, Component.translatable("item.moresnifferflowers."+name+".desc"), lengthInSeconds, comparatorOutput);
        }

        private static ResourceKey<JukeboxSong> createSong(String name) {
            return ResourceKey.create(Registries.JUKEBOX_SONG, MoreSnifferFlowers.loc(name));
        }
    }
}
