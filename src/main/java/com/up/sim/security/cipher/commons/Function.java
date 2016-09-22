package com.up.sim.security.cipher.commons;

/**
 * 加密算法
 *
 * @author paranoidq
 * @since 0.1
 */
public enum Function {

    RSA("RSA"),
    DES("DES"),
    _2DES("2DES"),
    _3DES("DESede"),
    AES("AES");

    /**
     * 加密算法名字
     */
    private String algoName;

    Function(String algoName) {
        this.algoName = algoName;
    }

    public String value() {
        return this.algoName;
    }

}
