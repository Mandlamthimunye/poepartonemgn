
package com.chat.login;

//import packages
import org.json.JSONObject;
import java.io.*;
/**
 *
 * @author Mandla
 */
public class StoredMessageManager {//start of class
    
    //declare and populate arrays for stored  messages,message id,message hash,recipient
    public static  int MAX_STOREDMESSAGES = 100;
    public static Message[]storedMessages = new Message[MAX_STOREDMESSAGES];
    public static String[]storedMessageId = new String[MAX_STOREDMESSAGES];
    public static String[]storedMessageHash = new String[MAX_STOREDMESSAGES];
    public static String[] storedRecipient = new String[MAX_STOREDMESSAGES];
    public static int storedCount = 0;
    
    
    //creating a method to read JSON file into arrays and desplay to the user
    public static void readFromJSON(){//start of readf from JSON File method
        File file = new File("message.json");
        
        if(!file.exists())
            return;
        
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){//start of try-catch block
         String line;
         storedCount = 0;
         
         while((line = reader.readLine()) != null && storedCount < MAX_STOREDMESSAGES){//start of while loop
             if(line.trim().isEmpty())
                 continue;
             JSONObject json = new JSONObject(line);
             Message message = new Message(json);
             storedMessages[storedCount] = message;
             storedMessageId[storedCount] = message.getMessageId();
             storedMessageHash[storedCount] = message.getMessageHash();
             storedRecipient[storedCount] = message.getRecipient();
             storedCount++;
             
         }//end of while loop
         
    }catch(IOException e){
     System.out.println("error reading JSON file:" + e.getMessage());
    }    
  }//end of method
    
    
    //method to add stored messages from JSON file to arrays
    public static void addStoredMessage(Message message){//start of add stored messages method
        if(storedCount < MAX_STOREDMESSAGES){//start of if-else statement
            storedMessages[storedCount] = message;
            storedMessageId[storedCount] = message.getMessageId();
            storedMessageHash[storedCount] = message.getMessageHash();
            storedRecipient[storedCount] = message.getRecipient();
            storedCount++;
            
        }else{
            System.out.println("Stored messages array is full.");
        }//end of if-else statement
    }//end of 
    
    //a) Display the sender and recipient of all stored messages
    public static String displayAllStoredMessage(){//start of method
        if(storedCount == 0){
           System.out.println("no stored messages found");
        }
        StringBuilder sb = new StringBuilder();
        for(int i =0;i < storedCount;i++){
            sb.append("Recipient:").append(storedMessages[i].getRecipient()).
                    append(",Message:").append(storedMessages[i].getMessageText()).append("\n");
            
        }
        return sb.toString();
    }//end of method
   
    //b) Display the longest message
    public static String getLongestMessage(){
        if(storedCount ==0)
            return"no stored messages";
        Message longest = storedMessages[0];
        for(int i = 1;i<storedCount;i++){
            if(storedMessages[i].getMessageText().length()> longest.getMessageText().length()){
                longest = storedMessages[i];
            }
        }
        return"Longest message\nRecipient:"+longest.getRecipient()+
                "\nMessage:" + longest.getMessageText()+
                "\nLength: " + longest.getMessageText().length() + "chars";
    }
    //c) Search a message ID and desplay all the corresponding recipient and message
    public static String searchByMessageId(String messageId){
      for(int i = 0; i<storedCount;i++){
          if(storedMessageId[i].equals(messageId)){
              Message message = storedMessages[i];
              return "Found: Recipient = "+ message.getRecipient()+ ",Message ="+ message.getMessageText();
          }
      }
      return "Message Id not found";
    }
    
    //d) search for all messages stored by a particular recipient
    public static String searchByRecipient(String recipient){//start of method
       StringBuilder results = new StringBuilder();
       boolean found = false;
       for(int i = 0; i< storedCount;i++){
        
       if(storedMessages[i].getRecipient().equalsIgnoreCase(recipient)){
           found = true;
           results.append("Message ID:").
                   append(storedMessageId[i]).append("Message:").
                   append(storedMessages[i].getMessageText()).
                   append("\n");
       }
    }
       return found ? results.toString(): "No nessages for recipient"+ recipient;
    }
    //e) delete a message using message hash
    public static boolean deleteByMessageHash(String messageHash){
        int index = -1;
    
        for(int i= 0; i <storedCount; i++){
            if (storedMessageHash[i].equals(messageHash)){
                index = i;
                break;
            }
        }
        if(index == -1)return false;
          
        
        for(int i= index;i<storedCount -1;i++){
            storedMessages[i]= storedMessages[i+1];
            storedMessageHash[i] = storedMessageHash[i+1];
            storedMessageId[i] = storedMessageId[i+1];
        }
        storedMessages[storedCount-1] = null;
        storedMessageHash[storedCount-1] = null;
        storedMessageId[storedCount-1] = null;
        storedCount--;
        
        return true;
        
        
           
    }
    //f) desplay a report that lists the full details of all the stored messages
    public static String getDisplayFullReport(){
        if(storedCount ==0)
            return"no stored messages";
        StringBuilder sb = new StringBuilder();
        sb.append("==== STORED MESSAGE REPORT ====");
        
        for(int i =0; i<storedCount; i++){
            Message message = storedMessages[i];
            sb.append("message number:").append(i+1).append("\n");
            sb.append("Message ID: ").append(message.getMessageId()).append("\n");
            sb.append("Message Hash:").append(message.getMessageHash()).append("\n");
            sb.append("Recipient:").append(message.getRecipient()).append("\n");
            sb.append("Sequence number:").append(message.getSequenceNumber()).append("\n");
            sb.append("Message Text:").append(message.getMessageText()).append("\n");
            sb.append("====END OF STORED MESSAGE REPORT ====");
            
        }
        return sb.toString();
    }
    
    public static int getStoredCount(){
        return storedCount;
    }
}//end of class