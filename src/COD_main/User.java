/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package COD_main;

/**
 *
 * @author Petru Botnar
 */
//user class definition
//this class is used as base class for the users of the system
public class User {
    //definition of class fields
    private int user_id;
    private String username;
    private String password;
    private String type;
    private boolean isLogged = false;
    
    //user contructor
    public User(int user_id, String username, String password, String type) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.type = type;
    }
    //user constructor without type field
    public User(int user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIsLogged() {
        return isLogged;
    }

    public void setIsLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

}
