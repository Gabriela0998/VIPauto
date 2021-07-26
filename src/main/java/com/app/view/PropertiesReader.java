package com.app.view;

import com.app.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private PropertiesReader() {
    }

    public static Properties properties() throws IOException {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("application.properties");
        Properties prop = new Properties();
        prop.load(inputStream);
        inputStream.close();
        return prop;
    }

}
