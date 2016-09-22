package com.up.sim.security.cipher.customization;

import com.google.common.annotations.Beta;
import com.up.sim.commons.GlobalConstants;
import com.up.sim.security.cipher.SymmetricCipher;
import com.up.sim.security.key.SymmetricKey;
import com.up.sim.security.key.utils.DESKeyUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author paranoidq
 * @since 0.1
 */
public class DESCipher extends SymmetricCipher {

    private static final DESCipher CIPHER = new DESCipher();

    public static SymmetricCipher getInstance() {
        return CIPHER;
    }

    @Override
    public byte[] encrypt(String cipherKey, SymmetricKey key, byte[] data) throws Exception {
        if (key.getEncoded().length  == 16) {
            return do2DesEncrypt(cipherKey, key, data);
        } else {
            return super.encrypt(cipherKey, key, data);
        }
    }

    @Override
    public byte[] decrypt(String cipherKey, SymmetricKey key, byte[] data) throws Exception {
        if (key.getEncoded().length == 16) {
            return do2DesDecrypt(cipherKey, key, data);
        } else {
            return super.decrypt(cipherKey, key, data);
        }
    }

    @Override
    public boolean verify(String cipherKey, byte[] sign, SymmetricKey key, byte[] data) throws Exception {
        if (key.getEncoded().length == 16) {
            return StringUtils.equals(
                    Base64.encodeBase64String(data),
                    Base64.encodeBase64String(do2DesDecrypt(cipherKey, key, sign))
            );
        } else {
            return super.verify(cipherKey, sign, key, data);
        }
    }

    /**
     * <li>第一步: 用前8byte对data进行加密</li>
     * <li>第二步: 用后8byte对第一步的结果进行解密</li>
     * <li>第三部: 用前8byte对第二部的记过进行加密得到结果</li>
     *
     * @param cipherKey
     * @param immutableKey
     * @param data
     * @return
     * @throws Exception
     */
    private byte[] do2DesEncrypt(String cipherKey, SymmetricKey immutableKey, byte[] data) throws Exception {
        String alteredCipherKey = desCipherKey(cipherKey);
        SymmetricKey key1 = DESKeyUtil.getDESKey(ArrayUtils.subarray(immutableKey.getKeySpec(), 0, 8), 8);
        byte[] phrase1 = super.encrypt(alteredCipherKey, key1, data);

        SymmetricKey key2 = DESKeyUtil.getDESKey(ArrayUtils.subarray(immutableKey.getKeySpec(), 8, 16), 8);
        byte[] phrase2 = super.decrypt(alteredCipherKey, key2, phrase1);

        byte[] phrase3 = super.encrypt(alteredCipherKey, key1, phrase2);
        return phrase3;
    }

    /**
     * 与加密的步骤相反
     * @param cipherKey
     * @param immutableKey
     * @param sign
     * @return
     * @throws Exception
     *
     * TODO: 对于mode和padding的处理没有参考到资料, 需要实验一下是否是从2des上层继承下来对每个部分做padding
     */
    @Beta
    private byte[] do2DesDecrypt(String cipherKey, SymmetricKey immutableKey, byte[] sign) throws Exception {
        String alteredCipherKey = desCipherKey(cipherKey);
        SymmetricKey key1 = DESKeyUtil.getDESKey(ArrayUtils.subarray(immutableKey.getKeySpec(), 0, 8), 8);
        byte[] phrase1 = super.decrypt(alteredCipherKey, key1, sign);

        SymmetricKey key2 = DESKeyUtil.getDESKey(ArrayUtils.subarray(immutableKey.getKeySpec(), 8, 16), 8);
        byte[] phrase2 = super.encrypt(alteredCipherKey, key2, phrase1);

        byte[] phrase3 = super.decrypt(alteredCipherKey, key1, phrase2);
        return phrase3;
    }

    private String desCipherKey(String cipherKey) {
        int index = cipherKey.indexOf(GlobalConstants.SLASTH);
        return "DES" + cipherKey.substring(index);
    }
}
