package com.biletimcepte.util;

import lombok.NoArgsConstructor;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@NoArgsConstructor
public final class PasswordUtil {
    public static String preparePasswordHash(String password, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return getPBKDF2SecurePassword(password, salt);
    }

    private static String getPBKDF2SecurePassword(String passwordToHash, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] byteSaltArray = salt.getBytes();
        KeySpec spec = new PBEKeySpec(passwordToHash.toCharArray(), byteSaltArray, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();

        return enc.encodeToString(hash);
    }

    public static boolean validatePassword(String passwordHash, String password) {
        return passwordHash.equals(password);
    }
}
