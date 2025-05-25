package bukc.project;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailSender {

//    public static void sendEmail(String toEmail, String subject, String body) throws MessagingException, UnsupportedEncodingException {
//    final String fromEmail = "iqify001@gmail.com"; // <-- double check this
//    final String password = "sxye gffa vwpz qyis";     // <-- must be a Gmail App Password
//
//    Properties props = new Properties();
//    props.put("mail.smtp.auth", "true");
//    props.put("mail.smtp.starttls.enable", "true");
//    props.put("mail.smtp.host", "smtp.gmail.com");
//    props.put("mail.smtp.port", "587");
//
//    Session session = Session.getInstance(props, new Authenticator() {
//        protected PasswordAuthentication getPasswordAuthentication() {
//            return new PasswordAuthentication(fromEmail, password);
//        }
//    });
//
//    Message message = new MimeMessage(session);
//    message.setFrom(new InternetAddress(fromEmail));
//    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
//    message.setSubject(subject);
//    message.setText(body);
//
//    Transport.send(message);
//}
    
    public static String otp1;
    
    public static void sendEmail(String ToName, String toEmail, String subject, String otp) throws MessagingException, UnsupportedEncodingException {
        
        otp1 = otp;
        
        final String fromEmail = "iqify001@gmail.com";
        final String password = "sxye gffa vwpz qyis"; // App Password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail, "BUKC Verification"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);

        String htmlContent = """
    <html>
        <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
            <div style="max-width: 600px; margin: auto; background-color: #fff; padding: 30px; border-radius: 8px;">
                <h2 style="color: #333;">Email Verification - BUKC</h2>
                <p style="font-size: 14px; color: #555;">Dear %s,</p>
                <p style="font-size: 14px; color: #555;">Please use the following OTP to verify your email address:</p>
                <p style="font-size: 24px; color: #000; font-weight: bold; margin: 20px 0;">%s</p>
                <p style="font-size: 14px; color: #555;">This code will expire in 10 minutes. Please do not share it with anyone.</p>
                <br>
                <p style="font-size: 14px; color: #555;">Thank you,<br><strong>BUKC Student Portal Team</strong></p>
            </div>
        </body>
    </html>
""".formatted(ToName, otp);


        message.setContent(htmlContent, "text/html; charset=utf-8");

        Transport.send(message);
    }

    public static String generateOTP() {
        int otp = (int) (Math.random() * 900000) + 100000; // 6-digit
        return String.valueOf(otp);
    }

}
