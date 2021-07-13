package Model;

import com.mysql.jdbc.exceptions.MySQLDataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
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

    /**
     * Obtem a conexão
     * @return Connection
     */
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
        }
        return value;
    }

    /**
     * Obter Utilizador
     * @param aPs PreparedStatement com a Query para Base de Dados
     * @return String
     */
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
                return  true;
            }else{
                System.out.println("**Ocorreu um erro a Eliminar da Tabela**");
                return false;
            }

        } catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException swe){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("ERRO");
           alert.setHeaderText("Não é possível remoer este registo!");
           alert.setContentText("Este registo está a ser utilizado por outras partes do programa!\nPara fins de Histórico é necessário a preservação deste registo, pelo que o mesmo não pode ser eliminado!\nPara informação adicional contacte os devidos serviços de apoio informático!");
           alert.showAndWait();
        }catch (SQLException e) {
            System.out.println("!! SQL Exception !!\n"+e);
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("!! Class Not Found. Unable to load Database Drive !!\n" + e);
            return false;
        }
        return  false;
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

        String stmt = "Select id, tipo, nome, num_interno, email from user WHERE nome != 'ARDUINO'";
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
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("!! Class Not Found. Unable to load Database Drive !!\n"+e);
            return false;

        } catch (IllegalAccessException e) {
            System.out.println("!! Illegal Access !!\n"+e);
            return false;

        } catch (InstantiationException e) {
            System.out.println("!! Class Not Instanciaded !!\n"+e);
            return false;

        }
        return false;
    }

    /**
     * função para obter lista com todas limpezas a aprovar
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

    /**
     * função para obter lista com todas limpezas
     * @return
     */
    public static ObservableList<Limpeza> getAllLimp(){
        String userData = "";
        ResultSet rs = null;

        ObservableList<Limpeza> list = FXCollections.observableArrayList();

        String stmt = "SELECT limpeza.id, area.designacao, componentes.designacao as 'componente', data, user.nome, user.num_interno, assinatura FROM limpeza LEFT JOIN area_componentes on limpeza.area_componentes_id = area_componentes.id LEFT JOIN area on area.id = area_componentes.area_id LEFT JOIN componentes on componentes.id = area_componentes.componentes_id left join user on limpeza.user_id = user.id";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new Limpeza(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));
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
     * função para obter lista com todas rastreabilidades a validar
     * @return
     */
    public static ObservableList<Rastreabilidade> getAllRastToVal(){
        String userData = "";
        ResultSet rs = null;

        ObservableList<Rastreabilidade> list = FXCollections.observableArrayList();

        String stmt = "SELECT rastreabilidade.id, lote, d_t_entrada, origem, produto.nome, user.nome, fornecedor.nome FROM rastreabilidade LEFT JOIN produto on rastreabilidade.produto_id = produto.id LEFT JOIN user on rastreabilidade.user_id = user.id LEFT JOIN fornecedor on rastreabilidade.fornecedor_id = fornecedor.id WHERE assinado_user IS NULL";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new Rastreabilidade(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
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
     * função para obter lista com todas rastreabilidades a validar
     * @return
     */
    public static ObservableList<Rastreabilidade> getAllRast(){
        String userData = "";
        ResultSet rs = null;

        ObservableList<Rastreabilidade> list = FXCollections.observableArrayList();

        String stmt = "SELECT rastreabilidade.id, lote, d_t_entrada, origem, produto.nome, user.nome, fornecedor.nome, assinado_user FROM rastreabilidade LEFT JOIN produto on rastreabilidade.produto_id = produto.id LEFT JOIN user on rastreabilidade.user_id = user.id LEFT JOIN fornecedor on rastreabilidade.fornecedor_id = fornecedor.id";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new Rastreabilidade(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8)));
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
     * função para obter lista temperaturas a validar
     * @return
     */
    public static ObservableList<Temperatura> getAllTempToVal(){
        String userData = "";
        ResultSet rs = null;

        ObservableList<Temperatura> list = FXCollections.observableArrayList();

        String stmt = "SELECT temperatura.id, area_frigorifica.designacao, temperatura, data_hora, user.nome FROM `temperatura` LEFT JOIN area_frigorifica ON temperatura.area_frigorifica_id = area_frigorifica.numero LEFT JOIN user on temperatura.user_id = user.id WHERE assinado IS NULL";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new Temperatura(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDate(4), rs.getString(5)));
            }
        } catch (MySQLDataException e) {
            System.out.println("!! Class Not Instanciaded !!\n"+e);
            return null;

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
     * função para obter lista com todas temperaturas
     * @return
     */
    public static ObservableList<Temperatura> getAllTemp(){
        String userData = "";
        ResultSet rs = null;

        ObservableList<Temperatura> list = FXCollections.observableArrayList();

        String stmt = "SELECT temperatura.id, area_frigorifica.designacao, temperatura, data_hora, user.nome, assinado FROM `temperatura` LEFT JOIN area_frigorifica ON temperatura.area_frigorifica_id = area_frigorifica.numero LEFT JOIN user on temperatura.user_id = user.id";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new Temperatura(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDate(4), rs.getString(5), rs.getInt(6)));
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
     * função para obter lista com todos os utilizadores
     * @return
     */
    public static ObservableList<Componente> getAllComponents(){
        String userData = "";
        ResultSet rs = null;

        ObservableList<Componente> list = FXCollections.observableArrayList();

        String stmt = "SELECT id,designacao FROM componentes;";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new Componente(rs.getInt(1),rs.getString(2)));
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
     * Obter ID de uma área Controlada
     * @param aPs PreparedStatement com a Query para Base de Dados
     * @return Int
     */
    public static int getControlledAreaID(PreparedStatement aPs){
        int areaID = -1;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            rs = aPs.executeQuery();
            while(rs.next()){
                areaID = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("!! SQL Exception !!\n"+e);
            e.printStackTrace();
            return -1;
        } catch (ClassNotFoundException e) {
            System.out.println("!! Class Not Found. Unable to load Database Drive !!\n"+e);
            return -1;
        } catch (IllegalAccessException e) {
            System.out.println("!! Illegal Access !!\n"+e);
            return -1;
        } catch (InstantiationException e) {
            System.out.println("!! Class Not Instanciaded !!\n"+e);
            return -1;
        } finally {
            if (conn != null) {
                try {
                    return areaID;
                } catch (Exception e) {
                    System.out.println("!! Exception closing DB connection !!\n"+e);
                    return -1;
                }
            }
        } // end of finally
        return -1;
    }

    /**
     * Obter Componentes de uma áerea Controlada
     * @param aPs PreparedStatement com a Query para Base de Dados
     * @return String
     */
    public static String getControlledAreaComponents(PreparedStatement aPs){
        String comps = "";
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            rs = aPs.executeQuery();
            while(rs.next()){
                comps += " - " + rs.getString(1) + ";\n";
            }
        } catch (SQLException e) {
            System.out.println("!! SQL Exception !!\n"+e);
            e.printStackTrace();
            return "Sem Componenentes";
        } catch (ClassNotFoundException e) {
            System.out.println("!! Class Not Found. Unable to load Database Drive !!\n"+e);
            return "Sem Componenentes";
        } catch (IllegalAccessException e) {
            System.out.println("!! Illegal Access !!\n"+e);
            return "Sem Componenentes";
        } catch (InstantiationException e) {
            System.out.println("!! Class Not Instanciaded !!\n"+e);
            return "Sem Componenentes";
        }
        return comps;
    }

    /**
     * função para obter lista com todos os componentes de uma Area
     * @return
     */
    public static ObservableList<Componente> getCompFromArea(int aID){
        String userData = "";
        ResultSet rs = null;

        ObservableList<Componente> list = FXCollections.observableArrayList();

        String stmt = "SELECT area_componentes.componentes_id, componentes.designacao from area_componentes LEFT JOIN componentes ON componentes_id = componentes.id WHERE area_id = ?";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);
            aPs.setInt(1, aID);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new Componente(rs.getInt(1), rs.getString(2)));
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
     * função para obter lista com todos os componentes de uma Area
     * @return
     */
    public static ObservableList<Componente> getCompDispArea(int aID){
        String userData = "";
        ResultSet rs = null;

        ObservableList<Componente> list = FXCollections.observableArrayList();

        String stmt = "SELECT * FROM componentes WHERE NOT EXISTS (SELECT area_componentes.componentes_id from area_componentes WHERE area_componentes.componentes_id = componentes.id AND area_id = ?)";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);
            aPs.setInt(1, aID);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new Componente(rs.getInt(1), rs.getString(2)));
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
     * Obter IDs e Designações de Áreas Frigoríficas
     * @return ObservableList de Strings
     */
    public static ObservableList<String> getRefrigeratorsIDandDesign(){
        ObservableList <String> list = FXCollections.observableArrayList();
        ResultSet rs = null;
        String stmt = "SELECT numero, designacao FROM area_frigorifica;";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);
            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(rs.getInt(1) + " - " + rs.getString(2));
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
        }
        return list;
    }

    /**
     * Obter Dados para o gráfico do canto superior esquerdo da Dashboard
     * @param aPs PreparedStatement com a Query para Base de Dados
     * @return Float
     */
    public static float getTempsGrapgh1(PreparedStatement aPs){
        float temp = -9999;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            rs = aPs.executeQuery();
            while(rs.next()){
                temp = rs.getFloat(1);
            }
        } catch (SQLException e) {
            System.out.println("!! SQL Exception !!\n"+e);
            e.printStackTrace();
            return -9999;
        } catch (ClassNotFoundException e) {
            System.out.println("!! Class Not Found. Unable to load Database Drive !!\n"+e);
            return -9999;
        } catch (IllegalAccessException e) {
            System.out.println("!! Illegal Access !!\n"+e);
            return -9999;
        } catch (InstantiationException e) {
            System.out.println("!! Class Not Instanciaded !!\n"+e);
            return -9999;
        }
        return temp;
    }

    /**
     * Obter dados para o gráfico do canto superior direito da Dashboard
     * @param aID ID da Área Frogorífica
     * @param aDate Data da temperatiura que se quer obter
     * @return ArrayList de floats
     */
    public static ArrayList<Float> getDispersion(int aID, String aDate){
        ArrayList <Float> list = new ArrayList<>();
        ResultSet rs = null;
        String stmt = "SELECT temperatura FROM temperatura WHERE data_hora LIKE '%" + aDate + "%' AND area_frigorifica_id =" + aID + ";";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);
            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(rs.getFloat(1));
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
        }
        return list;
    }

    /**
     * Obter dados para as tabelas da Dashboard
     * @param aPs PreparedStatement com a Query para Base de Dados
     * @return Int
     */
    public static int getTableData(PreparedStatement aPs){
        int receivedCounter = -1;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            rs = aPs.executeQuery();
            while(rs.next()){
                receivedCounter= rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("!! SQL Exception !!\n"+e);
            e.printStackTrace();
            return -1;
        } catch (ClassNotFoundException e) {
            System.out.println("!! Class Not Found. Unable to load Database Drive !!\n"+e);
            return -1;
        } catch (IllegalAccessException e) {
            System.out.println("!! Illegal Access !!\n"+e);
            return -1;
        } catch (InstantiationException e) {
            System.out.println("!! Class Not Instanciaded !!\n"+e);
            return -1;
        }
        return receivedCounter;
    }

    /**
     * Obter todos os registos de validade da base de dados
     * @return ObservableList de ExpirationDates
     */
    public static ObservableList<ExprirationDate> getAllExpirationDates(){
        String userData = "";
        ResultSet rs = null;

        ObservableList<ExprirationDate> list = FXCollections.observableArrayList();

        String stmt = "Select ean, n_interno, nome, markdown, validade, offset from validade";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            if(conn == null){
                conn = DriverManager.getConnection(properties.getProperty("url"), getProperties());
            }
            PreparedStatement aPs = ConnectDB.getConn().prepareStatement(stmt);

            rs = aPs.executeQuery();
            while(rs.next()){
                list.add(new ExprirationDate(
                        rs.getString("ean"),
                        String.valueOf(rs.getInt("n_interno")),
                        rs.getString("nome"),
                        rs.getInt("markdown"),
                        rs.getDate("validade"),
                        rs.getInt("offset")));
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
