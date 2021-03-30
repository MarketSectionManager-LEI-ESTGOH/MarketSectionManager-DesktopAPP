package Model;

import java.io.*;
import java.sql.*;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectDB {

    static Properties properties = new Properties();
    static File file = new File("dbconn.dat");

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

    /**
     * função para comparar Strings
     * @param aPs  PreparedStatement para selecionar os dados a serem comparados
     * @param toCheck  string a comparar
     * @return  true se strings forem iguais / false se strings forem diferentes ou ocorrer erros
     */
    public static boolean checkString(PreparedStatement aPs, String toCheck){
        Connection conn = null;
        ResultSet rs = null;
        boolean found = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            rs = aPs.executeQuery();
            while(rs.next()){
                String value = rs.getString(1);
                if(value.equals(toCheck)){
                    found = true;
                }
            }
            conn.close();
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
                    conn.close();
                    return found;
                } catch (Exception e) {
                    System.out.println("!! Exception closing DB connection !!\n"+e);
                    return false;
                }
            }
        } // end of finally
        return false;
    }

}
