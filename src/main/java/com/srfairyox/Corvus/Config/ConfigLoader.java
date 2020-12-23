package com.srfairyox.Corvus.Config;

import com.srfairyox.Corvus.Main;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ConfigLoader {
    private static File configJSON;

    public ConfigLoader() {
        configJSON = new File("config.json");
        if(!configJSON.exists()) {
            try {
                Files.copy(Objects.requireNonNull(Main.instance.getClass().getClassLoader().getResourceAsStream(configJSON.getName())), configJSON.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save(Boolean enableLog, Boolean enableDebugConsole, int gateSelectedIndex, Boolean placeGateOnMap, Boolean spinOnlyEE, int minUridium, int maxSpinCost) throws IOException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("enableLog", enableLog);
        jsonObject.put("enableDebugConsole", enableDebugConsole);
        jsonObject.put("gateSelectedIndex", gateSelectedIndex);
        jsonObject.put("placeGateOnMap", placeGateOnMap);
        jsonObject.put("spinOnlyEE", spinOnlyEE);
        jsonObject.put("minUridium", minUridium);
        jsonObject.put("maxSpinCost", maxSpinCost);

        Files.write(Paths.get(configJSON.getName()), jsonObject.toString().getBytes());
    }

    public String load() {
        try {
             return new String(Files.readAllBytes(configJSON.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
