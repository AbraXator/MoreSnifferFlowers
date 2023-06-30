package net.abraxator.ceruleanvines.init;

public class GLM {
    /*public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(
            ForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get(), CeruleanVines.MOD_ID);

    public static class SnifferLootModifier extends LootModifier {
        public static final Supplier<Codec<SnifferLootModifier>> CODEC = Suppliers.memoize(() ->
                RecordCodecBuilder.create(o -> codecStart(o).apply(o, SnifferLootModifier::new)));

        /**
         * Constructs a LootModifier.
         *
         * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
         */
        /*protected SnifferLootModifier(LootItemCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Override
        protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            generatedLoot.add(new ItemStack(ModItems.CERULEAN_VINE_PATCH::get));
            return generatedLoot;
        }

        @Override
        public Codec<? extends IGlobalLootModifier> codec() {
        }
    }*/
}
