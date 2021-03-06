/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Quark Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Quark
 * 
 * Quark is Open Source and distributed under the
 * CC-BY-NC-SA 3.0 License: https://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB
 * 
 * File Created @ [Aug 14, 2016, 9:38:27 PM (GMT)]
 */
package vazkii.quark.decoration.feature;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.arl.block.BlockMod;
import vazkii.arl.recipe.RecipeHandler;
import vazkii.quark.base.module.Feature;
import vazkii.quark.decoration.block.BlockCustomBookshelf;

public class VariedBookshelves extends Feature {

	public static BlockMod custom_bookshelf;

	boolean renameVanillaBookshelves;
	
	@Override
	public void setupConfig() {
		renameVanillaBookshelves = loadPropBool("Rename vanilla bookshelves to Oak Bookshelf", "", true);
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		if(renameVanillaBookshelves)
			Blocks.BOOKSHELF.setUnlocalizedName("oak_bookshelf");
		
		custom_bookshelf = new BlockCustomBookshelf();

		List<ResourceLocation> recipeList = new ArrayList(CraftingManager.field_193380_a.getKeys());
		for(ResourceLocation res : recipeList) {
			IRecipe recipe = CraftingManager.field_193380_a.getObject(res);
			ItemStack out = recipe.getRecipeOutput();
			if(recipe instanceof ShapedRecipes && !out.isEmpty() && (out.getItem() == Item.getItemFromBlock(Blocks.BOOKSHELF))) {
				ShapedRecipes shaped = (ShapedRecipes) recipe;
				NonNullList<Ingredient> ingredients = shaped.recipeItems;
				for(int i = 0; i < ingredients.size(); i++) {
					Ingredient ingr = ingredients.get(i);
					if(ingr.apply(new ItemStack(Blocks.PLANKS)))
						ingredients.set(i, Ingredient.func_193369_a(new ItemStack(Blocks.PLANKS, 1, 0)));
				}
			}
		}
		
		for(int i = 0; i < 5; i++)
			RecipeHandler.addOreDictRecipe(new ItemStack(custom_bookshelf, 1, i),
					"WWW", "BBB", "WWW",
					'W', new ItemStack(Blocks.PLANKS, 1, i + 1),
					'B', new ItemStack(Items.BOOK));
		
		RecipeHandler.addOreDictRecipe(new ItemStack(Blocks.BOOKSHELF),
				"WWW", "BBB", "WWW",
				'W', "plankWood",
				'B', new ItemStack(Items.BOOK));
		
		OreDictionary.registerOre("bookshelf", new ItemStack(custom_bookshelf, 1, OreDictionary.WILDCARD_VALUE));
		
		OreDictionary.registerOre("bookshelfSpruce", new ItemStack(custom_bookshelf, 1, 0));
		OreDictionary.registerOre("bookshelfBirch", new ItemStack(custom_bookshelf, 1, 1));
		OreDictionary.registerOre("bookshelfJungle", new ItemStack(custom_bookshelf, 1, 2));
		OreDictionary.registerOre("bookshelfAcacia", new ItemStack(custom_bookshelf, 1, 3));
		OreDictionary.registerOre("bookshelfDarkOak", new ItemStack(custom_bookshelf, 1, 4));
	}
	
	@Override
	public boolean requiresMinecraftRestartToEnable() {
		return true;
	}
	
}
