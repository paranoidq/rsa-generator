package com.up.sim.security.key;

import com.google.common.base.Optional;

import java.security.Key;

/**
 * @author paranoidq
 * @since 0.1
 */
public class SymmetricKey implements Key {

    private byte[] keySpec;
    private byte[] encoded;
    private Key key;

    /**
     * @param key for 2DES, key is null
     * @param encoded
     */
    public SymmetricKey(Key key, byte[] encoded, byte[] keySpec) {
        this.key = key;
        this.encoded = encoded;
        this.keySpec = keySpec;
    }

    @Override
    public String getAlgorithm() {
        return key.getAlgorithm();
    }

    @Override
    public String getFormat() {
        throw new UnsupportedOperationException("This method is not implemented for DESKey");
    }

    @Override
    public byte[] getEncoded() {
        return encoded;
    }

    public Optional<Key> getKey() {
        return Optional.fromNullable(key);
    }

    public byte[] getKeySpec() {
        return this.keySpec;
    }

}
