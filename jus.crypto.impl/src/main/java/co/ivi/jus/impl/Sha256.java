/*
 * Copyright (c) 2021, Xuelei Fan. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package co.ivi.jus.impl;

import co.ivi.jus.crypto.Digest;
import co.ivi.jus.crypto.Returned;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

final class Sha256 implements Digest {
    static final Returned.ReturnValue<Digest> returnedSha256;

    private final MessageDigest md;

    static {
        Sha256 sha256 = new Sha256();
        returnedSha256 = sha256.md == null ?
                null : new Returned.ReturnValue<>(sha256);
    }

    private Sha256() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            md = null;
        }
        this.md = md;
    }

    @Override
    public byte[] digest(byte[] message) {
        return md.digest(message);
    }
}
