package com.up.sim.security.key;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 * @author paranoidq
 * @since 0.1
 */
public interface AsymmetricKey {

    /**
     * 生成keyPair
     * @param keySize
     * @return
     * @throws Exception
     */
    KeyPair generateKeyPair(int keySize) throws Exception;

    /**
     * 生成keyPair
     * @param keySize
     * @param random
     * @return
     * @throws Exception
     */
    KeyPair generateKeyPair(int keySize, SecureRandom random) throws Exception;

    /**
     * 根据key bytes获得privateKey
     * @param keyInBytes
     * @return
     * @throws Exception
     */
    PrivateKey getPrivateKey(byte[] keyInBytes) throws Exception;

    /**
     * 根据key bytes获得publicKey
     * @param keyInBytes
     * @return
     * @throws Exception
     */
    PublicKey getPublicKey(byte[] keyInBytes) throws Exception;

    /**
     * 从pem格式的文件中读取privateKey
     * @param privateKeyPath
     * @return
     * @throws Exception
     */
    PrivateKey getPrivateKeyFromPemFile(String privateKeyPath) throws Exception;

    /**
     * 从pem格式的文件中读取publicKey
     * @param publicKeyPath
     * @return
     * @throws Exception
     */
    PublicKey getPublicKeyFromPemFile(String publicKeyPath) throws Exception;

    /**
     * 从der格式的二进制文件中读取privateKey
     * @param privateKeyPath
     * @return
     * @throws Exception
     */
    PrivateKey getPrivateKeyFromDerFile(String privateKeyPath) throws Exception;

    /**
     * 从der格式的二进制文件中读取publicKey
     * @param publicKeyPath
     * @return
     * @throws Exception
     */
    PublicKey getPublicKeyFromDerFile(String publicKeyPath) throws Exception;

    /**
     * 存储private 和 public key到文件
     * pem格式
     * @throws Exception
     */
    void saveKeyAsPem(String description, byte[] keyInBytes, String absFilePath) throws Exception;

}
