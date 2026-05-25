
package com.chat.login;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;




public class Message {//start of class

  public static int globalSequenceCounter = 0;
  public static List<Message> sentMessagesList = new ArrayList<>();
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

    //create message id method for rendom generation of numbers
   public String generateRandomMessageId(){//start of method

     Random rand = new Random();
     StringBuilder sb = new StringBuilder();

     for(int i =0; i < 10; i++){//for loop
          
                sb.append(rand.nextInt(10));
     }//end of for loop

     return sb.toString();

 }
   //check message Id method
   public boolean checkMessageId(){//start of method

      return messageId != "varified" && messageId.length() == 10 && messageId.matches("\\d+");

   }//end of method
    
   //check recepient cell phone number method
   public  String checkRecipientCell(){//start of method
    
    //IF statements to verify cellphone numbers
    if(recipient == null || recipient.isEmpty()){
      return "invalid cell phone number.";
    }
    if(recipient.length() > 10){

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
  }

   // create messagehash method
public String createMessageHash(){//start of  messagehash method
    
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

public void send(){//start of method
  this.sequenceNumber= ++globalSequenceCounter;
  this.messageHash = createMessageHash();
  sentMessagesList.add(this);
    }//end of method

    public void prepareForStorage(){//start of method
      this.sequenceNumber = 0;
      this.messageHash = createMessageHash();
    }

    //STORE MESSAGE METHOD
    public void storeMessage(){//start of method
      try(PrintWriter writer = new PrintWriter(new FileWriter("messages.txt", true))){

        writer.println("=====Message Details=====");
        writer.println("Message ID: " + this.messageId);
        writer.println("Recipient : " + this.recipient);
        writer.println("Message Text: " + this.messageText);
        writer.println("Message Hash: " + this.messageHash);
        writer.println("-----------------------------------");
        System.out.println("Message stored successfully in messages.txt");
      }catch(IOException e){
        System.out.println("An error occurred while storing the message: " + e.getMessage());
      }
    }
      

    //method to print messages
    public static String printMessages(){//start of method
      if(sentMessagesList.isEmpty()){
        return "No messages have been sent yet.";

      }else{
        StringBuilder sb = new StringBuilder();
        sb.append("=====Sent Messages=====\n"); 
        
        for(Message message : sentMessagesList){
          sb.append("Message ID: ").append(message.messageId).append("\n");
          sb.append("Recipient : ").append(message.recipient).append("\n");
          sb.append("Message Text: ").append(message.messageText).append("\n");
          sb.append("Message Hash: ").append(message.messageHash).append("\n");
          sb.append("-----------------------------------\n");
        }
        return sb.toString();
      }
    }//end of method


    //method to return total messages sent
    public static int returnTotalMessages(){//start of method
      return globalSequenceCounter;
    }//end of method  

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
  }//end of class
