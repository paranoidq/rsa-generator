package com.up.sim.security.key.utils;

import com.google.common.base.Preconditions;
import com.up.sim.security.key.KeyAlgorithm;
import com.up.sim.security.key.SymmetricKey;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * DES key util
 *
 * @author paranoidq
 * @since 0.1
 */
public class DESKeyUtil {


    public static final int KEY_SIZE_SINGLE = 8;
    public static final int KEY_SIZE_DOUBLE = 16;
    public static final int KEY_SIZE_TRIPLE = 24;

    /**
     * Only DES, 2DES adn 3DES are accepted
     */
    public static final Set<Integer> KEYSIZE_SET = new HashSet<Integer>(Arrays.asList(KEY_SIZE_SINGLE, KEY_SIZE_DOUBLE, KEY_SIZE_TRIPLE));

    public static SymmetricKey getDESKey(byte[] keySpec, int keySize) throws Exception {
        Preconditions.checkArgument(!ArrayUtils.isEmpty(keySpec) && keySize == keySpec.length, "Key size not match");
        Preconditions.checkArgument(KEYSIZE_SET.contains(keySize), "Wrong key size, only accept 8, 16, 24 bytes");

        if (keySize == 8) {
            Key key = getDESKey(keySpec);
            return new SymmetricKey(key, key.getEncoded(), keySpec);
        } else if (keySize == 24) {
            Key key = get3DESKey(keySpec);
            return new SymmetricKey(key, key.getEncoded(), keySpec);
        } else {
            /** 2des is less used, keep it last */
            byte[] encoded = get2DESKey(keySpec);
            return new SymmetricKey(null, encoded, keySpec);
        }
    }

    /**
     * Get des key (单倍长)
     * @param keySpec
     * @return
     * @throws Exception
     */
    private static Key getDESKey(byte[] keySpec) throws Exception {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(KeyAlgorithm.DES);
        DESKeySpec desEdeKeySpec = new DESKeySpec(keySpec);
        return secretKeyFactory.generateSecret(desEdeKeySpec);
    }

    /**
     * Get 2des key (双倍长)
     * @param keySpec
     * @return
     * @throws Exception
     */
    private static byte[] get2DESKey(byte[] keySpec) throws Exception {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(KeyAlgorithm.DES);
        DESKeySpec firstPart = new DESKeySpec(ArrayUtils.subarray(keySpec, 0, 8));
        byte[] firstPartEncoded = secretKeyFactory.generateSecret(firstPart).getEncoded();

        DESKeySpec secondPart = new DESKeySpec(ArrayUtils.subarray(keySpec, 8, 16));
        byte[] secondPartEncoded = secretKeyFactory.generateSecret(secondPart).getEncoded();

        return ArrayUtils.addAll(firstPartEncoded, secondPartEncoded);
    }

    /**
     * Get 3des key (三倍长)
     * @param keySpec
     * @return
     * @throws Exception
     */
    private static Key get3DESKey(byte[] keySpec) throws Exception {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(KeyAlgorithm.DES3);
        DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(keySpec);
        return secretKeyFactory.generateSecret(deSedeKeySpec);
    }

    public static void main(String[] args) throws Exception {
        DESKeyUtil util = new DESKeyUtil();

        byte[] md5 = DigestUtils.md5("123456");
        System.out.println(Hex.encodeHexString(md5));

        Key key = util.getDESKey(Hex.encodeHexString(md5).getBytes());
        System.out.println(Hex.encodeHex(key.getEncoded()));


        key = util.getDESKey("e10adc39".getBytes());
        System.out.println(Hex.encodeHex(key.getEncoded()));

        key = util.getDESKey("49ba59ab".getBytes());
        System.out.println(Hex.encodeHex(key.getEncoded()));

        key = util.getDESKey("be56e057".getBytes());
        System.out.println(Hex.encodeHex(key.getEncoded()));

        key = util.get3DESKey("e10adc3949ba59abbe56e057".getBytes());
        System.out.println(Hex.encodeHex(key.getEncoded()));

        byte[] encoded = util.get2DESKey("e10adc3949ba59abbe56e057".getBytes());
        System.out.println(Hex.encodeHex(encoded));

        System.out.println(Hex.encodeHexString("e".getBytes()));
    }
}
