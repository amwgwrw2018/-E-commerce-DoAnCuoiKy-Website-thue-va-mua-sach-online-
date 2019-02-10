package SendMail;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMailService {

    private static String USER_NAME = "tannhut1391997@gmail.com";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "nhut1997"; // GMail password
//    private static String RECIPIENT = "tannhut1391997@gmail.com";

//    public static void main(String[] args) {
        String from = USER_NAME;
        String pass = PASSWORD;
//        String[] to = { RECIPIENT }; // list of recipient email addresses
//        String subject = "Java send mail example";
//        String body = "Welcome to JavaMail!";
//
//        sendFromGMail(from, pass, to, subject, body);
//    }

    public void addNewMail(String mailContentByHTML,String... to) {
//    	Random rd=new Random();
//    	int numberRd=0;
//    	String newPassword="";
//    	char charrd;
//    	for (int i = 0; i < 8; i++) {
//    	
//    		
//    		if(i%2==0) {
//    			numberRd=rd.nextInt(10)+48;
//    			newPassword+=(char)numberRd;
//    		}else {
//    			charrd=(char) (rd.nextInt(25)+65);
//    			newPassword+=charrd;
//    		}
//    	}
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

message.setContent(mailContentByHTML, "text/html; charset=utf-8");
            message.setSubject("New password");

            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
//    public static void main(String[] args) {
//		SendMailService sm=new SendMailService();
//		sm.addNewMail(mailContentByHTML, to);("123","tannhut1391997@gmail.com");
//	}

}