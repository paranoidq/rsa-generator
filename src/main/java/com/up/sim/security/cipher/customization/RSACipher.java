package com.up.sim.security.cipher.customization;

import com.up.sim.security.cipher.AsymmetricCipher;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author paranoidq
 * @since 0.1
 */
public class RSACipher extends AsymmetricCipher {

    private static final RSACipher CIPHER = new RSACipher();

    public static AsymmetricCipher getInstance() {
        return CIPHER;
    }

    @Override
    public byte[] decrypt(String cipherKey, PrivateKey privateKey, byte[] data) throws Exception {
        return super.decrypt(cipherKey, privateKey, data);
    }

    @Override
    public byte[] decrypt(String cipherKey, PublicKey publicKey, byte[] data) throws Exception {
        return super.decrypt(cipherKey, publicKey, data);
    }

    @Override
    public byte[] encrypt(String cipherKey, PrivateKey privateKey, byte[] data) throws Exception {
        return super.encrypt(cipherKey, privateKey, data);
    }

    @Override
    public byte[] encrypt(String cipherKey, PublicKey publicKey, byte[] data) throws Exception {
        return super.encrypt(cipherKey, publicKey, data);
    }

    @Override
    public boolean verify(String cipherKey, PrivateKey privateKey, byte[] data, byte[] sign) throws Exception {
        return super.verify(cipherKey, privateKey, data, sign);
    }

    @Override
    public boolean verify(String cipherKey, PublicKey privateKey, byte[] data, byte[] sign) throws Exception {
        return super.verify(cipherKey, privateKey, data, sign);
    }
}
