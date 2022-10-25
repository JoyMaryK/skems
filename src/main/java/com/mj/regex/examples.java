package com.mj.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class examples {
    
    // Function to validate the username
    public static boolean isValidUsername(String name)
    {
  
        // Regex to check valid username.
        String regex = "^[A-Za-z]\\w{5,29}$";
  
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
  
        // If the username is empty
        // return false
        if (name == null) {
            return false;
        }
  
        // Pattern class contains matcher() method
        // to find matching between given username
        // and regular expression.
        Matcher m = p.matcher(name);
  
        // Return if the username
        // matched the ReGex
        return m.matches();
    }
  
    public static boolean isValidPhoneNo(String phoneNo){

         String regexPhone = "^[0][1|7]\\d{8}$" ;

         Pattern p1 = Pattern.compile(regexPhone);

         Matcher m1 = p1.matcher(phoneNo);
          
         return m1.matches();
    }

    public static boolean isValidRegNo(String regNo){

        String regexReg = "^[A-Z]P?[0-9][0-9]\\/\\d{5}\\/[1|2][0-9]$" ;

        Pattern p1 = Pattern.compile(regexReg);

        Matcher m1 = p1.matcher(regNo);
         
        return m1.matches();
   }

    // Driver Code
    public static void main(String[] args)
    {
  
        


        String p1= "S13/03140/18", p2 = "SP13/03140/22", p3= "SP13\\03140\\29", p4="SS14";
        System.out.println("p1"+isValidRegNo(p1));
        System.out.println("p2"+isValidRegNo(p2));
        System.out.println("p3"+isValidRegNo(p3));
        System.out.println("p4"+isValidRegNo(p4));
    }
}
