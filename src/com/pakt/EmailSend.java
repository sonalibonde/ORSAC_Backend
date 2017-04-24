package com.pakt;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class EmailSend {

 public void sendHtmlEmail(String to,String ownerid) throws AddressException,
	            MessagingException {
	 System.out.println("in Email send");
	 
	// String host="mail.javatpoint.com";//change accordingly  
    // String to="sonoojaiswal1987@gmail.com";//change accordingly  
     final String user="tech@indtrack.com";//change accordingly  
     final String password="Tech@3469";//change accordingly  
 
     Properties properties = System.getProperties();  
     
     
     properties.put("mail.smtp.auth", "true");
     properties.put("mail.smtp.starttls.enable", "true");
     properties.put("mail.smtp.host", "smtp.gmail.com");
       properties.put("mail.smtp.port", "587");
     
//     properties.setProperty("mail.smtp.host", "smtp.gmail.com");  
//     properties.put("mail.smtp.socketFactory.class",  
//             "javax.net.ssl.SSLSocketFactory");  
//     properties.put("mail.smtp.auth", "true");  
//     properties.put("mail.smtp.port", "587");
//     properties.put("mail.smtp.auth", "true");  
 
     Session session = Session.getDefaultInstance(properties,  
    		 new javax.mail.Authenticator() {  
    protected PasswordAuthentication getPasswordAuthentication() {  
     return new PasswordAuthentication(user,password);  
    }  
     });  
       
     try{  
        MimeMessage message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(user));  
        message.addRecipient(Message.RecipientType.TO,  
                                 new InternetAddress(to));  
 
       message.setSubject("HTML Message");  
       
//       <a href="mailto:someone@somewhere.com?subject=Welcome&body=Check this out, please click <a href='http://www.mydomain.com'>here</a>">Send to a Friend</a>
       message.setContent("<a href='http://elocator.in/ForgotPassword?ownersid="+ownerid+"'>Link</a>","text/html" );  
   
      Transport.send(message);  
        System.out.println("message sent....");  
     }catch (MessagingException ex) {ex.printStackTrace();}  

 }
	
	
	
}