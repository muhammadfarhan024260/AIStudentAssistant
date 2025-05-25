package bukc.project;

public class OTPGenerator {
    public static String generateOTP() {
        return String.valueOf(100000 + new java.util.Random().nextInt(900000));
    }
}