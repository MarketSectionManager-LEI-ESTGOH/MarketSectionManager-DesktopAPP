package Controller;

import Model.ConnectDB;
import Model.Encryption;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {

    private String username, email;
    private int userID, userType;

    public User(){

    }

    public User(String aUsername, String aEmail, int aUserID, int aUserType){
        username = aUsername;
        email = aEmail;
        userID = aUserID;
        userType = aUserType;
    }

    public int getUserID() {
        return userID;
    }

    public int getUserType() {
        return userType;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Regista um novo utilizador apenas para teste.
     * @param aType
     * @param aUsername
     * @param aNumInt
     * @param aPassword
     * @param aEmail
     * @return
     */
    public boolean registerUser(int aType, String aUsername, int aNumInt, String aPassword, String aEmail){
        aPassword = Encryption.encrypt(aPassword);
        try {
            String stmt = "INSERT INTO user (tipo, nome, num_interno, password, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, aType);
            ps.setString(2, aUsername);
            ps.setInt(3, aNumInt);
            ps.setString(4, aPassword);
            ps.setString(5, aEmail);
            return ConnectDB.insertIntoTable(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * Verifica o login do utilizador.
     * @param aNumInt
     * @param aPassword
     * @return
     */
    public static boolean checkLogin(int aNumInt, String aPassword){
        aPassword = Encryption.encrypt(aPassword);
        try {
            String stmt = "SELECT password FROM user WHERE num_interno = ?";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, aNumInt);
            return ConnectDB.checkString(ps, aPassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static User createObjUser(int aUserID){
        ConnectDB.loadProperties();
        try{
            String stmt = "SELECT tipo, nome, email FROM user WHERE num_interno = ?";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, aUserID);
            String data = ConnectDB.getUser(ps);
            String dataSplit[] = data.split(":");
            User u = new User(dataSplit[1], dataSplit[2], aUserID, Integer.parseInt(dataSplit[0]));
            return u;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", userID=" + userID +
                ", userType=" + userType +
                '}';
    }
}
