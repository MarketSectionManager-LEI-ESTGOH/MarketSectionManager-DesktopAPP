package View;

import Controller.User;
import Model.ConnectDB;

public class CmdLnMainTest {

    public static void main(String[] args) {
        ConnectDB.loadProperties();

        User u = new User();

        if(u.checkLogin(99, "root")){
            System.out.println("Sucesso");
        }else{
            System.out.println("Falhou");
        }
    }



}
