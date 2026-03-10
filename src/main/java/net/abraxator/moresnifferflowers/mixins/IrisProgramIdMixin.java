package net.abraxator.moresnifferflowers.mixins;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.irisshaders.iris.shaderpack.loading.ProgramGroup;
import net.irisshaders.iris.shaderpack.loading.ProgramId;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(ProgramId.class)
public class IrisProgramIdMixin {
    @Shadow
    @Mutable
    @Final
    private static ProgramId[] $VALUES;

    @Invoker("<init>")
    public static ProgramId newColor(String name, int id, ProgramGroup group, String programName, ProgramId fallback) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/irisshaders/iris/shaderpack/loading/ProgramId;$VALUES:[Lnet/irisshaders/iris/shaderpack/loading/ProgramId;", shift = At.Shift.AFTER))
    private static void DD$addCustomColor(CallbackInfo ci) {
        List<ProgramId> list = new ArrayList<>(Arrays.asList($VALUES));

        ProgramId id = newColor("GOLD_ITEM", list.size(), ProgramGroup.Gbuffers, "moresnifferflowers:gold_item", ProgramId.Block);
        MoreSnifferFlowers.goldProgramId = id;
        list.add(id);

        $VALUES = list.toArray(new ProgramId[0]);
    }
}
