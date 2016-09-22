package com.up.sim.service.utils;

import com.up.sim.commons.Encoding;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author paranoidq
 * @since 0.1
 */
public class UrlEncodeService {

    private static UrlEncodeService INSTANCE = new UrlEncodeService();

    private UrlEncodeService() {}

    public static UrlEncodeService getInstance() {
        return INSTANCE;
    }


    /**
     * URL encode
     * @param input
     * @return
     * @throws UnsupportedEncodingException
     */
    public String encode(String input, Encoding encoding) throws UnsupportedEncodingException {
        return URLEncoder.encode(input, encoding.value());
    }

    /**
     * URL decode
     * @param input
     * @return
     * @throws UnsupportedEncodingException
     */
    public String decode(String input, Encoding encoding) throws UnsupportedEncodingException {
        return URLDecoder.decode(input, encoding.value());
    }
}
