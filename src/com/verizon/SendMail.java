package com.verizon;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class SendMail
{
	//private static final String SMTP_HOST_NAME = "smtp.vzbi.com";
	private static final String SMTP_HOST_NAME = "smtp.vzbi.com";
	
	  private static String emailMsgTxt      = "Today Food Availability";
	  private static String emailSubjectTxt  = "Food Availability On "   ;
	  private static String emailFromAddress = "francis.a.ramesh@gmail.com";
	  private static String[] emailList = {"crisaris007@gmail.com", "francis.a.michael@verizon.com", "francis.a.ramesh@gmail.com", "arock_1978@yahoo.co.in"};
	
	  public static void main(String args[]) throws Exception
	  {
	    SendMail smtpMailSender = new SendMail();
	    emailSubjectTxt += getCurrentDate();
	    smtpMailSender.postMail( emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
	    System.out.println("Sucessfully Sent mail to All Users");
	  }
	
	  public static String getCurrentDate()
	  {
		  	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			//get current date time with Date()
			Date date = new Date();
						  
			//get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			return(dateFormat.format(cal.getTime()));
	  }
	  
	  public void postMail( String recipients[ ], String subject,
	                            String message , String from) throws MessagingException
	  {
	    boolean debug = false;
	
	     Properties props = new Properties();
	     props.put("mail.smtp.host", SMTP_HOST_NAME);
	     props.put("mail.smtp.auth", "true");
	
	    Authenticator auth = new SMTPAuthenticator();
	    Session session = Session.getDefaultInstance(props, auth);
	
	    session.setDebug(debug);
	
	
	    Message msg = new MimeMessage(session);
	
	
	    InternetAddress addressFrom = new InternetAddress(from);
	    msg.setFrom(addressFrom);
	
	    InternetAddress[] addressTo = new InternetAddress[recipients.length];
	    for (int i = 0; i < recipients.length; i++)
	    {
	        addressTo[i] = new InternetAddress(recipients[i]);
	    }
	    msg.setRecipients(Message.RecipientType.TO, addressTo);
	
	    msg.setSubject(subject);
	    msg.setContent(message, "text/plain");
	    Transport.send(msg);
	    
	    sendSMS();
	 }
	
	private class SMTPAuthenticator extends javax.mail.Authenticator
	{
	
	    public PasswordAuthentication getPasswordAuthentication()
	    {
	        String username = "";
	        String password = "";
	        return new PasswordAuthentication(username, password);
	    }
	}
	
	public static void sendSMS() {
        try {
                String host = "localhost";
                int port = 9500;
                String username = "admin";
                String password = "abc123";

                MyOzSmsClient osc = new MyOzSmsClient(host, port);
                osc.login(username, password);

                String line = "Hello World";

                if(osc.isLoggedIn()) {
                	System.out.println("SMS message:");
                	osc.sendMessage("+919965477443", line);
                    osc.logout();
                }


        } catch (IOException e) {
                System.out.println(e.toString());
                e.printStackTrace();
                
        } catch (InterruptedException e) {
                System.out.println(e.toString());
                e.printStackTrace();
        }
}

	
}


