package com.up.sim.security.cipher.commons;

/**
 * 填充方式
 *
 * @author paranoidq
 * @since 0.1
 */
public enum Padding {

    NoPadding,
    ZerosPadding,
    PKCS5Padding,
    PKCS7Padding;

    public String value() {
        return this.name();
    }
}
