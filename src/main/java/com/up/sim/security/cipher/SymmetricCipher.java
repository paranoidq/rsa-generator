package com.up.sim.security.cipher;

import com.up.sim.security.key.SymmetricKey;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;

/** 对称加解密算法
 *
 * @author paranoidq
 * @since 0.1
 */
public abstract class SymmetricCipher {

    /**
     *
     * @param cipherKey
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public byte[] encrypt(String cipherKey, SymmetricKey key, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(cipherKey);
        cipher.init(Cipher.ENCRYPT_MODE, key.getKey().get());
        return cipher.doFinal(data);
    }

    public byte[] decrypt(String cipherKey, SymmetricKey key, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(cipherKey);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public boolean verify(String cipherKey, byte[] sign, SymmetricKey key, byte[] data) throws Exception {
        return StringUtils.equals(
                Base64.encodeBase64String(data),
                Base64.encodeBase64String(decrypt(cipherKey, key, sign))
        );
    }
}
