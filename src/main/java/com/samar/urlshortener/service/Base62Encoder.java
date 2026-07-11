package com.samar.urlshortener.service;
import org.springframework.stereotype.Component;
@Component
public class Base62Encoder {
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = ALPHABET.length();
    public static final int SHORT_CODE_LENGTH = 7;

    public String encode(long id) {
        StringBuilder sb = new StringBuilder();
        if (id == 0) sb.append(ALPHABET.charAt(0));
        while (id > 0) { sb.append(ALPHABET.charAt((int)(id % BASE))); id /= BASE; }
        sb.reverse();
        while (sb.length() < SHORT_CODE_LENGTH) sb.insert(0, ALPHABET.charAt(0));
        return sb.toString();
    }

    public long decode(String shortCode) {
        long result = 0;
        for (char c : shortCode.toCharArray()) {
            int index = ALPHABET.indexOf(c);
            if (index == -1) throw new IllegalArgumentException("Invalid character: " + c);
            result = result * BASE + index;
        }
        return result;
    }
}
