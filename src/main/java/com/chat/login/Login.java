package com.chat.login;

import java.util.Scanner;//scanner
import java.util.Random;
/**
 * Login class for user registration and authentication
 * @author Mandla Mthimunye(ST10527946)
 * @version 1.0
 */
public class Login {//first class for login details

    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;
    
    // Default constructor
    public Login() {
        this.username = "";
        this.password = "";
        this.cellPhoneNumber = "";
        this.firstName = "";
        this.lastName = "";
    }
    

     //constructor for Login class

    public Login(String username, String password, String cellPhoneNumber, 
                 String firstName, String lastName) {

        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;

    }
    
    
     //Checks if username contains an underscore (_) and is no more than 5 characters
     //@param username The username to validate
     //@return true if valid, false otherwise
     
    public boolean checkUserName(String username) {

        if (username == null) {
            return false;
        }
        // Check if username contains underscore and length <= 5
        return username.contains("_") && username.length() <= 5;

    }
    
    /**
     * Validates password complexity:
     * - At least 8 characters long
     * - Contains a capital letter
     * - Contains a number
     * - Contains a special character
     * @param password The password to validate
     * @return true if password meets all requirements, false otherwise
     */
    public boolean checkPasswordComplexity(String password) {

        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        // Check each character in password
        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);
            
