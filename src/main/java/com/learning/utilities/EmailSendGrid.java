package com.learning.utilities;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.codec.binary.Base64;

public class EmailSendGrid {
	
	private static final String TO_ADDRESS = ConfigReader.getConfig("to_address");
	private static final String CC_ADDRESS = ConfigReader.getConfig("cc_address");
	private static final String FROM_ADDRESS = "harsh.mishra@quovantis.com";
	
	private static final String FROM_NAME = "mPulse Automation Practice Suit";
	private static final String SENDGRID_API_KEY = "<Key>";
	
	public static void sendEmailMessage(String subject, String messageText) throws IOException {
	    Content content = new Content("text/plain", messageText);
	    Mail mail = buildMail(subject);
	    mail.addContent(content);
	    sendEmailRequest(mail);
	}
	
	public static void sendEmailMessage(String subject, String messageText, String filePath, String fileName) throws IOException {
	    Content content = new Content("text/plain", messageText);
	    
	    Mail mail = buildMail(subject);
	    mail.addContent(content);
	    Path file = Paths.get(filePath);
        Attachments attachments = new Attachments();
        byte[] attachmentContentBytes = Files.readAllBytes(file);
        
	    Base64 x = new Base64();
	    String encodedString = x.encodeAsString(attachmentContentBytes);
	    attachments.setContent(encodedString);
	    attachments.setDisposition("attachment");
//	    attachments.setFilename(file.getFileName().toString());
	    attachments.setFilename(fileName);
	    attachments.setType("text/html");
	    mail.addAttachments(attachments);
	    sendEmailRequest(mail);
	}
	
	private static void sendEmailRequest(Mail mail) throws IOException {
		SendGrid sg = new SendGrid(SENDGRID_API_KEY);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println("Response Status code -"+ response.getStatusCode());
			if (response.getStatusCode() == 202) {
				System.out.println("Email Message sent successfully.... to "+TO_ADDRESS+ ", "+CC_ADDRESS);
			}
		} catch (IOException ex) {
			System.out.println(ex);
			throw ex;
		}
	}
	
	public static Mail buildMail(String subject) {
		Email from = new Email(FROM_ADDRESS);
	    from.setName(FROM_NAME);
	    
	    Mail mail = new Mail();
	    mail.setFrom(from);
	    
	    Personalization personalization = new Personalization();
	    personalization.setSubject(subject);
	    
	    Email to = new Email();
	    for (String toEmail: TO_ADDRESS.split(",")) {
	    	to.setEmail(toEmail.trim());
	    	personalization.addTo(to);
	    }
	    
	    Email cc = new Email();
	    for (String ccEmail: CC_ADDRESS.split(",")) {
	    	cc.setEmail(ccEmail.trim());
	    	personalization.addCc(cc);
	    }
	    
	    mail.addPersonalization(personalization);
	    return mail;
	}
}
