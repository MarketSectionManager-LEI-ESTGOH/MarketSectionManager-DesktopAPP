package Controller;

import Model.ConnectDB;
import Model.Encryption;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private String username, email, userTypeConv;
    private int userID, userType;

    public User(){

    }

    public User(String aUsername, String aEmail, int aUserID, int aUserType){
        username = aUsername;
        email = aEmail;
        userID = aUserID;
        userType = aUserType;
        switch (userType){
            case 0:
                userTypeConv = "Funcionário";
                break;
            case 1:
                userTypeConv = "Administrador";
                break;
            default:
                userTypeConv = "NDF";
                break;
        }
    }

    public int getUserID() {
        return userID;
    }

    public int getUserType() {
        return userType;
    }

    public String getUserTypeConv() { return userTypeConv;}

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

    public static boolean removeUserFromDB(User toRemove){
        try {
            String stmt = "DELETE FROM user WHERE num_interno = ?";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, toRemove.getUserID());
            return ConnectDB.removeFromDB(ps);
        }catch (Exception e){

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

    /**
     *Check if email exists in DB
     * true = exists, false = is free
     * @param aEmail
     * @return
     */
    public static boolean checkEmailExists(String aEmail){
        try {
            String stmt = "SELECT email FROM user WHERE email = ?";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setString(1, aEmail);
            return ConnectDB.checkString(ps, aEmail);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    /**
     * Check if emails is correct
     * https://howtodoinjava.com/java/regex/java-regex-validate-email-address/
     * @param aEmail
     * @return
     */
    public static boolean checkEmail(String aEmail){
        String regex = "^(.+)@(.+)[\\w]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(aEmail);
        return matcher.matches();
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
