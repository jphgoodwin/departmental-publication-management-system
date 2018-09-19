package control;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class EmailSystem {
	
	private static Session session;
	
	private static String fromAddress = "uoygpmcsystem@gmail.com";
	private static String password = "GpmcGroupA2017";
	
	private static void setUp(){
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(fromAddress, password);
					}
				  });
		EmailSystem.session = session;
	}
	
	public static void sendEmail(String emailAddress, String message) {
		setUp();
		
		try {
			Message email = new MimeMessage(session);
			email.setFrom(new InternetAddress(fromAddress));
			email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));
			email.setSubject("Test Subject");
			email.setText(message);
			
			Transport.send(email);
			System.out.println("Email Sent!");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void sendEmail(ArrayList<String> addressList, String message) {
		setUp();
		
		try {
			Message email = new MimeMessage(session);
			email.setFrom(new InternetAddress(fromAddress));
			
			InternetAddress[] address = new InternetAddress[addressList.size()];
			
			for(int i = 0; i < addressList.size(); i++){
				address[i] = new InternetAddress(addressList.get(i));
			}
			
			email.setRecipients(Message.RecipientType.TO, address);
			email.setSubject("Test Subject");
			email.setText(message);
			
			System.out.println("made it here");
			
			System.out.println(InternetAddress.toString(address));
			
			Transport.send(email);
			System.out.println("Message Sent!");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}