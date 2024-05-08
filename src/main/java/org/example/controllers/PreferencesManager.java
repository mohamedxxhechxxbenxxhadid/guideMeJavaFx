package org.example.controllers;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PreferencesManager {
    private static final String FILE_PATH = "user_preferences.properties";
    private Properties properties;

    public PreferencesManager() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUserPreference(String key) {
        return properties.getProperty(key);
    }

    public void setUserPreference(String key, String value) {
        properties.setProperty(key, value);
        try {
            properties.store(new FileOutputStream(FILE_PATH), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

