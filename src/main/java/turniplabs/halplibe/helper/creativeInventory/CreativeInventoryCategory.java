package turniplabs.halplibe.helper.creativeInventory;

public enum CreativeInventoryCategory {
    /** Could not be fit into other categories. */
    MISCELLANEOUS,

    /** workbench, furnace, chest, trommel, etc. */
    WORKBENCHES,

    /** raw Stones and their bricks */
    STONE,

    /** Logs, planks, slabs, signs, fences, trapdoors... */
    WOOD,

    /** Plants. Grass, tallgrass, mushrooms, pumpkins, cobwebs, bone piles */
    ORGANIC,

    /** Non-organic natural items.
     * gravel, stone, sand, ice, clay, glowstone. */
    NATURAL,

    /** Redstone Components */
    REDSTONE,

    /** Ores of all stones and minerals. */
    ORE,

    /** Metal bricks, Metal blocks, etc.
     *  Blocks that can be made into and out of their base materials.
     *  (but not clay or glowstone) */
    STORAGE
}
