package turniplabs.halplibe.util;

public interface RecipeEntrypoint {
    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code recipesReady}.
     */
    void onRecipesReady();

    /**
     * The entrypoint name inside {@code fabric.mod.json} is {@code recipesReady}.
     */
    void initNamespaces();
}
