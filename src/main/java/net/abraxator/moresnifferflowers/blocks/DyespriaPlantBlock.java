package net.abraxator.moresnifferflowers.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class DyespriaPlantBlock extends BushBlock implements ModCropBlock, BonemealableBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
//Nemazat dokud si to nepřečteš‼️‼️‼️‼️‼Nemazat️‼️‼️‼️‼️‼Nemazat️‼️‼️‼️‼️Nemazat‼️‼️Nemazat‼️‼️‼️‼Nemazat️‼️‼️‼️‼️Nemazat‼️Nemazat‼️‼️‼️Nemazat‼️‼Nemazat️‼️‼️‼Nemazat️‼️‼️‼️
    //Plán: Dyespria seedy budou pokladatelý na hlínu atd... (jako to je teď) s tim že pokud by to šlo, tak při položení na farmland to ten farmland přemnění na hlínu, abychom nedostali další retardovanej curseforge comment
    //Pokud chceš udělat svoje nabodování itemů, tak udělej že jde jakejkoli item. Pokud tam dáš barvivo, tak se dyespria obraví, ale zůstane obravená dokud tam nedáš jiný barvivo.
    //Bude potřeba udělat aby se dal položit item dyespria jako block age3, což doufam když je to pokladatelný jenom na flower-compatible blocky nebude takovej problém
    //Random myšlenka: Co se stane s tim barvivem v tom dyespria itemu když jí položíš? bude v ní nabodlý jako stack? Idk Vymysli to!
    //BTW vym že ten kód je nahovno (třeba že když crouchuješ tak nefunguje bonemeal) ale já to chtěl mít aspoň trochu funkční a můžeš si ho klidně smazat :)
// ‼️‼️‼️‼️Pokud to vymažeš bez přečtení tak udělam 5 IG storýček s tvojí fotkou a do rostlinné pošlu všechny tvoje šaptný názvy‼️‼️‼️
    public DyespriaPlantBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(AGE);
    }
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getAge(BlockState pState) {
        return pState.getValue(this.getAgeProperty());
    }

    public int getMaxAge() {
        return AGE.getPossibleValues().stream().toList().get(AGE.getPossibleValues().size() - 1);
    }

    public final boolean isMaxAge(BlockState pState) {
        return this.getAge(pState) >= this.getMaxAge();
    }
    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return !this.isMaxAge(pState);
    }
    protected void grow(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom){
        if (!isMaxAge(pState)) {
            float f = getGrowthSpeed(this, pLevel, pPos);
            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(25.0F / f) + 1) == 0)) {
                pLevel.setBlock(pPos, pState.setValue(AGE, (pState.getValue(AGE) + 1)), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
            }
        }
    }
    @Override
    @SuppressWarnings("deprecated")
    public @NotNull InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);

        if(pLevel.isClientSide()) return InteractionResult.PASS;
          if(itemStack.is(Items.BONE_MEAL)) {
            performBonemeal(((ServerLevel) pLevel), pLevel.getRandom(), pPos, pState);
          }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            grow(pState, pLevel, pPos, pRandom);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return !isMaxAge(pState);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        int age = getAge(pState);
        pLevel.setBlock(pPos, pState.setValue(AGE, age >= 3 ? age : age + 1), 2);
    }
}
