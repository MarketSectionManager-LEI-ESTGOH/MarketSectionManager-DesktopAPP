package View;

import Model.ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Main {

    public static void main(String[] args) {
        //ISTO É APENAS UM TESTE AO FICHEIRO DAS PROPRIEDADES
        ConnectDB c = new ConnectDB();

        c.loadProperties();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(c.getUrl(), c.getProperties());
            PreparedStatement ps = conn.prepareStatement("SELECT nome FROM user WHERE nome = ?");
            ps.clearParameters();
            ps.setString(1, "John Doe");

            if(c.checkString(ps, "John Doe")){
                System.out.println("yes");
            }else{
                System.out.println("no");
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

    }

}
