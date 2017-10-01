package com.hg.mylifetomorrow.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.mylifetomorrow.domain.EmailData;

	@Service
	public class EmailService {		
		
		private final Properties prop= System.getProperties();
		private final static String SMTP_HOST = "smtp.gmail.com";
		private final static String SMTP_PORT = "587";
		private final static  String GMAIL_USERNAME = "haasinigroups@gmail.com";
		private final static  String GMAIL_PASSWORD = "dbllncghonxviexj";
		
		@Autowired
		private ServletContext context;
		
	
		public void createEmail(final EmailData emailData) throws AddressException, MessagingException, IOException { 
			
			final Session session=	getSession();
			//session.setDebug(true);	
	
			MimeMessage message = new MimeMessage(session);
	
			message.setFrom(new InternetAddress(GMAIL_USERNAME,"Haasini Groups"));
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(emailData.getTo(),emailData.getUserName()));
			message.setSubject("Welcome to My Life Tomorrow!");
			
			
			
			if(emailData.getBodyTemplate().equalsIgnoreCase("SIMPLE")){
				message.setText("Password: "+emailData.getBodyQ());
			}else{
			
		         MimeMultipart multipart = new MimeMultipart("related");
	
	
		         BodyPart messageBodyPart = new MimeBodyPart();
		         final InputStream emailBodyStream=context.getResourceAsStream("/static/email/email-content.html");
		         final StringBuilder emailbody=new StringBuilder();
		         String htmlemailBody = IOUtils.toString(emailBodyStream, "UTF-8");
		         emailbody.append(htmlemailBody.replace("#USER#", emailData.getUserName()));
		         final StringBuilder body=new StringBuilder();
		         body.append("<div style=\"margin-bottom: 20px;font-size: 16px;font-weight: 300;line-height: 1.4;\">");
		         if(emailData.getQuesOrReply().equalsIgnoreCase("Q")){		        	 
		        	 body.append("<div style=\"width:80px;float:left;font-weight: bold;\">Question :</div>");
		        	 body.append("<div style=\"border-bottom: 1px black dotted;margin-bottom:20px;\">");
		        	 body.append(emailData.getBodyQ());
		        	 body.append("</div>");

		         }else {
		        	 body.append("<div style=\"width:80px;float:left;font-weight: bold;\">Question :</div>");
		        	 body.append("<div style=\"border-bottom: 1px black dotted;margin-bottom:20px;\">");
		        	 body.append(emailData.getBodyQ());
		        	 body.append("</div>");
		        	 body.append("<div style=\"width:120px;float:left;font-weight: bold;\">Guru Reply :</div>");
		        	 body.append("<div style=\"border-bottom: 1px black dotted;margin-bottom:25px;\">");
		        	 body.append(emailData.getBodyR());
		        	 body.append("</div>");
		         }
		         body.append("</div>");
		         final String emailBodyContent=emailbody.toString().replace("#BODY#", body.toString());	    	

		         System.out.println("-----------------------------------");
		         System.out.println(emailBodyContent);
		         messageBodyPart.setContent(emailBodyContent, "text/html");
		         // add it
		         multipart.addBodyPart(messageBodyPart);
	
		         // second part (the image)
		         messageBodyPart = new MimeBodyPart();
		         final InputStream imgStream=context.getResourceAsStream("/static/img/hg-logo_email.jpg");
		         DataSource fds = new ByteArrayDataSource(imgStream,"image/jpeg");
	
		         messageBodyPart.setDataHandler(new DataHandler(fds));
		         messageBodyPart.setHeader("Content-ID", "<image>");
	
		         // add image to the multipart
		         multipart.addBodyPart(messageBodyPart);
	
		         // put everything together
		         message.setContent(multipart);
			}


	         System.out.println("Sent message successfully....");
			Transport transport = session.getTransport("smtp");
			transport.connect(SMTP_HOST, GMAIL_USERNAME, GMAIL_PASSWORD);
			transport.sendMessage(message, message.getAllRecipients());
		}
		
		@PostConstruct
		public void setupSMTPProperties(){
			prop.setProperty("mail.smtp.starttls.enable", "true");
			prop.setProperty("mail.smtp.host", SMTP_HOST);
			prop.setProperty("mail.smtp.user", GMAIL_USERNAME);
			prop.setProperty("mail.smtp.password", GMAIL_PASSWORD);
			prop.setProperty("mail.smtp.port", SMTP_PORT);
			prop.setProperty("mail.smtp.auth", "true");
		}
	
		private Session getSession(){
			Session session = Session.getInstance(prop, new Authenticator()  {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(GMAIL_USERNAME,
	                    GMAIL_PASSWORD);
				}
			});
			return session;
		}
}
		
