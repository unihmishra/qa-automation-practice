package com.learning.utilities;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Author: Harsh Mishra
 * Date: Dec-2018
 */
public class EmailSMTP {
	private static String TO_ADDRESS = ConfigReader.getConfig("to_address");
	private static String CC_ADDRESS = ConfigReader.getConfig("cc_address");

	private static final String FROM_NAME = "qaauto.mpulse@gmail.com";
	private static final String username = "qaauto.mpulse@gmail.com";
	private static final String password = "******";

	private static final String host = "smtp.gmail.com";
	private static final String port = "587";

	public static void sendEmailMessage(String subject, String messageText) {
		Message message = getMessageObj();
		try {
			message.setFrom(new InternetAddress(FROM_NAME));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(TO_ADDRESS, false));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CC_ADDRESS, false));
			message.setSubject(subject);
			message.setText(messageText);			
			message.setSentDate(new Date());
			Transport.send(message);
			System.out.println("Message sent successfully....");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void sendEmailMessage(String subject, String messageText, String filePath, String fileName) {
		Message message = getMessageObj();
		try {
			message.setFrom(new InternetAddress(FROM_NAME));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(TO_ADDRESS, false));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CC_ADDRESS, false));
			message.setSubject(subject);
			try {
				emailWithAttachments(message, messageText, filePath, fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			message.setSentDate(new Date());
			Transport.send(message);
			System.out.println("Message sent successfully....");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private static Message getMessageObj() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		// Create a default MimeMessage object.
		return new MimeMessage(session);
	}
	
	private static void emailWithAttachments(Message message, String textMessage, String filePath, String fileName) throws Exception {
		// creating first MimeBodyPart object 
        BodyPart part1 = new MimeBodyPart();  
        part1.setText(textMessage); 
          
        // creating second MimeBodyPart object 
        BodyPart part2 = new MimeBodyPart();  
        DataSource source = new FileDataSource(filePath);   
        part2.setDataHandler(new DataHandler(source));   
        part2.setFileName(fileName);   
          
        // creating MultiPart object 
        Multipart multipartObject = new MimeMultipart();   
        multipartObject.addBodyPart(part1);   
        multipartObject.addBodyPart(part2);   
  
        // set body of the email. 
        message.setContent(multipartObject);
	}
}
