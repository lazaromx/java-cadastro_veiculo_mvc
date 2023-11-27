package br.lazaro;


import br.lazaro.repositories.db.DBConfig;
import org.junit.Test;
import static org.junit.Assert.*;

public class DBConfigTest {

    // DBConfig.getUrl() returns a non-null string when database.url property is present in database.properties file
    @Test
    public void getUrlNotNullTest() {
        String url = DBConfig.getUrl();
        assertNotNull(url);
    }

    // DBConfig.getUsuario() returns a non-null string when database.usuario property is present in database.properties file
    @Test
    public void getUsuarioNotNullTest() {
        String usuario = DBConfig.getUsuario();
        assertNotNull(usuario);
    }

    // DBConfig.getSenha() returns a non-null string when database.senha property is present in database.properties file
    @Test
    public void getSenhaNotNullTest() {
        String senha = DBConfig.getSenha();
        assertNotNull(senha);
    }

    // DBConfig.getUrl() returns the correct URL string when database.url property is present in database.properties file
    @Test
    public void getUrlCorretoTest() {
        String url = DBConfig.getUrl();
        assertEquals("jdbc:mysql://localhost:3306/db_concessionaria", url);
    }

}