package bukc.project;

import java.io.*;
import java.util.*;
import static java.util.Collections.emptyList;

public class UserDataHandler {

    public static final String FILE_NAME = "users.ser";

    public static List<User> readUsers() {
        List<User> users = new ArrayList();
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                users = (List<User>) in.readObject();
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
        }
        return users;
    }

    // Save new user to the file
    public static void addUser(User newUser) {
        List<User> users = readUsers();
        users.add(newUser);
//        users(emptyList);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            out.writeObject(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Validate login
    public static boolean isValidUser(String username, String password) {
        List<User> users = readUsers();

        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return true;
            }
        }
        return false;
    }

    //For Email Check Exist or not 
    public static boolean isValidEmail(String email) {
        List<User> users = readUsers();

        for (User user : users) {
            if (user.email.equals(email)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isValidUsername(String username) {
        List<User> users = readUsers();

        for (User user : users) {
            if (user.username.equals(username)) {
                return true;
            }
        }

        return false;
    }

    public static String getUsernameByEmail(String email) {
        List<User> users = readUsers();

        for (User user : users) {
            if (user.email.equals(email)) {
                return user.username;
            }
        }
        return null;
    }

    public static String getEmailByUsername(String username) {
        List<User> users = readUsers();

        for (User user : users) {
            if (user.username.equals(username)) {
                return user.email;
            }
        }
        return null;
    }

    public static void changePass(String email, String password) {
        List<User> users = readUsers();
        boolean updated = false;

        for (User user : users) {
            if (user.email.equals(email)) {
                user.password = password;
                updated = true;
                break;
            }
        }

        if (updated) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
                out.writeObject(users);
                out.close(); // Important to close stream
//                System.out.println("Password updated successfully.");
            } catch (IOException e) {
                System.out.println("Error while updating password: " + e.getMessage());
            }
        }
    }

    public static boolean isPassCorrectByEmail(String email, String password) {
        List<User> users = readUsers();

        for (User user : users) {
            if (user.email.equals(email)) {
                return user.password.equals(password);
            }
        }

        return false;
    }

    public static boolean isPassCorrectByUser(String username, String password) {
        List<User> users = readUsers();

        for (User user : users) {
            if (user.username.equals(username)) {
                return user.password.equals(password);
            }
        }

        return false;
    }

    public static void displayUsers() {

        List<User> users = new ArrayList();
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                users = (List<User>) in.readObject();
                for (User user : users) {
                    System.out.println("\nUsername = " + user.username);
                    System.out.println("Email = " + user.email);
                    System.out.println("Password = " + user.password);
                }
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
        }
    }

    public static void deleteAllUsers() {

        List<User> users = new ArrayList();
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                users = (List<User>) in.readObject();
                in.close();

                users.clear();

                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                out.writeObject(users);
                out.close();

                System.out.println("All users have been deleted.");
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
        }
    }

    public static void deleteUser(String email) {
        List<User> users = readUsers(); // Read existing users
        boolean removed = false;

        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.email.equals(email)) {
                iterator.remove(); // Remove the matching user
                removed = true;
                break;
            }
        }

        if (removed) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
                out.writeObject(users);
                out.close();
//            System.out.println("User deleted successfully.");
            } catch (IOException e) {
                System.out.println("Error while deleting user: " + e.getMessage());
            }
        }
    }

}
