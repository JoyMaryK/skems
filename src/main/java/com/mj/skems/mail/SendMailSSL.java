package com.mj.skems.mail;

public class SendMailSSL{    
    public static void main(String[] args) {    


        String content = "Dear [[name]],<br>"
            + "Please click the link below to verify your registration:<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
            + "Thank you,<br>"
            + "Your company name.";
        //from,password,to,subject,message  
        SendMail.send("joyskems@gmail.com","xeo cjac blqd ewqlt","joyskems@gmail.com","hello javatpoint",content);  
        //change from, password and to  
    }    
   }   