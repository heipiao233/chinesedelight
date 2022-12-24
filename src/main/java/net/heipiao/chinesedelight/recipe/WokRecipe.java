package net.heipiao.chinesedelight.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;

import net.heipiao.chinesedelight.utils.Three;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class WokRecipe implements Recipe<Inventory> {
    private final List<Three<Ingredient, Integer, Integer>> items;
    private final ItemStack output;
    private final Identifier id;

    protected WokRecipe(Identifier id, List<Three<Ingredient, Integer, Integer>> items, ItemStack output) {
        this.items = items;
        this.output = output;
        this.id = id;
    }

    public List<Three<Ingredient, Integer, Integer>> getItems() {
        return items;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return DefaultedList.copyOf(Ingredient.EMPTY, items.stream().map(Three::a).collect(Collectors.toList()).toArray(new Ingredient[0]));
    }

    @Override
    public boolean matches(Inventory var1, World var2) {
        return false;
    }

    @Override
    public ItemStack craft(Inventory var1) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int var1, int var2) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.WOK.serializer;
    }

    @Override
    public RecipeType<WokRecipe> getType() {
        return ModRecipeTypes.WOK.get();
    }

    static class WokRecipeBuilder {
        private final List<Three<Ingredient, Integer, Integer>> items = new ArrayList<>();
        private final ItemStack output;
        private final Identifier id;

        protected WokRecipeBuilder(Identifier id, ItemStack output) {
            this.output = output;
            this.id = id;
        }

        public WokRecipe build() {
            return new WokRecipe(id, new ImmutableList.Builder<Three<Ingredient, Integer, Integer>>().addAll(items).build(), output);
        }

        public void add(Ingredient stack, int maxTime, int minFry) {
            items.add(new Three<>(stack, maxTime, minFry));
        }
    }

    public static class WokRecipeSerializer implements RecipeSerializer<WokRecipe> {

        @Override
        public WokRecipe read(Identifier var1, JsonObject var2) {
            var result = JsonHelper.getObject(var2, "result");
            var builder = new WokRecipeBuilder(var1, getItemStack(result));
            for (var itemElement: JsonHelper.getArray(var2, "items")) {
                var item = itemElement.getAsJsonObject();
                builder.add(Ingredient.fromJson(item.getAsJsonObject("stack")), JsonHelper.getInt(item, "maxTime"), JsonHelper.getInt(item, "minFry"));
            }
            return builder.build();
        }

        private ItemStack getItemStack(JsonObject result) {
            return new ItemStack(JsonHelper.getItem(result, "item"), JsonHelper.getInt(result, "count"));
        }

        @Override
        public WokRecipe read(Identifier var1, PacketByteBuf var2) {
            var builder = new WokRecipeBuilder(var1, var2.readItemStack());
            for (int i = 0; i < var2.readInt(); i++) {
                builder.add(Ingredient.fromPacket(var2), var2.readInt(), var2.readInt());
            }
            return builder.build();
        }

        @Override
        public void write(PacketByteBuf var1, WokRecipe var2) {
            var1.writeItemStack(var2.output);
            var1.writeInt(var2.items.size());
            for (var item: var2.items) {
                item.a().write(var1);
                var1.writeInt(item.b());
                var1.writeInt(item.c());
            }
        }
    }

    public boolean check(List<Three<ItemStack, Integer, Integer>> items2) {
        if(items.size() != items2.size()) return false;
        for (int i=0;i<items.size();i++) {
            var itema = items.get(i);
            var itemb = items2.get(i);
            if(!itema.a().test(itemb.a()))return false;
            if(itema.b() < itemb.b())return false;
            if(itema.c() > itemb.c())return false;
        }
        return true;
    }
    
}
