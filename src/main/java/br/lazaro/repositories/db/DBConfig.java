package br.lazaro.repositories.db;

import java.io.InputStream;
import java.util.Properties;

public class DBConfig {
    private static final Properties propriedades = new Properties();

    static {
        try (InputStream input = DBConfig.class.getClassLoader().getResourceAsStream("database.properties")) {
            propriedades.load(input);
        } catch (Exception e) {
            e.printStackTrace();
            // Trate as exceções apropriadas para o seu aplicativo
        }
    }

    public static String getUrl() {
        return propriedades.getProperty("database.url");
    }

    public static String getUsuario() {
        return propriedades.getProperty("database.usuario");
    }

    public static String getSenha() {
        return propriedades.getProperty("database.senha");
    }
}
