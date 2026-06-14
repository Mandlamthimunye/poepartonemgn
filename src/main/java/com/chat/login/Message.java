
package com.chat.login;

//import packages
import java.io.*;
import java.util.Random;
import org.json.JSONObject;




public class Message {//start of class
 
   //arrays for stored messages
    public static final int MAX_MESSAGES = 100;
    public static Message[] sentMessagesArray = new Message[MAX_MESSAGES];
    public static int messageCount = 0;
    static int globalSequenceCounter = 0;
  
  //new arrays for discarded messages
 public static Message[] disregardedMessagesArray = new Message[MAX_MESSAGES];
  public static int disregardedCount = 0; 
  
    //declaring variables messageId,numOfMessages,recipient,messageText,messageHash.
    public String messageId;
    public int sequenceNumber;
    public String recipient;
    public String messageText;
    public String messageHash;
        

    //constructors for random ten digits
    public Message(String recipient,String messageText, String uniqueMessageId){//open constructors
      this.recipient = recipient;
      this.messageText = messageText;
      this.messageId = uniqueMessageId;

    }//close constructors
    
    
    //constructors to recreate a message from JSON file
    public Message(JSONObject json){//start of message method
        this.messageId = json.getString("messageId");
        this.sequenceNumber = json.getInt("sequenceNumber");
        this.recipient = json.getString("recipient");
        this.messageText = json.getString("messageText");
        this.messageHash = json.getString("messageHash");
    }//end of message method

    //create message id method for rendom generation of numbers
   public String generateRandomMessageId(){//start of method

     Random rand = new Random();
     StringBuilder sb = new StringBuilder();

     for(int i =0; i < 10; i++){//for loop
          
                sb.append(rand.nextInt(10));
     }//end of for loop

     return sb.toString();

 }//end of generateRandomMessageId method
   
   //check message Id method
   public boolean checkMessageId(){//start of check message ID  method

      return messageId != "varified" && messageId.length() == 10 && messageId.matches("\\d+");

   }//end of check message ID method
    
   //check recepient cell phone number method
   public  String checkRecipientCell(){//start of check recipient cell method
    
    //IF statements to verify cellphone numbers
    if(recipient == null || recipient.isEmpty()){
      return "invalid cell phone number.";
    }
    if(recipient.length() > 12){

      return "Recipient cellphone number must be 10 characters long.";
    }
    //starts with international code '+'
    if(!recipient.startsWith("+")&& !Character.isDigit(recipient.charAt(0))){

      return "Cell phone number is incorrectly formated or does not contain an international code.Please correct the number and try again.";
    }
    if (recipient.startsWith("+") && recipient.length() < 2){

    return "After '+' two digits must follow e.g +27.";//the must be '+' follow by atleast two digits
    } 

      return null;//if all conditions are met return null";
  }//end of chech recipient cell method

   // create messagehash method
public String createMessageHash(){//start of create messagehash method
    
    if(sequenceNumber < 0 || messageText == null || messageText.trim().isEmpty()){
      return "";
    }
    String firstTwo = messageId.substring(0,2);
    String[] words = messageText.trim().split("\\s+");
      String firstWord = words[0];
    String lastWord = words[words.length - 1];
    String combined = (firstWord + lastWord).toUpperCase();
    
    return String.format("%s:%d:%s", firstTwo, sequenceNumber, combined);   
  
 }//end of messagehash method

//send method
public void send(){//start of send method
  this.sequenceNumber= ++globalSequenceCounter;
  this.messageHash = createMessageHash();
  
  if(messageCount < MAX_MESSAGES){
      sentMessagesArray[messageCount] = this;
      messageCount++;
      
  }else{
      System.out.println("Message is not stored in memory");
  }
  
    }//end of send method


//store messages using JSON file format
    public void prepareForStorage(){//start of prepare for storage method
      this.sequenceNumber = 0;
      this.messageHash = createMessageHash();
    }//end of prepare for storage method

    //STORE MESSAGE METHOD
    public void storeMessage(){//start of store message method
       JSONObject json = new JSONObject(); 
        json.put("Message ID: ", this.messageId);
        json.put("Recipient : ",this.recipient);
        json.put("Message Text: " ,this.messageText);
        json.put("Message Hash: ",this.messageHash);
        
        try(FileWriter fw = new FileWriter("message.jason", true);
                PrintWriter out = new PrintWriter(fw)){//start of try-catch block
            System.out.println(json.toString());
          System.out.println("Message stored successfully in messages.json (JSON Format)");
          
        }catch (IOException e){
            System.out.println("Error storing message as JSON: "+ e.getMessage());
        }//end of try-catch block
    }//end of store message method
    
      

    //method to print messages, display sent messages
    public static String printMessages(){//start of method
      if(messageCount == 0){//start of if statement
        return "No messages have been sent yet.";
      }//end of if statement
        StringBuilder sb = new StringBuilder();
        for(int i =0; i<messageCount;i++){
            Message message = sentMessagesArray[i];
          sb.append("=====SENT MESSAGES=====");
          sb.append("Message ID: ").append(message.messageId).append("\n");
          sb.append("Recipient : ").append(message.recipient).append("\n");
          sb.append("Message Text: ").append(message.messageText).append("\n");
          sb.append("Message Hash: ").append(message.messageHash).append("\n");
          sb.append("=====END=====");
        }
        return sb.toString();
      }//end of method
    
// method for storing disregarded messages
public void disregarded(){//start of discard method
    if(disregardedCount < MAX_MESSAGES){//start of if statement
      disregardedMessagesArray[disregardedCount] = this;
      disregardedCount++;
    }else{
      System.out.println("discarded messages array is full.");  
    }//end of if statement
}//end of discard method


    //method to return total messages sent
    public static int returnTotalMessages(){//start of method
      return globalSequenceCounter;
    }//end of method  
    
    //method to read messages from JSON File
    public static java.util.List<JSONObject> readAllMessagesFromJSON(){
        java.util.List<JSONObject> messages = new java.util.ArrayList<>();
        File file =new File("messages.json");
        if(!file.exists())
            return messages;
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            
            while((line = reader.readLine())!= null){
            if(line.trim().isEmpty())
                continue;
            JSONObject obj = new JSONObject(line);
            messages.add(obj);
        }
        }catch(IOException e){
            System.out.println("Error reading JSAON File:" + e.getMessage());
                }
        return messages;
        }
    

    //getters
    public String getMessageId() {
      return messageId;
    }
    public String getRecipient() {
      return recipient;
    }
    public String getMessageText() {
      return messageText;
    }
    public String getMessageHash() {
      return messageHash;
    }
    public int getSequenceNumber(){
      return sequenceNumber;
    }
    public static Message[]getDiscardedMessagesArray(){
        return disregardedMessagesArray;
    }
    public static int getDiscardedMessageArray(){
        return disregardedCount;
    }

    //getters for the arrays
    public static Message[] getSentMessagesArray(){
        return sentMessagesArray;
    }
    public static int getMessageCount(){
        return messageCount;
    }
    
    
  }//end of class
