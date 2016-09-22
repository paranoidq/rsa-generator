package com.up.sim.commons;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

/**
 * @author paranoidq
 * @since 0.1
 */
public enum Encoding {

    UTF_8("UTF-8"),
    GBK("GBK")
    ;

    private static final Logger logger = Logger.getLogger(Encoding.class);

    private static ObservableList<Encoding> encodingTypes4View;
    static {
        encodingTypes4View = FXCollections.observableArrayList(Encoding.values());
    }


    private String encodingType;
    private Encoding(String encodingType) {
        this.encodingType = encodingType;
    }

    public String value() {
        return this.encodingType;
    }

    public static Encoding get(String encodingType) {
        if (UTF_8.value().equalsIgnoreCase(encodingType)) {
            return UTF_8;
        } else if (GBK.value().equalsIgnoreCase(encodingType)) {
            return GBK;
        } else {
            logger.error("未知的编码类型, 返回默认值: " + GBK.value());
            return UTF_8;
        }
    }

    /**
     * 返回界面combobox绑定的所有encodingTypes
     * @return
     */
    public static ObservableList<Encoding> getEncodings4View() {
        return encodingTypes4View;
    }

}
