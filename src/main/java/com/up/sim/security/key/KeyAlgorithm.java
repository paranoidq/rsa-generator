package com.up.sim.security.key;

import java.util.HashMap;
import java.util.Map;

/**
 * @author paranoidq
 * @since 0.1
 */
public class KeyAlgorithm {

    public static final String RSA = "RSA";
    public static final String DES = "DES";
    public static final String DES2 = "2DES";
    public static final String DES3 = "DESede";



    private String name;
    KeyAlgorithm(String name) {
        this.name = name;
    }

    public String value() {
        return this.name;
    }


    private static final Map<String, KeyAlgorithm> map = new HashMap<String, KeyAlgorithm>();
    static {
//        for (KeyAlgorithm algorithm : values()) {
//            map.put(algorithm.value(), algorithm);
//        }
    }

    public static KeyAlgorithm get(String name) {
        return map.get(name);
    }
}
