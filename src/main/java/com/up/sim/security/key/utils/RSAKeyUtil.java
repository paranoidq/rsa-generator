package com.up.sim.security.key.utils;


import com.up.sim.security.key.KeyAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.*;
import java.net.URL;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author paranoidq
 * @since 0.1
 */
public class RSAKeyUtil {

    public static KeyPair generateKeyPair(int keySize) throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(KeyAlgorithm.RSA);
        generator.initialize(keySize);
        return generator.generateKeyPair();
    }

    public static KeyPair generateKeyPair(int keySize, SecureRandom random) throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(KeyAlgorithm.RSA);
        generator.initialize(keySize, random);
        return generator.generateKeyPair();
    }

    public static PrivateKey getPrivateKey(byte[] keyInBytes) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KeyAlgorithm.RSA);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyInBytes);
        return keyFactory.generatePrivate(privateKeySpec);
    }

    public static PublicKey getPublicKey(byte[] keyInBytes) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KeyAlgorithm.RSA);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyInBytes);
        return keyFactory.generatePublic(publicKeySpec);
    }

    public static PrivateKey getPrivateKeyFromPemFile(String privateKeyPath) throws Exception {
        byte[] keyBytes = getKeyBytesFromPem(privateKeyPath);
        return getPrivateKey(keyBytes);

    }

    public static PublicKey getPublicKeyFromPemFile(String publicKeyPath) throws Exception {
        byte[] keyBytes = getKeyBytesFromPem(publicKeyPath);
        return getPublicKey(keyBytes);
    }

    public static PrivateKey getPrivateKeyFromDerFile(String privateKeyPath) throws Exception {
        return null;
    }

    public static PublicKey getPublicKeyFromDerFile(String publicKeyPath) throws Exception {
        return null;
    }

    public static void saveKeyAsPem(String description, byte[] keyInBytes, String absFilePath) throws Exception {
        PemWriter writer = new PemWriter(new OutputStreamWriter(new FileOutputStream(absFilePath)));
        PemObject object = new PemObject(description, keyInBytes);
        writer.writeObject(object);
        writer.close();
    }


    /**
     * 从pem文件中获取key bytes
     * use bouncycastle lib
     * @param path
     * @return
     * @throws IOException
     */
    private static byte[] getKeyBytesFromPem(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        PemReader reader = new PemReader(br);
        PemObject pemObject = reader.readPemObject();
        return pemObject.getContent();
    }

    /**
     *
     * @param path
     * @return
     */
    private static byte[] getKeyBytesFromDer(String path) {
        return null;
    }




    /**
     * Test
     * @param args
     */
    public static void main(String[] args) {
        try {
            URL uri = Thread.currentThread().getContextClassLoader().getResource("ins_unionpay_prikey_encode.key");
            PrivateKey privateKey = getPrivateKeyFromPemFile(uri.getPath());
            System.out.println(Base64.encodeBase64String(privateKey.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
