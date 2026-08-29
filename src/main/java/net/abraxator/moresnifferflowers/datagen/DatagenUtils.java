package net.abraxator.moresnifferflowers.datagen;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;

import java.util.function.Supplier;

public class DatagenUtils {



    public static class SupplierTagAppender<T>{
        final IntrinsicHolderTagsProvider.IntrinsicTagAppender<T> parent;

        public SupplierTagAppender(IntrinsicHolderTagsProvider.IntrinsicTagAppender<T> parent){
            this.parent = parent;
        }

        public final SupplierTagAppender<T> add(Supplier<T> value) {
            parent.add(value.get());
            return this;
        }

        @SafeVarargs
        public final SupplierTagAppender<T> add(Supplier<T>... values) {
            for (Supplier<T> value : values) {
                add(value);
            }
            return this;
        }
    }
}
