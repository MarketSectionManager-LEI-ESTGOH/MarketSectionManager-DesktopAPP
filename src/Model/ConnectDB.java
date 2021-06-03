package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
import java.sql.DriverManager;
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

    /**
     * função para inserção de dados na BD
     * @param aStatement  PreparedStatement a executar
     * @return  true se ocorreu com sucesso / false se decorreram erros
     */
    public static boolean removeFromDB(PreparedStatement aStatement){

        try {
            Class.forName("com.mysql.jdbc.Driver");

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            int result = aStatement.executeUpdate();
            if(result == 1){
                System.out.println("**Eliminado da Tabela com Sucesso**");
            }else{
                System.out.println("**Ocorreu um erro a Eliminar da Tabela**");
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

    /**
     * função para atualizar dados na BD
     * @param aStatement  PreparedStatement a executar
     * @return  true se ocorreu com sucesso / false se decorreram erros
     */
    public static boolean updateDB(PreparedStatement aStatement){

        try {
            Class.forName("com.mysql.jdbc.Driver");

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            int result = aStatement.executeUpdate();
            if(result == 1){
                System.out.println("**Atualizado na Tabela com Sucesso**");
                return true;
            }else{
                System.out.println("**Ocorreu um erro a Atualizar na Tabela**");
                return false;
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
    }

    /**
     * função para obter lista com todos os utilizadores
     * @return
     */
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

    /**
     * função para obter lista com todos os produtos
     * @return
     */
    public static ObservableList<Product> getAllProducts(){
        String userData = "";
        ResultSet rs = null;

        ObservableList<Product> list = FXCollections.observableArrayList();

        String stmt = "Select n_interno, nome, fresco, preco, venda, ean, marca from produto";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new Product(rs.getInt("n_interno"), rs.getString("nome"), rs.getInt("fresco"), rs.getFloat("preco"), rs.getInt("venda"), rs.getString("ean"), rs.getString("marca")));
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

    /**
     * função para obter lista com todos as arcas
     * @return
     */
    public static ObservableList<ArcaFrigorifica> getAllArcas(){
        String userData = "";
        ResultSet rs = null;

        ObservableList<ArcaFrigorifica> list = FXCollections.observableArrayList();

        String stmt = "Select numero, designacao, fabricante, d_t_adicao, tem_min, tem_max, d_t_limpeza, user_limpeza, user.nome from area_frigorifica LEFT JOIN user ON user_limpeza = user.num_interno;";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new ArcaFrigorifica(rs.getInt("numero"), rs.getString("designacao"), rs.getString("fabricante"), rs.getDate("d_t_adicao"), rs.getFloat("tem_min"), rs.getFloat("tem_max"), rs.getDate("d_t_limpeza"), rs.getBigDecimal("user_limpeza"), rs.getString("user.nome")));
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

    /**
     * função para obter lista com todos os fornecedores
     * @return
     */
    public static ObservableList<Fornecedor> getAllFornecedores(){
        String userData = "";
        ResultSet rs = null;

        ObservableList<Fornecedor> list = FXCollections.observableArrayList();

        String stmt = "Select identificador, nome, contacto, email, morada from fornecedor;";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new Fornecedor(rs.getString("identificador"), rs.getString("nome"), rs.getInt("contacto"), rs.getString("email"), rs.getString("morada")));
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

    /**
     * função para obter lista com todos os areas controladas
     * @return
     */
    public static ObservableList<Area> getAllAreasCont(){
        String userData = "";
        ResultSet rs = null;

        ObservableList<Area> list = FXCollections.observableArrayList();

        String stmt = "Select numero, designacao from area;";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new Area(rs.getInt("numero"), rs.getString("designacao")));
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


    /**
     * função para overificar se determinado número interno existe
     * @return
     */
    public static boolean checkProductNumIt(int aNumInt){
        ResultSet rs = null;
        String stmt = "Select n_interno from produto";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);

            rs = aPs.executeQuery();
            while(rs.next()){
                if(rs.getInt("n_interno") == aNumInt){
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("!! SQL Exception !!\n"+e);
            e.printStackTrace();
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("!! Class Not Found. Unable to load Database Drive !!\n"+e);
            return true;

        } catch (IllegalAccessException e) {
            System.out.println("!! Illegal Access !!\n"+e);
            return true;

        } catch (InstantiationException e) {
            System.out.println("!! Class Not Instanciaded !!\n"+e);
            return true;

        }
        return false;
    }

    /**
     * função para obter lista com todos os utilizadores
     * @return
     */
    public static ObservableList<Limpeza> getAllLimpToVal(){
        String userData = "";
        ResultSet rs = null;

        ObservableList<Limpeza> list = FXCollections.observableArrayList();

        String stmt = "SELECT limpeza.id, area.designacao, componentes.designacao as 'componente', data, user.nome, user.num_interno FROM limpeza LEFT JOIN area_componentes on limpeza.area_componentes_id = area_componentes.id LEFT JOIN area on area.id = area_componentes.area_id LEFT JOIN componentes on componentes.id = area_componentes.componentes_id left join user on limpeza.user_id = user.id WHERE limpeza.assinatura IS NULL and limpeza.data_assinatura IS NULL";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new Limpeza(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getInt(6)));
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
