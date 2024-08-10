package by.northdakota.Utils;

import java.io.FileInputStream;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static{
        loadProperties();
    }

    private static void loadProperties() {
        try(var inputStream = new FileInputStream("C:\\Users\\artei\\IdeaProjects\\" +
                                              "jdbcStart\\src\\main\\resources\\application.properties")){
            PROPERTIES.load(inputStream);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String get(String key){
        return PROPERTIES.getProperty(key);
    }
}
