package Model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private String username, email, userTypeConv;
    private int userID, userType;

    /**
     * Construtor Vazio da Classe
     */
    public User(){}

    /**
     * Construtor da Classe
     * @param aUsername Nome do Utilizador
     * @param aEmail Email do Utilizador
     * @param aUserID ID do Utilizador
     * @param aUserType Tipo do Utilizador
     */
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

    /**
     * Obter o ID do Utilizador
     * @return Int
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Obter o tipo do Utilizador
     * @return Int
     */
    public int getUserType() {
        return userType;
    }

    /**
     * Obter Tipo do Utilizador (String)
     * @return String
     */
    public String getUserTypeConv() { return userTypeConv;}

    /**
     * Obter o Email do Utilizador
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obter o Nome do Utilizador
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Regista um novo utilizador apenas para teste.
     * @param aType Tipo do Utilizador
     * @param aUsername Nome do Utilizador
     * @param aNumInt Número Interno do Utilizador
     * @param aPassword Password do Utilizador
     * @param aEmail Email do Utilizador
     * @return True/False
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
     * @param aNumInt Múmero Interno do Utilizador
     * @param aPassword Password do Utilizador
     * @return True/False
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

    /**
     * Remove Utilizador da Base de Dados
     * @param toRemove Objeto do Tipo User que irá ser removido
     * @return True/False
     */
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

    /**
     * Cria um objeto do tipo User a partir de dados da Base de Dados
     * @param aUserID ID do Utilizador
     * @return User
     */
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
     * @param aEmail Email do Utilizador
     * @return True/False
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
     * @param aEmail Email do Utilizador
     * @return True/False
     */
    public static boolean checkEmail(String aEmail){
        String regex = "^(.+)@(.+)[\\w]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(aEmail);
        return matcher.matches();
    }

    /**
     * Definir Nome do Utilizador
     * @param username String - Nome do Utilizador
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Definir Email do Utilizador
     * @param email String - Email do Utilizador
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    /**
     * Imprime a Classe no Ecrã
     */
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", userID=" + userID +
                ", userType=" + userType +
                '}';
    }
}
