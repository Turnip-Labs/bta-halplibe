package turniplabs.halplibe.helper;

import com.b100.utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import net.minecraft.core.Global;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.HasJsonAdapter;
import net.minecraft.core.data.registry.recipe.RecipeEntryBase;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.adapter.ItemStackJsonAdapter;
import net.minecraft.core.data.registry.recipe.adapter.RecipeJsonAdapter;
import net.minecraft.core.data.registry.recipe.adapter.RecipeSymbolJsonAdapter;
import net.minecraft.core.data.registry.recipe.adapter.WeightedRandomBagJsonAdapter;
import net.minecraft.core.data.registry.recipe.adapter.WeightedRandomLootObjectJsonAdapter;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderBlastFurnace;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderFurnace;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShapeless;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderTrommel;
import turniplabs.halplibe.helper.recipeBuilders.modifiers.BlastFurnaceModifier;
import turniplabs.halplibe.helper.recipeBuilders.modifiers.FurnaceModifier;
import turniplabs.halplibe.helper.recipeBuilders.modifiers.TrommelModifier;
import turniplabs.halplibe.helper.recipeBuilders.modifiers.WorkbenchModifier;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class RecipeBuilder {
    @Nonnull
    public static RecipeNamespace getRecipeNamespace(String modID){
        if (Registries.RECIPES.getItem(modID) != null){
            return Registries.RECIPES.getItem(modID);
        }
        RecipeNamespace modSpace = new RecipeNamespace();
        Registries.RECIPES.register(modID, modSpace);
        return Objects.requireNonNull(modSpace);
    }
    @Nonnull
    public static RecipeGroup<?> getRecipeGroup(String modID, String key, RecipeSymbol symbol){
        return getRecipeGroup(getRecipeNamespace(modID), key, symbol);
    }
    @Nonnull
    public static RecipeGroup<?> getRecipeGroup(RecipeNamespace namespace, String key, RecipeSymbol symbol){
        if (namespace.getItem(key) != null){
            return namespace.getItem(key);
        }
        RecipeGroup<?> group = new RecipeGroup<>(symbol);
        namespace.register(key, group);
        return Objects.requireNonNull(group);
    }
    public static RecipeBuilderShaped Shaped(String modID){
        return new RecipeBuilderShaped(modID);
    }
    public static RecipeBuilderShaped Shaped(String modID, String... shape){
        return new RecipeBuilderShaped(modID, shape);
    }
    public static RecipeBuilderShapeless Shapeless(String modID){
        return new RecipeBuilderShapeless(modID);
    }
    public static RecipeBuilderFurnace Furnace(String modID){
        return new RecipeBuilderFurnace(modID);
    }
    public static RecipeBuilderBlastFurnace BlastFurnace(String modID){
        return new RecipeBuilderBlastFurnace(modID);
    }
    public static RecipeBuilderTrommel Trommel(String modID){
        return new RecipeBuilderTrommel(modID);
    }
    public static TrommelModifier ModifyTrommel(String namespace, String key){
        return new TrommelModifier(namespace, key);
    }
    public static WorkbenchModifier ModifyWorkbench(String namespace){
        return new WorkbenchModifier(namespace);
    }
    public static FurnaceModifier ModifyFurnace(String namespace){
        return new FurnaceModifier(namespace);
    }
    public static BlastFurnaceModifier ModifyBlastFurnace(String namespace){
        return new BlastFurnaceModifier(namespace);
    }
    public static boolean isExporting = false;
    public static void exportRecipes(){
        isExporting = true;
        Path filePath = Paths.get(Global.accessor.getMinecraftDir() + "/" + "recipeDump");
        createDir(filePath);
        String path = filePath + "/recipes.json";
        List<RecipeEntryBase<?, ?, ?>> recipes = Registries.RECIPES.getAllSerializableRecipes();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        ArrayList usedAdapters = new ArrayList();
        for (RecipeEntryBase<?, ?, ?> recipe : recipes) {
            HasJsonAdapter hasJsonAdapter = (HasJsonAdapter) recipe;
            RecipeJsonAdapter<?> recipeJsonAdapter = hasJsonAdapter.getAdapter();
            if (usedAdapters.contains(recipeJsonAdapter)) continue;
            builder.registerTypeAdapter(recipe.getClass(), recipeJsonAdapter);
            usedAdapters.add(recipeJsonAdapter);
        }
        builder.registerTypeAdapter(ItemStack.class, new ItemStackJsonAdapter());
        builder.registerTypeAdapter(RecipeSymbol.class, new RecipeSymbolJsonAdapter());
        builder.registerTypeAdapter(new TypeToken<WeightedRandomBag<WeightedRandomLootObject>>(){}.getType(), new WeightedRandomBagJsonAdapter());
        builder.registerTypeAdapter(WeightedRandomLootObject.class, new WeightedRandomLootObjectJsonAdapter());
        Gson gson = builder.create();
        JsonArray jsonArray = new JsonArray();
        for (RecipeEntryBase recipeEntryBase : recipes) {
            TypeAdapter<RecipeEntryBase> typeAdapter = (TypeAdapter<RecipeEntryBase>) gson.getAdapter(recipeEntryBase.getClass());
            JsonElement json = typeAdapter.toJsonTree(recipeEntryBase);
            jsonArray.add(json);
        }
        File file = FileUtils.createNewFile(new File(path));
        try (FileWriter fileWriter = new FileWriter(file)){
            gson.toJson(jsonArray, fileWriter);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        isExporting = false;
    }
    private static void createDir(Path path){
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
        }
    }
}
