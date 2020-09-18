package util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CryptoHelper {

    public static CryptoHelper cryptoHelper;
    private static final String SHA256_ALGORITHM = "SHA-256";
    private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
    private static final String randomString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public CryptoHelper() {
    }

    public static CryptoHelper getInstance() {
        if (cryptoHelper == null) {
            cryptoHelper = new CryptoHelper();
        }
        return cryptoHelper;
    }

    public byte[] doHashPassword(String password) {

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance(SHA256_ALGORITHM);
            return md.digest(password.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }

    }

    public String generateRandomString(int length) throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int chara = sr.nextInt(randomString.length());
            sb.append(randomString.charAt(chara));
        }

        return sb.toString();
    }

    public String byteArrayToHexString(byte[] byteArr) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : byteArr) {
            hexString.append(String.format("%02X", b));
        }
        return hexString.toString();
    }

}
