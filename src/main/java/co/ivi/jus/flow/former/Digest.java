/*
 * Copyright (c) 2021, Xuelei Fan. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package co.ivi.jus.flow.former;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public sealed abstract class Digest {
    private static final Sha256 sha256 = new Sha256();
    private static final Sha512 sha512 = new Sha512();

    private static final class Sha256 extends Digest {
        private final MessageDigest md;

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

    private static final class Sha512 extends Digest {
        private final MessageDigest md;

        private Sha512() {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("SHA-512");
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

    public static Digest of(String algorithm) throws NoSuchAlgorithmException {
        return switch (algorithm) {
            case "SHA-256" -> {
                if (sha256.md == null) {
                    throw new NoSuchAlgorithmException(
                            "Unsupported digest algorithm SHA-256");
                } else {
                    yield sha256;
                }
            }
            case "SHA-512" -> {
                if (sha512.md == null) {
                    throw new NoSuchAlgorithmException(
                            "Unsupported digest algorithm SHA-512");
                } else {
                    yield sha512;
                }
            }
            case null, default -> throw new NoSuchAlgorithmException(
                    "Unsupported digest algorithm " + algorithm);
        };
    }

    public abstract byte[] digest(byte[] message);
}
