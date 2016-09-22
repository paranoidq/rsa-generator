package com.up.sim.security.cipher.commons;

/**
 * 加密算法的分组方式
 *
 * @author paranoidq
 * @since 0.1
 */
public enum Mode {
    ECB,
    CBC,
    CFB,
    OFB,
    CTF;

    public String value() {
        return this.name();
    }


}
