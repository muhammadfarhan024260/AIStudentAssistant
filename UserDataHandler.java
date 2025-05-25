package bukc.project;

import java.io.*;
import java.util.*;

public class UserDataHandler {
    public static final String FILE_NAME = "users.ser";
    
    public static List<User> readUsers(){
        List<User> users = new ArrayList();
        File file = new File(FILE_NAME); 
        
        if(file.exists()){
            try{
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                users = (List<User>) in.readObject();
            }
            catch(Exception e){
                System.out.println("Error : " + e.getMessage());
            }
        }
        return users;
    }
    
    // Save new user to the file
    public static void addUser(User newUser) {
        List<User> users  = readUsers();
        users.add(newUser);
        
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            out.writeObject(users);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    // Validate login
    public static boolean isValidUser(String username, String password) {
        List<User> users = readUsers();
        
        for(User user : users){
            if(user.username.equals(username) && user.password.equals(password)){
                return true;
            }
        }
        return false;
    }
    
    public static void printDatabase(){
        List<User> users = readUsers();
        
        for(User user : users){
            System.out.println("\n---User---");
            System.out.println("Name : " + user.username);
            System.out.println("Email : " + user.email);
            System.out.println("password : " + user.password);
        }
    }
    
    
    //For Email Check Exist or not 
    
    public static boolean isValidEmail(String email){
        List<User> users = readUsers();

            for(User user : users){            
                if(user.email.equals(email)){
                    return true;
                }
            }
           
            return false;
    }
    
    public static boolean validateUsername(String username){
        List<User> users = readUsers();

            for(User user : users){            
                if(user.username.equals(username)){
                    return true;
                }
            }
            return false;
    }
    
    public static boolean validatePassword(String username, String password){
        List<User> users = readUsers();

            for(User user : users){            
                if(user.username.equals(username) || user.email.equals(username)){
                    if(user.password.equals(password)){
                        return true;
                    }
                    
                }
            }
            return false;
    }    
                                         
    public static void deleteAllUsers() {
    List<User> emptyList = new ArrayList<>();
    try {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
        out.writeObject(emptyList);
        out.close();
        System.out.println("All users have been deleted.");
    } catch (IOException e) {
        System.out.println("Error while deleting: " + e.getMessage());
    }
}

    // public static void deleteAllUsers() {
//    List<User> emptyList = new ArrayList<>();
//    try {
//        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
//        out.writeObject(emptyList);
//        out.close();
//        System.out.println("All users have been deleted.");
//    } catch (IOException e) {
//        System.out.println("Error while deleting: " + e.getMessage());
//    }
//}

    
}
