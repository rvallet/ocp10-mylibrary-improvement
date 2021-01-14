package com.library.mslibrary.utils;

import org.apache.commons.lang.RandomStringUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class RandomTools {

    public static int randomNum (int min, int max) throws NoSuchAlgorithmException {
        SecureRandom ran = new SecureRandom();
        return ran.nextInt(max-min+1)+min;
    }

    public static boolean randomBoolean() throws NoSuchAlgorithmException {
        SecureRandom ran = SecureRandom.getInstanceStrong();
        return ran.nextBoolean();
    }

    public static String randomText(int nbmaxNbWords, int maxWordSize) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        Random ran = SecureRandom.getInstanceStrong();

        for (int i=0; i<ran.nextInt(nbmaxNbWords); i++) {
            int ranWordSize = ran.nextInt(maxWordSize);
            if (ranWordSize < 2) {ranWordSize=2;}
            sb.append(RandomStringUtils.random(ranWordSize, true, false)+" ");
        }
        sb.append(".");
        return sb.toString();
    }
}
