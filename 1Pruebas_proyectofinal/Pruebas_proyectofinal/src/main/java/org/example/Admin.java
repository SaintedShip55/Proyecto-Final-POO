package org.example;
public class Admin extends User {
    private String password;
    private int loginAttempts;

    public Admin(String username, String password) {
        super(username);
        this.password = password;
        this.loginAttempts = 0;
    }

    public boolean authenticate(String enteredPassword) {
        if (password.equals(enteredPassword)) {
            loginAttempts = 0; // Reset login attempts on successful login
            return true;
        } else {
            loginAttempts++;
            return false;
        }
    }
    public int getLoginAttempts() {
        return loginAttempts;
    }
}