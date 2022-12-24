package net.heipiao.chinesedelight.block.entity;

import java.util.ArrayList;
import java.util.List;

import com.nhoryzon.mc.farmersdelight.entity.block.HeatableBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.SyncedBlockEntity;

import net.heipiao.chinesedelight.ModTags;
import net.heipiao.chinesedelight.recipe.ModRecipeTypes;
import net.heipiao.chinesedelight.recipe.WokRecipe;
import net.heipiao.chinesedelight.utils.Three;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WokBlockEntity extends SyncedBlockEntity implements HeatableBlockEntity {
    private int processTick = 0, fryTime = 0;
    private List<Three<ItemStack, Integer, Integer>> items = new ArrayList<>();
    private ItemStack last = ItemStack.EMPTY;

    public WokBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public WokBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(ModBlockEntityTypes.WOK.get(), blockPos, blockState);
    }

    public static void tick(World var1, BlockPos var2, BlockState var3, WokBlockEntity var4) {
        if(!var4.last.isEmpty()&&var4.isHeated(var1, var2))
            var4.processTick++;
    }

    public ItemStack use(ItemStack stack, World world) {
        if(!isHeated(world, pos)) return ItemStack.EMPTY;
        if(stack.isIn(ModTags.SPATULA)) {
            this.fryTime++;
            return ItemStack.EMPTY;
        } else if(stack.isOf(Items.BOWL)) {
            if(last.isEmpty()) return ItemStack.EMPTY;
            stack.decrement(1);
            items.add(new Three<>(last, processTick, fryTime));
            last = ItemStack.EMPTY;
            processTick = fryTime = 0;
            var res = findRecipe(world);
            items.clear();
            return res;
        } else {
            if(!last.isEmpty()) {
                items.add(new Three<>(last, processTick, fryTime));
                processTick = fryTime = 0;
            }
            last = stack.split(1);
            return stack.getRecipeRemainder();
        }
    }

    private ItemStack findRecipe(World world) {
        var recipes = world.getRecipeManager().listAllOfType(ModRecipeTypes.WOK.get());
        for (WokRecipe wokRecipe: recipes) {
            if(wokRecipe.check(items))
                return wokRecipe.craft(null);
        }
        return Items.COCOA_BEANS.getDefaultStack();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
    }
    
}
