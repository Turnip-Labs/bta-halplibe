package turniplabs.halplibe.util;

@Deprecated(since = "6.1.0")
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
