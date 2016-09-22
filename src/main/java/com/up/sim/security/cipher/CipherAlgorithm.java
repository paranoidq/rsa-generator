package com.up.sim.security.cipher;

import com.up.sim.commons.GlobalConstants;
import com.up.sim.security.cipher.commons.Function;
import com.up.sim.security.cipher.commons.Mode;
import com.up.sim.security.cipher.commons.Padding;

import java.util.Arrays;
import java.util.List;

/**
 * 加解密算法构建
 * @author paranoidq
 * @since 0.1
 */
public class CipherAlgorithm {

    /**
     * Frequent used algorithms
     */
    public static final String RSA = "RSA";
    public static final String DES = "DES";
    public static final String _3DES = "DESede/ECB/PKCS5Padding";


    /**
     * 获取所有的加密算法
     * @return
     */
    private static List<Function> getFunctions() {
        return Arrays.asList(Function.values());
    }

    /**
     * 获取所有的分组模式
     * @return
     */
    private static List<Mode> getMode() {
        return Arrays.asList(Mode.values());
    }

    /**
     * 获取所有的填充方式
     * @return
     */
    private static List<Padding> getPadding() {
        return Arrays.asList(Padding.values());
    }

    /**
     * 根据function, mode, padding获取加密算法的正式名字,用于{@link javax.crypto.Cipher#getInstance(String)}
     * @param function
     * @param mode
     * @param padding
     * @return
     */
    public static String get(Function function, Mode mode, Padding padding) {
        StringBuffer sb = new StringBuffer();
        sb.append(function.value()).append(GlobalConstants.SLASTH)
                .append(mode.value()).append(GlobalConstants.SLASTH)
                .append(padding.value()).append(GlobalConstants.SLASTH);
        return sb.toString();
    }


    /**
     * 根据function获取加密算法的名字, 其余为默认值
     * @param function
     * @return
     */
    public static String get(Function function) {
        return function.value();
    }


}
