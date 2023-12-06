package az.azercell.bankingapp.util;

public class PhoneNumberCheckerUtil {

    public static boolean isAzerbaijaniPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10) return false;
        for (char c : phoneNumber.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
