package com.up.sim.commons;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author paranoidq
 * @since 0.1
 */
public enum DigestType {

    MD5,
    SHA256
    ;


    private static ObservableList<String> digest4View;
    static {
        digest4View = FXCollections.observableArrayList();
        for (DigestType t : DigestType.values()) {
            digest4View.add(t.value());
        }
    }

    public String value() {
        return this.name();
    }

    public static DigestType get(String type) {
        if (MD5.value().equals(type)) {
            return MD5;
        } else {
            return SHA256;
        }
    }

    public static ObservableList<String> getDigest4View() {
        return digest4View;
    }
}
