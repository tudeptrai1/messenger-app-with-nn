
package Network;



import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class SendEmail implements Runnable{
    private String recepient = null;
    private String titleEmail = null;
    private String contentEmail = null;
    
    public SendEmail(String recepient,String titleEmail,String contentEmail) {
        this.recepient = recepient;
        this.titleEmail = titleEmail;
        this.contentEmail = contentEmail;
    }
    
    
    
    public boolean sendMail(String recepient,String titleEmail,String contentEmail) throws Exception{
        System.out.println("Prepare send email");
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myAccountEmail = "gasieuviet1234@gmail.com";
        String myPassword = "gacuanam123";
                
        Session session = Session.getInstance(properties,new Authenticator() {
            @Override
            protected  PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountEmail, myPassword);
            }
        });
        
        Message message = null;
        message = prepareMessage(session,myAccountEmail,recepient,titleEmail,contentEmail);
        if(message == null){
            return false;        
        }
        try {
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }

    private Message prepareMessage(Session session, String myAccountEmail, String recepient, String titleEmail, String htmlCode) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(titleEmail);
            message.setContent(htmlCode,"text/html");
            return message;
        } catch (Exception e) {
            System.out.println("Can't send Email !!! Error : " + e.getMessage());
        }
        return null;
    }

    @Override
    public void run() {
        try {
            boolean rs = sendMail(recepient, titleEmail, contentEmail);
            if(rs){
                System.out.println("Gui mail thanh cong");
            }
            else{
                System.out.println("Khong the gui");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }   
}
