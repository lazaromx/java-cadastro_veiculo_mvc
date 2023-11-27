package br.lazaro.repositories.db;

import org.junit.Test;
import static org.junit.Assert.*;

public class DBConfigTest {
    @Test
    public void getUrlNotNullTest() {
        String url = DBConfig.getUrl();
        assertNotNull(url);
    }
    @Test
    public void getUsuarioNotNullTest() {
        String usuario = DBConfig.getUsuario();
        assertNotNull(usuario);
    }
    @Test
    public void getSenhaNotNullTest() {
        String senha = DBConfig.getSenha();
        assertNotNull(senha);
    }
    @Test
    public void getUrlCorretoTest() {
        String url = DBConfig.getUrl();
        assertEquals("jdbc:mysql://localhost:3306/db_concessionaria", url);
    }

}