package com.up.sim.security.cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 非对称加解密算法
 *
 * @author paranoidq
 * @since 0.1
 */
public abstract class AsymmetricCipher {


    public byte[] decrypt(String cipherKey, PrivateKey privateKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(cipherKey);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    public byte[] decrypt(String cipherKey, PublicKey publicKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(cipherKey);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    public byte[] encrypt(String cipherKey, PrivateKey privateKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(cipherKey);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    public byte[] encrypt(String cipherKey, PublicKey publicKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(cipherKey);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    public boolean verify(String cipherKey, PrivateKey privateKey, byte[] data, byte[] sign) throws Exception {
        return StringUtils.equals(
                Base64.encodeBase64String(data),
                Base64.encodeBase64String(decrypt(cipherKey, privateKey, sign)));
    }

    public boolean verify(String cipherKey, PublicKey privateKey, byte[] data, byte[] sign) throws Exception {
        return StringUtils.equals(
                Base64.encodeBase64String(data),
                Base64.encodeBase64String(decrypt(cipherKey, privateKey, sign)));
    }
}
