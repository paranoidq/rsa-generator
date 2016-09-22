package com.up.sim.service.utils;

import com.up.sim.commons.Encoding;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.nio.charset.Charset;

/**
 * @author paranoidq
 * @since 0.1
 */
public class DigestService {

    private static DigestService INSTANCE = new DigestService();

    public static DigestService getInstance() {
        return INSTANCE;
    }


    public String digest(String input, String algorithm, Encoding encoding) {
        if ("MD5".equalsIgnoreCase(algorithm)) {
            return md5(input, encoding);
        } else if ("SHA256".equalsIgnoreCase(algorithm)) {
            return sha256(input, encoding);
        }
        return StringUtils.EMPTY;
    }


    /**
     * MD5
     * @param input
     * @return
     */
    private String md5(String input, Encoding encoding) {
        return DigestUtils.md5Hex(input.getBytes(Charset.forName(encoding.value())));
    }


    /**
     * sha256
     * @param input
     * @return
     */
    private String sha256(String input, Encoding encoding) {
        return DigestUtils.sha256Hex(input.getBytes(Charset.forName(encoding.value())));
    }
}
