package turniplabs.halplibe.util;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;

import java.util.HashMap;

public class DirectoryManager {
    public static final HashMap<String, HashMap<String, String>> resourceDirectoryMap = new HashMap<>();
    public static final String BLOCK_TEXTURES = "halplibe:block_textures";
    public static final String ITEM_TEXTURES = "halplibe:item_textures";
    public static final String LANGUAGE = "halplibe:language";
    public static final String SOUND = "halplibe:sound";
    public static final String MUSIC = "halplibe:music";
    public static final String STREAMING = "halplibe:streaming";
    public static final String CAVE_MUSIC = "halplibe:cave_music";
    public static final String ARMOR = "halplibe:armor";
    public static final FabricLoader fl = FabricLoader.getInstance();
    static {
        for (ModContainer container : fl.getAllMods()){
            String id = container.getMetadata().getId();
            ModMetadata metadata = container.getMetadata();
            if (metadata.containsCustomValue(BLOCK_TEXTURES)){
                assignBlockTextureDirectory(id, metadata.getCustomValue(BLOCK_TEXTURES).getAsString());
            }
            if (metadata.containsCustomValue(ITEM_TEXTURES)){
                assignItemTextureDirectory(id, metadata.getCustomValue(ITEM_TEXTURES).getAsString());
            }
            if (metadata.containsCustomValue(LANGUAGE)){
                assignLanguageDirectory(id, metadata.getCustomValue(LANGUAGE).getAsString());
            }
            if (metadata.containsCustomValue(MUSIC)){
                assignMusicDirectory(id, metadata.getCustomValue(MUSIC).getAsString());
            }
            if (metadata.containsCustomValue(SOUND)){
                assignSoundDirectory(id, metadata.getCustomValue(SOUND).getAsString());
            }
            if (metadata.containsCustomValue(CAVE_MUSIC)){
                assignCaveMusicDirectory(id, metadata.getCustomValue(CAVE_MUSIC).getAsString());
            }
            if (metadata.containsCustomValue(STREAMING)){
                assignStreamingDirectory(id, metadata.getCustomValue(STREAMING).getAsString());
            }
            if (metadata.containsCustomValue(ARMOR)){
                assignArmorDirectory(id, metadata.getCustomValue(ARMOR).getAsString());
            }
        }
    }
    public static void assignBlockTextureDirectory(String modID, String path){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        resourceDirectoryMap.get(modID).put(BLOCK_TEXTURES, path);
    }
    public static void assignItemTextureDirectory(String modID, String path){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        resourceDirectoryMap.get(modID).put(ITEM_TEXTURES, path);
    }
    public static void assignLanguageDirectory(String modID, String path){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        resourceDirectoryMap.get(modID).put(LANGUAGE, path);
    }
    public static void assignSoundDirectory(String modID, String path){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        resourceDirectoryMap.get(modID).put(SOUND, path);
    }
    public static void assignMusicDirectory(String modID, String path){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        resourceDirectoryMap.get(modID).put(MUSIC, path);
    }
    public static void assignCaveMusicDirectory(String modID, String path){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        resourceDirectoryMap.get(modID).put(CAVE_MUSIC, path);
    }
    public static void assignStreamingDirectory(String modID, String path){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        resourceDirectoryMap.get(modID).put(STREAMING, path);
    }
    public static void assignArmorDirectory(String modID, String path){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        resourceDirectoryMap.get(modID).put(ARMOR, path);
    }
    public static String getBlockTextureDirectory(String modID){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        return resourceDirectoryMap.get(modID).getOrDefault(BLOCK_TEXTURES, "/assets/" + modID + "/block/");
    }
    public static String getItemTextureDirectory(String modID){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        return resourceDirectoryMap.get(modID).getOrDefault(ITEM_TEXTURES, "/assets/" + modID + "/item/");
    }
    public static String getLanguageDirectory(String modID){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        return resourceDirectoryMap.get(modID).getOrDefault(LANGUAGE, "/lang/" + modID + "/");
    }
    public static String getSoundDirectory(String modID){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        return resourceDirectoryMap.get(modID).getOrDefault(SOUND, "/assets/" + modID + "/sound/");
    }
    public static String getMusicDirectory(String modID){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        return resourceDirectoryMap.get(modID).getOrDefault(MUSIC, "/assets/" + modID + "/music/");
    }
    public static String getCaveMusicDirectory(String modID){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        return resourceDirectoryMap.get(modID).getOrDefault(CAVE_MUSIC, "/assets/" + modID + "/cavemusic/");
    }
    public static String getStreamingDirectory(String modID){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        if (fl.getModContainer(modID).isPresent() && fl.getModContainer(modID).get().getMetadata().containsCustomValue(STREAMING)){
            resourceDirectoryMap.get(modID).put(STREAMING, fl.getModContainer(modID).get().getMetadata().getCustomValue(STREAMING).getAsString());
        }
        return resourceDirectoryMap.get(modID).getOrDefault(STREAMING, "/assets/" + modID + "/streaming/");
    }
    public static String getArmorDirectory(String modID){
        resourceDirectoryMap.putIfAbsent(modID, new HashMap<>());
        return resourceDirectoryMap.get(modID).getOrDefault(ARMOR, "/assets/" + modID + "/armor/");
    }
}
