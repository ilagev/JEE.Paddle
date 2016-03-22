package data.entities;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

import data.entities.Encrypt;

public class EncryptTest {

    @Test
    public void testEncryptInBase64UrlSafe() {
        String url = new Encrypt().encryptInBase64UrlSafe(Calendar.getInstance().toString());
        assertEquals(-1, url.indexOf("+"));
        assertEquals(-1, url.indexOf("/"));
        assertEquals(-1, url.indexOf("="));
    }

}
