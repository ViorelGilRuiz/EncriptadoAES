/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class Encriptado {

    private static final String MILLAVE = "2024tareadam2024";

    public static String encriptar(String textoEncriptar) throws Exception {

        Key millaveenBytes = new SecretKeySpec(MILLAVE.getBytes(), "AES");

        Cipher encriptador = Cipher.getInstance("AES");
        encriptador.init(Cipher.ENCRYPT_MODE, millaveenBytes);

        byte[] bystesEncriptados = encriptador.doFinal(textoEncriptar.getBytes());

        return Base64.encodeBase64String(bystesEncriptados);
    }

    public static String desencriptar(String TextoDesencriptar) throws Exception {

        byte[] bystesEncriptados = Base64.decodeBase64(TextoDesencriptar);

        Key millaveenBytes = new SecretKeySpec(MILLAVE.getBytes(), "AES");

        Cipher encriptador = Cipher.getInstance("AES");
        encriptador.init(Cipher.ENCRYPT_MODE, millaveenBytes);

        String desencriptado = new String(encriptador.doFinal(bystesEncriptados));

        return desencriptado;

    }
}
