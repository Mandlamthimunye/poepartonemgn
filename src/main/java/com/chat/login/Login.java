package com.chat.login;

import java.util.Scanner;  // Import for Scanner 

/**
 * Login class for user registration and authentication
 * @author Mnadla Mthimunye(ST10527946)
 * @version 1.0
 */
public class Login {//first class for login 
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;
    
    
    public Login() {//default constructor
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
    
    public boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        // Check if username contains underscore and length <= 5
        return username.contains("_") && username.length() <= 5;
    }
    
    
     // Validates password complexity:
     //At least 8 characters long
     // Contains a capital letter
    //Contains a number
    //Contains a special character
    
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        // Check each detail of the  password
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
    
    
     // Validates cell phone number format:
     //  Contains international country code (+ followed by numbers)
     //  Total length should be appropriate (code + up to 10 digits)
    public boolean checkCellPhoneNumber(String phoneNumber) {//check cellphone number class
        if (phoneNumber == null) {
            return false;
        }
        
        // Regular expression to validate international phone number
        // Pattern: starts with +, followed by 8-12 digits (total length 9-13 characters)
        
        String phoneRegex = "^\\+[0-9]{8,12}$";
        
        return phoneNumber.matches(phoneRegex);
    }
    
    
    //Registers a new user with validation checks
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
    
    
    // Verifies login credentials
    
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername) && 
               this.password.equals(enteredPassword);
    }
    
    //login status result
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
    
    /**
     * Main method to demonstrate registration and login with user input.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        
        // A new Login object
        Login user = new Login();
        
        System.out.println("===== User Registration =====");
        
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
        System.out.println("\n" + user.registerUser());
        
        // If registration is successful, proceed to login
        if (user.registerUser().equals("User successfully registered!")) {
            System.out.println("\n===== User Login =====");
            System.out.print("Enter username: ");
            String loginUsername = userInput.nextLine();
            System.out.print("Enter password: ");
            String loginPassword = userInput.nextLine();
            
            System.out.println(user.returnLoginStatus(loginUsername, loginPassword));
        }
        
        userInput.close();
    }
}
