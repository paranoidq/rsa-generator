package com.up.sim.service.utils;

import com.up.sim.commons.Encoding;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.Charset;

/**
 * @author paranoidq
 * @since 0.1
 */
public class Base64Service {

    private static Base64Service INSTANCE = new Base64Service();

    private Base64Service() {}

    public static Base64Service getInstance() {
        return INSTANCE;
    }

    /**
     * 对正常字符串进行BASE64编码
     * @param str
     * @return
     */
    public String encode(String str, Encoding encoding) {
        return Base64.encodeBase64String(str.getBytes(Charset.forName(encoding.value())));
    }


    /**
     * 对byte数组的16进制表示进行BASE64编码
     * <p>需要先利用Hex获取数组的byte表示,然后再进行BASE64编码</p>
     * @param hex
     * @return
     * @throws DecoderException
     */
    public String encodeHex(String hex) throws DecoderException {
        return Base64.encodeBase64String(Hex.decodeHex(hex.toCharArray()));
    }


    /**
     * BASE64解码
     * @param str
     * @return 解码后的字符串
     */
    public String decode(String str) {
        return new String(Base64.decodeBase64(str));
    }

    /**
     * BASE64解码
     * @param str
     * @return 解码后byte数组的16进制表示
     */
    public String decodeHex(String str) {
        return String.valueOf(Hex.encodeHex(Base64.decodeBase64(str)));
    }


}
