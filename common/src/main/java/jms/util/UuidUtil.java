package jms.util;

public class UuidUtil {

    private static final long MSB = 0x8000000000000000L;

    public static String generateFromPhoneNumber(String phoneNumber) {

        String numString = phoneNumber.replaceAll("[^\\d]", "");
        int middle = numString.length() / 2;

        long num1 = Long.parseLong(numString.substring(0, middle));
        long num2 = Long.parseLong(numString.substring(middle));

        return Long.toHexString(MSB | num1) + Long.toHexString(MSB | num2);

    }

}
