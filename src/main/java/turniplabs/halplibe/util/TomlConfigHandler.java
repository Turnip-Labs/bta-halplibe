package turniplabs.halplibe.util;

import net.fabricmc.loader.api.FabricLoader;
import turniplabs.halplibe.util.toml.Toml;
import turniplabs.halplibe.util.toml.TomlParser;

import java.io.*;
import java.nio.file.Files;

public class TomlConfigHandler {
    private static final String CONFIG_DIRECTORY = FabricLoader.getInstance().getGameDir().toString() + "/config/";
    private Toml defaults;
    private Toml config;
    private Toml rawParsed;
    private final String configFileName;
    private final File configFile;

    public TomlConfigHandler(String modID, Toml defaults) {
        this.configFileName = modID + ".cfg";
        this.configFile = new File(getFilePath());
        this.defaults = defaults;
        if (defaults.getComment().isPresent())
            this.config = new Toml(defaults.getComment().get());
        else this.config = new Toml();
        config.addMissing(defaults);
        create();
    }

    public TomlConfigHandler(String modID, Toml defaults, boolean create) {
        this.configFileName = modID + ".cfg";
        this.configFile = new File(getFilePath());
        this.defaults = defaults;
        if (defaults.getComment().isPresent())
            this.config = new Toml(defaults.getComment().get());
        else this.config = new Toml();
        config.addMissing(defaults);
        if(create) create();
    }

    //creates the actual config file
    public void create(){
        File configFile = new File(getFilePath());
        HalpLibeUtils.LOGGER.info("Config file name: " + this.configFileName);
        HalpLibeUtils.LOGGER.info("Config file path: " + configFile.getAbsolutePath());
        try {
            if (!configFile.exists()) {
                HalpLibeUtils.LOGGER.info("Config file does not exist. Creating...");
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
                writeConfig();
            } else {
                // load only reads the entries in the file
                loadConfig();
                // ensure that new entries are written to the file
                writeConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaults(Toml defaults){
        this.defaults = defaults;
        config.addMissing(defaults);
    }

    public File getConfigFile() {
        return configFile;
    }

    public String getFilePath() {
        return CONFIG_DIRECTORY + configFileName;
    }

    public String getString(String key) {
        Object o = this.config.get(key);
        if (o == null) return null;
        return o.toString();
    }

    public int getInt(String key) {
        try {
            return this.config.get(key, Integer.class);
        } catch (NullPointerException e) {
            throw new RuntimeException("Entry '"+key+"' doesn't exist!", e);
        }
    }

    public long getLong(String key) {
        try {
            return this.config.get(key, Long.class);
        } catch (NullPointerException e) {
            throw new RuntimeException("Entry '"+key+"' doesn't exist!", e);
        }
    }

    public float getFloat(String key) {
        try {
            return this.config.get(key, Float.class);
        } catch (NullPointerException e) {
            throw new RuntimeException("Entry '"+key+"' doesn't exist!", e);
        }
    }

    public double getDouble(String key) {
        try {
            return this.config.get(key, Double.class);
        } catch (NullPointerException e) {
            throw new RuntimeException("Entry '"+key+"' doesn't exist!", e);
        }
    }

    public boolean getBoolean(String key) {
        try {
            return this.config.get(key, Boolean.class);
        } catch (NullPointerException e) {
            throw new RuntimeException("Entry '"+key+"' doesn't exist!", e);
        }
    }

    public void writeConfig() {
        File configFile = new File(getFilePath());

        // make sure the actual config has all the required entries
        config.merge(defaults);
        if (rawParsed != null) {
            // preserve undefined entries
            // used due to run config handler
            rawParsed.merge(true, config);
        } else rawParsed = config;

        // write the config
        try (OutputStream output = new FileOutputStream(configFile)) {
            output.write(rawParsed.toString().getBytes());
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        File configFile = new File(getFilePath());
        // make sure the actual config has all the required entries
        config.merge(defaults);
        loadConfig(configFile, this.config);
    }

    private void loadConfig(File configFile, Toml properties) {
        try {
            String s = Files.readString(configFile.toPath());
            Toml parsed = TomlParser.parse(s);

            if (defaults.getComment().isPresent())  {
                rawParsed = new Toml(defaults.getComment().get());
                rawParsed.addMissing(parsed);
            } else rawParsed = parsed;

            properties.merge(true, rawParsed);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Toml getRawParsed() {
        return rawParsed;
    }
}