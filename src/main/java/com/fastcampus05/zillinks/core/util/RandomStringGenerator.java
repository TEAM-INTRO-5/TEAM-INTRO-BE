package com.fastcampus05.zillinks.core.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

public class RandomStringGenerator {
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String SPECIAL = "!@#";
    private static final int STRING_LENGTH = 12;

    public static String generateRandomString() {
        SecureRandom random = new SecureRandom();
        ArrayList<Character> chars = new ArrayList<>(STRING_LENGTH);

        // 특수 문자 3개
        for (int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(SPECIAL.length());
            char randomChar = SPECIAL.charAt(randomIndex);
            chars.add(randomChar);
        }

        // 나머지 9개 문자의 개수와 종류 결정
        int remainingCount = STRING_LENGTH - 3;
        int numberOfUpperChars = random.nextInt(remainingCount + 1);
        int numberOfLowerChars = random.nextInt(remainingCount + 1 - numberOfUpperChars);
        int numberOfNumberChars = remainingCount - numberOfUpperChars - numberOfLowerChars;

        for (int i = 0; i < numberOfUpperChars; i++) {
            int randomIndexUpper = random.nextInt(CHAR_UPPER.length());
            char randomCharUpper = CHAR_UPPER.charAt(randomIndexUpper);
            chars.add(randomCharUpper);
        }

        for (int i = 0; i < numberOfLowerChars; i++) {
            int randomIndexLower = random.nextInt(CHAR_LOWER.length());
            char randomCharLower = CHAR_LOWER.charAt(randomIndexLower);
            chars.add(randomCharLower);
        }

        for (int i = 0; i < numberOfNumberChars; i++) {
            int randomIndexNumber = random.nextInt(NUMBER.length());
            char randomCharNumber = NUMBER.charAt(randomIndexNumber);
            chars.add(randomCharNumber);
        }

        Collections.shuffle(chars, random);

        StringBuilder sb = new StringBuilder(STRING_LENGTH);
        for (Character c : chars) {
            sb.append(c);
        }

        return sb.toString();
    }
}
