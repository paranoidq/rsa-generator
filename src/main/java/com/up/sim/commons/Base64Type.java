package com.up.sim.commons;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author paranoidq
 * @since 0.1
 */
public enum Base64Type {

    HEX_BASE64("字节数16进制表示"),
    STRING_BASE64("字符串类型")
    ;


    private String type;
    Base64Type(String type) {
        this.type = type;
    }

    private static ObservableList<String> base64Types4View;
    static {
        base64Types4View = FXCollections.observableArrayList();
        for (Base64Type t : Base64Type.values()) {
            base64Types4View.add(t.value());
        }
    }


    public String value() {
        return this.type;
    }

    public static Base64Type get(String type) {
        if (STRING_BASE64.value().equals(type)) {
            return STRING_BASE64;
        } else {
            return HEX_BASE64;
        }
    }

    public static ObservableList<String> getBase64Type4View() {
        return base64Types4View;
    }
}
