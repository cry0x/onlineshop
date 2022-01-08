package org.msia_projekt.product_service.testUtilities;

import java.util.Random;

public class RandomData {

    public static byte[] RandomByteArray(int size) {
        Random random = new Random();
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);

        return bytes;
    }
}
