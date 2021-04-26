package Model;

import Controller.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectDB {

    static Properties properties = new Properties();
    static File file = new File("dbconn.dat");
    static Connection conn;

    /**
     * função para carregar as propriedades a partir de um ficheiro, caso não exista cria o ficheiro com propriedades
     * predefinidas
     */
    public static void loadProperties() {
        if (file.exists()) {
            try (InputStream input = new FileInputStream(file)){
                properties.load(input);

                properties.getProperty("url");
                properties.setProperty("user", Encryption.decryptDBProperties(properties.getProperty("user")));
                properties.setProperty("password", Encryption.decryptDBProperties(properties.getProperty("password")));
                properties.getProperty("autoReconnect");
                properties.getProperty("useSSL");

                try {
                    conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } catch ( IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * função para obter as propriedades definidas para a BD
     * @return  as propriedades
     */
    public static Properties getProperties() {
        return properties;
    }

    /**
     * Obtem o url da conexão.
     * @return String com url da conexão
     */
    public static String getUrl(){ return properties.getProperty("url");}

    public static Connection getConn() { return conn; }

    /**
     * função para comparar Strings
     * @param aPs  PreparedStatement para selecionar os dados a serem comparados
     * @param toCheck  string a comparar
     * @return  true se strings forem iguais / false se strings forem diferentes ou ocorrer erros
     */
    public static boolean checkString(PreparedStatement aPs, String toCheck){
        ResultSet rs = null;
        boolean found = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            rs = aPs.executeQuery();
            while(rs.next()){
                String value = rs.getString(1);
                if(value.equals(toCheck)){
                    found = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("!! SQL Exception !!\n"+e);
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("!! Class Not Found. Unable to load Database Drive !!\n"+e);
            return false;
        } finally {
            if (conn != null) {
                try {
                    return found;
                } catch (Exception e) {
                    System.out.println("!! Exception closing DB connection !!\n"+e);
                    return false;
                }
            }
        } // end of finally
        return false;
    }

    /**
     * função para obter valor String da BD
     * @param aPs  PreparedStatement para obter o valor
     * @return  retorna a string ou null se ocorrerem erros
     */
    public static String getString(PreparedStatement aPs){
        ResultSet rs = null;
        String value = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            rs = aPs.executeQuery();
            while(rs.next()){
                value = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println("!! SQL Exception !!\n"+e);
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("!! Class Not Found. Unable to load Database Drive !!\n"+e);
            return null;
        } catch (IllegalAccessException e) {
            System.out.println("!! Illegal Access !!\n"+e);
            return null;
        } catch (InstantiationException e) {
            System.out.println("!! Class Not Instanciaded !!\n"+e);
            return null;
        } finally {
            if (conn != null) {
                try {
                    return value;
                } catch (Exception e) {
                    System.out.println("!! Exception closing DB connection !!\n"+e);
                    return null;
                }
            }
        } // end of finally
        return null;
    }

    public static String getUser(PreparedStatement aPs){
        String userData = "";
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            rs = aPs.executeQuery();
            while(rs.next()){
                userData = rs.getInt("tipo")+":"+rs.getString("nome")+":"+rs.getString("email");
            }
        } catch (SQLException e) {
            System.out.println("!! SQL Exception !!\n"+e);
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("!! Class Not Found. Unable to load Database Drive !!\n"+e);
            return null;
        } catch (IllegalAccessException e) {
            System.out.println("!! Illegal Access !!\n"+e);
            return null;
        } catch (InstantiationException e) {
            System.out.println("!! Class Not Instanciaded !!\n"+e);
            return null;
        } finally {
            if (conn != null) {
                try {
                    return userData;
                } catch (Exception e) {
                    System.out.println("!! Exception closing DB connection !!\n"+e);
                    return null;
                }
            }
        } // end of finally
        return null;
    }

    /**
     * função para inserção de dados na BD
     * @param aStatement  PreparedStatement a executar
     * @return  true se ocorreu com sucesso / false se decorreram erros
     */
    public static boolean insertIntoTable(PreparedStatement aStatement){

        try {
            Class.forName("com.mysql.jdbc.Driver");

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            int result = aStatement.executeUpdate();
            if(result == 1){
                System.out.println("**Inserido na Tabela com Sucesso**");
            }else{
                System.out.println("**Ocorreu um erro a Inserir na Tabela**");
            }

        } catch (SQLException e) {
            System.out.println("!! SQL Exception !!\n"+e);
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("!! Class Not Found. Unable to load Database Drive !!\n"+e);
            return false;
        } finally {
            if (conn != null) {
                try {
                    return true;
                } catch (Exception e) {
                    System.out.println("!! Exception closing DB connection !!\n"+e);
                    return false;
                }
            }
        } // end of finally
        return false;
    }

    public static ObservableList<User> getAllUsers(){
        String userData = "";
        ResultSet rs = null;

        ObservableList<User> list = FXCollections.observableArrayList();

        String stmt = "Select id, tipo, nome, num_interno, email from user";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new User( rs.getString("nome"), rs.getString("email"), rs.getInt("num_interno"), rs.getInt("tipo")));
            }
        } catch (SQLException e) {
            System.out.println("!! SQL Exception !!\n"+e);
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("!! Class Not Found. Unable to load Database Drive !!\n"+e);
            return null;

        } catch (IllegalAccessException e) {
            System.out.println("!! Illegal Access !!\n"+e);
            return null;

        } catch (InstantiationException e) {
            System.out.println("!! Class Not Instanciaded !!\n"+e);
            return null;

        } finally {
            if (conn != null) {
                try {
                    return list;
                } catch (Exception e) {
                    System.out.println("!! Exception closing DB connection !!\n"+e);
                    return null;
                }
            }
        } // end of finally
        return null;

    }

}