            if (Character.isUpperCase(ch)) {
                hasCapital = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecial = true;
            }

        }
        
        return hasCapital && hasNumber && hasSpecial;

    }
    

    public boolean checkCellPhoneNumber(String phoneNumber) {

        if (phoneNumber == null) {
            return false;
        }
        
        
        String phoneRegex = "^\\+[0-9]{8,12}$";
        
        return phoneNumber.matches(phoneRegex);

    }
    
    
     //Registers a new user with validation check
    public String registerUser() {

        if (!checkUserName(this.username)) {
            return "Username is not correctly formatted; please ensure that your username " +
                   "contains an underscore and is no more than five characters in length.";
        } else if (!checkPasswordComplexity(this.password)) {
            return "Password is not correctly formatted; please ensure that the password " +
                   "contains at least eight characters, a capital letter, a number, and a special character.";
        } else if (!checkCellPhoneNumber(this.cellPhoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        } else {
            return "User successfully registered!";
        }

    }
    
    
     //Verifies login credentials
    public boolean loginUser(String enteredUsername, String enteredPassword) {

        return this.username.equals(enteredUsername) && 
               this.password.equals(enteredPassword);

    }
    
    
     //Returns login status message
     
    public String returnLoginStatus(String enteredUsername, String enteredPassword) {

        if (loginUser(enteredUsername, enteredPassword)) {

            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";

        } else {

            return "Username or password incorrect, please try again.";

        }

    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }
    
    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    //unique message ID generator method
    public static  String generateUniqueMessageId(String recipient, int counter){//start of unique message generator method
     Random random = new Random();
     StringBuilder randomPart = new StringBuilder(10);
     for (int i = 0; i < 5; i++) {
         randomPart.append(random.nextInt(10));
     }

     StringBuilder numbersOnly = new StringBuilder();

     for(Character ch : randomPart.toString().toCharArray()){
         if(Character.isDigit(ch)) numbersOnly.append(ch);   
     }

     String messageId = numbersOnly.toString();
     String subPart;
        if(messageId.length() >= 3){
            subPart = messageId.substring(0, 3);
        } else {
            subPart = String.format("%-3s", messageId).replace(' ', '0');
        }

        String counterPart = String.format("%02d", counter);

        return randomPart.toString() + subPart + counterPart;
        
    }//end of unique message generator method
    
    // Messaging menu for user interaction
    public static void messagingMenu(Scanner userInput) {//start of messaging menu method

        boolean inMessaging = true;
        while (inMessaging) {//start of while loop
        System.out.println("===== MESSAGING MENU =====");
        System.out.println("1. New Message");
        System.out.println("2. View Sent Messages");
        System.out.println("3. View total number of messages sent");
        System.out.println("4. Exit Messaging Menu");
        System.out.println("====END OF MESSAGING MENU====");
        System.out.print("Enter your choice: ");
        int choice = readInt(userInput);
        
        switch (choice) {//start of switch statement
            case 1:
                composeMessage(userInput);
                break;
            case 2:
                System.out.println(Message.printMessages());
                break;
            case 3:
                System.out.println("Total messages sent: " + Message.returnTotalMessages());
                break;
            case 4:
                inMessaging = false;
                System.out.println("Exiting messaging menu...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }//end of switch statement
        
    }//end of while loop
        
}//end of messaging method

// Method to compose a new message
    private static void composeMessage(Scanner userInput) {//start of compose message method

        System.out.println("Are sending multiple messages? ");
        String numOfMessages = userInput.nextLine().trim().toLowerCase();

        // If user indicates they are sending multiple messages, ask how many?
        if(numOfMessages.equals("yes")){
            System.out.println("How many messages would you like to send? ");
            int count = readInt(userInput);
            
            if(count <= 0){//start of if statement
                System.out.println("Invalid number of messages. Returning to messaging menu.");
                return;
            }//end of if statement

            // for Loop to compose and send multiple messages
            for(int i = 1; i <= count; i++){//start of for loop

                System.out.println("Enter recipient's cell phone number (format: + followed by 8-12 digits, e.g., +27831234567): ");
                String recipient = userInput.nextLine().trim();
                System.out.print("Enter your massage. message should not be more than 250 characters: ");
                String messageText = userInput.nextLine().trim();
                
                if(messageText.length() > 250){//start of if statement
                System.out.println("Message exceeds 250 characters by x[enter number]. Please reduce the size.");
                return;
                }//end of if statement

                String uniqueMessageId = Login.generateUniqueMessageId(recipient, i);
                Message message = new Message(recipient, messageText, uniqueMessageId);
                if (!message.checkMessageId()){//start of if statement  
                System.out.println("Message Id is invalid.");
                 return;
                }//end of if statement

                String recipientError = message.checkRecipientCell();
                if(recipientError != null){//start of if statement
                 System.out.println("Please correct the recipient's cell phone number and try again."+ recipientError);
                 continue;
                 }//end of if statement

                 System.out.println("===== MESSAGE ENTERED MENU ===== .");
        System.out.println("What would you like to do with the message?");
        System.out.println("1. Send Message");
        System.out.println("2. Save Message");
        System.out.println("3. Disregarded Message");
        System.out.println("=====END OF MESSAGE ENTERED MENU=====");
        System.out.print("Enter your choice: ");
        int choice = readInt(userInput);
        

        switch(choice){//start of switch case
            case 1:
                
                message.send();
                System.out.println("Message sent successfully!");
                System.out.println("Message ID: " + message.getMessageId());
                System.out.println("Message Hash: " + message.getMessageHash());
                System.out.println("Recipient : " + message.getRecipient());
                System.out.println("Message Text: " + message.getMessageText());
                break;

            case 2:
                System.out.println("Message saved successfully!");
                message.prepareForStorage();
                message.storeMessage(); 
                break;

            case 3:
                System.out.println("Message disregarded.");
                message.disregarded();
                break;
                
            default:

                System.out.println("Invalid choice. Please try again.");

        } //end of switch case 

    }//end of for loop

}else{

        System.out.println("Enter recipient's cell phone number (format: + followed by 8-12 digits, e.g., +27831234567): ");
        String recipient = userInput.nextLine().trim();
        System.out.print("Enter your massage. message should not be more than 250 characters: ");
        String messageText = userInput.nextLine().trim();
        
        if(messageText.length() > 250){//start of if statement
        System.out.println("Message exceeds 250 characters by x[enter number]. Please reduce the size.");
        return;
        }//end of if statement

        String uniqueMessageId = Login.generateUniqueMessageId(recipient, 1);
        Message message = new Message(recipient, messageText, uniqueMessageId);
        if (!message.checkMessageId()){//start of if statement  
        System.out.println("Message Id is invalid.");
         return;
        }//end of if statement

        String recipientError = message.checkRecipientCell();
        if(recipientError != null){//start of if statement
         System.out.println("Please correct the recipient's cell phone number and try again."+ recipientError);
         return;
         }//end of if statement

        System.out.println("===== Message Entered Menu ===== .");
        System.out.println("What would you like to do with the message?");
        System.out.println("1. Send Message");
        System.out.println("2. Save Message");
        System.out.println("3. Disregarded Message");
        System.out.println("===== END MESSAGE ENTERED =====");
        System.out.print("Enter your choice: ");
        int choice = readInt(userInput);
        

        switch(choice){//start of switch case
            case 1:
                
                message.send();
                System.out.println("Message sent successfully!");
                System.out.println("Message ID: " + message.getMessageId());
                System.out.println("Message Hash: " + message.getMessageHash());
                System.out.println("Recipient : " + message.getRecipient());
                System.out.println("Message Text: " + message.getMessageText());
                break;

            case 2:
                System.out.println("Message saved successfully!");
                message.prepareForStorage();
                message.storeMessage();
                StoredMessageManager.addStoredMessage(message);
                break;

            case 3:
                System.out.println("Message disregarded.");
                break;

            default:

                System.out.println("Invalid choice. Please try again.");

        } //end of switch case 

    }//end of else statement
}//end of compose message method

    // Method to validate recipient cell phone number
    private static int readInt(Scanner userInput) {//start of read int method

      while(true){//loop until valid input is received

        try{//attempt to read an integer from user input

            return Integer.parseInt(userInput.nextLine());

        }catch(NumberFormatException e){

            System.out.print("Invalid input. Please enter a number.");

        }

      }//end of while loop

    }//end of read int method

     // Main menu method for user interaction
    private static void mainMenu(Scanner userInput){//start of method

        boolean inMainMenu = true;

        while(inMainMenu){//start of while loop


            System.out.println("===== Welcome to QuickChat =====");
            System.out.println("=====QUICKCHAT MENU=====");
            System.out.println("1. Send Messages");
            System.out.println("2. Stored Messages");
            System.out.println("3.Coming soon!");
            System.out.println("4. Quit");
            System.out.println("=====END OF QUICKCHAT=====");
            System.out.print("choose option: ");
            int option = readInt(userInput);
            
            switch(option){//start of switch case

                case 1:
                    messagingMenu(userInput);
                    break;

                case 2:
                    storedMessagesMenu(userInput);
                    
                    break;
                case 3:
                    System.out.println("This feature is coming soon!");
                    break;
                case 4:
                    System.out.println("Thank you for using QuickChat. Goodbye!");
                    inMainMenu = false;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");

            }//end of switch case

        }//end of while loop

    }//end of method
    
    private static void storedMessagesMenu(Scanner userInput){//start of stored message menu method
        boolean inStoredMenu = true;
        
        while(inStoredMenu){//start of while loop
           System.out.println("==== STORED MESSAGES MENU ====");
           System.out.println("a. Display all stored messages(recipient and message)");
           System.out.println("b. Display the the longest message");
           System.out.println("c. Search by message Id");
           System.out.println("d. Search for all messages by recipient");
           System.out.println("e. Delete message using message hash");
           System.out.println("f. Display full report of all stored messages");
           System.out.println("g.Back to Main Menu");
           
           System.out.print("Select: ");
           String option = userInput.nextLine().trim().toLowerCase();
           
           switch(option){//start of switch statement 
               case "a":
                   System.out.println(StoredMessageManager.displayAllStoredMessage());
                   break;
                   
               case "b" :
                   System.out.println(StoredMessageManager.getLongestMessage());
                   break;
                   
               case "c" :
                   System.out.print("Please enter message ID:");
                   String messageId = userInput.nextLine();
                   System.out.println(StoredMessageManager.searchByMessageId(messageId));
                   break;
                   
               case "d" :
                   System.out.println("Enter recipient Number:");
                   String recipient = userInput.nextLine();
                   System.out.println(StoredMessageManager.searchByRecipient(recipient));
                   break;
                   
               case "e" :
                   System.out.print("Enter message hash to delete:");
                   String messageHash = userInput.nextLine();
                   boolean deleted = StoredMessageManager.deleteByMessageHash(messageHash);
                   System.out.println(deleted ? "Message deleted successfully.": "messageHash not found.");
                   break;
                   
               case "f" :
                   System.out.println(StoredMessageManager.getDisplayFullReport());
                   break;
                   
               case "g" :
                   inStoredMenu = false;
                   break;
                   
               default:
                   System.out.println("Invalid option. ");   
           }
        }//end of while loop
    }//end of method
    


     //Main method for registration and login with user input.
    public static void main(String[] args) {//Start of main method

        StoredMessageManager.readFromJSON(); //load existing stored messages
        Scanner userInput = new Scanner(System.in);
                  
        // Create a new Login object
        Login user = new Login();
        
        System.out.println("===== USER REGISTRATION =====");
        
        // Get user details
        System.out.print("Enter username (must contain '_' and be <= 5 characters): ");
        String username = userInput.nextLine();
        user.setUsername(username);
        
        System.out.print("Enter password (min 8 chars, 1 capital, 1 number, 1 special char): ");
        String password = userInput.nextLine();
        user.setPassword(password);
        
        System.out.print("Enter cell phone number (format: + followed by 8-12 digits, e.g., +27831234567): ");
        String phone = userInput.nextLine();
        user.setCellPhoneNumber(phone);
        
        System.out.print("Enter first name: ");
        String firstName = userInput.nextLine();
        user.setFirstName(firstName);
        
        System.out.print("Enter last name: ");
        String lastName = userInput.nextLine();
        user.setLastName(lastName);
        
        // Attempt registration
        String registerMessage = user.registerUser();
        System.out.println("\n" + registerMessage);
        
        // If registration successful, proceed to login
        if (!registerMessage.equals("User successfully registered!")) {

            System.out.println("Registration failed.Exiting.");
            userInput.close();
            return;
        }

        // Login process
            System.out.println("===== USER LOGIN =====");
            System.out.print("Enter username: ");
            String loginUsername = userInput.nextLine();
            System.out.print("Enter password: ");
            String loginPassword = userInput.nextLine();
            
            System.out.println(user.returnLoginStatus(loginUsername, loginPassword));
            if (user.loginUser(loginUsername, loginPassword)) {//start of if statement
                mainMenu(userInput);
            } else {
                System.out.println("Login failed. Exiting.");
            }//end of if statement
            
            userInput.close();
        }//end of main method    
    }//end of first class 

